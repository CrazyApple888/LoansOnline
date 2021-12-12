package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import me.isachenko.loansonline.R
import me.isachenko.loansonline.databinding.FragmentRegistrationBinding
import me.isachenko.loansonline.presentation.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding get() = _binding!!

    private lateinit var nameError: String
    private lateinit var passwordError: String
    private lateinit var repeatPasswordError: String

    private val viewModel: RegistrationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)

        initTextListeners()
        initErrorMessages()

        viewModel.registrationResult.observe(this) { res ->
            if (res) {
                Toast.makeText(context, getString(R.string.success_message), Toast.LENGTH_SHORT)
                    .show()
                navigateBack()
            } else Toast.makeText(context, getString(R.string.failure_message), Toast.LENGTH_SHORT)
                .show()
        }
        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotBlank()) Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }

        binding.registerButton.setOnClickListener {
            viewModel.register(
                binding.nameEditText.text.toString(),
                binding.passwordEditText.text.toString()
            )
        }

        return binding.root
    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    private fun initErrorMessages() {
        nameError = getString(R.string.nameError)
        passwordError = getString(R.string.passwordError)
        repeatPasswordError = getString(R.string.repeatPasswordError)

    }

    private fun initTextListeners() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.registerButton.isEnabled = viewModel.isAllCorrect()
            }
        }
        with(binding) {
            nameEditText.addTextChangedListener {
                viewModel.name = nameEditText.text.toString()
                nameInputLayout.error = if (viewModel.isNameCorrect()) null
                else nameError
            }
            passwordEditText.addTextChangedListener {
                viewModel.password = passwordEditText.text.toString()
                passwordInputLayout.error = if (viewModel.isPasswordCorrect()) null
                else passwordError
            }
            repeatPasswordEditText.addTextChangedListener {
                viewModel.repeatPassword = repeatPasswordEditText.text.toString()
                repeatPasswordInputLayout.error = if (viewModel.arePasswordSame()) null
                else repeatPasswordError
            }

            nameEditText.addTextChangedListener(textWatcher)
            passwordEditText.addTextChangedListener(textWatcher)
            repeatPasswordEditText.addTextChangedListener(textWatcher)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
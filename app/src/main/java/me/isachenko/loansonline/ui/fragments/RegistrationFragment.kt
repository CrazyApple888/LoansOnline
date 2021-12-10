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
import androidx.navigation.fragment.findNavController
import me.isachenko.loansonline.R
import me.isachenko.loansonline.databinding.FragmentRegistrationBinding
import me.isachenko.loansonline.presentation.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment : Fragment() {

    //todo: null this reference
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
            //todo
            if (res) {
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                navigateBack()
            }
            else Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show()
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
        findNavController().popBackStack()
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
                binding.registerButton.isEnabled = checkAll()
            }
        }
        with(binding) {
            nameEditText.addTextChangedListener { checkName() }
            passwordEditText.addTextChangedListener { checkPassword() }
            repeatPasswordEditText.addTextChangedListener { checkRepeatPassword() }

            nameEditText.addTextChangedListener(textWatcher)
            passwordEditText.addTextChangedListener(textWatcher)
            repeatPasswordEditText.addTextChangedListener(textWatcher)
        }
    }

    private fun checkName() =
        with(binding) {
            nameInputLayout.error =
                if (viewModel.isNameCorrect(nameEditText.text.toString())) {
                    null
                } else {
                    nameError
                }
        }

    private fun checkPassword() =
        with(binding) {
            passwordInputLayout.error =
                if (viewModel.isPasswordCorrect(passwordEditText.text.toString())) {
                    null
                } else {
                    passwordError
                }
        }

    private fun checkRepeatPassword() =
        with(binding) {
            repeatPasswordInputLayout.error =
                if (viewModel.arePasswordSame(
                        passwordEditText.text.toString(),
                        repeatPasswordEditText.text.toString()
                    )
                ) {
                    null
                } else {
                    repeatPasswordError
                }
        }

    private fun checkAll(): Boolean =
        with(binding) {
            viewModel.isNameCorrect(nameEditText.text.toString()) && viewModel.isPasswordCorrect(
                passwordEditText.text.toString()
            ) && viewModel.arePasswordSame(
                passwordEditText.text.toString(),
                repeatPasswordEditText.text.toString()
            )
        }

}
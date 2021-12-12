package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import me.isachenko.loansonline.R
import me.isachenko.loansonline.databinding.FragmentLoginBinding
import me.isachenko.loansonline.presentation.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.registrationSuggest.setOnClickListener { navigateToRegistration() }
        binding.loginButton.setOnClickListener { viewModel.login() }
        binding.nameEditText.addTextChangedListener {
            viewModel.name = binding.nameEditText.text.toString()
        }
        binding.passwordEditText.addTextChangedListener {
            viewModel.password = binding.passwordEditText.text.toString()
        }
        viewModel.isLoginSuccessful.observe(this) { isSuccessful ->
            if (isSuccessful) navigateToHomeScreen()
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun navigateToRegistration() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, RegistrationFragment())
            .addToBackStack("Registration")
            .commit()
    }

    private fun navigateToHomeScreen() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, HomeScreenFragment())
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
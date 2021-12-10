package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import me.isachenko.loansonline.R
import me.isachenko.loansonline.databinding.FragmentLoginBinding
import me.isachenko.loansonline.presentation.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    //todo null reference
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

        binding.loginButton.setOnClickListener { viewModel.login() }
        binding.nameEditText.addTextChangedListener {
            viewModel.name = binding.nameEditText.text.toString()
        }
        binding.passwordEditText.addTextChangedListener {
            viewModel.password = binding.passwordEditText.text.toString()
        }
        viewModel.errorMessage.observe(this) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.registrationSuggest.setOnClickListener { navigateToRegistration() }
        viewModel.isLoginSuccessful.observe(this) { isSuccessful ->
            if (isSuccessful) navigateToHomeScreen()
        }
    }

    private fun navigateToRegistration() {
        findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
    }

    private fun navigateToHomeScreen() {
        findNavController().navigate(R.id.action_loginFragment_to_bottom_navigation2)
    }
}
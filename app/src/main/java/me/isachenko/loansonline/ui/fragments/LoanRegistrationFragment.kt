package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.isachenko.loansonline.presentation.LoanRegistrationViewModel
import me.isachenko.loansonline.databinding.LoanRegistrationFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoanRegistrationFragment : Fragment() {

    private var _binding: LoanRegistrationFragmentBinding? = null
    private val binding: LoanRegistrationFragmentBinding get() = _binding!!

    private val viewModel: LoanRegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoanRegistrationFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

}
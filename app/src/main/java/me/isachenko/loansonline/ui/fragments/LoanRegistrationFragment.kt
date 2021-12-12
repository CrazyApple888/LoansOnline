package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import me.isachenko.loansonline.presentation.LoanRegistrationViewModel
import me.isachenko.loansonline.databinding.LoanRegistrationFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.core.widget.addTextChangedListener

class LoanRegistrationFragment : Fragment() {

    private var _binding: LoanRegistrationFragmentBinding? = null
    private val binding: LoanRegistrationFragmentBinding get() = _binding!!

    private val viewModel: LoanRegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoanRegistrationFragmentBinding.inflate(inflater, container, false)

        initListeners()
        viewModel.loadLoanConditions()

        return binding.root
    }

    private fun initListeners() {
        with(binding) {
            close.setOnClickListener { navigateBack() }
            nameEditText.addTextChangedListener {
                viewModel.firstName = nameEditText.text.toString()
            }
            surnameEditText.addTextChangedListener {
                viewModel.lastName = surnameEditText.text.toString()
            }
            phoneEditText.addTextChangedListener {
                viewModel.phoneNumber = phoneEditText.text.toString()
            }
            amountSlider.addOnChangeListener { slider, value, _ ->
                viewModel.amount = value.toInt()
                loanAmountText.text = value.toInt().toString()
                slider.value = viewModel.amount.toFloat()
            }
            createLoanButton.setOnClickListener { viewModel.createLoan() }
        }

        viewModel.loanConditions.observe(this) {
            with(binding) {
                periodText.text = it.period.toString()
                percentText.text = it.percent.toString()
                amountSlider.valueFrom = 0F
                amountSlider.valueTo = it.maxAmount.toFloat()
            }
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotBlank()) Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }

        viewModel.isSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                //todo show success message
                navigateBack()
            } else {
                //todo show failure message
                navigateBack()
            }
        }

    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

}
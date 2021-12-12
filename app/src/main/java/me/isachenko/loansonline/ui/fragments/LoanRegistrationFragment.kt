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
import me.isachenko.loansonline.databinding.FragmentLoanRegistrationBinding
import me.isachenko.loansonline.presentation.LoanRegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoanRegistrationFragment : Fragment() {

    private var _binding: FragmentLoanRegistrationBinding? = null
    private val binding: FragmentLoanRegistrationBinding get() = _binding!!

    private val viewModel: LoanRegistrationViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoanRegistrationBinding.inflate(inflater, container, false)

        initListeners()
        viewModel.loadLoanConditions()

        return binding.root
    }

    private fun initListeners() {
        initTextWatcher()
        with(binding) {
            close.setOnClickListener { navigateBack() }
            nameEditText.addTextChangedListener {
                viewModel.firstName = nameEditText.text.toString()
                nameInputLayout.error = if (viewModel.isFirstNameCorrect()) null
                else getString(R.string.nameError)
            }
            surnameEditText.addTextChangedListener {
                viewModel.lastName = surnameEditText.text.toString()
                surnameInputLayout.error = if (viewModel.isLastNameCorrect()) null
                else getString(R.string.nameError)
            }
            phoneEditText.addTextChangedListener {
                viewModel.phoneNumber = phoneEditText.text.toString()
                phoneInputLayout.error = if (viewModel.isPhoneCorrect()) null
                else getString(R.string.phoneError)
            }
            amountSlider.addOnChangeListener { slider, value, _ ->
                viewModel.amount = value.toInt()
                loanAmountText.text = value.toInt().toString()
                slider.value = viewModel.amount.toFloat()
                binding.createLoanButton.isEnabled = viewModel.isAllCorrect()
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
                Toast.makeText(context, getString(R.string.success_message), Toast.LENGTH_SHORT)
                    .show()
                navigateBack()
            } else {
                Toast.makeText(context, getString(R.string.failure_message), Toast.LENGTH_SHORT)
                    .show()
                navigateBack()
            }
        }

    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    private fun initTextWatcher() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.createLoanButton.isEnabled = viewModel.isAllCorrect()
            }
        }

        with(binding) {
            nameEditText.addTextChangedListener(textWatcher)
            surnameEditText.addTextChangedListener(textWatcher)
            phoneEditText.addTextChangedListener(textWatcher)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
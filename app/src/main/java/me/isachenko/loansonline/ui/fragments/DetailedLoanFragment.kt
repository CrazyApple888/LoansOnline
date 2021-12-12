package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import me.isachenko.loansonline.R
import me.isachenko.loansonline.databinding.FragmentDetailedLoanBinding
import me.isachenko.loansonline.domain.entity.State
import me.isachenko.loansonline.presentation.DetailedLoanViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailedLoanFragment : Fragment() {

    companion object {
        const val loanIdKey = "LOANID"
    }

    private var _binding: FragmentDetailedLoanBinding? = null
    private val binding: FragmentDetailedLoanBinding get() = _binding!!

    private var loanId: Int? = null
    private val viewModel: DetailedLoanViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedLoanBinding.inflate(inflater, container, false)
        if (savedInstanceState == null) {
            arguments?.let {
                loanId = it.getInt(loanIdKey)
            }
        } else {
            loanId = savedInstanceState.getInt(loanIdKey)
        }
        setIsGoneForTextViews(true)
        binding.progressCircular.isGone = false
        binding.close.setOnClickListener { navigateBack() }


        loanId?.let { viewModel.getLoanInfo(it) }
        viewModel.loanInfo.observe(this) { loan ->
            setIsGoneForTextViews(false)
            with(binding) {
                progressCircular.isGone = true
                firstnameText.text = loan.firstName
                lastnameText.text = loan.lastName
                phoneText.text = loan.phoneNumber
                dateText.text = loan.date
                periodText.text = loan.period.toString()
                percentText.text = loan.percent.toString()
                amountText.text = loan.amount.toString()
                when (loan.state) {
                    State.APPROVED -> {
                        statusText.text = getString(R.string.APPROVED)
                        getLoanInfo.text = getString(R.string.get_loan_instruction)
                    }
                    State.REGISTERED -> statusText.text = getString(R.string.REGISTERED)
                    State.REJECTED -> statusText.text = getString(R.string.REJECTED)
                }
            }
        }
        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotBlank()) Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        loanId?.let {
            outState.putInt(loanIdKey, it)
        }
    }

    private fun navigateBack() {
        parentFragmentManager.popBackStack()
    }

    private fun setIsGoneForTextViews(isGone: Boolean) {
        with(binding) {
            firstName.isGone = isGone
            lastName.isGone = isGone
            phoneNumber.isGone = isGone
            date.isGone = isGone
            period.isGone = isGone
            percent.isGone = isGone
            amount.isGone = isGone
            status.isGone = isGone
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
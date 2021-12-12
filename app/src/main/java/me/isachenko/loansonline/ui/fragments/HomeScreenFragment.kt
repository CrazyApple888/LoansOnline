package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import me.isachenko.loansonline.R
import me.isachenko.loansonline.databinding.FragmentHomeScreenBinding
import me.isachenko.loansonline.presentation.HomeScreenViewModel
import me.isachenko.loansonline.ui.adapter.LoansAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding: FragmentHomeScreenBinding get() = _binding!!

    private val adapter: LoansAdapter by inject()
    private val viewModel: HomeScreenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        binding.loansRecyclerView.adapter = adapter
        binding.newLoanButton.setOnClickListener { navigateToLoanRegistration() }

        //todo add progress bar on loading
        viewModel.loans.observe(this) { loans ->
            if (loans.isEmpty()) {
                binding.emptyHistoryText.isGone = false
                binding.loansRecyclerView.isGone = true
            } else {
                binding.emptyHistoryText.isGone = true
                binding.loansRecyclerView.isGone = false
                adapter.loans = loans
            }
        }

        viewModel.getLoansList()

        return binding.root
    }

    private fun navigateToLoanRegistration() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, LoanRegistrationFragment())
            .addToBackStack(null)
            .commit()
    }
}
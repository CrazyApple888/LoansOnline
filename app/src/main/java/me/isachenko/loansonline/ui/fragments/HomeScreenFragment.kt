package me.isachenko.loansonline.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import me.isachenko.loansonline.R
import me.isachenko.loansonline.databinding.FragmentHomeScreenBinding
import me.isachenko.loansonline.presentation.HomeScreenViewModel
import me.isachenko.loansonline.ui.adapter.LoansAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding: FragmentHomeScreenBinding get() = _binding!!

    private val adapter =
        LoansAdapter { loanId -> navigateToDetailedLoanScreen(loanId) }
    private val viewModel: HomeScreenViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        binding.loansRecyclerView.adapter = adapter
        binding.newLoanButton.setOnClickListener { navigateToLoanRegistration() }
        binding.logOut.setOnClickListener {
            viewModel.logout()
            navigateToLoginScreen()
        }

        binding.progress.isGone = false
        viewModel.loans.observe(this) { loans ->
            if (loans.isEmpty()) {
                binding.emptyHistoryText.isGone = false
                binding.loansRecyclerView.isGone = true
            } else {
                binding.emptyHistoryText.isGone = true
                binding.loansRecyclerView.isGone = false
                adapter.loans = loans
            }
            binding.progress.isGone = true
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error.isNotBlank()) Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
        viewModel.getLoansList()

        return binding.root
    }

    private fun navigateToLoginScreen() {
        AlertDialog.Builder(context)
            .setMessage(getString(R.string.logout_message))
            .setPositiveButton(
                getString(R.string.yes)
            ) { dialog, _ ->
                dialog.dismiss()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_activity_fragment_container, LoginFragment())
                    .commit()
            }
            .setNegativeButton(
                getString(R.string.no)
            ) { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun navigateToLoanRegistration() {
        parentFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_from_right,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out_from_left
            )
            .replace(R.id.main_activity_fragment_container, LoanRegistrationFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun navigateToDetailedLoanScreen(loanId: Int) {
        val bundle = Bundle()
        bundle.putInt(DetailedLoanFragment.loanIdKey, loanId)
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.main_activity_fragment_container,
                DetailedLoanFragment::class.java,
                bundle
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
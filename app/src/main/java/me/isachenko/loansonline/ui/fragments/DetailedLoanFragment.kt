package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.isachenko.loansonline.databinding.FragmentDetailedLoanBinding

class DetailedLoanFragment : Fragment() {

    companion object {
        const val loanIdKey = "LOANID"
    }

    private var _binding: FragmentDetailedLoanBinding? = null
    private val binding: FragmentDetailedLoanBinding get() = _binding!!

    private var loanId: Int? = null

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

        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        loanId?.let {
            outState.putInt(loanIdKey, it)
        }
    }

}
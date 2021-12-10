package me.isachenko.loansonline.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import me.isachenko.loansonline.databinding.FragmentHomeScreenBinding
import androidx.navigation.ui.NavigationUI
import me.isachenko.loansonline.R


class HomeScreenFragment : Fragment() {

    private var _binding: FragmentHomeScreenBinding? = null
    private val binding: FragmentHomeScreenBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navFragment = childFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNav, navController)
    }
}
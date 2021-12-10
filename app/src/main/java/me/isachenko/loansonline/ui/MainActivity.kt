package me.isachenko.loansonline.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import me.isachenko.loansonline.R
import me.isachenko.loansonline.presentation.MainViewModel
import me.isachenko.loansonline.ui.fragments.HomeScreenFragment
import me.isachenko.loansonline.ui.fragments.LoginFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (null == savedInstanceState) {
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.main_activity_fragment_container) as NavHostFragment
            val navController = navHostFragment.navController
            if (viewModel.checkIfKeyContains()) {
                navigateToHomeScreen(navController)
            } else {
                navigateToLogin(navController)
            }
        }
    }

    private fun navigateToHomeScreen(navController: NavController) {
        navController.navigate(R.id.bottom_navigation)
    }

    private fun navigateToLogin(navController: NavController) {
        navController.navigate(R.id.loginFragment)
    }
}
package me.isachenko.loansonline.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        if (viewModel.checkIfKeyContains()) {
            navigateToHomeScreen()
        } else {
            navigateToLogin()
        }
    }

    private fun navigateToHomeScreen() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, HomeScreenFragment())
            .commit()
    }

    private fun navigateToLogin() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, LoginFragment())
            .commit()
    }
}
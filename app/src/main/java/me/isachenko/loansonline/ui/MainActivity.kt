package me.isachenko.loansonline.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.isachenko.loansonline.R
import me.isachenko.loansonline.ui.fragments.RegistrationFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_activity_fragment_container, RegistrationFragment())
            .commit()

    }
}
package me.isachenko.loansonline.di

import android.content.Context
import android.content.SharedPreferences
import me.isachenko.loansonline.R
import me.isachenko.loansonline.data.network.retrofit.AuthenticationService
import me.isachenko.loansonline.data.network.retrofit.LoansEndPoint
import me.isachenko.loansonline.data.repository.UserRepositoryImpl
import me.isachenko.loansonline.domain.usecases.RegisterUserUseCase
import me.isachenko.loansonline.domain.repository.UserRepository
import me.isachenko.loansonline.domain.usecases.LoginUseCase
import me.isachenko.loansonline.domain.usecases.ValidateNameUseCase
import me.isachenko.loansonline.domain.usecases.ValidatePasswordUseCase
import me.isachenko.loansonline.presentation.LoginViewModel
import me.isachenko.loansonline.presentation.RegistrationViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.binds
import org.koin.dsl.module

val appModule = module {
    factory { ValidateNameUseCase() }
    factory { ValidatePasswordUseCase() }
    factory {
        RegisterUserUseCase(
            UserRepositoryImpl(
                provideSharedPreferences(androidContext()),
                provideAuthService()
            )
        )
    } binds arrayOf(UserRepository::class)
    factory {
        LoginUseCase(
            UserRepositoryImpl(
                provideSharedPreferences(androidContext()),
                provideAuthService()
            )
        )
    }
    viewModel { RegistrationViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), provideLoginErrorMessage(androidContext())) }
}

private fun provideAuthService(): AuthenticationService {
    return LoansEndPoint().retrofit.create(AuthenticationService::class.java)
}

private fun provideSharedPreferences(context: Context): SharedPreferences {
    val sharedPrefsName = context.getString(R.string.sharedPrefsName)
    return context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
}

private fun provideLoginErrorMessage(context: Context): String {
    return context.getString(R.string.loginError)
}
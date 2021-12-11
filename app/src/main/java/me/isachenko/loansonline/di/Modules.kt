package me.isachenko.loansonline.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import me.isachenko.loansonline.R
import me.isachenko.loansonline.data.network.retrofit.AuthenticationService
import me.isachenko.loansonline.data.network.retrofit.LoansEndPoint
import me.isachenko.loansonline.data.network.retrofit.LoansService
import me.isachenko.loansonline.data.repository.KeyRepositoryImpl
import me.isachenko.loansonline.data.repository.LoansRepositoryImpl
import me.isachenko.loansonline.data.repository.UserRepositoryImpl
import me.isachenko.loansonline.domain.repository.KeyRepository
import me.isachenko.loansonline.domain.repository.UserRepository
import me.isachenko.loansonline.domain.KeyOperationsInteractor
import me.isachenko.loansonline.domain.repository.LoansRepository
import me.isachenko.loansonline.domain.usecases.*
import me.isachenko.loansonline.presentation.HomeScreenViewModel
import me.isachenko.loansonline.presentation.LoginViewModel
import me.isachenko.loansonline.presentation.MainViewModel
import me.isachenko.loansonline.presentation.RegistrationViewModel
import me.isachenko.loansonline.ui.LoansAdapter
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {
    factory { ValidateNameUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { RegisterUserUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetLoansListUseCase(get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            provideSharedPreferences(androidContext()),
            provideAuthService(),
            provideApiKeyStorageName(androidContext())
        )
    }
    single<KeyRepository> {
        KeyRepositoryImpl(
            provideSharedPreferences(androidContext()),
            provideApiKeyStorageName(androidContext())
        )
    }
    single<LoansRepository> { LoansRepositoryImpl(provideLoansService(androidContext())) }

    factory { KeyOperationsInteractor(get()) }

    viewModel { RegistrationViewModel(get(), get(), get()) }
    viewModel { LoginViewModel(get(), provideLoginErrorMessage(androidContext()), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }

    factory { provideLoansAdapter(androidContext()) }
}

private fun provideLoansAdapter(context: Context): LoansAdapter {
    return LoansAdapter(
        context.getString(R.string.amountTemplate),
        context.getString(R.string.percentTemplate),
        context.getString(R.string.dateTemplate),
        context.getString(R.string.periodTemplate),
        R.drawable.ic_loan_approved,
        R.drawable.ic_loan_registered,
        R.drawable.ic_loan_rejected
    )
}

private fun provideAuthService(): AuthenticationService {
    //todo move LoansEndPoint to DI
    return LoansEndPoint().retrofit.create(AuthenticationService::class.java)
}

private fun provideLoansService(context: Context): LoansService {
    return createRetrofitWithToken(context).create(LoansService::class.java)
}

private fun provideSharedPreferences(context: Context): SharedPreferences {
    val sharedPrefsName = context.getString(R.string.sharedPrefsName)
    return context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE)
}

private fun provideLoginErrorMessage(context: Context): String {
    return context.getString(R.string.loginError)
}

private fun provideApiKeyStorageName(context: Context): String =
    context.getString(R.string.ApiKeyStorageName)

private fun createRetrofitWithToken(context: Context): Retrofit {
    val sharedPrefsName = context.getString(R.string.sharedPrefsName)
    val token = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE).getString(
        provideApiKeyStorageName(context),
        ""
    )
    val okHttp = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val authRequest = chain.request().newBuilder()
                .addHeader("Authorization", token!!)
                .build()

            chain.proceed(authRequest)
        }
        .build()

    return Retrofit.Builder()
        .baseUrl("https://focusstart.appspot.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(okHttp)
        .build()
}
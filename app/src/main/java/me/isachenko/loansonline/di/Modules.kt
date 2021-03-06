package me.isachenko.loansonline.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import me.isachenko.loansonline.R
import me.isachenko.loansonline.data.model.ErrorMessageStore
import me.isachenko.loansonline.data.network.retrofit.AuthenticationService
import me.isachenko.loansonline.data.network.retrofit.LoansService
import me.isachenko.loansonline.data.repository.KeyRepositoryImpl
import me.isachenko.loansonline.data.repository.LoansRepositoryImpl
import me.isachenko.loansonline.data.repository.UserRepositoryImpl
import me.isachenko.loansonline.domain.repository.KeyRepository
import me.isachenko.loansonline.domain.repository.UserRepository
import me.isachenko.loansonline.domain.KeyOperationsInteractor
import me.isachenko.loansonline.domain.repository.LoansRepository
import me.isachenko.loansonline.domain.usecases.*
import me.isachenko.loansonline.presentation.*
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

val appModule = module {
    factory { ValidateNameUseCase() }
    factory { ValidatePhoneUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { RegisterUserUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetLoansListUseCase(get()) }
    factory { GetLoanConditionsUseCase(get()) }
    factory { CreateLoanUseCase(get()) }
    factory { GetLoanInfoUseCase(get()) }

    factory<UserRepository> {
        UserRepositoryImpl(
            provideSharedPreferences(androidContext()),
            provideAuthService(),
            get(),
            provideApiKeyStorageName(androidContext())
        )
    }
    factory<KeyRepository> {
        KeyRepositoryImpl(
            provideSharedPreferences(androidContext()),
            provideApiKeyStorageName(androidContext())
        )
    }
    factory<LoansRepository> { LoansRepositoryImpl(provideLoansService(androidContext()), get()) }

    factory { KeyOperationsInteractor(get()) }
    factory { provideErrorMessageStore(androidContext()) }

    viewModel {
        RegistrationViewModel(
            get(),
            get(),
            get(),
            provideConnectionErrorMessage(androidContext())
        )
    }
    viewModel { LoginViewModel(get(), provideLoginErrorMessage(androidContext()), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { HomeScreenViewModel(get(), provideConnectionErrorMessage(androidContext()), get()) }
    viewModel {
        LoanRegistrationViewModel(
            get(),
            get(),
            get(),
            get(),
            provideConnectionErrorMessage(androidContext())
        )
    }
    viewModel { DetailedLoanViewModel(get(), provideConnectionErrorMessage(androidContext())) }

    single(named("approvedImageId")) { R.drawable.ic_loan_approved }
    single(named("registeredImageId")) { R.drawable.ic_loan_registered }
    single(named("rejectedImageId")) { R.drawable.ic_loan_rejected }
    single(named("nameRegex")) { Regex(androidContext().getString(R.string.nameRegex)) }
}

private fun provideConnectionErrorMessage(context: Context): String =
    context.getString(R.string.connection_error)

private fun provideErrorMessageStore(context: Context): ErrorMessageStore =
    ErrorMessageStore(
        context.getString(R.string.e401),
        context.getString(R.string.e403),
        context.getString(R.string.e404),
    )

private fun provideAuthService(): AuthenticationService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://focusstart.appspot.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()

    return retrofit.create(AuthenticationService::class.java)
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
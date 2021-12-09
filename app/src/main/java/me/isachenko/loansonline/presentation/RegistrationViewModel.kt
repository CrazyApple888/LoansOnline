package me.isachenko.loansonline.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.usecases.RegisterUserUseCase
import me.isachenko.loansonline.domain.usecases.ValidateNameUseCase
import me.isachenko.loansonline.domain.usecases.ValidatePasswordUseCase

class RegistrationViewModel(
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { ctx, err ->
        //todo handle exception
        Log.i("ISACHTAG", "Got exception ${err.message}")
    }

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> get() = _registrationResult

    fun isPasswordCorrect(password: String): Boolean =
        validatePasswordUseCase(password)

    fun arePasswordSame(password: String, repeatPassword: String): Boolean =
        password == repeatPassword

    fun isNameCorrect(name: String): Boolean =
        validateNameUseCase(name)

    fun register(name: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            val result = registerUserUseCase.invoke(name, password)
            when (result) {
                is ApiResult.Success -> _registrationResult.value = true
                is ApiResult.Failure -> handleError(result.errorCode, result.message)
            }
        }
    }

    private fun handleError(errorCode: Int, message: String) {
        _registrationResult.value = false
        Log.i("ISACHTAG", "$errorCode $message")
        //todo
        //400 - user exists
    }

}
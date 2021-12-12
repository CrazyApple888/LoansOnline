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
    private val registerUserUseCase: RegisterUserUseCase,
    private val connectionErrorMessage: String
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, err ->
        Log.i("ISACHTAG", "Got exception ${err.message}")
        _errorMessage.value = connectionErrorMessage
    }

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _registrationResult = MutableLiveData<Boolean>()
    val registrationResult: LiveData<Boolean> get() = _registrationResult

    var name: String = ""
    var password: String = ""
    var repeatPassword: String = ""

    fun isPasswordCorrect(): Boolean =
        validatePasswordUseCase(password)

    fun arePasswordSame(): Boolean =
        password == repeatPassword

    fun isNameCorrect(): Boolean =
        validateNameUseCase(name)

    fun isAllCorrect(): Boolean =
        arePasswordSame() && isNameCorrect() && isPasswordCorrect()

    fun register(name: String, password: String) {
        viewModelScope.launch(exceptionHandler) {
            when (val result = registerUserUseCase.invoke(name, password)) {
                is ApiResult.Success -> _registrationResult.value = true
                is ApiResult.Failure -> _errorMessage.value = result.message
            }
        }
    }

}
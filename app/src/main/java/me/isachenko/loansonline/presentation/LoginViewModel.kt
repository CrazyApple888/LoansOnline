package me.isachenko.loansonline.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.isachenko.loansonline.domain.KeyOperationsInteractor
import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.usecases.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginErrorMessage: String,
    private val keyOperationsInteractor: KeyOperationsInteractor
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { ctx, err ->

        Log.i("ISACHTAG", "Got exception ${err.message}")
    }

    private val _isLoginSuccessful = MutableLiveData<Boolean>()
    val isLoginSuccessful: LiveData<Boolean> get() = _isLoginSuccessful

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    var name: String = ""
    var password: String = ""

    fun login() {
        viewModelScope.launch(exceptionHandler) {
            when(val result = loginUseCase(name, password)) {
                is ApiResult.Success -> {
                    _isLoginSuccessful.value = true
                    result.data?.also {
                        keyOperationsInteractor.saveKey(it)
                    }
                }
                is ApiResult.Failure -> handleError()
            }
        }
    }

    private fun handleError() {
        _isLoginSuccessful.value = false
        _errorMessage.value = loginErrorMessage
    }

}
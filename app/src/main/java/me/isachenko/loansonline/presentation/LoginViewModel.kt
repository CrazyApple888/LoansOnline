package me.isachenko.loansonline.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.isachenko.loansonline.domain.model.ApiResult
import me.isachenko.loansonline.domain.usecases.LoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val loginErrorMessage: String
) : ViewModel() {

    private val _isLoginSuccessful = MutableLiveData<Boolean>()
    val isLoginSuccessful: LiveData<Boolean> get() = _isLoginSuccessful

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    var name: String = ""
    var password: String = ""

    fun login() {
        viewModelScope.launch {
            val result = loginUseCase(name, password)
            when(result) {
                is ApiResult.Success -> _isLoginSuccessful.value = true
                is ApiResult.Failure -> handleError()
            }
        }
    }

    private fun handleError() {
        _isLoginSuccessful.value = false
        _errorMessage.value = loginErrorMessage
    }

}
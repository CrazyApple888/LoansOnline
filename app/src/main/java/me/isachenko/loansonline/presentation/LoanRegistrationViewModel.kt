package me.isachenko.loansonline.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.LoanConditions
import me.isachenko.loansonline.domain.usecases.CreateLoanUseCase
import me.isachenko.loansonline.domain.usecases.GetLoanConditionsUseCase

class LoanRegistrationViewModel(
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val createLoanUseCase: CreateLoanUseCase,
    private val connectionErrorMessage: String
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { ctx, err ->
        _errorMessage.value = connectionErrorMessage
        Log.i("ISACHTAG", "Got exception ${err.message}")
    }

    private val _loanConditions = MutableLiveData<LoanConditions>()
    val loanConditions: LiveData<LoanConditions> get() = _loanConditions

    var amount: Int = 0
    var firstName: String = ""
    var lastName: String = ""
    var phoneNumber: String = ""

    private val _errorMessage = MutableLiveData<String>("")
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean> get() = _isSuccess

    fun loadLoanConditions() {
        viewModelScope.launch(exceptionHandler) {
            when (val result = getLoanConditionsUseCase()) {
                is ApiResult.Success -> _loanConditions.value = result.data ?: return@launch
                is ApiResult.Failure -> _errorMessage.value = result.message
            }
        }
    }

    fun createLoan() {
        viewModelScope.launch(exceptionHandler) {
            val result = createLoanUseCase(
                amount,
                firstName,
                lastName,
                loanConditions.value!!.percent,
                loanConditions.value!!.period,
                phoneNumber
            )

            when (result) {
                is ApiResult.Success -> _isSuccess.value = true
                is ApiResult.Failure -> {
                    _isSuccess.value = false
                    _errorMessage.value = result.message
                }
            }
        }
    }

}
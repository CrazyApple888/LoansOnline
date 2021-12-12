package me.isachenko.loansonline.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.usecases.GetLoanInfoUseCase

class DetailedLoanViewModel(
    private val getLoanInfoUseCase: GetLoanInfoUseCase,
    private val connectionErrorMessage: String
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { ctx, err ->
        _errorMessage.value = connectionErrorMessage
        Log.i("ISACHTAG", "Got exception ${err.message}")
    }

    private val _errorMessage = MutableLiveData("")
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loanInfo = MutableLiveData<Loan>()
    val loanInfo: LiveData<Loan> get() = _loanInfo

    fun getLoanInfo(loanId: Int) {
        viewModelScope.launch(exceptionHandler) {
            when (val result = getLoanInfoUseCase(loanId)) {
                is ApiResult.Success -> result.data?.let { _loanInfo.value = it }
                is ApiResult.Failure -> _errorMessage.value = result.message
            }
        }
    }

}
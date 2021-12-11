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
import me.isachenko.loansonline.domain.usecases.GetLoansListUseCase

class HomeScreenViewModel(
    private val getLoansListUseCase: GetLoansListUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { ctx, err ->
        //todo handle exception
        Log.i("ISACHTAG", "Got exception ${err.message}")
    }

    private val _loans = MutableLiveData<List<Loan>>()
    val loans: LiveData<List<Loan>> get() = _loans

    fun getLoansList() {
        viewModelScope.launch(exceptionHandler) {
            when(val result = getLoansListUseCase()) {
                is ApiResult.Success -> _loans.value = result.data ?: emptyList()
                is ApiResult.Failure -> handleError(result.message, result.errorCode)
            }
        }
    }

    private fun handleError(error: String, code: Int) {
        //todo
    }

}
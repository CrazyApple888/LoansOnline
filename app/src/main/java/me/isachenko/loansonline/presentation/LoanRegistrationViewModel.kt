package me.isachenko.loansonline.presentation

import androidx.lifecycle.ViewModel
import me.isachenko.loansonline.domain.usecases.GetLoanConditionsUseCase

class LoanRegistrationViewModel(
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase
) : ViewModel() {

    fun loadLoanConditions() {

    }

}
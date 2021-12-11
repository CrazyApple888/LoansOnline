package me.isachenko.loansonline.domain.usecases

import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.LoanConditions
import me.isachenko.loansonline.domain.repository.LoansRepository

class GetLoanConditionsUseCase(
    private val repository: LoansRepository
) {
    suspend operator fun invoke(): ApiResult<LoanConditions> =
        repository.getLoanConditions()
}
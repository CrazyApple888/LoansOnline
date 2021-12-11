package me.isachenko.loansonline.domain.usecases

import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.repository.LoansRepository

class GetLoansListUseCase(
    private val repository: LoansRepository
) {

    suspend operator fun invoke(): ApiResult<List<Loan>> =
        repository.getLoansList()

}
package me.isachenko.loansonline.domain.usecases

import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.repository.LoansRepository

class CreateLoanUseCase(
    private val repository: LoansRepository
) {

    suspend operator fun invoke(
        amount: Int,
        firstName: String,
        lastName: String,
        percent: Double,
        period: Int,
        phoneNumber: String
    ): ApiResult<Loan> =
        repository.createNewLoan(
            amount,
            firstName,
            lastName,
            percent,
            period,
            phoneNumber
        )

}
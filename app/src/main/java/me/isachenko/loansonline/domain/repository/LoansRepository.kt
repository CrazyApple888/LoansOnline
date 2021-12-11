package me.isachenko.loansonline.domain.repository

import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.entity.LoanConditions

interface LoansRepository {

    suspend fun getLoansList(): ApiResult<List<Loan>>

    suspend fun getLoanConditions(): ApiResult<LoanConditions>

}
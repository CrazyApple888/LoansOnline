package me.isachenko.loansonline.domain.repository

import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.Loan

interface LoansRepository {

    suspend fun getLoansList(): ApiResult<List<Loan>>

}
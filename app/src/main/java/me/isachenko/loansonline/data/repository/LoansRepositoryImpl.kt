package me.isachenko.loansonline.data.repository

import me.isachenko.loansonline.data.model.responses.LoansListResponse
import me.isachenko.loansonline.data.network.retrofit.LoansService
import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.entity.State
import me.isachenko.loansonline.domain.repository.LoansRepository
import java.text.SimpleDateFormat
import java.util.*

class LoansRepositoryImpl(
    private val loansService: LoansService
) : LoansRepository {
    override suspend fun getLoansList(): ApiResult<List<Loan>> {
        val loans = loansService.getLoansList()
        return if (loans.isSuccessful) {
            ApiResult.Success(convertDataModelToDomain(loans.body()))
        } else {
            ApiResult.Failure(loans.code(), loans.message())
        }
    }

    private fun convertDataModelToDomain(response: LoansListResponse?): List<Loan>? {
        return response?.map {
            Loan(
                it.amount,
                modelDateToDomain(it.date) ?: "",
                it.firstName,
                it.id,
                it.lastName,
                it.percent,
                it.period,
                it.phoneNumber,
                modelStateToDomain(it.state)
            )
        }
    }

    private fun modelDateToDomain(date: String): String? {
        val responseFormatter =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val domainFormatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        return responseFormatter.parse(date)?.let {
            domainFormatter.format(it)
        }
    }

    private fun modelStateToDomain(state: String): State =
        when (state) {
            "APPROVED" -> State.APPROVED
            "REGISTERED" -> State.REGISTERED
            else -> State.REJECTED
        }
}
package me.isachenko.loansonline.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.isachenko.loansonline.data.model.ErrorMessageStore
import me.isachenko.loansonline.data.model.requests.LoanRequest
import me.isachenko.loansonline.data.model.responses.LoanDataResponse
import me.isachenko.loansonline.data.model.responses.LoansListResponse
import me.isachenko.loansonline.data.network.retrofit.LoansService
import me.isachenko.loansonline.domain.entity.*
import me.isachenko.loansonline.domain.repository.LoansRepository
import java.text.SimpleDateFormat
import java.util.*

class LoansRepositoryImpl(
    private val loansService: LoansService,
    private val errorMessageStore: ErrorMessageStore
) : LoansRepository {
    override suspend fun getLoansList(): ApiResult<List<Loan>> {
        val loans = withContext(Dispatchers.IO) {
            loansService.getLoansList()
        }
        return if (loans.isSuccessful) {
            ApiResult.Success(dataListModelToDomain(loans.body()))
        } else {
            ApiResult.Failure(loans.code(), errorMessageStore.getMessageForCode(loans.code()))
        }
    }

    override suspend fun getLoanConditions(): ApiResult<LoanConditions> {
        val result = withContext(Dispatchers.IO) {
            loansService.getLoansConditions()
        }
        return if (result.isSuccessful && result.body() != null) {
            ApiResult.Success(
                LoanConditions(
                    result.body()!!.percent,
                    result.body()!!.period,
                    result.body()!!.maxAmount
                )
            )
        } else {
            ApiResult.Failure(result.code(), errorMessageStore.getMessageForCode(result.code()))
        }
    }

    override suspend fun createNewLoan(
        amount: Int,
        firstName: String,
        lastName: String,
        percent: Double,
        period: Int,
        phoneNumber: String
    ): ApiResult<Loan> {
        val loanRequest = modelRequestToData(
            amount,
            firstName,
            lastName,
            percent,
            period,
            phoneNumber
        )
        val result = withContext(Dispatchers.IO) {
            loansService.createNewLoan(loanRequest)
        }

        return if (result.isSuccessful) {
            ApiResult.Success(result.body()?.let { dataResponseToDomainLoan(it) })
        } else {
            ApiResult.Failure(result.code(), errorMessageStore.getMessageForCode(result.code()))
        }
    }

    private fun modelRequestToData(
        amount: Int,
        firstName: String,
        lastName: String,
        percent: Double,
        period: Int,
        phoneNumber: String
    ): LoanRequest =
        LoanRequest(
            amount,
            firstName,
            lastName,
            percent,
            period,
            phoneNumber
        )


    private fun dataListModelToDomain(response: LoansListResponse?): List<Loan>? {
        return response?.map {
            dataResponseToDomainLoan(it)
        }
    }

    private fun dataResponseToDomainLoan(loan: LoanDataResponse): Loan =
        Loan(
            loan.amount,
            modelDateToDomain(loan.date) ?: "",
            loan.firstName,
            loan.id,
            loan.lastName,
            loan.percent,
            loan.period,
            loan.phoneNumber,
            modelStateToDomain(loan.state)
        )

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
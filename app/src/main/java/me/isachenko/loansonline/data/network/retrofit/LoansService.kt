package me.isachenko.loansonline.data.network.retrofit

import me.isachenko.loansonline.data.model.requests.LoanRequest
import me.isachenko.loansonline.data.model.responses.LoanConditionsResponse
import me.isachenko.loansonline.data.model.responses.LoanDataResponse
import me.isachenko.loansonline.data.model.responses.LoansListResponse
import retrofit2.Response
import retrofit2.http.*

interface LoansService {

    @POST("loans")
    suspend fun createNewLoan(
        @Header("Authorization") token: String,
        @Body loan: LoanRequest
    ): Response<LoanDataResponse>

    @GET("loans/{Id}")
    suspend fun getLoanData(
        @Header("Authorization") token: String,
        @Path("Id") id: Int
    ): Response<LoanDataResponse>

    @GET("loans/all")
    suspend fun getLoansList(@Header("Authorization") token: String): Response<LoansListResponse>

    @GET("loans/conditions")
    suspend fun getLoansConditions(@Header("Authorization") token: String): Response<LoanConditionsResponse>

}
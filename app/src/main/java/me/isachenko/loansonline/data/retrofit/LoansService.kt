package me.isachenko.loansonline.data.retrofit

import me.isachenko.loansonline.data.requests.UserRequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoansService {

    @POST("login")
    fun login(@Body user: UserRequestBody) : Response<String>

    @POST("register")
    fun register(@Body user: UserRequestBody) : Response<String>

}
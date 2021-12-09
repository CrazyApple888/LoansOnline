package me.isachenko.loansonline.data.network.retrofit

import me.isachenko.loansonline.data.model.requests.UserRequestBody
import me.isachenko.loansonline.data.model.responses.RegistrationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {

    @POST("login")
    suspend fun login(@Body user: UserRequestBody) : Response<String>

    @POST("registration")
    suspend fun register(@Body user: UserRequestBody) : Response<RegistrationResponse>

}
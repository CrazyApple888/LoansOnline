package me.isachenko.loansonline.data.repository

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.isachenko.loansonline.data.model.requests.UserRequestBody
import me.isachenko.loansonline.data.network.retrofit.AuthenticationService
import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.repository.UserRepository

class UserRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val authService: AuthenticationService,
    private val apiKeyPreferenceName: String
) : UserRepository {


    override suspend fun register(name: String, password: String): ApiResult {
        val request = UserRequestBody(name, password)
        val result = withContext(Dispatchers.IO) {
            authService.register(request)
        }
        return if (result.isSuccessful) {
            ApiResult.Success
        } else {
            ApiResult.Failure(result.code(), result.message())
        }
    }

    override suspend fun login(name: String, password: String): ApiResult {
        val user = UserRequestBody(name, password)
        val result = withContext(Dispatchers.IO) {
            authService.login(user)
        }
        return if (result.isSuccessful) {
            sharedPreferences.edit()
                .putString(apiKeyPreferenceName, result.body())
                .apply()
            ApiResult.Success
        } else {
            ApiResult.Failure(result.code(), result.message())
        }
    }
}
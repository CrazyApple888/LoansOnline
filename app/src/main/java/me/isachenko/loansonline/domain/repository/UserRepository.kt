package me.isachenko.loansonline.domain.repository

import me.isachenko.loansonline.domain.model.ApiResult

interface UserRepository {

    suspend fun register(name: String, password: String) : ApiResult

    suspend fun login(name: String, password: String) : ApiResult

    fun hasKey() : Boolean

}
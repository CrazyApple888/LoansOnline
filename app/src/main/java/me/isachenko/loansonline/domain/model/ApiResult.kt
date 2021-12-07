package me.isachenko.loansonline.domain.model

sealed interface ApiResult {
    object Success : ApiResult
    data class Failure(val errorCode: Int, val message: String) : ApiResult
}
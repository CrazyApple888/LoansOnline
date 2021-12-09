package me.isachenko.loansonline.domain.entity

sealed interface ApiResult<T> {
    data class Success<T>(val data: T? = null): ApiResult<T>
    data class Failure<T>(val errorCode: Int, val message: String, val data: T? = null) : ApiResult<T>
}
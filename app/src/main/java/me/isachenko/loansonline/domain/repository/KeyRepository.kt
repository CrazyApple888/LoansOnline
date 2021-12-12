package me.isachenko.loansonline.domain.repository

interface KeyRepository {

    fun hasKey() : Boolean

    fun getKey() : String?

    fun saveKey(key: String)

    fun deleteKey()
}
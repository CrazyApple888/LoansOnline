package me.isachenko.loansonline.domain

interface UserRepository {

    fun register(name: String, password: String)

}
package me.isachenko.loansonline.domain.entity

enum class State{
    APPROVED,
    REGISTERED,
    REJECTED
}

data class Loan(
    val amount: Int,
    val date: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val state: State
)
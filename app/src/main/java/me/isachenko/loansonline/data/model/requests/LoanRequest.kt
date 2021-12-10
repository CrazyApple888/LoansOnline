package me.isachenko.loansonline.data.model.requests

data class LoanRequest(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Int,
    val period: Int,
    val phoneNumber: String
)
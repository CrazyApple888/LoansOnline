package me.isachenko.loansonline.data.model.responses

data class LoanDataResponse(
    val amount: Int,
    val date: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val percent: Int,
    val period: Int,
    val phoneNumber: String,
    val state: String
)
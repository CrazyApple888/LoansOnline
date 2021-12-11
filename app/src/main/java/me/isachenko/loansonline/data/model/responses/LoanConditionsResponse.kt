package me.isachenko.loansonline.data.model.responses

data class LoanConditionsResponse(
    val maxAmount: Int,
    val percent: Double,
    val period: Int
)
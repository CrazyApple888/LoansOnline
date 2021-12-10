package me.isachenko.loansonline.data.model.responses

data class LoanConditionsResponse(
    val maxAmount: Int,
    val percent: Int,
    val period: Int
)
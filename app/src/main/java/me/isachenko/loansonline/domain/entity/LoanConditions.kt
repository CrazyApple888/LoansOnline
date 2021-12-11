package me.isachenko.loansonline.domain.entity

data class LoanConditions(
    val percent: Double,
    val period: Int,
    val maxAmount: Int
)

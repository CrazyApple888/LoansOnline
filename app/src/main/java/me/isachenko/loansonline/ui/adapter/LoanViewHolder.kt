package me.isachenko.loansonline.ui.adapter

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import me.isachenko.loansonline.databinding.ItemLoanBinding
import me.isachenko.loansonline.domain.entity.Loan

class LoanViewHolder(
    private val binding: ItemLoanBinding,
    private val amountTemplate: String,
    private val percentTemplate: String,
    private val dateTemplate: String,
    private val periodTemplate: String
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loan: Loan, @DrawableRes imageId: Int, imageColor: Int) {
        with(binding) {
            amountText.text = amountTemplate.format(loan.amount)
            percentText.text = percentTemplate.format(loan.percent)
            date.text = dateTemplate.format(loan.date)
            periodText.text = periodTemplate.format(loan.period)
            statusImage.setImageResource(imageId)
            statusImage.setColorFilter(imageColor)
        }
    }
}
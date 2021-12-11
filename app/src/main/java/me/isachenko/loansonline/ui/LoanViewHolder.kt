package me.isachenko.loansonline.ui

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

    //todo color for image
    fun bind(loan: Loan, @DrawableRes imageId: Int) {
        binding.amountText.text = amountTemplate.format(loan.amount)
        binding.percentText.text = percentTemplate.format(loan.percent)
        binding.date.text = dateTemplate.format(loan.date)
        binding.periodText.text = periodTemplate.format(loan.period)
        binding.statusImage.setImageResource(imageId)
    }
}
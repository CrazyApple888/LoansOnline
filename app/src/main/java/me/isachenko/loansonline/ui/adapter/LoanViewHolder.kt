package me.isachenko.loansonline.ui.adapter

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import me.isachenko.loansonline.databinding.ItemLoanBinding
import me.isachenko.loansonline.domain.entity.Loan

class LoanViewHolder(
    private val binding: ItemLoanBinding,
    val onClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loan: Loan, @DrawableRes imageId: Int, imageColor: Int) {
        with(binding) {
            amountValueText.text = loan.amount.toString()
            percentValueText.text = loan.percent.toString()
            dateValueText.text = loan.date
            periodValueText.text = loan.period.toString()
            statusImage.setImageResource(imageId)
            statusImage.setColorFilter(imageColor)
            card.setOnClickListener { onClick(loan.id) }
        }
    }
}
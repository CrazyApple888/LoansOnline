package me.isachenko.loansonline.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import me.isachenko.loansonline.databinding.ItemLoanBinding
import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.entity.State

class LoansAdapter(
    @DrawableRes private val approvedImageId: Int,
    @DrawableRes private val registeredImageId: Int,
    @DrawableRes private val rejectedImageId: Int
) : RecyclerView.Adapter<LoanViewHolder>() {

    var loans: List<Loan> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val binding = ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LoanViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val loan = loans[position]
        val (imageId, imageColor) = when (loan.state) {
            State.APPROVED -> approvedImageId to Color.GREEN
            State.REGISTERED -> registeredImageId to Color.YELLOW
            State.REJECTED -> rejectedImageId to Color.RED
        }

        holder.bind(loan, imageId, imageColor)
    }

    override fun getItemCount(): Int = loans.size
}
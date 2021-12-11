package me.isachenko.loansonline.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import me.isachenko.loansonline.databinding.ItemLoanBinding
import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.entity.State

class LoansAdapter(
    private val amountTemplate: String,
    private val percentTemplate: String,
    private val dateTemplate: String,
    private val periodTemplate: String,
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

        return LoanViewHolder(
            binding,
            amountTemplate,
            percentTemplate,
            dateTemplate,
            periodTemplate
        )
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        val loan = loans[position]
        val imageId = when (loan.state) {
            State.APPROVED -> approvedImageId
            State.REGISTERED -> registeredImageId
            State.REJECTED -> rejectedImageId
        }

        holder.bind(loan, imageId)
    }

    override fun getItemCount(): Int = loans.size
}
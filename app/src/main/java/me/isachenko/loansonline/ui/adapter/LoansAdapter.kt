package me.isachenko.loansonline.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.isachenko.loansonline.databinding.ItemLoanBinding

import me.isachenko.loansonline.domain.entity.Loan
import me.isachenko.loansonline.domain.entity.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named



class LoansAdapter(
    val onClick: (Int) -> Unit
) : RecyclerView.Adapter<LoanViewHolder>(), KoinComponent {

    private val approvedImageId: Int by inject(qualifier = named("approvedImageId"))
    private val registeredImageId: Int by inject(qualifier = named("registeredImageId"))
    private val rejectedImageId: Int by inject(qualifier = named("rejectedImageId"))

    var loans: List<Loan> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val binding = ItemLoanBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return LoanViewHolder(binding, onClick)
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
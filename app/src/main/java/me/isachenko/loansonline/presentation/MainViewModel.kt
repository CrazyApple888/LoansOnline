package me.isachenko.loansonline.presentation

import androidx.lifecycle.ViewModel
import me.isachenko.loansonline.domain.KeyOperationsInteractor

class MainViewModel(
    private val interactor: KeyOperationsInteractor
) : ViewModel() {

    fun checkIfKeyContains() =
        interactor.hasKey()

}
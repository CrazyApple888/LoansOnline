package me.isachenko.loansonline.presentation

import androidx.lifecycle.ViewModel

class MainViewModel(
    private val interactor: KeyOperationsInteractor
) : ViewModel() {

    fun checkIfKeyContains() =
        interactor.hasKey()

}
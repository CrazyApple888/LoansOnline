package me.isachenko.loansonline.presentation

import me.isachenko.loansonline.domain.repository.KeyRepository

class KeyOperationsInteractor(
    private val repository: KeyRepository
) {

    fun hasKey(): Boolean =
        repository.hasKey()

    fun getKey(): String? =
        repository.getKey()

    fun saveKey(key: String) =
        repository.saveKey(key)

}
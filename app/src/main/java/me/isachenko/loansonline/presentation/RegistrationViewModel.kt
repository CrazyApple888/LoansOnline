package me.isachenko.loansonline.presentation

import androidx.lifecycle.ViewModel
import me.isachenko.loansonline.domain.ValidateNameUseCase
import me.isachenko.loansonline.domain.ValidatePasswordUseCase

class RegistrationViewModel(
    //todo: inject this
    private val validatePasswordUseCase: ValidatePasswordUseCase = ValidatePasswordUseCase(),
    private val validateNameUseCase: ValidateNameUseCase = ValidateNameUseCase()
) : ViewModel() {

    fun isPasswordCorrect(password: String): Boolean =
        validatePasswordUseCase(password)

    fun arePasswordSame(password: String, repeatPassword: String): Boolean =
        password == repeatPassword

    fun isNameCorrect(name: String): Boolean =
        validateNameUseCase(name)

}
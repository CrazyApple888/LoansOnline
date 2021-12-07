package me.isachenko.loansonline.domain.usecases

class ValidatePasswordUseCase {

    //8-30 symbols including numbers, uppercase and lowercase letters
    private val passwordRegex = Regex("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,30}$")

    operator fun invoke(password: String) : Boolean =
        passwordRegex.matches(password)

}
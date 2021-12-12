package me.isachenko.loansonline.domain.usecases

class ValidatePhoneUseCase {

    operator fun invoke(number: String): Boolean =
        number.isNotBlank()
}
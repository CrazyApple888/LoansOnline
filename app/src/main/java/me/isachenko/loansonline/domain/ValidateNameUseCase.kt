package me.isachenko.loansonline.domain

class ValidateNameUseCase {

    //1-30 symbols in latin or cyrillic
    private val nameRegex = Regex("^[a-zA-Zа-яА-Я]{1,30}")

    var name: String = ""
    var password: String = ""
    var repeatPassword: String = ""

    operator fun invoke(name: String) : Boolean =
        nameRegex.matches(name)

}
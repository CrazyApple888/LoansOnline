package me.isachenko.loansonline.domain.usecases

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named

class ValidateNameUseCase : KoinComponent {

    //1-30 symbols in latin or cyrillic
    private val nameRegex: Regex by inject(qualifier = named("nameRegex"))

    operator fun invoke(name: String) : Boolean =
        nameRegex.matches(name)

}
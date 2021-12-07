package me.isachenko.loansonline.domain.usecases

import me.isachenko.loansonline.domain.model.ApiResult
import me.isachenko.loansonline.domain.repository.UserRepository

class RegisterUserUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String, password: String): ApiResult =
        userRepository.register(name, password)
}
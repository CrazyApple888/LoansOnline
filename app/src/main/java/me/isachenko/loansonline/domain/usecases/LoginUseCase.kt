package me.isachenko.loansonline.domain.usecases

import me.isachenko.loansonline.domain.entity.ApiResult
import me.isachenko.loansonline.domain.repository.UserRepository

class LoginUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(name: String, password: String) : ApiResult<String> =
        userRepository.login(name, password)
}
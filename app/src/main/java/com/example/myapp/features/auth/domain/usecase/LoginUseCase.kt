package com.example.myapp.features.auth.domain.usecase

import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.model.User
import com.example.myapp.features.auth.domain.repository.AuthRepository

/**
 * Use case executing login authentication business logic.
 * Encapsulates domain invariants and interacts strictly via interfaces.
 */
class LoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(credentials: AuthCredentials): Result<User> {
        if (credentials.username.isBlank()) {
            return Result.failure(IllegalArgumentException("Username is required"))
        }
        if (credentials.password.isBlank()) {
            return Result.failure(IllegalArgumentException("Password is required"))
        }
        if (credentials.password.length < 4) {
            return Result.failure(IllegalArgumentException("Password must be at least 4 characters"))
        }

        return authRepository.login(credentials)
    }
}

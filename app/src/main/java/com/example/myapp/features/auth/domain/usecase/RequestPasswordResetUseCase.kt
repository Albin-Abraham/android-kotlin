package com.example.myapp.features.auth.domain.usecase

import com.example.myapp.features.auth.domain.repository.AuthRepository

/**
 * Use case executing password recovery request business logic.
 */
class RequestPasswordResetUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<Unit> {
        val trimmedEmail = email.trim()
        if (trimmedEmail.isBlank() || !trimmedEmail.contains("@")) {
            return Result.failure(IllegalArgumentException("Please enter a valid email address"))
        }

        return authRepository.requestPasswordReset(trimmedEmail)
    }
}

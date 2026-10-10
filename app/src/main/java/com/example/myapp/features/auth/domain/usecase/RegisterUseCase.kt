package com.example.myapp.features.auth.domain.usecase

import com.example.myapp.features.auth.domain.model.User
import com.example.myapp.features.auth.domain.repository.AuthRepository

/**
 * Use case executing user account registration.
 * Single responsibility: validates registration inputs and orchestrates domain repository.
 */
class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        username: String,
        email: String,
        password: String,
        acceptedTerms: Boolean
    ): Result<User> {
        if (username.trim().isBlank()) {
            return Result.failure(IllegalArgumentException("Username cannot be empty"))
        }
        if (email.trim().isBlank() || !email.contains("@")) {
            return Result.failure(IllegalArgumentException("Please provide a valid email address"))
        }
        if (password.length < 6) {
            return Result.failure(IllegalArgumentException("Password must be at least 6 characters"))
        }
        if (!acceptedTerms) {
            return Result.failure(IllegalArgumentException("You must accept the Terms of Service & Privacy Policy"))
        }

        return authRepository.register(username.trim(), email.trim(), password)
    }
}

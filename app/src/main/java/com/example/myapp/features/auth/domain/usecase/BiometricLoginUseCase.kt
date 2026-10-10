package com.example.myapp.features.auth.domain.usecase

import com.example.myapp.features.auth.domain.model.User
import com.example.myapp.features.auth.domain.repository.AuthRepository

/**
 * Use case executing cryptographic biometric authentication handshake.
 */
class BiometricLoginUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.authenticateWithBiometrics()
    }
}

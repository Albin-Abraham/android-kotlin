package com.example.myapp.features.auth.data.repository

import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.model.User
import com.example.myapp.features.auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

/**
 * Data layer implementation of [AuthRepository].
 * Simulates remote network and secure hardware authentication operations.
 */
class AuthRepositoryImpl : AuthRepository {

    override suspend fun login(credentials: AuthCredentials): Result<User> {
        delay(600) // Simulate network roundtrip
        return if (credentials.username.equals("user", ignoreCase = true) && credentials.password == "password") {
            Result.success(
                User(
                    id = "usr_101",
                    username = credentials.username,
                    email = "user@example.com",
                    token = "jwt_token_sample_xyz"
                )
            )
        } else {
            Result.failure(Exception("Invalid username or password"))
        }
    }

    override suspend fun register(username: String, email: String, password: String): Result<User> {
        delay(600)
        return Result.success(
            User(
                id = "usr_${System.currentTimeMillis()}",
                username = username,
                email = email,
                token = "jwt_token_sample_new"
            )
        )
    }

    override suspend fun requestPasswordReset(email: String): Result<Unit> {
        delay(500)
        return Result.success(Unit)
    }

    override suspend fun authenticateWithBiometrics(): Result<User> {
        delay(500)
        return Result.success(
            User(
                id = "usr_biometric_101",
                username = "enterprise_admin",
                email = "admin@zenos.io",
                token = "jwt_biometric_token_valid"
            )
        )
    }
}

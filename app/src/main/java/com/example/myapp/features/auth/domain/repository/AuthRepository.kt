package com.example.myapp.features.auth.domain.repository

import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.model.User

/**
 * Domain Repository Interface for Authentication.
 * Inversion of Dependency (SOLID DIP) - Domain defines contract, Data implements it.
 */
interface AuthRepository {
    suspend fun login(credentials: AuthCredentials): Result<User>
    suspend fun register(username: String, email: String, password: String): Result<User>
    suspend fun requestPasswordReset(email: String): Result<Unit>
}

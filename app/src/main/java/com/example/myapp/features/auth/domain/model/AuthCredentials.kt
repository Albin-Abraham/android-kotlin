package com.example.myapp.features.auth.domain.model

/**
 * Value Object representing credentials with domain invariant validation.
 */
data class AuthCredentials(
    val username: String,
    val password: String
) {
    val isValid: Boolean get() = username.isNotBlank() && password.length >= 4
}

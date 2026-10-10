package com.example.myapp.features.auth.domain.model

/**
 * Domain Entity representing an authenticated User.
 * Enforces business invariants (non-blank ID, valid email, non-empty username).
 * Pure Kotlin with zero framework/ORM dependencies.
 */
data class User(
    val id: String,
    val username: String,
    val email: String,
    val token: String
) {
    init {
        require(id.isNotBlank()) { "User ID cannot be blank" }
        require(username.isNotBlank()) { "Username cannot be blank" }
        require(email.isNotBlank() && email.contains("@")) { "User must have a valid email address" }
        require(token.isNotBlank()) { "Authentication token cannot be blank" }
    }
}

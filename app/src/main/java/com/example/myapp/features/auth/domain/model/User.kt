package com.example.myapp.features.auth.domain.model

/**
 * Domain Entity representing an authenticated User.
 * Pure business model without ORM or UI framework ties.
 */
data class User(
    val id: String,
    val username: String,
    val email: String,
    val token: String
)

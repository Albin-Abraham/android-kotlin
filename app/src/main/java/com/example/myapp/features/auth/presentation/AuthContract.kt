package com.example.myapp.features.auth.presentation

import com.example.myapp.features.auth.domain.model.User

sealed interface AuthIntent {
    data class UpdateField(val key: String, val value: Any?) : AuthIntent
    data object SubmitLogin : AuthIntent
    data object SubmitSignUp : AuthIntent
    data object SubmitForgotPassword : AuthIntent
    data object ClearError : AuthIntent
}

data class AuthUiState(
    val formValues: Map<String, Any?> = emptyMap(),
    val fieldErrors: Map<String, String?> = emptyMap(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authenticatedUser: User? = null,
    val isActionSuccess: Boolean = false
)

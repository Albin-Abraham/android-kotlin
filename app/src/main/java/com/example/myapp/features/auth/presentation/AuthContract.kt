package com.example.myapp.features.auth.presentation

import com.example.myapp.features.auth.domain.model.User

/**
 * User interactions and system triggers for Authentication.
 */
sealed interface AuthIntent {
    data class UpdateField(val key: String, val value: Any?) : AuthIntent
    data class ToggleRememberMe(val enabled: Boolean) : AuthIntent
    data object SubmitLogin : AuthIntent
    data object StartBiometricAuth : AuthIntent
    data object SubmitSignUp : AuthIntent
    data object SubmitForgotPassword : AuthIntent
    data object ClearError : AuthIntent
}

/**
 * One-time side-effects for navigation and transient feedback.
 * Prevents re-triggering navigation on recomposition/lifecycle changes.
 */
sealed interface AuthEffect {
    data class NavigateToHome(val user: User) : AuthEffect
    data object NavigateToSignUp : AuthEffect
    data object NavigateToForgotPassword : AuthEffect
    data class ShowToast(val message: String) : AuthEffect
}

/**
 * Immutable UI State representing authentication screen status.
 */
data class AuthUiState(
    val formValues: Map<String, Any?> = emptyMap(),
    val fieldErrors: Map<String, String?> = emptyMap(),
    val rememberMe: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val authenticatedUser: User? = null,
    val isActionSuccess: Boolean = false,
    val isBiometricAvailable: Boolean = true
)

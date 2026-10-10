package com.example.myapp.features.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Stateful Login Route integrating [AuthViewModel], lifecycle-aware state collection,
 * and one-time effect navigation handling.
 */
@Composable
fun LoginRoute(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // Collect one-time effects safely with lifecycle awareness
    LaunchedEffect(viewModel) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is AuthEffect.NavigateToHome -> onLoginSuccess()
                is AuthEffect.NavigateToSignUp -> onNavigateToSignUp()
                is AuthEffect.NavigateToForgotPassword -> onNavigateToForgotPassword()
                is AuthEffect.ShowToast -> {
                    // Optional toast/snackbar handling
                }
            }
        }
    }

    LoginScreen(
        state = state,
        loginSchema = viewModel.loginSchema,
        onIntent = viewModel::onIntent,
        onNavigateToSignUp = onNavigateToSignUp,
        onNavigateToForgotPassword = onNavigateToForgotPassword,
        modifier = modifier
    )
}

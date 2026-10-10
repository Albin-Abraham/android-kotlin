package com.example.myapp.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.myapp.core.domain.form.FormFieldDescriptor
import com.example.myapp.core.ui.components.common.AppButton
import com.example.myapp.core.ui.components.common.AppButtonVariant
import com.example.myapp.core.ui.components.form.DynamicForm
import com.example.myapp.core.ui.layouts.AppScaffold
import com.example.myapp.core.ui.layouts.AppTopBar
import com.example.myapp.core.ui.theme.spacing
import com.example.myapp.features.auth.presentation.components.AuthCard
import com.example.myapp.features.auth.presentation.components.AuthErrorBanner
import com.example.myapp.features.auth.presentation.components.AuthFooter
import com.example.myapp.features.auth.presentation.components.AuthHeader
import com.example.myapp.features.auth.presentation.components.BiometricAuthSection
import com.example.myapp.features.auth.presentation.components.LoginOptionsRow

/**
 * Pure Stateless Login Screen.
 * Composes UI state and dispatches user intents using standardized Auth components and design tokens.
 */
@Composable
fun LoginScreen(
    state: AuthUiState,
    loginSchema: List<FormFieldDescriptor>,
    onIntent: (AuthIntent) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing

    AppScaffold(
        topBar = {
            AppTopBar(title = "Sign In")
        },
        bottomBar = null,
        modifier = modifier
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.12f),
                            MaterialTheme.colorScheme.surfaceContainerLowest
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .navigationBarsPadding()
                    .padding(horizontal = spacing.medium, vertical = spacing.small),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Standardized Enterprise Auth Card
                AuthCard {
                    // 1. Standardized Branded Header
                    AuthHeader(
                        title = "Welcome Back",
                        subtitle = "Sign in to access your secure account",
                        icon = Icons.Default.Lock,
                        iconContentDescription = "Login security badge"
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    // 2. Dynamic Form Input Fields
                    DynamicForm(
                        fields = loginSchema,
                        formValues = state.formValues,
                        fieldErrors = state.fieldErrors,
                        onFieldValueChange = { key, value ->
                            onIntent(AuthIntent.UpdateField(key, value))
                        },
                        onImeSubmit = {
                            onIntent(AuthIntent.SubmitLogin)
                        }
                    )

                    // 3. Options Row (Remember Me & Forgot Password)
                    LoginOptionsRow(
                        rememberMe = state.rememberMe,
                        onRememberMeChange = { enabled ->
                            onIntent(AuthIntent.ToggleRememberMe(enabled))
                        },
                        onForgotPasswordClick = onNavigateToForgotPassword
                    )

                    // 4. Accessible Animated Error Banner
                    AuthErrorBanner(
                        errorMessage = state.errorMessage,
                        modifier = Modifier.padding(top = spacing.extraSmall)
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    // 5. Primary Action Button
                    AppButton(
                        text = "Sign In",
                        onClick = { onIntent(AuthIntent.SubmitLogin) },
                        isLoading = state.isLoading,
                        variant = AppButtonVariant.PRIMARY
                    )

                    // 6. Dedicated Biometric Section
                    if (state.isBiometricAvailable) {
                        BiometricAuthSection(
                            onBiometricClick = { onIntent(AuthIntent.StartBiometricAuth) },
                            isLoading = state.isLoading
                        )
                    }

                    Spacer(modifier = Modifier.height(spacing.small))

                    // 7. Sign Up Navigation Footer
                    AuthFooter(
                        promptText = "Don't have an account?",
                        actionText = "Sign Up",
                        onActionClick = onNavigateToSignUp
                    )
                }
            }
        }
    }
}

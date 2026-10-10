package com.example.myapp.features.auth.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapp.core.domain.form.FormFieldDescriptor
import com.example.myapp.core.ui.components.common.AppButton
import com.example.myapp.core.ui.components.common.AppButtonVariant
import com.example.myapp.core.ui.components.form.DynamicForm
import com.example.myapp.core.ui.layouts.AppScaffold
import com.example.myapp.core.ui.layouts.AppTopBar
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.ambientAuraBackground
import com.example.myapp.core.ui.theme.breakpoints
import com.example.myapp.core.ui.theme.spacing
import com.example.myapp.features.auth.presentation.components.*

/**
 * Pure Stateless Login Screen.
 * Composes UI state and dispatches user intents through dedicated, accessible subcomponents.
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
    val breakpoints = MaterialTheme.breakpoints

    AppScaffold(
        topBar = {
            AppTopBar(title = "Sign In")
        },
        bottomBar = null,
        modifier = modifier
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .ambientAuraBackground()
        ) {
            val isCompactHeight = maxHeight < 600.dp

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = spacing.medium,
                        vertical = if (isCompactHeight) spacing.small else spacing.medium
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = if (isCompactHeight) Arrangement.Top else Arrangement.Center
            ) {
                // Executive Auth Card Container
                AppSurface(
                    tier = SurfaceTier.LOW,
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    ),
                    shadowElevation = 3.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .widthIn(max = breakpoints.maxCardWidth)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.large, vertical = spacing.large),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(spacing.medium)
                    ) {
                        // 1. Decorative Security Badge
                        SecurityBadge()

                        // 2. Header Typography
                        LoginHeader()

                        Spacer(modifier = Modifier.height(spacing.extraExtraSmall))

                        // 3. Dynamic Form Input Fields
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

                        // 4. Options Row (Remember Me & Forgot Password)
                        LoginOptionsRow(
                            rememberMe = state.rememberMe,
                            onRememberMeChange = { enabled ->
                                onIntent(AuthIntent.ToggleRememberMe(enabled))
                            },
                            onForgotPasswordClick = onNavigateToForgotPassword
                        )

                        // 5. Accessible Error Banner
                        AuthErrorBanner(errorMessage = state.errorMessage)

                        // 6. Primary Action Button
                        AppButton(
                            text = "Sign In",
                            onClick = { onIntent(AuthIntent.SubmitLogin) },
                            isLoading = state.isLoading,
                            variant = AppButtonVariant.PRIMARY
                        )

                        // 7. Dedicated Biometric Section
                        if (state.isBiometricAvailable) {
                            BiometricAuthSection(
                                onBiometricClick = { onIntent(AuthIntent.StartBiometricAuth) },
                                isLoading = state.isLoading
                            )
                        }

                        // 8. Sign Up Navigation Footer
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
}

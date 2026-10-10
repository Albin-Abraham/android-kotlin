package com.example.myapp.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.core.ui.components.common.AppButton
import com.example.myapp.core.ui.components.common.AppButtonVariant
import com.example.myapp.core.ui.components.form.DynamicForm
import com.example.myapp.core.ui.layouts.AppScaffold
import com.example.myapp.core.ui.layouts.AppTopBar
import com.example.myapp.features.auth.presentation.components.AuthCard
import com.example.myapp.features.auth.presentation.components.AuthErrorBanner
import com.example.myapp.features.auth.presentation.components.AuthFooter
import com.example.myapp.features.auth.presentation.components.AuthHeader
import com.example.myapp.core.ui.theme.spacing

/**
 * Stateful Route for the SignUp Flow.
 */
@Composable
fun SignUpRoute(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(state.isActionSuccess) {
        if (state.isActionSuccess && state.authenticatedUser != null) {
            onSignUpSuccess()
        }
    }

    SignUpScreen(
        state = state,
        schema = viewModel.signUpSchema,
        onFieldChange = { key, value -> viewModel.onIntent(AuthIntent.UpdateField(key, value)) },
        onSubmit = { viewModel.onIntent(AuthIntent.SubmitSignUp) },
        onNavigateToLogin = onNavigateToLogin,
        modifier = modifier
    )
}

/**
 * Stateless, accessible, and responsive SignUpScreen.
 * Utilizes shared design tokens, AuthCard, AuthHeader, and accessible Checkbox terms.
 */
@Composable
fun SignUpScreen(
    state: AuthUiState,
    schema: List<com.example.myapp.core.domain.form.FormFieldDescriptor>,
    onFieldChange: (key: String, value: Any?) -> Unit,
    onSubmit: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = MaterialTheme.spacing

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Create Account",
                onBackClick = onNavigateToLogin
            )
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
                AuthCard {
                    // Standardized Branded Header
                    AuthHeader(
                        title = "Join Us Today",
                        subtitle = "Enter your details to create an account",
                        icon = Icons.Default.PersonAdd,
                        iconContentDescription = "Account creation badge"
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    // Dynamic Form with validation and responsive spacing
                    DynamicForm(
                        fields = schema,
                        formValues = state.formValues,
                        fieldErrors = state.fieldErrors,
                        onFieldValueChange = onFieldChange,
                        onImeSubmit = onSubmit
                    )

                    // Accessible Animated Error Banner
                    AuthErrorBanner(
                        errorMessage = state.errorMessage,
                        modifier = Modifier.padding(top = spacing.small)
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    // Primary Action Button
                    AppButton(
                        text = "Create Account",
                        onClick = onSubmit,
                        isLoading = state.isLoading,
                        variant = AppButtonVariant.PRIMARY
                    )

                    Spacer(modifier = Modifier.height(spacing.small))

                    // Navigation Footer
                    AuthFooter(
                        promptText = "Already have an account?",
                        actionText = "Sign In",
                        onActionClick = onNavigateToLogin
                    )
                }
            }
        }
    }
}

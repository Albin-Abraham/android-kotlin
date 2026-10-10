package com.example.myapp.features.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.*
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
import com.example.myapp.core.ui.theme.spacing
import com.example.myapp.features.auth.presentation.components.AuthCard
import com.example.myapp.features.auth.presentation.components.AuthErrorBanner
import com.example.myapp.features.auth.presentation.components.AuthFooter
import com.example.myapp.features.auth.presentation.components.AuthHeader

/**
 * Standardized ForgotPassword screen using shared Auth components.
 */
@Composable
fun ForgotPasswordScreen(
    onResetRequested: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = MaterialTheme.spacing

    LaunchedEffect(state.isActionSuccess) {
        if (state.isActionSuccess) {
            onResetRequested()
        }
    }

    AppScaffold(
        topBar = {
            AppTopBar(
                title = "Reset Password",
                onBackClick = onNavigateBack
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
                    AuthHeader(
                        title = "Password Recovery",
                        subtitle = "Enter your registered email to receive reset instructions",
                        icon = Icons.Default.LockReset,
                        iconContentDescription = "Reset password badge"
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    DynamicForm(
                        fields = viewModel.forgotPasswordSchema,
                        formValues = state.formValues,
                        fieldErrors = state.fieldErrors,
                        onFieldValueChange = { key, value ->
                            viewModel.onIntent(AuthIntent.UpdateField(key, value))
                        },
                        onImeSubmit = {
                            viewModel.onIntent(AuthIntent.SubmitForgotPassword)
                        }
                    )

                    AuthErrorBanner(
                        errorMessage = state.errorMessage,
                        modifier = Modifier.padding(top = spacing.extraSmall)
                    )

                    Spacer(modifier = Modifier.height(spacing.medium))

                    AppButton(
                        text = "Send Instructions",
                        onClick = { viewModel.onIntent(AuthIntent.SubmitForgotPassword) },
                        isLoading = state.isLoading,
                        variant = AppButtonVariant.PRIMARY
                    )

                    Spacer(modifier = Modifier.height(spacing.small))

                    AuthFooter(
                        promptText = "Remember your password?",
                        actionText = "Back to Sign In",
                        onActionClick = onNavigateBack
                    )
                }
            }
        }
    }
}

package com.example.myapp.features.auth.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.core.ui.components.common.AppButton
import com.example.myapp.core.ui.components.common.AppButtonVariant
import com.example.myapp.core.ui.components.form.DynamicForm
import com.example.myapp.core.ui.layouts.AppScaffold
import com.example.myapp.core.ui.layouts.AppTopBar
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier
import com.example.myapp.core.ui.theme.spacing

@Composable
fun ForgotPasswordScreen(
    onResetRequested: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()
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
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f),
                            MaterialTheme.colorScheme.surfaceContainerLowest
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = spacing.medium, vertical = spacing.small),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
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
                        .widthIn(max = 480.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.large, vertical = spacing.large),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(spacing.medium)
                    ) {
                        // Halo Badge
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(76.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                        ) {
                            AppSurface(
                                tier = SurfaceTier.HIGH,
                                shape = CircleShape,
                                shadowElevation = 4.dp,
                                modifier = Modifier.size(56.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.LockReset,
                                        contentDescription = "Reset Password Badge",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(spacing.extraSmall)
                        ) {
                            Text(
                                text = "Forgot Password?",
                                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Enter your registered email and we'll send you recovery instructions.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

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

                        AnimatedVisibility(
                            visible = state.errorMessage != null,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            state.errorMessage?.let { errorMsg ->
                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.errorContainer,
                                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(spacing.small),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Warning,
                                            contentDescription = "Error",
                                            tint = MaterialTheme.colorScheme.error,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(spacing.small))
                                        Text(
                                            text = errorMsg,
                                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                                        )
                                    }
                                }
                            }
                        }

                        AppButton(
                            text = "Send Reset Link",
                            onClick = { viewModel.onIntent(AuthIntent.SubmitForgotPassword) },
                            isLoading = state.isLoading,
                            variant = AppButtonVariant.PRIMARY
                        )
                    }
                }
            }
        }
    }
}

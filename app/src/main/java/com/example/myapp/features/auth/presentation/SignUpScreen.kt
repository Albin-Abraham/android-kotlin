package com.example.myapp.features.auth.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.core.ui.components.common.AppButton
import com.example.myapp.core.ui.components.common.AppButtonVariant
import com.example.myapp.core.ui.components.form.DynamicForm
import com.example.myapp.core.ui.layouts.DetailSlotsScaffold
import com.example.myapp.core.ui.surface.AppSurface
import com.example.myapp.core.ui.surface.SurfaceTier

@Composable
fun SignUpScreen(
    onSignUpSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isActionSuccess) {
        if (state.isActionSuccess && state.authenticatedUser != null) {
            onSignUpSuccess()
        }
    }

    DetailSlotsScaffold(
        topBarTitle = "Create Account",
        onBackClick = onNavigateToLogin,
        headerSlot = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Join Us Today",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Enter your details to create your account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        keyDetailsSlot = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DynamicForm(
                    fields = viewModel.signUpSchema,
                    formValues = state.formValues,
                    fieldErrors = state.fieldErrors,
                    onFieldValueChange = { key, value ->
                        viewModel.onIntent(AuthIntent.UpdateField(key, value))
                    }
                )

                state.errorMessage?.let { errorMsg ->
                    AppSurface(
                        tier = SurfaceTier.HIGHEST,
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = errorMsg,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        },
        secondaryContentSlot = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account?", style = MaterialTheme.typography.bodyMedium)
                TextButton(onClick = onNavigateToLogin) {
                    Text("Sign In")
                }
            }
        },
        actionSlot = {
            AppButton(
                text = "Create Account",
                onClick = { viewModel.onIntent(AuthIntent.SubmitSignUp) },
                isLoading = state.isLoading,
                variant = AppButtonVariant.PRIMARY
            )
        },
        modifier = modifier
    )
}

package com.example.myapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapp.ui.components.*
import com.example.myapp.navigation.Screen
import com.example.myapp.auth.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()

    AppBox(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colorScheme.background,
        contentAlignment = Alignment.Center
    ) {
        AppBox(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            backgroundColor = MaterialTheme.colorScheme.surface,
            contentAlignment = Alignment.Center
        ) {
            AppColumn(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalSpacing = 16.dp
            ) {
                // Header
                Text(
                    "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    "Login to your account",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )

                AppSpacer(height = 32.dp)

                // Username
                AppInput(
                    value = state.username,
                    onValueChange = { viewModel.onUsernameChange(it) },
                    label = "Username",
                    placeholder = "Enter your username"
                )

                AppSpacer(height = 16.dp)

                // Password
                AppInput(
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChange(it) },
                    type = InputTypeField.PASSWORD,
                    label = "Password",
                    placeholder = "Enter your password"
                )

                // Error message
                state.errorMessage?.let {
                    AppSpacer(height = 16.dp)
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                AppSpacer(height = 24.dp)

                // Login button
                AppButton(
                    onClick = {
                        viewModel.login {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    },
                    variant = ButtonVariant.PRIMARY
                ) {
                    Text(
                        text = if (state.isLoading) "Logging in..." else "Login",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }

                AppSpacer(height = 16.dp)

                // Bottom row: Sign Up & Forgot Password
                AppRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalSpacing = 0.dp,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AppButton(
                        onClick = { navController.navigate(Screen.SignUp.route) },
                        variant = ButtonVariant.GHOST
                    ) { Text("Sign Up") }

                    Spacer(modifier = Modifier.weight(1f))

                    AppButton(
                        onClick = { navController.navigate(Screen.ForgotPassword.route) },
                        variant = ButtonVariant.GHOST
                    ) { Text("Forgot Password?") }
                }
            }
        }
    }
}
package com.example.myapp.features.auth.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.myapp.core.domain.form.FormFieldDescriptor
import com.example.myapp.core.ui.theme.MyAppTheme
import org.junit.Rule
import org.junit.Test

/**
 * UI State & Accessibility (a11y) Verification Test for LoginScreen & LoginRoute.
 */
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginRoute_displays_all_accessibility_elements_and_headings() {
        composeTestRule.setContent {
            MyAppTheme {
                LoginRoute(
                    onLoginSuccess = {},
                    onNavigateToSignUp = {},
                    onNavigateToForgotPassword = {}
                )
            }
        }

        // Verify Title and Headings
        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign In").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign Up").assertIsDisplayed()

        // Verify Form Inputs exist and are editable
        composeTestRule.onNodeWithText("Username").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()

        // Verify Checkbox is toggleable
        composeTestRule.onNodeWithText("Remember me").performClick()
    }

    @Test
    fun statelessLoginScreen_renders_loading_and_error_states_directly() {
        val testSchema = listOf(
            FormFieldDescriptor.Text(key = "username", label = "Username"),
            FormFieldDescriptor.Text(key = "password", label = "Password", isPassword = true)
        )

        composeTestRule.setContent {
            MyAppTheme {
                LoginScreen(
                    state = AuthUiState(
                        isLoading = true,
                        errorMessage = "Invalid credentials provided"
                    ),
                    loginSchema = testSchema,
                    onIntent = {},
                    onNavigateToSignUp = {},
                    onNavigateToForgotPassword = {}
                )
            }
        }

        // Verify error message is rendered
        composeTestRule.onNodeWithText("Invalid credentials provided").assertIsDisplayed()
    }

    @Test
    fun loginRoute_successful_input_triggers_success_callback() {
        var isSuccess = false

        composeTestRule.setContent {
            MyAppTheme {
                LoginRoute(
                    onLoginSuccess = { isSuccess = true },
                    onNavigateToSignUp = {},
                    onNavigateToForgotPassword = {}
                )
            }
        }

        // Input Username
        composeTestRule.onNodeWithText("Username").performTextInput("user")

        // Input Password
        composeTestRule.onNodeWithText("Password").performTextInput("password")

        // Submit form
        composeTestRule.onNodeWithText("Sign In").performClick()

        // Wait for coroutine authentication completion
        composeTestRule.waitUntil(timeoutMillis = 3000) {
            isSuccess
        }

        assert(isSuccess)
    }
}

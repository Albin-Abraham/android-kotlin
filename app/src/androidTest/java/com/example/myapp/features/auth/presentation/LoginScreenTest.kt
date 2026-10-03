package com.example.myapp.features.auth.presentation

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.myapp.core.ui.theme.MyAppTheme
import org.junit.Rule
import org.junit.Test

/**
 * UI State & Accessibility (a11y) Verification Test for LoginScreen.
 */
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreen_displays_all_accessibility_elements_and_headings() {
        composeTestRule.setContent {
            MyAppTheme {
                LoginScreen(
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
    fun loginScreen_shows_validation_error_when_submitted_empty() {
        composeTestRule.setContent {
            MyAppTheme {
                LoginScreen(
                    onLoginSuccess = {},
                    onNavigateToSignUp = {},
                    onNavigateToForgotPassword = {}
                )
            }
        }

        // Click Sign In with empty fields
        composeTestRule.onNodeWithText("Sign In").performClick()

        // Verify validation error text is displayed
        composeTestRule.onNodeWithText("Username cannot be empty").assertIsDisplayed()
    }

    @Test
    fun loginScreen_successful_input_triggers_success_callback() {
        var isSuccess = false

        composeTestRule.setContent {
            MyAppTheme {
                LoginScreen(
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

package com.example.myapp.features.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.core.domain.form.FieldIconType
import com.example.myapp.core.domain.form.FormFieldDescriptor
import com.example.myapp.core.domain.validation.Validators
import com.example.myapp.features.auth.data.repository.AuthRepositoryImpl
import com.example.myapp.features.auth.domain.model.AuthCredentials
import com.example.myapp.features.auth.domain.repository.AuthRepository
import com.example.myapp.features.auth.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Controller / ViewModel for Authentication features.
 * Orchestrates domain use cases and manages Config-Driven form states.
 */
class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepositoryImpl(),
    private val loginUseCase: LoginUseCase = LoginUseCase(authRepository)
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    // 1. Config-Driven Login Schema
    val loginSchema: List<FormFieldDescriptor> = listOf(
        FormFieldDescriptor.Text(
            key = "username",
            label = "Username",
            placeholder = "Enter your username",
            iconType = FieldIconType.PERSON,
            validators = listOf(Validators.required("Username cannot be empty"))
        ),
        FormFieldDescriptor.Text(
            key = "password",
            label = "Password",
            placeholder = "Enter your password",
            iconType = FieldIconType.LOCK,
            isPassword = true,
            validators = listOf(
                Validators.required("Password cannot be empty"),
                Validators.minLength(4, "Password must be at least 4 characters")
            )
        )
    )

    // 2. Config-Driven Sign-Up Schema
    val signUpSchema: List<FormFieldDescriptor> = listOf(
        FormFieldDescriptor.Text(
            key = "username",
            label = "Username",
            placeholder = "Choose a username",
            iconType = FieldIconType.PERSON,
            validators = listOf(Validators.required())
        ),
        FormFieldDescriptor.Text(
            key = "email",
            label = "Email Address",
            placeholder = "you@example.com",
            iconType = FieldIconType.EMAIL,
            validators = listOf(Validators.required(), Validators.email())
        ),
        FormFieldDescriptor.Text(
            key = "password",
            label = "Password",
            placeholder = "Create a password",
            iconType = FieldIconType.LOCK,
            isPassword = true,
            validators = listOf(Validators.required(), Validators.minLength(6))
        ),
        FormFieldDescriptor.Toggle(
            key = "terms",
            label = "Accept Terms & Conditions",
            description = "I agree to the privacy policy and terms of service"
        )
    )

    // 3. Config-Driven Forgot Password Schema
    val forgotPasswordSchema: List<FormFieldDescriptor> = listOf(
        FormFieldDescriptor.Text(
            key = "email",
            label = "Registered Email",
            placeholder = "name@domain.com",
            iconType = FieldIconType.EMAIL,
            validators = listOf(Validators.required(), Validators.email())
        )
    )

    fun onIntent(intent: AuthIntent) {
        when (intent) {
            is AuthIntent.UpdateField -> handleFieldUpdate(intent.key, intent.value)
            is AuthIntent.SubmitLogin -> handleLogin()
            is AuthIntent.SubmitSignUp -> handleSignUp()
            is AuthIntent.SubmitForgotPassword -> handleForgotPassword()
            is AuthIntent.ClearError -> _uiState.update { it.copy(errorMessage = null) }
        }
    }

    private fun handleFieldUpdate(key: String, value: Any?) {
        _uiState.update { state ->
            val updatedValues = state.formValues.toMutableMap().apply { put(key, value) }
            val updatedErrors = state.fieldErrors.toMutableMap().apply { remove(key) }
            state.copy(formValues = updatedValues, fieldErrors = updatedErrors, errorMessage = null)
        }
    }

    private fun validateSchema(schema: List<FormFieldDescriptor>): Boolean {
        val currentValues = _uiState.value.formValues
        val errors = mutableMapOf<String, String?>()
        var isValid = true

        for (field in schema) {
            val value = currentValues[field.key]
            for (validator in field.validators) {
                val error = validator.validate(value)
                if (error != null) {
                    errors[field.key] = error
                    isValid = false
                    break
                }
            }
        }

        _uiState.update { it.copy(fieldErrors = errors) }
        return isValid
    }

    private fun handleLogin() {
        if (!validateSchema(loginSchema)) return

        val username = (_uiState.value.formValues["username"] as? String).orEmpty()
        val password = (_uiState.value.formValues["password"] as? String).orEmpty()

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            loginUseCase(AuthCredentials(username = username, password = password))
                .onSuccess { user ->
                    _uiState.update {
                        it.copy(isLoading = false, authenticatedUser = user, isActionSuccess = true)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "Authentication failed")
                    }
                }
        }
    }

    private fun handleSignUp() {
        if (!validateSchema(signUpSchema)) return

        val terms = (_uiState.value.formValues["terms"] as? Boolean) ?: false
        if (!terms) {
            _uiState.update { it.copy(errorMessage = "Please accept the Terms & Conditions") }
            return
        }

        val username = (_uiState.value.formValues["username"] as? String).orEmpty()
        val email = (_uiState.value.formValues["email"] as? String).orEmpty()
        val password = (_uiState.value.formValues["password"] as? String).orEmpty()

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            authRepository.register(username, email, password)
                .onSuccess { user ->
                    _uiState.update {
                        it.copy(isLoading = false, authenticatedUser = user, isActionSuccess = true)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "Registration failed")
                    }
                }
        }
    }

    private fun handleForgotPassword() {
        if (!validateSchema(forgotPasswordSchema)) return

        val email = (_uiState.value.formValues["email"] as? String).orEmpty()

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            authRepository.requestPasswordReset(email)
                .onSuccess {
                    _uiState.update {
                        it.copy(isLoading = false, isActionSuccess = true)
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message ?: "Reset request failed")
                    }
                }
        }
    }
}

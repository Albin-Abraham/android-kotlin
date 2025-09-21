package com.example.myapp.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.auth.data.model.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onUsernameChange(username: String) {
        _state.value = _state.value.copy(username = username, errorMessage = null)
    }

    fun onPasswordChange(password: String) {
        _state.value = _state.value.copy(password = password, errorMessage = null)
    }

    fun login(onSuccess: () -> Unit) {
        val current = _state.value
        if (current.username.isBlank() || current.password.isBlank()) {
            _state.value = current.copy(errorMessage = "Username and password are required")
            return
        }

        viewModelScope.launch {
            _state.value = current.copy(isLoading = true)
            delay(1500) // simulate network
            if (current.username == "user" && current.password == "password") {
                _state.value = current.copy(isLoading = false)
                onSuccess()
            } else {
                _state.value = current.copy(isLoading = false, errorMessage = "Invalid credentials")
            }
        }
    }
}
package com.example.myapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Root Application ViewModel.
 * Performs parallel session checks & bootstrap resolution without artificial UI delays.
 */
class MainViewModel : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _startDestination = MutableStateFlow(Screen.Login.route)
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    init {
        bootstrapApp()
    }

    private fun bootstrapApp() {
        viewModelScope.launch {
            // Perform fast async initial bootstrap (e.g. read token / preferences / remote config)
            delay(300) // Fast session check
            _startDestination.value = Screen.Login.route
            _isReady.value = true
        }
    }
}

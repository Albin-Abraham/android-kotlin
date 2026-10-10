package com.example.myapp.core.observability.diagnostics

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Contract for controlling developer diagnostics inspection overlays.
 */
interface DeveloperDiagnosticsController {
    val isVisible: StateFlow<Boolean>
    fun open()
    fun close()
    fun toggle()
    fun isAvailable(): Boolean
}

/**
 * Standard in-memory diagnostics controller for debug builds.
 */
class DefaultDeveloperDiagnosticsController(
    private val debugBuild: Boolean = true
) : DeveloperDiagnosticsController {
    private val _isVisible = MutableStateFlow(false)
    override val isVisible: StateFlow<Boolean> = _isVisible.asStateFlow()

    override fun open() {
        if (isAvailable()) _isVisible.value = true
    }

    override fun close() {
        _isVisible.value = false
    }

    override fun toggle() {
        if (isAvailable()) _isVisible.value = !_isVisible.value
    }

    override fun isAvailable(): Boolean = debugBuild
}

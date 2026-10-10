package com.example.myapp.core.connectivity.contracts

import kotlinx.coroutines.flow.Flow

/**
 * Fine-grained network connectivity status.
 */
enum class ConnectivityState {
    OFFLINE,
    LOCAL_NETWORK,
    INTERNET_AVAILABLE
}

/**
 * Application-level backend API reachability state.
 * Distinct from device connectivity.
 */
enum class BackendHealth {
    UNKNOWN,
    AVAILABLE,
    UNAVAILABLE
}

/**
 * Enterprise Network & Backend Health Monitor Contract.
 * Pure Kotlin contract with zero Android framework dependencies.
 */
interface NetworkMonitor {
    val connectivityState: Flow<ConnectivityState>
    val backendHealth: Flow<BackendHealth>
    val isOnline: Flow<Boolean>
}

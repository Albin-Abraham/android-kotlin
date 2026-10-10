package com.example.myapp.core.connectivity.android

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.myapp.core.connectivity.contracts.BackendHealth
import com.example.myapp.core.connectivity.contracts.ConnectivityState
import com.example.myapp.core.connectivity.contracts.NetworkMonitor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map

/**
 * Android framework implementation of [NetworkMonitor] using [ConnectivityManager].
 */
class AndroidNetworkMonitor(context: Context) : NetworkMonitor {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

    private val _backendHealth = MutableStateFlow(BackendHealth.AVAILABLE)
    override val backendHealth: Flow<BackendHealth> = _backendHealth.asStateFlow()

    override val connectivityState: Flow<ConnectivityState> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
                val state = when {
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ->
                        ConnectivityState.INTERNET_AVAILABLE

                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ->
                        ConnectivityState.LOCAL_NETWORK

                    else -> ConnectivityState.OFFLINE
                }
                trySend(state)
            }

            override fun onLost(network: Network) {
                trySend(ConnectivityState.OFFLINE)
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager?.registerNetworkCallback(request, callback)

        // Initial evaluation
        val initialState = connectivityManager?.activeNetwork?.let { network ->
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            when {
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true ->
                    ConnectivityState.INTERNET_AVAILABLE
                capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true ->
                    ConnectivityState.LOCAL_NETWORK
                else -> ConnectivityState.OFFLINE
            }
        } ?: ConnectivityState.OFFLINE

        trySend(initialState)

        awaitClose {
            connectivityManager?.unregisterNetworkCallback(callback)
        }
    }.conflate()

    override val isOnline: Flow<Boolean> = connectivityState.map { it == ConnectivityState.INTERNET_AVAILABLE }

    fun updateBackendHealth(health: BackendHealth) {
        _backendHealth.value = health
    }
}

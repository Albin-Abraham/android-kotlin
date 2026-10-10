package com.example.myapp.core.navigation

import com.example.myapp.core.security.tenant.TenantContext

sealed interface NavigationAuthState {
    data object Unauthenticated : NavigationAuthState
    data class Authenticated(
        val userId: String,
        val userRoles: Set<String> = emptySet(),
        val globalPermissions: Set<String> = emptySet()
    ) : NavigationAuthState
}

sealed interface GuardDecision {
    data object Allow : GuardDecision
    data class Redirect(val destinationRoute: String) : GuardDecision
    data class BlockWithAlert(val reason: String) : GuardDecision
}

interface NavigationGuard {
    val priority: Int get() = 0 // Higher priority runs first
    suspend fun evaluate(
        targetRoute: ZenOsRoute,
        tenantContext: TenantContext?,
        authState: NavigationAuthState
    ): GuardDecision
}

/**
 * Built-in Authentication Guard that intercepts protected routes.
 */
class AuthenticationGuard(
    private val loginRoutePattern: String = "login",
    override val priority: Int = 100
) : NavigationGuard {
    override suspend fun evaluate(
        targetRoute: ZenOsRoute,
        tenantContext: TenantContext?,
        authState: NavigationAuthState
    ): GuardDecision {
        if (targetRoute.requiresAuthentication && authState is NavigationAuthState.Unauthenticated) {
            return GuardDecision.Redirect(loginRoutePattern)
        }
        return GuardDecision.Allow
    }
}

/**
 * Built-in Permission Guard that verifies target route permission requirements against tenant and user context.
 */
class PermissionGuard(
    override val priority: Int = 50
) : NavigationGuard {
    override suspend fun evaluate(
        targetRoute: ZenOsRoute,
        tenantContext: TenantContext?,
        authState: NavigationAuthState
    ): GuardDecision {
        if (targetRoute.requiredPermissions.isEmpty()) {
            return GuardDecision.Allow
        }

        val availablePermissions = when (authState) {
            is NavigationAuthState.Authenticated -> authState.globalPermissions + (tenantContext?.permissions ?: emptySet())
            is NavigationAuthState.Unauthenticated -> emptySet()
        }

        val missingPermissions = targetRoute.requiredPermissions - availablePermissions
        if (missingPermissions.isNotEmpty()) {
            return GuardDecision.BlockWithAlert("Access denied. Missing required permissions: ${missingPermissions.joinToString(", ")}")
        }
        return GuardDecision.Allow
    }
}

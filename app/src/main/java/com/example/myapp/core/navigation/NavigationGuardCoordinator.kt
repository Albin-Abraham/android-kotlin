package com.example.myapp.core.navigation

import com.example.myapp.core.security.tenant.TenantContext

/**
 * Coordinates asynchronous navigation guard execution in priority order.
 * Prevents recursive redirect loops and resolves navigation decisions.
 */
class NavigationGuardCoordinator(
    private val guards: List<NavigationGuard> = listOf(
        AuthenticationGuard(),
        PermissionGuard()
    ),
    private val maxRedirectDepth: Int = 5
) {
    suspend fun evaluate(
        targetRoute: ZenOsRoute,
        tenantContext: TenantContext?,
        authState: NavigationAuthState,
        redirectDepth: Int = 0
    ): GuardDecision {
        if (redirectDepth >= maxRedirectDepth) {
            return GuardDecision.BlockWithAlert("Exceeded maximum redirect depth ($maxRedirectDepth). Possible navigation loop detected.")
        }

        val sortedGuards = guards.sortedByDescending { it.priority }
        for (guard in sortedGuards) {
            when (val decision = guard.evaluate(targetRoute, tenantContext, authState)) {
                is GuardDecision.Allow -> continue
                is GuardDecision.Redirect -> return decision
                is GuardDecision.BlockWithAlert -> return decision
            }
        }
        return GuardDecision.Allow
    }
}

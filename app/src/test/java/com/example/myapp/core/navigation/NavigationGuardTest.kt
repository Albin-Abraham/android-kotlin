package com.example.myapp.core.navigation

import com.example.myapp.core.security.tenant.TenantContext
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class NavigationGuardTest {

    private object ProtectedRoute : ZenOsRoute {
        override val routePattern: String = "dashboard"
        override val requiresAuthentication: Boolean = true
        override val requiredPermissions: Set<String> = setOf("analytics.view")
    }

    private object PublicRoute : ZenOsRoute {
        override val routePattern: String = "login"
        override val requiresAuthentication: Boolean = false
    }

    @Test
    fun unauthenticated_user_targeting_protected_route_is_redirected_to_login() = runBlocking {
        val coordinator = NavigationGuardCoordinator()
        val decision = coordinator.evaluate(
            targetRoute = ProtectedRoute,
            tenantContext = null,
            authState = NavigationAuthState.Unauthenticated
        )

        assertTrue(decision is GuardDecision.Redirect)
        assertEquals("login", (decision as GuardDecision.Redirect).destinationRoute)
    }

    @Test
    fun unauthenticated_user_targeting_public_route_is_allowed() = runBlocking {
        val coordinator = NavigationGuardCoordinator()
        val decision = coordinator.evaluate(
            targetRoute = PublicRoute,
            tenantContext = null,
            authState = NavigationAuthState.Unauthenticated
        )

        assertEquals(GuardDecision.Allow, decision)
    }

    @Test
    fun authenticated_user_without_required_permission_is_blocked_with_alert() = runBlocking {
        val coordinator = NavigationGuardCoordinator()
        val authState = NavigationAuthState.Authenticated(
            userId = "user_123",
            globalPermissions = emptySet()
        )
        val tenantContext = TenantContext(
            tenantId = "tenant_1",
            organizationName = "Acme Corp",
            permissions = setOf("payroll.read") // missing analytics.view
        )

        val decision = coordinator.evaluate(
            targetRoute = ProtectedRoute,
            tenantContext = tenantContext,
            authState = authState
        )

        assertTrue(decision is GuardDecision.BlockWithAlert)
        val alert = decision as GuardDecision.BlockWithAlert
        assertTrue(alert.reason.contains("analytics.view"))
    }

    @Test
    fun authenticated_user_with_required_permission_is_allowed() = runBlocking {
        val coordinator = NavigationGuardCoordinator()
        val authState = NavigationAuthState.Authenticated(
            userId = "user_123",
            globalPermissions = emptySet()
        )
        val tenantContext = TenantContext(
            tenantId = "tenant_1",
            organizationName = "Acme Corp",
            permissions = setOf("analytics.view")
        )

        val decision = coordinator.evaluate(
            targetRoute = ProtectedRoute,
            tenantContext = tenantContext,
            authState = authState
        )

        assertEquals(GuardDecision.Allow, decision)
    }

    @Test
    fun loop_detector_aborts_when_redirect_depth_exceeds_maximum() = runBlocking {
        val coordinator = NavigationGuardCoordinator(maxRedirectDepth = 3)
        val decision = coordinator.evaluate(
            targetRoute = ProtectedRoute,
            tenantContext = null,
            authState = NavigationAuthState.Unauthenticated,
            redirectDepth = 3
        )

        assertTrue(decision is GuardDecision.BlockWithAlert)
        assertTrue((decision as GuardDecision.BlockWithAlert).reason.contains("Exceeded maximum redirect depth"))
    }
}

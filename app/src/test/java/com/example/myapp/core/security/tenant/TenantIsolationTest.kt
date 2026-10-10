package com.example.myapp.core.security.tenant

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TenantIsolationTest {

    @Test
    fun tenantSwitch_purgesCache_and_preventsCrossTenantDataPollution() = runBlocking {
        var purgeTriggered = false
        val manager = DefaultTenantContextManager(onCachePurgeRequested = { purgeTriggered = true })
        val cache = TenantScopedCache<String, String>(manager)

        // 1. Activate Tenant A
        val resultA = manager.switchTenant(
            targetTenantId = "tenant_alpha",
            targetOrganizationName = "Alpha Corp"
        )
        assertTrue(resultA is TenantSwitchResult.Success)
        val tokenA = (resultA as TenantSwitchResult.Success).newContext.generationToken

        // 2. Write data for Tenant A
        cache.put("employee_profile_1", "Alpha Employee Data", tokenA)
        assertEquals("Alpha Employee Data", cache.get("employee_profile_1"))

        // 3. Switch to Tenant B
        val resultB = manager.switchTenant(
            targetTenantId = "tenant_beta",
            targetOrganizationName = "Beta Healthcare"
        )
        assertTrue(resultB is TenantSwitchResult.Success)
        assertTrue(purgeTriggered)

        // 4. Verify Tenant B cannot access Tenant A's cached data
        assertNull(cache.get("employee_profile_1"))

        // 5. Verify late-arriving async response from Tenant A is dropped
        cache.put("employee_profile_late", "Stale Alpha Data", tokenA)
        assertNull(cache.get("employee_profile_late"))
    }
}

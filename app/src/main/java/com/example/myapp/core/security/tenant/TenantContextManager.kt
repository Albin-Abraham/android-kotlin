package com.example.myapp.core.security.tenant

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * Immutable Tenant & Branch Context with an out-of-order generation token.
 */
data class TenantContext(
    val tenantId: String,
    val organizationName: String,
    val activeBranchId: String? = null,
    val permissions: Set<String> = emptySet(),
    val generationToken: String = UUID.randomUUID().toString(),
    val switchedAtEpochMillis: Long = System.currentTimeMillis()
)

/**
 * Result of a controlled 5-stage tenant context switch.
 */
sealed interface TenantSwitchResult {
    data class Success(val newContext: TenantContext) : TenantSwitchResult
    data class AccessDenied(val reason: String) : TenantSwitchResult
    data class TransitionError(val throwable: Throwable) : TenantSwitchResult
}

/**
 * Enterprise Tenant Context Manager.
 * Executes the formal 5-stage security isolation protocol to prevent cross-tenant data leaks.
 */
interface TenantContextManager {
    val activeTenant: StateFlow<TenantContext?>
    suspend fun switchTenant(
        targetTenantId: String,
        targetOrganizationName: String,
        targetBranchId: String? = null,
        targetPermissions: Set<String> = emptySet()
    ): TenantSwitchResult
    fun isGenerationValid(generationToken: String): Boolean
}

/**
 * Production implementation of [TenantContextManager].
 */
class DefaultTenantContextManager(
    private val onCachePurgeRequested: () -> Unit = {}
) : TenantContextManager {
    private val mutex = Mutex()
    private val _activeTenant = MutableStateFlow<TenantContext?>(null)
    override val activeTenant: StateFlow<TenantContext?> = _activeTenant.asStateFlow()

    override suspend fun switchTenant(
        targetTenantId: String,
        targetOrganizationName: String,
        targetBranchId: String?,
        targetPermissions: Set<String>
    ): TenantSwitchResult = mutex.withLock {
        try {
            // Stage 1: Validate target tenant identifier
            if (targetTenantId.isBlank()) {
                return TenantSwitchResult.AccessDenied("Invalid tenant identifier")
            }

            // Stage 2: Create new generation token to isolate in-flight requests
            val newGenerationToken = UUID.randomUUID().toString()

            // Stage 3: Purge stale tenant-specific in-memory caches
            onCachePurgeRequested()

            // Stage 4: Construct isolated new context
            val newContext = TenantContext(
                tenantId = targetTenantId,
                organizationName = targetOrganizationName,
                activeBranchId = targetBranchId,
                permissions = targetPermissions,
                generationToken = newGenerationToken,
                switchedAtEpochMillis = System.currentTimeMillis()
            )

            // Stage 5: Publish new active context
            _activeTenant.value = newContext
            TenantSwitchResult.Success(newContext)
        } catch (e: Exception) {
            TenantSwitchResult.TransitionError(e)
        }
    }

    override fun isGenerationValid(generationToken: String): Boolean {
        return _activeTenant.value?.generationToken == generationToken
    }
}

/**
 * Thread-safe Tenant-Partitioned In-Memory Cache.
 * Prevents out-of-order asynchronous responses from contaminating the active tenant.
 */
class TenantScopedCache<K : Any, V : Any>(
    private val tenantContextManager: TenantContextManager
) {
    private val cache = ConcurrentHashMap<String, ConcurrentHashMap<K, V>>()

    fun put(key: K, value: V, generationToken: String) {
        // Drop write if generation token is stale
        if (!tenantContextManager.isGenerationValid(generationToken)) {
            return
        }
        val tenantId = tenantContextManager.activeTenant.value?.tenantId ?: return
        cache.computeIfAbsent(tenantId) { ConcurrentHashMap() }[key] = value
    }

    fun get(key: K): V? {
        val tenantId = tenantContextManager.activeTenant.value?.tenantId ?: return null
        return cache[tenantId]?.get(key)
    }

    fun clearTenant(tenantId: String) {
        cache.remove(tenantId)
    }

    fun clearAll() {
        cache.clear()
    }
}

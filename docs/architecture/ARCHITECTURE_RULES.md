# ZenOS Architecture Governance: Dependency & Module Rules
**Document Version:** 1.0.0  
**Target Maturity:** 9.5/10 Enterprise Platform Standard  
**Scope:** Android Kotlin Platform, Feature Modules, Domain Contracts

---

## 1. Core Architectural Invariants

ZenOS enforces **Clean Architecture**, **Domain-Driven Design (DDD-Lite)**, and **SOLID Principles**.
All dependencies must flow **strictly inward** toward the Domain core.

```mermaid
flowchart TD
    subgraph Layer 3: Presentation & Outer Adapters
        UI[Jetpack Compose UI / ViewModels]
        DB[Room / SQLDelight Data Sources]
        NET[Retrofit / Ktor Client Adapters]
    end

    subgraph Layer 2: Application & Use Cases
        UC[Use Cases / Workflow Coordinators]
        REPO_IMPL[Repository Implementations]
    end

    subgraph Layer 1: Domain Core (Zero Framework Dependencies)
        ENTITIES[Domain Entities & Aggregates]
        VALUE_OBJECTS[Immutable Value Objects]
        REPO_INTERFACES[Repository Interfaces]
        VALIDATORS[Validation Rules & Engines]
    end

    UI -->|Calls| UC
    UC -->|Orchestrates| ENTITIES
    UC -->|Interacts with| REPO_INTERFACES
    REPO_IMPL -->|Implements| REPO_INTERFACES
    REPO_IMPL -->|Uses| DB
    REPO_IMPL -->|Uses| NET
```

---

## 2. Dependency Matrix & Forbidden Imports

| Source Layer | Target Layer | Status | Enforcement Mechanism |
|:---|:---|:---:|:---|
| `domain` | Android SDK (`android.*`, `androidx.*`) | 🔴 **FORBIDDEN** | ArchUnit / Gradle Compilation / Lint |
| `domain` | Database / ORM (`androidx.room.*`, SQL) | 🔴 **FORBIDDEN** | ArchUnit / Static Analysis |
| `domain` | Network / Serialization (`retrofit2.*`, `okhttp3.*`, `kotlinx.serialization`) | 🔴 **FORBIDDEN** | ArchUnit / Static Analysis |
| `presentation` | `domain` interfaces & models | 🟢 **ALLOWED** | Standard Compilation |
| `presentation` | `data` internal implementations | 🔴 **FORBIDDEN** | Package-Private / Module Boundary |
| `feature A` | `feature B` internal classes / ViewModels | 🔴 **FORBIDDEN** | ArchUnit / Api-Implementation module split |
| `feature A` | `feature B` Public API (`:features:*:api`) | 🟢 **ALLOWED** | Explicit Gradle Dependency |
| `core:navigation` | `feature` internal UI implementations | 🔴 **FORBIDDEN** | Route Registration Contracts |

---

## 3. Module Structure & Ownership Model

```
:app                             // Application Assembler & Dependency Injection Root
:core:common                     // Pure Kotlin Result types, Dispatcher Providers, Logger contracts
:core:design-system              // 14 Foundational Tokens, AppSurface, AppButton, AppScaffold
:core:navigation:api             // Typed Route contracts, Navigation Guards, Deep Link descriptors
:core:navigation:runtime         // NavHost orchestration, Adaptive Navigation Rails, Transitions
:core:security:api               // TenantContext, PermissionEvaluator, AuthTokenStore
:core:networking                 // Ktor / OkHttp client, Auth Interceptors, Exponential Backoff
:core:database                   // Room Driver, Migration Engine, Tenant-partitioned DAOs

:features:<feature-name>:api     // Public interfaces, Route declarations, Exported Models
:features:<feature-name>:domain  // Entities, Value Objects, Use Cases, Repository Contracts
:features:<feature-name>:data    // Repository Implementations, DTOs, Local & Remote Mappers
:features:<feature-name>:ui      // Jetpack Compose Screens, ViewModels, UI State Contracts
```

---

## 4. Public Feature Contract Standards

Features **never** expose internal state holders, Room entities, or ViewModels.
Features communicate exclusively through **Feature Public APIs**:

```kotlin
// In :features:employees:api
interface EmployeeFeatureApi {
    val detailRoute: String
    fun getEmployeeSummary(employeeId: String): Flow<EmployeeSummaryDto>
}

// Consumed in :features:payroll:domain without importing EmployeeViewModel or EmployeeScreen
class CalculatePayrollUseCase(
    private val employeeApi: EmployeeFeatureApi,
    private val payrollRepository: PayrollRepository
) {
    suspend operator fun invoke(employeeId: String, period: PayrollPeriod): Result<PayrollSlip> {
        // Safe, decoupled coordination
    }
}
```

---

## 5. Architectural Quality Gate Checklist (PR Review)

Before merging any pull request:
1. [ ] **Zero Android Imports in Domain**: Check all files in `**/domain/**`.
2. [ ] **No Cross-Feature Internal Leakage**: Features only import `:api` modules of peer features.
3. [ ] **No Concrete Repository Coupling**: ViewModels only inject `UseCase` or `Repository` interfaces.
4. [ ] **Immutable UI State**: State exposed to Compose is `val uiState: StateFlow<UiState>` containing `@Immutable` data classes.
5. [ ] **Idempotency & Tenant Scoping**: All mutation operations accept a `tenantId` and `idempotencyKey`.

# ZenOS Android Platform (Material 3 & Clean Architecture)

An enterprise-grade Android application platform built with **Kotlin 2.0**, **Jetpack Compose (Material 3)**, **Clean Architecture + DDD-Lite**, **14 Foundational Design Tokens**, **Declarative Navigation**, and **Tactile Micro-Interactions**.

---

## 🏛️ Architecture Governance & Documentation Index

For in-depth architectural specifications, lifecycle contracts, and quality verification standards, refer to the platform documentation:

| Document | Description | Target Standard |
|:---|:---|:---:|
| 📜 [**`ARCHITECTURE_RULES.md`**](file:///home/albin-abraham-george/Project/android-kotlin/docs/architecture/ARCHITECTURE_RULES.md) | Inward dependency direction, forbidden imports, module isolation, and PR review checklist. | 9.5/10 Enterprise |
| 🔄 [**`APPLICATION_LIFECYCLE.md`**](file:///home/albin-abraham-george/Project/android-kotlin/docs/architecture/APPLICATION_LIFECYCLE.md) | Cold bootstrap, session recovery, 5-stage tenant context switching protocol, and state ownership. | 9.5/10 Enterprise |
| 🗺️ [**`NAVIGATION_ARCHITECTURE.md`**](file:///home/albin-abraham-george/Project/android-kotlin/docs/architecture/NAVIGATION_ARCHITECTURE.md) | Typed route contracts (`ZenOsRoute`), asynchronous route guards, deep links, and adaptive multi-pane navigation. | 9.5/10 Enterprise |
| 🧪 [**`ARCHITECTURE_TESTING.md`**](file:///home/albin-abraham-george/Project/android-kotlin/docs/architecture/ARCHITECTURE_TESTING.md) | ArchUnit automated dependency tests, tenant isolation tests, performance budgets, and ADR index. | 9.5/10 Enterprise |

---

## 📂 Project Structure

```
com.example.myapp/
│
├── core/                                    # Shared Core Infrastructure & Frameworks
│   ├── domain/                              # Pure Domain Layer (Zero Android/UI dependencies)
│   │   ├── validation/                      # Reusable Strategy-based Field Validators & ValidationEngine
│   │   │   ├── FieldValidator.kt            # Strategy interface (ISP / LSP)
│   │   │   ├── Validators.kt                # Required, MinLength, Email, Regex implementations
│   │   │   └── ValidationEngine.kt          # Fluent composite validation rule chaining
│   │   └── form/                            # Declarative Schema Descriptors
│   │       └── FormFieldDescriptor.kt       # Polymorphic field types (Text, Number, Toggle, Checkbox, Selection)
│   │
│   └── ui/                                  # Design System & UI Composition Engines
│       ├── theme/                           # 14 Foundational Design Tokens & Constraints
│       │   ├── DesignTokens.kt              # Spacing, Elevation, Borders, Icons, Alpha, Motion, Breakpoints
│       │   ├── DesignConstraints.kt         # Responsive layout constraints & density checks
│       │   ├── Color.kt                     # Light & Dark tonal palettes
│       │   ├── Type.kt                      # M3 Typography tokens
│       │   ├── Shape.kt                     # M3 Shape tokens
│       │   └── Theme.kt                     # Dynamic color + M3 Surface Container support
│       ├── surface/                         # M3 Surface Management
│       │   └── SurfaceManager.kt            # 5-tier Surface container elevation manager (LOWEST to HIGHEST)
│       ├── layouts/                         # Layout & Alignment Primitives
│       │   ├── AppScaffold.kt               # Standardized M3 TopBar & Scroll-aware auto-hide AppBottomBar
│       │   ├── AppLayoutPrimitives.kt       # Token-aware AppColumn, AppRow, and FontOverlayContainer
│       │   ├── DetailSlotsScaffold.kt       # Slot-based template (Template Method Pattern)
│       │   └── AdaptiveLayout.kt            # Responsive multi-pane & orientation layouts
│       ├── motion/                          # Motion & Physics Micro-Interactions
│       │   └── TactileModifier.kt           # bounceClick modifier with spring physics and haptics
│       ├── state/                           # Resilient UI State Containers
│       │   ├── UiStateRenderer.kt           # Polymorphic Loading/Empty/Error/Success state manager
│       │   ├── EmptyStateWidget.kt          # Standardized empty state artwork & action callout
│       │   └── ErrorRetryBanner.kt          # Accessible inline error banner with retry action
│       ├── network/                         # Connectivity & Offline Sync
│       │   ├── NetworkMonitor.kt            # Live network connectivity callback flow
│       │   └── OfflineSyncBanner.kt         # Animated top glide banner for offline state
│       └── components/                      # Reusable UI Primitives
│           ├── form/
│           │   └── DynamicForm.kt           # Config-driven M3 Form renderer with autofill & touch targets
│           └── common/
│               └── AppButton.kt             # Material 3 Button with built-in loading and variant styling
│
├── features/                                # Feature Modules (Vertical Slices / DDD-lite)
│   ├── auth/                                # Authentication Feature Suite
│   │   ├── domain/                          # Domain Entities & Value Objects (User, AuthCredentials)
│   │   ├── data/                            # Repository Implementations (AuthRepositoryImpl)
│   │   └── presentation/                    # Compose UI, Routes & Subcomponents
│   │       ├── LoginRoute.kt / LoginScreen.kt
│   │       ├── SignUpRoute.kt / SignUpScreen.kt
│   │       ├── ForgotPasswordScreen.kt
│   │       └── components/                  # AuthCard, AuthHeader, AuthFooter, AuthErrorBanner
│   │
│   └── home/                                # Home & Dashboard Feature
│       └── presentation/
│           └── HomeScreen.kt                # Enterprise Dashboard with KPI metrics & scroll-aware bottom bar
│
├── navigation/                              # App Routing & Navigation
│   ├── Screen.kt                            # Sealed class route definitions
│   └── NavGraph.kt                          # Centralized NavHost registering all feature destinations
│
└── MainActivity.kt                          # Single-Activity entry point with edge-to-edge support
```

---

## 💎 Key Architectural & UX Capabilities

### 1. 14 Foundational Design Token Pillars
All spacing, elevations, borders, icon sizes, alpha levels, motion timings, and breakpoints are governed centrally in [`DesignTokens.kt`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/theme/DesignTokens.kt) and [`DesignConstraints.kt`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/theme/DesignConstraints.kt).

### 2. Scroll-Based Bottom Navigation Auto-Hide
[`AppBottomBarScrollBehavior`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/layouts/AppScaffold.kt#L30-L65) automatically slides the bottom navigation bar out of view on downward scroll, and smoothly springs it back on upward scroll or boundary reach.

### 3. Tactile Spring Micro-Interactions
Interactive elements utilize `.bounceClick()` from [`TactileModifier.kt`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/motion/TactileModifier.kt) with realistic spring physics (`Spring.DampingRatioMediumBouncy`) and contextual haptic feedback.

### 4. Token-Aware Layout Primitives & Font Contrast Protection
[`AppColumn`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/layouts/AppLayoutPrimitives.kt#L19), [`AppRow`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/layouts/AppLayoutPrimitives.kt#L53), and [`FontOverlayContainer`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/layouts/AppLayoutPrimitives.kt#L86) ensure automatic token spacing and WCAG AAA contrast ratio preservation across gradients and elevated surfaces.

### 5. Resilient UI State Containers & Offline Awareness
Polymorphic [`UiStateRenderer`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/state/UiStateRenderer.kt) smoothly transitions between `Loading`, `Empty`, `Error`, and `Success` states, while [`OfflineSyncBanner`](file:///home/albin-abraham-george/Project/android-kotlin/app/src/main/java/com/example/myapp/core/ui/network/OfflineSyncBanner.kt) alerts users to connectivity state changes in real time.

---

## ⚡ Developer Automation (`Makefile`)

A root [`Makefile`](file:///home/albin-abraham-george/Project/android-kotlin/Makefile) is provided for high-speed CLI developer ergonomics:

```bash
make help       # List all available build and test commands
make test       # Execute unit test suites (./gradlew testDebugUnitTest)
make build      # Assemble debug APK (./gradlew assembleDebug)
make install    # Build and install APK onto connected device / emulator
make run        # Install and start the main activity on device
make clean      # Clean Gradle caches and build directories
make logs       # Stream live logcat filtered for com.example.myapp
make restart    # Force-stop and restart the application on device
```
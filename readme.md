# Android Kotlin - Material 3 & Clean Architecture

A modern Android application built with **Kotlin 2.0**, **Jetpack Compose (Material 3)**, **Clean Architecture + DDD-lite**, **Config-Driven UI Forms**, and the **DetailSlots Pattern**.

---

## 🏛️ Architecture & Project Structure

The project strictly follows **Clean Architecture**, **SOLID Principles**, and **Material Design 3 Expressive Tiering**:

```
com.example.myapp/
│
├── core/                                    # Shared Core Infrastructure & Frameworks
│   ├── domain/                              # Pure Domain Layer (Zero Android/UI dependencies)
│   │   ├── validation/                      # Reusable Strategy-based Field Validators
│   │   │   ├── FieldValidator.kt            # Strategy interface (ISP / LSP)
│   │   │   └── Validators.kt                # Required, MinLength, Email implementations
│   │   └── form/                            # Declarative Schema Descriptors
│   │       └── FormFieldDescriptor.kt       # Polymorphic field types (Text, Number, Toggle, Selection)
│   │
│   └── ui/                                  # Design System & UI Composition Engines
│       ├── theme/                           # Material 3 Tonal Color Scheme & Typography
│       │   ├── Color.kt                     # Light & Dark tonal palettes
│       │   ├── Type.kt                      # M3 Typography tokens
│       │   ├── Shape.kt                     # M3 Shape tokens
│       │   └── Theme.kt                     # Dynamic color + M3 Surface Container support
│       ├── surface/                         # M3 Surface Management
│       │   └── SurfaceManager.kt            # 5-tier Surface container elevation manager
│       ├── layouts/                         # Layout & Alignment Engine
│       │   ├── DetailSlotsScaffold.kt       # Slot-based template (Template Method Pattern)
│       │   └── AdaptiveLayout.kt            # Responsive multi-pane & orientation layouts
│       └── components/                      # Reusable UI Primitives
│           ├── form/
│           │   └── DynamicForm.kt           # Config-driven M3 Form renderer (Factory Pattern)
│           └── common/
│               └── AppButton.kt             # Material 3 Button with built-in loading states
│
├── features/                                # Feature Modules (Vertical Slices / DDD-lite)
│   ├── auth/                                # Authentication Feature
│   │   ├── domain/
│   │   │   ├── model/                       # Domain Entities & Value Objects (User, AuthCredentials)
│   │   │   ├── repository/                  # Domain Repository Interfaces (AuthRepository)
│   │   │   └── usecase/                     # Use Cases (LoginUseCase)
│   │   ├── data/
│   │   │   └── repository/                  # Repository Implementations (AuthRepositoryImpl)
│   │   └── presentation/                    # Compose UI, Contracts & Coordinators
│   │       ├── AuthContract.kt              # Immutable UI State & Intent definitions
│   │       ├── AuthViewModel.kt             # Controller / Intent Dispatcher & Schema Configs
│   │       ├── LoginScreen.kt               # Sign In screen (DetailSlots + DynamicForm)
│   │       ├── SignUpScreen.kt              # Sign Up screen (DetailSlots + DynamicForm)
│   │       └── ForgotPasswordScreen.kt      # Password recovery screen
│   │
│   └── home/                                # Home & Dashboard Feature
│       └── presentation/
│           ├── HomeScreen.kt                # M3 Expressive Dashboard with Surface Container Tiers
│           └── SplashScreen.kt              # Material 3 Splash Transition
│
├── navigation/                              # App Routing & Navigation
│   ├── Screen.kt                            # Sealed class route definitions
│   └── NavGraph.kt                          # Centralized NavHost registering all feature destinations
│
└── MainActivity.kt                          # Single-Activity entry point with edge-to-edge support
```

---

## 💎 Key Design Patterns & Engineering Highlights

### 1. Config-Driven Forms
Forms are defined as pure domain schemas (`FormFieldDescriptor`) and rendered automatically via `DynamicForm`. Adding a new field requires zero boilerplate in UI screens.

### 2. DetailSlots Pattern
`DetailSlotsScaffold` provides standardized, accessible screen scaffolding (`headerSlot`, `mediaSlot`, `keyDetailsSlot`, `actionSlot`) across all app features, ensuring consistent handling of window insets (`innerPadding`, `imePadding`, `navigationBarsPadding`).

### 3. Material 3 Surface Manager
Enforces the 5-tier Material 3 surface container model (`LOWEST`, `LOW`, `BASE`, `HIGH`, `HIGHEST`) with dynamic theme switching and tonal depth.

### 4. SOLID & GoF Patterns Enforced
- **Single Responsibility (SRP)**: Domain entities enforce invariants; ViewModels orchestrate use cases; Composables only render UI.
- **Open/Closed (OCP)**: New field types and slot components can be added without modifying existing code.
- **Strategy Pattern**: `FieldValidator` strategies (`required`, `email`, `minLength`).
- **Template Method / Slot Pattern**: `DetailSlotsScaffold` standardizing screen flow.
- **Command / Intent Pattern**: `AuthIntent` for unidirectional data flow (UDF).

---

## 🧪 Testing

Domain rules, validators, and use cases are isolated with 100% unit test coverage in `app/src/test/java/`:
- `ValidatorsTest.kt` — Tests validation strategies for null, blank, length, and regex rules.
- `LoginUseCaseTest.kt` — Tests business logic invariants without framework mocks.
# Metalla Project Roadmap

This roadmap outlines the planned evolution of Metalla, prioritizing architectural purity, technical modernization, code quality, and agentic readiness before feature expansion.

---

## 🏗 Summary of Categories & Work Units

| Category | Description | Priority |
| :--- | :--- | :--- |
| **TECH** | Build system, dependencies, modern Kotlin APIs, dead code cleanup | **P0 (Immediate)** |
| **ARCH** | Clean Architecture boundaries, domain purity, data layer scaffolding, UDF | **P0 (Immediate)** |
| **QUALITY** | Compose UI testing, Room/Repo testing, static analysis & linting | **P1 (High)** |
| **AGENTIC** | Agent guidelines, architecture decision records, token-efficient docs | **P1 (High)** |
| **DESIGN** | Material 3 signature dark theme completion, atomic UI components | **P2 (Medium)** |
| **FEATURE** | End-user product capabilities (Holdings -> Live Prices -> Analytics) | **P3 (Planned)** |

---

## 1. 🛠 Technical & Build Foundations (TECH)

- [ ] **TECH-1: Remove Dead Code & Fix Timber Planting**
  - Delete unused `core/util/Klog.kt` (conflicts with Timber logging standards).
  - Update `App.kt` to only plant `Timber.DebugTree()` when `BuildConfig.DEBUG` is true.
- [ ] **TECH-2: Modern Date/Time Migration (`kotlinx-datetime`)**
  - Add `kotlinx-datetime` to `gradle/libs.versions.toml`.
  - Replace `java.util.Date` across domain models (`Product`, `Item`, `Supplier`) with `kotlinx.datetime.Instant`.
- [ ] **TECH-3: Gradle Quality & Release Optimization**
  - Enable and configure Android Lint in `app/build.gradle.kts` (`abortOnError = true`, `checkDependencies = true`).
  - Configure release build type with `isMinifyEnabled = true` and tuned ProGuard/R8 rules.
- [ ] **TECH-4: KSP & Room Persistence Dependencies**
  - Add KSP plugin and Room (`room-runtime`, `room-compiler`, `room-ktx`) to `libs.versions.toml` and `app/build.gradle.kts`.
- [ ] **TECH-5: Network Client Dependencies**
  - Evaluate and integrate HTTP client dependencies for future live metals price API (Ktor Client or Retrofit + OkHttp with `kotlinx.serialization`).

---

## 2. 🏛 Architecture & Clean Layering (ARCH)

- [ ] **ARCH-1: Domain Model Decoupling & Typification**
  - Remove Android framework resource ID (`drawableId: Int`) from `Product.kt`; decouple domain from Android UI dependencies.
  - Eliminate primitive obsession: Introduce domain enums `MetalType` (Gold, Silver, Platinum, Palladium), `ProductForm` (Bar, Coin, Round, Grain), and `WeightUnit` (Gram, Troy Ounce, Kilogram) with conversion utilities.
  - Replace floating-point `Double` in `Item.price` with safe financial representation (e.g., `BigDecimal` or minor currency units `Long` / `Money` value class).
- [ ] **ARCH-2: Data Layer Scaffolding (Offline-First SSOT)**
  - Establish `core/data/local/`: Room Database (`MetallaDatabase`), DAOs, Entities, and TypeConverters.
  - Establish `core/data/remote/`: DTO models, API services, network data source.
  - Establish `core/data/repository/`: Domain repository interfaces (`ProductRepository`, `PortfolioRepository`) and implementations.
  - Implement bidirectional mappers (`Entity <-> Domain`, `DTO <-> Domain`).
- [ ] **ARCH-3: Unidirectional Data Flow (UDF) & UI State Standardization**
  - Standardize feature ViewModels to expose a single immutable `StateFlow<UiState>` (`HomeUiState`, `DashboardUiState`, `SettingsUiState`).
  - Standardize UI action dispatch using the `UiAction` pattern (e.g., `fun onAction(action: HomeAction)`).
- [ ] **ARCH-4: Global Scaffold & Navigation Enhancements**
  - Enhance `MetallaApp` Scaffold with dynamic top bar titles, navigation action callbacks, and a shared `SnackbarHostState`.
  - Define type-safe parameterized destinations for detail screens (e.g., `@Serializable data class ProductDetail(val id: Long)`).
- [ ] **ARCH-5: Koin Dependency Injection Modularization**
  - Split `di/Modules.kt` into dedicated modules (`coreModule`, `domainModule`, `dataModule`, `featureModule`).

---

## 3. 🧪 Code Quality & Testing (QUALITY)

- [ ] **QUALITY-1: Test Suite Cleanup & Template Removal**
  - Remove starter template tests (`ExampleUnitTest.kt`, `ExampleInstrumentedTest.kt`).
- [ ] **QUALITY-2: Compose UI Component & Screen Testing**
  - Add Compose UI tests (`createComposeRule()`) verifying stateless `*Content` composables (`HomeContent`, `DashboardContent`, `SettingsContent`, `MetallaAppContent`).
- [ ] **QUALITY-3: Data Layer & Room In-Memory Unit Tests**
  - Implement Room in-memory database tests for DAOs.
  - Implement repository unit tests using MockK and test dispatchers.
- [ ] **QUALITY-4: Static Analysis (Detekt & Lint)**
  - Integrate Detekt with rules for Compose, Coroutines, and naming conventions.
- [ ] **QUALITY-5: Local Verification Script**
  - Create a single verification script (`./scripts/verify.sh`) to run formatting, unit tests, and lint in one step.

---

## 4. 🤖 Agentic AI & Developer Experience (AGENTIC)

- [x] **AGENTIC-1: Feature Implementation Recipe in `AGENTS.md`**
  - Standardized checklist for implementing new features across Domain, Data, Presentation, and Tests.
- [x] **AGENTIC-2: Architecture Directory Map & Boundaries in `AGENTS.md`**
  - Logical layer boundaries, package structure, and strict import rules.
- [x] **AGENTIC-3: Compose Hygiene & Stability Rules in `AGENTS.md`**
  - Explicit rules for `@Immutable`, `@Stable`, state hoisting, and preview conventions.
- [ ] **AGENTIC-4: Architecture Decision Records (ADRs)**
  - Document key architectural decisions in `docs/adr/` (Single-module structure, Koin DI, Jetpack Compose, Offline-first Room SSOT).
- [x] **AGENTIC-5: Precious Metals Domain Glossary**
  - Domain glossary in `docs/GLOSSARY.md` (troy ounces, fineness, spot prices, bid/ask spreads) to prevent domain mistakes.

---

## 5. 🎨 UI/UX & Design System (DESIGN)

- [ ] **DESIGN-1: Refine Signature Dark Brand Theme & Complete Color Roles**
  - Complete Material 3 color role tokens for the signature dark gold theme (`surfaceVariant`, `outline`, `error`, container roles).
  - Make `Surface` modifier in `MetallaTheme` configurable (avoiding mandatory `fillMaxSize()` in preview subcomponents).
- [ ] **DESIGN-2: Reusable Atomic UI Components**
  - Create reusable core components (`MetallaCard`, `MetallaTopAppBar`, `LoadingState`, `ErrorState`, `EmptyState`).

---

## 6. ✨ Product Feature Roadmap (FEATURE)

- [ ] **FEATURE-1: Portfolio Holdings Management**
  - Add, edit, and delete precious metal items (bars, coins) and associate with products and suppliers.
- [ ] **FEATURE-2: Portfolio Overview & Holding Details UI**
  - Total portfolio value, weight by metal type (gold, silver, platinum, palladium), and item list.
- [ ] **FEATURE-3: Live Metal Price Integration**
  - Integrate live metals spot price API (e.g. GoldAPI, Metals-API).
  - Implement Single Source of Truth (SSOT): network fetches update Room DB, UI strictly observes Room via `Flow`.
- [ ] **FEATURE-4: Background Sync & Price Alerts**
  - Periodic background sync via WorkManager; notifications for target spot price alerts.
- [ ] **FEATURE-5: Historical Performance Charts & Valuation**
  - Historical metal price charts, profit/loss calculations against purchase prices.
- [ ] **FEATURE-6: Multi-Currency & Data Portability**
  - Display values in multiple fiat currencies (USD, EUR, GBP, CHF); CSV/JSON portfolio import & export.



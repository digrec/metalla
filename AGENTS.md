# Agentic Development Guidelines

Guidelines and rules for AI agents and human contributors to Metalla. Keep this file compact, high-density, and token-friendly.

## 🏗 Architecture & Logical Package Boundaries

Metalla uses a consolidated single-module (`:app`) structure with strict logical layering:

```text
com.digrec.metalla/
├── core/
│   ├── domain/        # Pure Kotlin: business logic, UseCases, repository interfaces, models
│   ├── data/          # Room DB/DAOs, network DTOs, repository impls, model mappers
│   ├── ui/theme/      # Signature dark theme (MetallaTheme, Color, Type)
│   └── util/          # Shared utilities (Timber logging, dispatchers)
├── features/[name]/   # [Feature]Screen (stateful), [Feature]Content (stateless), [Feature]ViewModel, UiState, UiAction
├── ui/                # App shell (MainActivity, MetallaApp, MetallaNavHost, MetallaDestination)
└── di/                # Koin modules (Modules.kt)
```

### Layer Rules
- **Domain Layer:** Pure Kotlin only. **Never import `android.*`** (no Android resource IDs like `drawableId`). Use `Instant` for timestamps and `BigDecimal` / integer cents for money. Use typed enums (`MetalType`, `ProductForm`) instead of raw Strings. See [GLOSSARY.md](docs/GLOSSARY.md).
- **Data Layer:** Implements Domain repository interfaces. Network DTOs and Room Entities are private to Data and must be mapped to/from Domain models.
- **Presentation Layer:** Interacts with Domain strictly via UseCases or Repositories. Consumes and renders Domain models.
- **DI:** Use [Koin](https://insert-koin.io/). Register in `di/Modules.kt` (`singleOf`, `viewModelOf`).

## 📝 Coding Standards & Patterns

- **Kotlin First:** Idiomatic Kotlin (`when`, `let`, extensions, value classes).
- **Unidirectional Data Flow (UDF):**
  - **State:** Each ViewModel exposes a single immutable `StateFlow<[Feature]UiState>`.
  - **Actions:** ViewModels receive user events via a sealed interface `[Feature]UiAction` (e.g., `fun onAction(action: HomeUiAction)`).
- **Error Handling:** Use `MetallaResult<T>` (`Success`, `Error`, `Loading`) with functional operators (`map`, `onSuccess`, `onError`).
- **Coroutines & Threading:** Never hardcode `Dispatchers.IO` or `Dispatchers.Main`. Always inject `DispatcherProvider`.
- **Logging:** Use `Timber`. Strictly avoid `android.util.Log` and `java.util.logging`.
- **Theming:** Metalla has a **single signature dark brand theme** (`MetallaTheme`). Keep UI dark-optimized and consistent.
- **Formatting:** Enforced via `ktfmt` with `kotlinLangStyle`. Run `./gradlew ktfmtFormat` before committing.

## 🎨 Compose & UI Rules

- **Stateful / Stateless Split:**
  - `[Feature]Screen`: Handles DI (`koinViewModel()`), lifecycle-aware state collection (`collectAsStateWithLifecycle`), and action dispatch.
  - `[Feature]Content`: Pure, stateless UI accepting state and lambdas.
- **Previews:** Always provide `@Preview` targeting the stateless `[Feature]Content` wrapped in `MetallaTheme`.
- **Compose Stability:** Ensure UI state classes are `@Immutable` or `@Stable`. Avoid instantiating lambdas or objects inside composable loops.

## 🧪 Testing Guidelines

- **Unit Tests:** MockK + Turbine + `kotlinx-coroutines-test`.
- **Dispatchers in Tests:** Always use `MainDispatcherRule` and `TestDispatcherProvider`.
- **Stateless UI Tests:** Test Compose UI using `createComposeRule()` against stateless `[Feature]Content`.
- **Fakes over Mocks:** For repositories and data sources, prefer test fakes over deep mocking when practical.

## 📋 Feature Implementation Recipe

When implementing a feature or unit of work:
1. **Domain:** Define pure Models, Repository interfaces, and `UseCase` / `FlowUseCase`.
2. **Data:** Define Room Entity/DAO or Remote DTO, implement mappers, and implement Repository. Bind in Koin.
3. **Presentation:** Define `UiState`, `UiAction`, `ViewModel`, stateless `Content`, stateful `Screen`, and `@Preview`.
4. **Navigation:** Add type-safe destination to `MetallaDestination` and route in `MetallaNavHost`.
5. **Verify:** Run verification commands (see below).

## ⚡ Verification Commands

Run these checks to verify code integrity:
```bash
./gradlew ktfmtCheck           # Check code formatting
./gradlew testDebugUnitTest    # Run all unit tests
./gradlew lintDebug            # Run Android Lint
./gradlew check                # Comprehensive check
```

## 📦 Git Commits
- Use **Conventional Commits** (`feat:`, `fix:`, `refactor:`, `chore:`, `docs:`).
- Always include AI co-author footer:
  `Co-authored-by: [Agent Name] <[agent@email.com]>`


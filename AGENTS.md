# Agentic Development Guidelines

This document provides context and rules for AI agents contributing to Metalla. Following these guidelines ensures consistency and high-quality code.

## 🏗 Architectural Principles

- **Clean Architecture:** Maintain strict separation between layers.
    - **Physical Structure:** Physical Gradle modules are merged into a single module (`:app`), but **logical separation** of layers (Domain, Data, Presentation) must remain strictly enforced via packages.
    - **Domain Layer:** Business logic, models, and repository interfaces. No dependencies on other layers.
    - **Data Layer:** Implementation of repositories and data sources (Local/Remote). Depends on Domain.
    - **Presentation Layer:** UI screens, Composables, and ViewModels. Interacts with Domain via Use Cases or Repositories.
- **Dependency Injection:** Use [Koin](https://insert-koin.io/). Define modules in the top-level `di` package.
- **MAD (Modern Android Development):** Use Jetpack Compose, Material 3, and Compose Navigation with Type-Safe routes.
- **Dependencies:** Do not introduce new third-party libraries without explicit user permission. Stick to the defined tech stack.

## 📝 Coding Standards

- **Kotlin First:** Use idiomatic Kotlin (e.g., `when`, `let`, `apply`, `extension functions`).
- **Reactive Streams:** Use `StateFlow` and `SharedFlow` for state management and event propagation.
- **Naming:**
    - ViewModels: `[Feature]ViewModel`
    - Stateful Screens: `[Feature]Screen` (handles DI/State collection)
    - Stateless Content: `[Feature]Content` (pure UI for previews/testing)
    - UseCases: `[Action][Entity]UseCase` (e.g., `GetPortfolioItemsUseCase`)
- **Logging:** Use `Timber`. Avoid `Log.d/e`.
- **Formatting:** Code is strictly enforced using `ktfmt` with the `kotlinLangStyle` (4-space indent, official Kotlin conventions). Run `./gradlew ktfmtFormat` before committing.

## 🛠 Working with the Codebase

- **Finding Declarations:** Always search for existing patterns before implementing new features.
- **Compose Previews:** 
    - Always provide `@Preview` functions for UI components.
    - **Stateless Pattern:** Previews must use the stateless `[Feature]Content` version of a screen to avoid DI overhead and ensure performance.
- **Theming:** Use the centralized `MetallaTheme`. It includes a `Surface` that automatically applies background and text colors.
- **Git & Commits:**
    - Use **Conventional Commits** (e.g., `feat:`, `fix:`, `chore:`, `docs:`).
    - Always include a `Co-authored-by` footer in commit messages to indicate AI assistance, using your specific agent name and email.
      Example: `Co-authored-by: [Agent Name] <[agent@email.com]>`

## 🚀 Preferred Patterns

- **Navigation:** Use **Type-Safe Navigation** as defined in `ui.navigation`. Define new destinations as `@Serializable` objects/classes in `MetallaDestination`.
- **State Management:** Use `StateFlow` in ViewModels to expose UI state.
- **Error Handling:** Use the sealed `MetallaResult<T>` wrapper to propagate success/failure from the Data layer up to the Domain layer. Leverage functional helpers like `map`, `onSuccess`, and `onError` for transformations.
- **Threading & Coroutines:** Never hardcode `Dispatchers.IO` or `Dispatchers.Main`. Always inject the `DispatcherProvider` dependency (including `mainImmediate`) to maintain unit testability.
- **Business Logic:** Encapsulate all business logic in `UseCase` or `FlowUseCase` (see `core.domain`).
- **Model Mapping:** Maintain strict isolation between layer models. Network DTOs must be mapped to Domain Models in the Data layer, and Domain Models mapped to UI State in the Presentation layer.
- **UI Logic:** Keep Activities lean. Most logic should reside in ViewModels or the root `MetallaApp`. Use **Slot-based APIs** for high-level UI containers (like `Scaffold` wrappers).

## 🧪 Testing Guidelines

- **Unit Testing:** Use `MockK` for mocking and `Turbine` for testing `Flow` and `StateFlow`.
- **Coroutine Testing:** Use `MainDispatcherRule` in all ViewModel and UseCase tests to manage the Main dispatcher. Use `TestDispatcherProvider` to inject test dispatchers into components.
- **Stateless UI Testing:** Test UI components using their stateless `[Feature]Content` versions to keep tests fast and isolated.

# Agentic Development Guidelines

This document provides context and rules for AI agents contributing to Metalla. Following these guidelines ensures consistency and high-quality code.

## 🏗 Architectural Principles

- **Clean Architecture:** Maintain strict separation between layers.
    - **Note on Consolidation:** While physical Gradle modules may be merged into a single module (as per roadmap), the **logical separation** of layers (Domain, Data, Presentation) must remain strictly enforced via packages.
    - **Domain Layer:** Business logic, models, and repository interfaces. No dependencies on other layers.
    - **Data Layer:** Implementation of repositories and data sources (Local/Remote). Depends on Domain.
    - **Presentation Layer:** UI screens and ViewModels. Interacts with Domain via Use Cases or Repositories.
- **Dependency Injection:** Use [Koin](https://insert-koin.io/). Define modules in the top-level `di` package.
- **MAD (Modern Android Development):** Prefer Jetpack libraries and Kotlin-first APIs.
- **Dependencies:** Do not introduce new third-party libraries without explicit user permission. Stick to the defined tech stack.

## 📝 Coding Standards

- **Kotlin First:** Use idiomatic Kotlin (e.g., `when`, `let`, `apply`, `extension functions`).
- **Reactive Streams:** Use `StateFlow` and `SharedFlow` for state management and event propagation.
- **Naming:**
    - ViewModels: `[Feature]ViewModel`
    - Fragments: `[Feature]Fragment`
    - UseCases: `[Action][Entity]UseCase` (e.g., `GetPortfolioItemsUseCase`)
- **Logging:** Use `Timber`. Avoid `Log.d/e`.
- **Formatting:** Code is strictly enforced using `ktfmt` with the `kotlinLangStyle` (4-space indent, official Kotlin conventions). Run `./gradlew ktfmtFormat` before committing.

## 🛠 Working with the Codebase

- **Finding Declarations:** Always search for existing patterns before implementing new features.
- **Previews:** When working on UI, use Compose Previews (once migrated).
- **Git & Commits:**
    - Use **Conventional Commits** (e.g., `feat:`, `fix:`, `chore:`, `docs:`).
    - Always include a `Co-authored-by` footer in commit messages to indicate AI assistance, using your specific agent name and email.
      Example: `Co-authored-by: [Agent Name] <[agent@email.com]>`
- **Testing:**
    - Unit tests for Domain logic.
    - Unit tests for Data layer mapping and repository implementations.
    - UI tests for the Presentation layer (Espresso or Compose Test).

## 🚀 Preferred Patterns

- **State Management:** Use `StateFlow` in ViewModels to expose UI state.
- **Error Handling:** Use a sealed `Result<T>` wrapper (or similar) to propagate success/failure from the Data layer up to the Domain layer. Avoid throwing exceptions directly into the UI layer.
- **Threading & Coroutines:** Never hardcode `Dispatchers.IO` or `Dispatchers.Main`. Always inject `CoroutineDispatcher` dependencies to maintain unit testability.
- **Model Mapping:** Maintain strict isolation between layer models. Network DTOs must be mapped to Domain Models in the Data layer, and Domain Models mapped to UI State in the Presentation layer.
- **UI Logic:** Keep Fragments/Activities lean. Most logic should reside in ViewModels.
- **Resources:** Use standard Android resource naming conventions (`fragment_home.xml`, `ic_gold.xml`).

# Metalla Project Roadmap

This roadmap outlines the planned evolution of Metalla, from technical debt reduction to new feature implementation.

## 🛠 Technical Modernization (MAD)

- [x] **Migrate to Jetpack Compose:** Replace ViewBinding and XML layouts with declarative Compose UI.
- [x] **State Flow Migration:** Fully transition from `LiveData` to `StateFlow` for state management.
- [x] **Architectural Consolidation:** Simplify project structure by merging `:data` and `:domain` into a single `:app` module using a package-by-feature structure.
    - `ui/`: Top-level app scaffolding (e.g. MainActivity, future Compose root, global navigation).
    - `di/`: Composition Root (Koin modules wiring the entire app).
    - `core/`: Shared components (database, repository interfaces, shared domain/data logic, shared theme/design system).
    - `features/`: Package-by-feature UI and feature-specific logic.
- [x] **Version Catalog Optimization:** Ensure all dependencies are correctly managed in `libs.versions.toml`.
- [ ] **KSP Support:** Use KSP for any future annotation processing (e.g., Room, Hilt).
- [x] **Navigation Component for Compose:** Migrate to Compose Navigation (using type-safe routes).

## 🤖 Agent-Friendliness Improvements

- [x] **Standardize Use Cases:** Ensure all business logic is encapsulated in Use Cases (see `core.domain.UseCase`).
- [x] **Consistent Logging:** Standardize Timber usage across all modules.
- [x] **Code Formatting (ktfmt):** Enforce consistent code style across the project to reduce diff noise and improve agentic readability.
- [ ] **Better KDoc:** Improve documentation for core components to help agents understand intent.

## 🧪 Testing & Quality Strategy

- [x] **Unit Testing Foundations:**
    - [x] Target 80%+ coverage for `domain` logic (established with `MetallaResultTest` and `UseCaseTest`).
    - [ ] Target 70%+ coverage for `data` layer mapping and repositories.
- [ ] **UI & Integration Testing:**
    - [ ] Implement Espresso tests for critical View-based flows (e.g., Home, Settings).
    - [ ] Establish Compose Test suite for new UI components.

## ✨ Feature Roadmap

### V1: Foundations
- [x] Basic project structure and Clean Architecture modules setup.
- [ ] Implement Repository pattern and Use Cases.
- [ ] Initial UI for portfolio viewing (Currently placeholder).
- [ ] Local database persistence (Room).

### V2: Real-time Data
- [ ] Integrate external API for live metal prices (e.g., GoldAPI, Metals-API).
- [ ] **Single Source of Truth (SSOT):** Implement architecture where network fetches update local Room DB, and UI strictly observes Room via `Flow`.
- [ ] Background sync for price updates.
- [ ] Notification alerts for price targets.

### V3: Analytics & Advanced Tracking
- [ ] Historical performance charts.
- [ ] Support for different currencies.
- [ ] Import/Export portfolio data.

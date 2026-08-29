# Metalla

Metalla is a modern Android application for tracking precious metals portfolios. It focuses on clean architecture, modularization, and following Modern Android Development (MAD) guidelines.

![App Screenshot](https://via.placeholder.com/360x640?text=Metalla+UI+Premium+Gold)

## 🚀 Features

- Portfolio tracking for precious metals (Gold, Silver, Platinum, Palladium) - Initial setup.
- Real-time price updates (Planned).
- Detailed holdings overview (Planned).

## 🛠 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) with [Material 3](https://m3.material.io/)
- **Navigation:** [Compose Navigation](https://developer.android.com/guide/navigation/design/type-safety) with **Type-Safe Routes**
- **Architecture:** Clean Architecture (Domain, Data, Presentation)
- **Dependency Injection:** [Koin](https://insert-koin.io/)
- **Asynchronous Programming:** Coroutines & Flow
- **State Management:** StateFlow
- **Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
- **Logging:** [Timber](https://github.com/JakeWharton/timber)
- **Code Formatting:** [ktfmt](https://github.com/facebook/ktfmt) (Kotlinlang style)

## 📂 Project Structure

Metalla utilizes a consolidated single-module (`:app`) approach that strictly enforces layer separation through a package-by-feature structure:

- `ui/`: Top-level app scaffolding (e.g., `MainActivity`, `MetallaApp` root, and type-safe navigation definitions).
- `features/`: Package-by-feature UI and feature-specific business logic. Uses the **Stateful/Stateless Composable** pattern for optimal testability and preview performance.
- `core/`: Shared components (database, repository interfaces, shared domain/data logic, shared theme/design system).
- `di/`: Composition Root (Koin modules wiring the entire app together).

## 🛠 Getting Started

- **JDK Version:** Java 17 or higher.
- **Android Studio:** Ladybug or newer.
- **Minimum SDK:** API 24 (Android 7.0).

### 🎨 Code Style

The project uses `ktfmt` to maintain a consistent code style (official Kotlin conventions). You can format the code by running:

```bash
./gradlew ktfmtFormat
```

Code formatting is enforced; the build will fail if the code is not properly formatted.

## 🤖 Agentic Development

This project is optimized for AI-driven development. Please refer to [AGENTS.md](AGENTS.md) for detailed architectural guidelines, development patterns, and naming conventions.

## 🗺 Roadmap

See [ROADMAP.md](ROADMAP.md) for the current development status and future implementation milestones.

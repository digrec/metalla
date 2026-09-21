# Metalla

Metalla is a modern Android application for tracking precious metals portfolios (Gold, Silver, Platinum, Palladium). It adheres strictly to Modern Android Development (MAD) practices and Clean Architecture principles, featuring a signature premium dark brand theme.

![App Screenshot](https://via.placeholder.com/360x640?text=Metalla+UI+Premium+Gold)

## 🚀 Features

- **Portfolio Tracking:** Track physical precious metal holdings (bars, coins, rounds).
- **Signature Dark Experience:** Tailored dark gold aesthetic optimized for high contrast and financial data clarity.
- **Offline-First:** Single Source of Truth local persistence via Room Database with background sync (sync planned).
- **Real-time Valuation (Planned):** Live metals spot price updates and currency conversion.
- **Performance Analytics (Planned):** Historical charts and profit/loss calculation.

## 🛠 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/) (2.4.x)
- **UI & Theme:** [Jetpack Compose](https://developer.android.com/jetpack/compose) with [Material 3](https://m3.material.io/) (Signature Dark Brand Theme)
- **Navigation:** [Compose Navigation](https://developer.android.com/guide/navigation/design/type-safety) with **Type-Safe Routes**
- **Architecture:** Clean Architecture (Domain, Data, Presentation) with Unidirectional Data Flow (UDF) & Type-Driven Error Handling
- **Dependency Injection:** [Koin](https://insert-koin.io/)
- **Asynchronous Programming:** Coroutines & Flow (`StateFlow` UI state)
- **Serialization & Date/Time:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) & [Kotlinx DateTime](https://github.com/Kotlin/kotlinx-datetime)
- **Persistence:** [Room Database](https://developer.android.com/training/data-storage/room) with [KSP](https://github.com/google/ksp)
- **Logging:** [Timber](https://github.com/JakeWharton/timber)
- **Testing:** [MockK](https://mockk.io/), [Turbine](https://github.com/cashapp/turbine), AndroidX Room In-Memory Testing, Compose UI Test JUnit4
- **Formatting:** [ktfmt](https://github.com/facebook/ktfmt) (Kotlinlang style)

## 📂 Project Structure

Metalla utilizes a consolidated single-module (`:app`) approach that strictly enforces layer separation through a package-by-feature structure:

- `core/`:
  - `domain/`: Pure business logic, models, UseCases, and repository interfaces (no Android dependencies).
  - `data/`: Offline-first Room persistence, network clients, DTOs, and repository implementations.
  - `ui/theme/`: Centralized signature dark theme (`MetallaTheme`, typography, colors).
  - `util/`: Shared utilities and coroutine dispatchers.
- `features/`: Package-by-feature UI and ViewModels (using the **Stateful Screen / Stateless Content** pattern).
- `ui/`: Top-level app scaffolding (`MainActivity`, `MetallaApp` root, and type-safe navigation host).
- `di/`: Composition Root (Koin modules wiring the application).

## 🛠 Getting Started

- **JDK Version:** Java 17 or higher.
- **Android Studio:** Ladybug or newer.
- **Minimum SDK:** API 24 (Android 7.0).

### 🎨 Quality & Verification

Code formatting and test verification commands:

```bash
# Format code using ktfmt (official Kotlin conventions)
./gradlew ktfmtFormat

# Check code formatting without applying changes
./gradlew ktfmtCheck

# Run unit test suite
./gradlew testDebugUnitTest

# Compile instrumented Android tests (Room in-memory DB tests)
./gradlew compileDebugAndroidTestKotlin

# Run Android Lint
./gradlew lintDebug
```

## 🤖 Agentic Development

This project is optimized for both human and AI-assisted pair programming:
- Refer to [AGENTS.md](AGENTS.md) for architectural boundaries, UDF conventions, and the feature implementation recipe.
- Refer to [docs/adr/](docs/adr/) for Architecture Decision Records (ADRs).
- Refer to [docs/GLOSSARY.md](docs/GLOSSARY.md) for precious metals units and financial definitions.

## 🗺 Roadmap

See [ROADMAP.md](ROADMAP.md) for the categorized development roadmap (TECH, ARCH, QUALITY, AGENTIC, DESIGN, FEATURE).


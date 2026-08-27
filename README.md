# Metalla

Metalla is a modern Android application for tracking precious metals portfolios. It focuses on clean architecture, modularization, and following Modern Android Development (MAD) guidelines.

![App Screenshot](https://via.placeholder.com/360x640?text=Metalla+UI+Placeholder)

## 🚀 Features

- Portfolio tracking for precious metals (Gold, Silver, Platinum, Palladium) - Initial setup.
- Real-time price updates (Planned).
- Detailed holdings overview (Planned).

## 🛠 Tech Stack

- **Language:** [Kotlin](https://kotlinlang.org/)
- **Architecture:** Clean Architecture with MVVM
- **Dependency Injection:** [Koin](https://insert-koin.io/)
- **Asynchronous Programming:** Coroutines & Flow
- **Logging:** [Timber](https://github.com/JakeWharton/timber)
- **UI:** ViewBinding (Migration to Jetpack Compose planned)
- **Navigation:** Jetpack Navigation Component

## 📂 Project Structure

- `:app` - UI layer, DI setup, and Application class.
- `:data` - Future implementation of repositories and data sources (Local/Remote).
- `:domain` - Business logic models and logging (Will contain Use Cases and Repository interfaces).

## 🛠 Getting Started

- **JDK Version:** Java 17 or higher.
- **Android Studio:** Ladybug or newer.
- **Local Properties:** Ensure `local.properties` is configured if you add sensitive keys (e.g., API keys for prices).

## 🤖 Agentic Development

This project is optimized for AI-driven development. Please refer to [AGENTS.md](AGENTS.md) for architectural guidelines and development patterns.

## 🗺 Roadmap

See [ROADMAP.md](ROADMAP.md) for future plans and technical improvements.

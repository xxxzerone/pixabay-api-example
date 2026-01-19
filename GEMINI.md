# Project Overview

This project is an Android application built with **Jetpack Compose**, following **Clean Architecture** principles and utilizing the **MVVM + MVI** patterns.

## Tech Stack & Architecture

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Architecture Pattern**: MVVM (Model-View-ViewModel) + MVI (Model-View-Intent)
- **Architectural Style**: Clean Architecture (Presentation, Domain, Data layers)
- **Dependency Injection**: [Hilt](https://dagger.dev/hilt/)
- **Navigation**: [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- **Networking**: [Ktor](https://ktor.io/)

## Build Variants (Flavors)

The project is configured with the following build flavors:
- **dev**: For development and internal testing.
- **prod**: For production releases.

## Architecture Structure

### 1. Presentation Layer
- **Components**: Composables, ViewModels.
- **Pattern**: Uses MVI for unidirectional data flow. Events (Intents) are processed by the ViewModel, resulting in a new State.
- **Navigation**: Handled purely via Compose Navigation.

### 2. Domain Layer
- **Components**: UseCases, Repository Interfaces, Domain Models.
- **Responsibility**: Contains business logic and is independent of the Android framework.

### 3. Data Layer
- **Components**: Repository Implementations, Data Sources (API/DB), DTOs, Mappers.
- **Networking**: Ktor Client is used for making HTTP requests.
- **DI**: Hilt modules provide dependencies for Repositories and Data Sources.

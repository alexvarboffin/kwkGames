# GEMINI.md

## Project Overview

This is an Android application named **VAI**. It is a puzzle game, functionally a clone of "Unblock Me" or "Rush Hour," with a football (soccer) theme. The core gameplay involves sliding blocks within a grid to clear a path for a special "ball" block to reach an exit.

The application is built using modern Android development technologies:

*   **Language:** Kotlin
*   **UI:** Jetpack Compose
*   **Build:** Gradle
*   **Architecture:** The project aims for a clean architecture, separating concerns into Domain, Data, and Presentation (UI) layers.
*   **Key Libraries:**
    *   AndroidX (Lifecycle, Navigation, Room, DataStore)
    *   Coil for image loading
    *   Jetpack Compose for declarative UI

## Building and Running

This is a standard Android Gradle project.

### Using Android Studio

1.  Open the project in Android Studio.
2.  Let Gradle sync and download the necessary dependencies.
3.  Run the app on an emulator or a physical device using the "Run 'app'" configuration.

### Using Command Line

To build the application from the command line, you can use the Gradle wrapper:

*   **Build a debug APK:**
    ```bash
    ./gradlew assembleDebug
    ```
*   **Install on a connected device/emulator:**
    ```bash
    ./gradlew installDebug
    ```

## Development Conventions

The project follows specific architectural and coding guidelines:

*   **Clean Architecture:** Code should be organized into `domain`, `data`, and `presentation` layers.
    *   **Domain:** Contains business logic and interfaces.
    *   **Data:** Implements repositories and data sources (e.g., Room database, DataStore).
    *   **Presentation:** Contains ViewModels and UI state management.
    *   **UI:** Composable functions for displaying the UI.
*   **State Management:** UI state is managed using `StateFlow` from `ViewModel`. The state is exposed as a `StateFlow` and collected in the UI. Sealed classes are used to represent different UI states (e.g., `Loading`, `Success`, `Error`).
*   **Navigation:** Navigation is handled using a sealed `Route` class. Screens do not have direct knowledge of the navigator but instead expose callbacks.
*   **Dependency Injection:** The documentation suggests using a dependency injection framework like Koin.
*   **Testing:** The project is set up for unit and instrumentation tests. It is expected to write unit tests for ViewModels and business logic, and UI tests for composable functions.

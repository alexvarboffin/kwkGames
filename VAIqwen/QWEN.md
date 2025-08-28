# QWEN.md

## Project Overview

This is an Android application named **VAI**, a puzzle game with a football (soccer) theme. It's functionally a clone of "Unblock Me" or "Rush Hour," where players slide blocks within a grid to clear a path for a special "ball" block to reach an exit.

### Key Technologies
* **Language:** Kotlin
* **UI Framework:** Jetpack Compose
* **Architecture:** Clean Architecture with Domain, Data, and Presentation layers
* **Navigation:** AndroidX Navigation Compose
* **Dependency Injection:** Koin
* **Storage:** Local data source (no external database mentioned)
* **Image Loading:** Coil

### Project Structure
```
src/main/java/com/vai/vaidebet/vaibrazil/
├── data/                 # Data layer implementation
│   ├── datasource/       # Local data source with game levels
│   └── repository/       # Repository implementation
├── di/                   # Dependency injection modules
├── domain/               # Business logic and models
│   ├── model/            # Domain models (Block, GameLevel)
│   ├── repository/       # Repository interfaces
│   └── usecase/          # Use cases for business logic
├── presentation/         # ViewModels and UI state management
│   └── screens/          # Screen-specific ViewModels
├── ui/                   # Composable UI components
│   ├── composables/      # Reusable UI components
│   ├── screens/          # Screen-level composables
│   └── theme/            # App theme and styling
├── App.kt                # Main application composable
├── GameActivity.kt       # Game activity (not currently used)
├── MainActivity.kt       # Main application activity
├── SplashActivity.kt     # Splash screen activity
└── VAIApplication.kt     # Application class for DI initialization
```

## Building and Running

### Prerequisites
* Android Studio with Kotlin support
* Android SDK API level 36 (targetSdk)
* Minimum SDK API level 24 (minSdk)

### Using Android Studio
1. Open the project in Android Studio
2. Let Gradle sync and download dependencies
3. Run the app on an emulator or physical device using the "Run 'app'" configuration

### Using Command Line
To build and install from the command line:
```bash
# Build a debug APK
./gradlew assembleDebug

# Install on a connected device/emulator
./gradlew installDebug
```

## Development Conventions

### Architecture
The project follows Clean Architecture principles with clear separation of concerns:

1. **Domain Layer** (`domain/`)
   * Contains business logic and models
   * Defines repository interfaces
   * Implements use cases

2. **Data Layer** (`data/`)
   * Implements repositories
   * Provides data sources (currently only local)

3. **Presentation Layer** (`presentation/`)
   * Contains ViewModels for UI state management
   * Uses StateFlow for reactive state updates

4. **UI Layer** (`ui/`)
   * Composable functions for displaying the UI
   * Navigation between screens

### State Management
* UI state is managed using `StateFlow` from `ViewModel`
* Sealed classes represent different UI states (`Loading`, `Success`, `Error`, `Won`)
* State is collected in the UI using `collectAsState()`

### Navigation
* Navigation is handled using AndroidX Navigation Compose
* Screens are defined as sealed classes with routes
* Navigation is managed through a `NavController`

### Dependency Injection
* Uses Koin for dependency injection
* Modules are defined in `di/AppModule.kt`
* ViewModels are injected using `koinViewModel()` composable

### Game Logic
* Game levels are defined in `LocalDataSource`
* Each level consists of a grid with blocks of different orientations
* The goal is to move the target block (with balls) to the right edge of the grid
* Movement is validated to prevent overlaps and boundary violations
* Win condition is checked after each move

### UI Components
* Custom drawing using Canvas in Compose
* Touch gestures for block movement
* Football-themed visuals with green field background
* Star rating system based on move efficiency

## Testing
* Unit tests for ViewModels and business logic
* UI tests for composable functions (when implemented)
* Test setup is included in the project configuration

## Key Features
1. Splash screen with animated logo
2. Level selection screen
3. Interactive game board with draggable blocks
4. Move counter and grid toggle
5. Win screen with star rating based on performance
6. Level reset functionality
7. Privacy Policy and FAQ links

## Visual Design Requirements

### Color Scheme
* Primary colors: Yellow and black (as seen in the app icon)
* Football-themed backgrounds throughout the app
* Game board with football field design

### UI Screens
1. **Splash Screen** (`1.png`)
   * Separate SplashActivity as required
   * Animated "Click to start" button
   * Animated logo

2. **Main Menu** (`2.png`)
   * Slider menu for level selection
   * Privacy Policy and FAQ buttons that open in tabs
   * Animated logo

3. **Game Screen** (`3.1.png`, `3.2.png`)
   * Football field background
   * Grid toggle button
   * Back to menu button
   * Reset game button
   * Star rating system based on move efficiency

## Enhancement Requirements
* Add more varied block shapes and figures
* Improve graphics to be more engaging than the reference app
* Implement additional features as deemed appropriate
* Make the UI more entertaining while maintaining the core gameplay

## Technical Requirements
* Package name: `com.vai.vaidebet.vaibrazil`
* Links for Privacy Policy, FAQ, and Terms must open in browser tabs
* Splash screen must be a separate activity
* All architecture guidelines must be followed strictly
* Use sealed classes for UI states
* Implement proper error handling
* Write unit tests for ViewModels and business logic
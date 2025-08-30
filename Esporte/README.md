# Penalty Kicks - Android Port

This is a complete Android port of the HTML5 Penalty Kicks game, implemented using Kotlin and Jetpack Compose.

## Features

- Faithful recreation of the original HTML5 game mechanics
- Touch controls for shooting penalties
- Score tracking with multiplier system
- Sound effects and background music
- Multiple goalkeeper animations
- Player shooting animations

## Project Structure

```
app/
├── src/main/
│   ├── java/com/example/penaltikicks/
│   │   ├── App.kt              # Application class with constants
│   │   └── MainActivity.kt     # Main activity with Compose UI
│   ├── res/
│   │   ├── drawable/           # Game sprites and images
│   │   ├── raw/                # Sound files
│   │   └── values/             # String and color resources
│   └── AndroidManifest.xml
├── build.gradle.kts           # App build configuration
└── ...
```

## Building the App

### Prerequisites

- Android Studio Jellyfish (2023.3.1) or later
- Android SDK API Level 34
- Kotlin 1.9.22 or later
- JDK 17

### Building with Android Studio

1. Open the project in Android Studio
2. Wait for Gradle sync to complete
3. Select "Run > Run 'app'" or click the Run button

### Building from Command Line

1. Navigate to the project directory
2. Run `gradlew assembleDebug` (Windows) or `./gradlew assembleDebug` (Mac/Linux)
3. The APK will be generated at `app/build/outputs/apk/debug/app-debug.apk`

### Installing on Device

1. Connect an Android device with USB debugging enabled
2. Run `adb install app/build/outputs/apk/debug/app-debug.apk`

## Game Controls

- Tap and drag to aim and shoot
- The power of the shot is determined by the drag distance
- The goalkeeper will attempt to save the shot
- Score points by scoring goals
- The multiplier increases with consecutive goals

## Assets

All game assets (sprites, sounds) have been ported from the original HTML5 version:
- Background images
- Player and goalkeeper sprites
- Sound effects and music

## Technical Implementation

The game is implemented using:
- Jetpack Compose for UI rendering
- Coroutines for asynchronous operations
- MediaPlayer for audio playback
- Canvas API for custom drawing

## License

This is a port of a commercial game. The original game and assets are copyrighted by their respective owners.
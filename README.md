# Color Match

<img src="app/src/main/res/drawable/color_match_icon.png" width="120" alt="Color Match icon"/>

**Color Match** is my first Android application, originally created as a Java learning project.

Years later, I returned to the project and rebuilt it as a complete Android Studio project. The rebuilt version preserves the original game idea while fixing compatibility issues, improving the UI, and making the repository fully buildable.

## Demo

[Watch the rebuilt app demo](photo_video/color_match_demo_2026.mp4)

The demo was recorded on a real Android 11 device after the rebuild and final testing.

## What the App Does

Color Match is a simple memory-matching game:

- 16 cards are shuffled into four color groups
- Tap a card to reveal its color
- Match cards with the same color
- Matched cards remain cleared
- Use the shuffle button to start again

## 2026 Rebuild

The original repository only preserved a few source files from the first version.

The rebuilt project now includes a complete Android/Gradle structure and can be built directly from the repository.

Changes made during the rebuild include:

- Recreated the project in a modern Android Studio environment
- Migrated the original Java game logic and XML layout
- Removed dependence on the system button background for hidden cards
- Fixed Dark Mode behavior
- Prevented the activity from reshuffling when the system theme changes
- Updated card styling for modern Material components
- Added consistent spacing and slightly rounded card corners
- Added a custom application icon
- Tested the rebuilt app on a real Android 11 device
- Rebuilt and replaced the APK included in this repository

The full rebuild and debugging process is documented in:

[Read the Troubleshooting Log](Troubleshooting_Log.md)

## Build

### Requirements

- Android Studio or Android SDK
- JDK
- Minimum Android SDK: API 23
- Java
- Gradle Wrapper included in this repository

### Build the Debug APK

From the repository root:

```bash
./gradlew clean assembleDebug
```

The generated APK will be located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

A rebuilt APK is also located at:

[color_match.apk](color_match.apk)

You are welcome to directly download it and play it on your Android phone if you trust me.

## Repository Structure

```text
Color_Match/
├── app/                         # Complete rebuilt Android application
├── gradle/                      # Gradle wrapper and version configuration
├── code_file/                   # Original source files preserved from the first version
├── photo_video/                 # Original media and rebuilt demo video
├── color_match.apk              # Rebuilt APK
├── Original_Project_Archive.md  # Original screenshots, video, and presentation
├── Presentation.pdf             # Original project presentation
├── Troubleshooting_Log.md       # Rebuild and debugging record
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradlew
└── README.md
```

## Original Project Archive

The original screenshots, screen recording, setup images, and presentation are still preserved for historical reference.

[View the Original Project Archive](Original_Project_Archive.md)

The original source files I kept at the time are still available in:

```text
code_file/
├── activity_main.xml
├── MainActivity.java
└── themes.xml
```

They are intentionally preserved separately from the rebuilt `app/` project so the evolution of the project remains visible.

## About This Project

This repository is both a small Android application and a record of my learning process.

The first version reflects how I originally learned Android development. The rebuilt version shows how I later returned to an old project, investigated its compatibility problems, reconstructed the missing project structure, tested it on real hardware, and documented the process instead of simply replacing the old work.

That history is part of the project.

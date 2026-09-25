# Color Match

<img src="app/src/main/res/drawable/color_match_icon.png" width="120" alt="Color Match icon"/>

**Color Match** is my first Android application, originally created as a Java learning project.

Years later, I returned to the project and rebuilt it as a complete Android Studio project. The rebuilt version preserves the original game idea while fixing compatibility issues, improving the UI, and making the repository fully buildable.

## Demo

https://github.com/user-attachments/assets/9088f8bb-ff3a-487a-b2ee-66f1ed12e923

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

## Build from Source on Debian

Color Match can be built entirely from the command line. Android Studio is not required.

### Requirements

This project currently uses:

```text
JDK: 17
Compile SDK: Android API 37
Target SDK: Android API 37
Minimum SDK: Android API 23
Android Gradle Plugin: 9.4.1
Gradle Wrapper: 9.6.0
SDK Build Tools: 36.0.0
```

> `minSdk 23` means that the app can run on Android API 23 or newer.
> The build machine still needs Android API 37 because this project is compiled with `compileSdk 37`.

### 1. Install the basic tools

```bash
sudo apt update
( sudo apt upgrade -y )<- That is just a suggestion, not necessary

sudo apt install -y \
  git \
  openjdk-17-jdk \
  wget \
  unzip
```

Check Java:

```bash
java -version
```

It should report Java 17.

### 2. Clone the repository

```bash
cd ~

git clone https://github.com/noa-jou/Color_Match.git

cd Color_Match
```

### 3. Install the Android SDK Command-Line Tools

Create the Android SDK directory:

```bash
export ANDROID_HOME="$HOME/Android/Sdk"

mkdir -p "$ANDROID_HOME/cmdline-tools/latest"
```

Download the Android command-line tools:

```bash
cd /tmp

wget https://dl.google.com/android/repository/commandlinetools-linux-15859902_latest.zip
```

Extract them:

```bash
rm -rf /tmp/android-command-line-tools

mkdir -p /tmp/android-command-line-tools

unzip -q commandlinetools-linux-15859902_latest.zip \
  -d /tmp/android-command-line-tools
```

Move the tools into the Android SDK directory:

```bash
cp -r /tmp/android-command-line-tools/cmdline-tools/* \
  "$ANDROID_HOME/cmdline-tools/latest/"
```

Add the Android tools to the current shell:

```bash
export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"
```

Check that `sdkmanager` is available:

```bash
sdkmanager --version
```

### 4. Install the SDK packages required by Color Match

Accept the Android SDK licences:

```bash
yes | sdkmanager --licenses
```

Install the required Android SDK components:

```bash
sdkmanager \
  "platform-tools" \
  "platforms;android-37" \
  "build-tools;36.0.0"
```

### 5. Configure the SDK location for the project

Return to the repository:

```bash
cd ~/Color_Match
```

Create the local SDK configuration:

```bash
printf 'sdk.dir=%s\n' "$ANDROID_HOME" > local.properties
```

`local.properties` contains a machine-specific SDK path and is intentionally excluded from Git.

It should look similar to:

```text
sdk.dir=/home/your-user-name/Android/Sdk
```

### 6. Build the APK

Make sure the Gradle wrapper is executable:

```bash
chmod +x gradlew
```

Build a clean debug APK:

```bash
./gradlew clean assembleDebug
```

A successful build should end with:

```text
BUILD SUCCESSFUL
```

The generated APK will be located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Check it with:

```bash
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

### Optional: Install the APK on a connected Android device

If USB debugging is enabled and the device is visible through ADB:

```bash
adb devices
```

install the APK with:

```bash
adb install -r --no-streaming \
  app/build/outputs/apk/debug/app-debug.apk
```

The generated APK will be located at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

### A rebuilt APK by me is also located at:

```
color_match.apk
```

You are welcome to download, install, and play it directly on your Android phone if you trust me that much.



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

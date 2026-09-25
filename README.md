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

This procedure was tested on **Chromebook Linux (Debian 13 / Trixie)**.

> **Chromebook note:**  
> In the Linux Terminal, use `Ctrl + Shift + V` to paste.  
> `Ctrl + V` may appear as `^V` instead of pasting text.

### Project Requirements

The current project uses:

```text
Compile SDK: Android API 37
Target SDK: Android API 37
Minimum SDK: Android API 23
Android Gradle Plugin: 9.4.1
Gradle Wrapper: 9.6.0
Build Tools: 36.0.0
```

`minSdk 23` means that the app can run on Android API 23 or newer.

It does **not** mean that the build computer only needs Android API 23.

---

### 1. Install the basic tools

Update the Debian package list:

```bash
sudo apt update
```

Install Git, Java, Wget, and Unzip:

```bash
sudo apt install -y git default-jdk wget unzip
```

`default-jdk` is used instead of requesting a specific Java package because the available JDK version may differ between Debian releases.

For example, Debian 13 installed OpenJDK 21 during testing.

Verify the installation:

```bash
java -version
javac -version
git --version
wget --version | head -n 1
unzip -v | head -n 1
```

> `sudo apt upgrade` is optional and is not required just to build this project.

---

### 2. Clone Color Match

```bash
cd ~

git clone https://github.com/noa-jou/Color_Match.git

cd Color_Match
```

---

### 3. Create the Android SDK directory

Set the Android SDK location for the current Terminal session:

```bash
export ANDROID_HOME="$HOME/Android/Sdk"
```

Create the command-line tools directory:

```bash
mkdir -p "$ANDROID_HOME/cmdline-tools/latest"
```

---

### 4. Download the Android command-line tools

Move to a temporary directory:

```bash
cd /tmp
```

Download Google's Android SDK command-line tools:

```bash
wget https://dl.google.com/android/repository/commandlinetools-linux-15859902_latest.zip
```

Prepare a temporary extraction directory:

```bash
rm -rf /tmp/android-command-line-tools

mkdir -p /tmp/android-command-line-tools
```

Extract the package:

```bash
unzip -q commandlinetools-linux-15859902_latest.zip \
  -d /tmp/android-command-line-tools
```

Copy the command-line tools into the Android SDK:

```bash
cp -r /tmp/android-command-line-tools/cmdline-tools/* \
  "$ANDROID_HOME/cmdline-tools/latest/"
```

Add the Android tools to the current Terminal session:

```bash
export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"
```

Verify `sdkmanager`:

```bash
sdkmanager --version
```

A deprecation warning from `sdkmanager` does not necessarily mean that the installation failed.

---

### 5. Accept the Android SDK licences

```bash
yes | sdkmanager --licenses
```

Continue until you see:

```text
All SDK package licenses accepted
```

If this appears afterward:

```text
yes: standard output: Broken pipe
```

it can be ignored. It only means that `sdkmanager` finished before the `yes` command stopped sending input.

---

### 6. Check the available Android 37 SDK package

Do not assume that the package is named exactly:

```text
platforms;android-37
```

Check the available Android platforms first:

```bash
sdkmanager --list | grep -E '^  platforms;android-|^platforms;android-'
```

You can also check the available Build Tools:

```bash
sdkmanager --list | grep 'build-tools;'
```

During testing on Debian 13, the available Android 37 platforms included:

```text
platforms;android-37.0
platforms;android-37.1
platforms;android-37.2
```

while:

```text
platforms;android-37
```

was not available.

The tested installation therefore used:

```bash
sdkmanager \
  "platform-tools" \
  "platforms;android-37.2" \
  "build-tools;36.0.0"
```

If a future Android SDK exposes a newer `android-37.x` package instead, use the available Android 37 package shown by `sdkmanager --list`.

---

### 7. Configure the SDK location for Gradle

Return to the repository:

```bash
cd ~/Color_Match
```

Create the machine-specific `local.properties` file:

```bash
printf 'sdk.dir=%s\n' "$ANDROID_HOME" > local.properties
```

Check it:

```bash
cat local.properties
```

It should look similar to:

```text
sdk.dir=/home/your-user-name/Android/Sdk
```

`local.properties` is intentionally excluded from Git because the SDK path is different on every computer.

Without this file, Gradle may fail with:

```text
SDK location not found.
Define a valid SDK location with an ANDROID_HOME environment variable
or by setting the sdk.dir path in your project's local properties file.
```

---

### 8. Build the APK

Make sure the included Gradle wrapper is executable:

```bash
chmod +x gradlew
```

Build the project:

```bash
./gradlew clean assembleDebug
```

There is no need to install Gradle separately because this repository already includes the Gradle wrapper.

A successful build should end with:

```text
BUILD SUCCESSFUL
```

The new APK will be generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

Verify it:

```bash
ls -lh app/build/outputs/apk/debug/app-debug.apk
```

---

### Optional: Keep the Android SDK commands available after restarting Linux

The earlier `export` commands only apply to the current Terminal session.

To make them persistent:

```bash
echo 'export ANDROID_HOME="$HOME/Android/Sdk"' >> ~/.bashrc

echo 'export PATH="$ANDROID_HOME/cmdline-tools/latest/bin:$ANDROID_HOME/platform-tools:$PATH"' >> ~/.bashrc

source ~/.bashrc
```

You can verify them with:

```bash
echo "$ANDROID_HOME"

sdkmanager --version
```

---

### Optional: Install the APK on a connected Android device

If Android Debug Bridge can see the device:

```bash
adb devices
```

install or update Color Match with:

```bash
adb install -r --no-streaming \
  app/build/outputs/apk/debug/app-debug.apk
```

## A rebuilt APK by me is also located at:

```
color_match.apk
```

You are welcome to download, install, and play it directly on your Android phone if you trust me that much.

---

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

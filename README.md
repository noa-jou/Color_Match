# Color Match

My first Android application, built with **Java** while I was learning Android development.

This is a small learning project rather than a complete Android Studio project repository.

## Demo

The repository includes:

* [color_match.apk](color_match.apk) — the compiled Android application
* [photo_video/XRecorder.mp4](photo_video/XRecorder.mp4) — a recorded demo
* [Presentation.pdf](Presentation.pdf) — presentation materials from the original project

## Project Files

Only the files that I directly modified during development were preserved:

```text
code_file/
├── activity_main.xml
├── MainActivity.java
└── themes.xml
```

The rest of the Android Studio project files were not included because they were automatically generated and were not modified as part of the exercise.

### [MainActivity.java](code_file/MainActivity.java)

Contains the main Java logic of the application.

### [activity_main.xml](code_file/activity_main.xml)

Defines the main user interface layout.

### themes.xml(code_file/themes.xml)

Contains theme-related customization used by the application.

## Development Environment

The application was originally created using:

* Java
* JDK
* Android Studio
* Android XML layouts

During setup, I configured the Java environment variables on Windows.

<img src="photo_video/java_env.jpg" width="400"/>

I then created a new Android Studio project targeting an Android version compatible with my test device.

<img src="photo_video/android_version.jpg" width="400"/>

The following image shows the files I modified during the project:

<img src="photo_video/file_upload.jpg" width="800"/>


## APK

A compiled APK is included in this repository:

[color_match.apk](color_match.apk)

Because the APK is distributed directly rather than through Google Play, Android may display a warning before installation.

## Repository Structure

```text
Color_Match/
├── code_file/
│   ├── activity_main.xml
│   ├── MainActivity.java
│   └── themes.xml
├── color_match.apk
├── photo_video/
│   ├── android_version.jpg
│   ├── file_upload.jpg
│   ├── java_env.jpg
│   ├── Screenshot.jpg
│   └── XRecorder.mp4
├── Presentation.pdf
└── README.md
```

## About This Project

This was my **first Android application**.

The goal was not to build a production-ready application, but to understand the basic Android development workflow, including:

* Creating an Android Studio project
* Writing basic Java application logic
* Designing an interface with XML
* Modifying Android themes
* Building an APK
* Installing and testing an application on an Android device

I keep this project as a record of where my Android development journey started.


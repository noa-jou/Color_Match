# Color Match – Troubleshooting Log

This document records the process of revisiting and troubleshooting **Color Match**, my first Android application.

The project was originally created as a small Java/Android learning exercise. I revisited it on 2026-09-23 while reorganizing my GitHub portfolio.

## Project Background

The original repository contains only the files that I directly modified during the exercise:

```text
Color_Match/
├── code_file/
│   ├── activity_main.xml
│   ├── MainActivity.java
│   └── themes.xml
├── color_match.apk
├── photo_video/
├── Presentation.pdf
└── README.md
````

It is not a complete Android Studio project.

The application is a simple color-matching game with 16 buttons representing cards.

---

## Issue 1 – Incorrect Behavior in Android Dark Mode

### Problem

The application worked normally in Android Light Mode, but the card appearance and UI could behave incorrectly when the phone was using Dark Mode.

### Cause

The application used a Day/Night Material theme:

```xml
<style
    name="Theme.Color_match"
    parent="Theme.MaterialComponents.DayNight.NoActionBar.Bridge">
```

This allows Android to automatically change the interface according to the system Light/Dark Mode.

At the same time, the game manually controls card colors in Java.

The original code also used the first Button's default background as the card-back appearance:

```java
Drawable original;

original = card_ary[0].getBackground();
```

When a card needed to be hidden again, the background was restored with:

```java
card_ary[onpick[0]].setBackground(original);
```

The problem is that `original` was not a background explicitly defined by the game. It was whatever appearance Android's active theme had assigned to the Button.

As a result, the card-back appearance could change depending on the system theme.

### Fix

The application now defines its own card-back appearance instead of using Android's default Button background.

The `Drawable` dependency and `original` variable were removed:

```java
import android.graphics.drawable.Drawable;
```

```java
Drawable original;
```

```java
original = card_ary[0].getBackground();
```

A new method was added:

```java
private void hideCard(Button card) {
    card.setBackgroundColor(Color.GRAY);
    card.setClickable(true);
}
```

The rule is now simply:

```text
Hidden card = Gray
```

All cards are explicitly hidden when the app starts:

```java
for (Button card : card_ary) {
    hideCard(card);
}
```

The Shuffle function also uses the same method:

```java
for (Button card : card_ary) {
    hideCard(card);
}
```

And `hideTheFirst()` now uses:

```java
public void hideTheFirst() {
    hideCard(card_ary[onpick[0]]);
}
```

The application theme can also be changed from:

```xml
parent="Theme.MaterialComponents.DayNight.NoActionBar.Bridge"
```

to:

```xml
parent="Theme.MaterialComponents.Light.NoActionBar.Bridge"
```

Because colors are part of the game logic, a fixed Light theme provides a more consistent visual environment.

### Status

The likely cause has been identified and the source code has been modified.

The fix still needs to be verified by rebuilding the APK and testing it on a real Android device in both:

```text
Android Light Mode
Android Dark Mode
```

**Status: Awaiting rebuild and device testing**

---

## Issue 2 – Android Studio Gray Window on Chromebook

### Problem

Android Studio could start and its controls were clickable, but most of the window appeared covered by a gray layer.

Environment:

```text
ChromeOS
Linux development environment
Android Studio: /opt/android-studio/
```

### Cause

The problem appeared to be related to the graphical toolkit used by the JetBrains Runtime in the ChromeOS Linux environment.

### Fix

Android Studio was launched from the terminal with:

```bash
/opt/android-studio/bin/studio -Dawt.toolkit.name=XToolkit
```

The gray overlay disappeared and Android Studio rendered normally.

To make the workaround permanent, I opened:

```text
Main Menu
→ Help
→ Edit Custom VM Options
```

Alternatively:

```text
Ctrl + Shift + A
```

and searched for:

```text
Edit Custom VM Options
```

Then I added:

```text
-Dawt.toolkit.name=XToolkit
```

After restarting Android Studio normally, the interface continued to render correctly.

### Status

**Status: RESOLVED**

---

## Issue 3 – Git Push Triggered Repository Rule Warnings

### Problem

After updating the project, I ran:

```bash
git push
```

The push succeeded, but GitHub reported:

```text
Bypassed rule violations for refs/heads/main:

- Cannot update this protected ref.
- Changes must be made through a pull request.
- Cannot change this locked branch.
- Commits must have verified signatures.
```

The push itself still completed:

```text
main -> main
```

### Cause

An active GitHub Ruleset named:

```text
protectMain
```

contained several restrictive rules:

```text
Restrict creations
Restrict updates
Restrict deletions
Require linear history
Require signed commits
Require a pull request before merging
Block force pushes
```

The ruleset also allowed:

```text
Repository admin → Always allow
```

This explains why GitHub reported rule violations but still accepted the push: the repository administrator had bypass permission.

The ruleset was also targeting:

```text
All branches
```

even though it was intended to protect `main`.

### Fix

For this personal repository, the ruleset was simplified.

The following restrictions are not currently necessary:

```text
Restrict creations        OFF
Restrict updates          OFF
Require signed commits    OFF
Require pull request      OFF
```

Useful protections remain:

```text
Restrict deletions        ON
Block force pushes        ON
Require linear history    Optional
```

### Branch Targeting

The ruleset originally targeted:

```text
All branches
```
It was changed to:

```
Default branch
```

GitHub currently shows:
```
Applies to 1 target: main
```

This means the ruleset protects the repository's default branch, which is currently main.

Using **Default branch** is preferable to hard-coding the branch name because the protection will continue to follow the repository's default branch if it is renamed in the future.

This keeps the normal personal workflow simple:

```bash
git add .
git commit -m "Update project"
git push
```

while still protecting the repository from accidental branch deletion and force pushes.

### Status

**Status: RESOLVED**

---

## Current Project Status

As of 2026-09-23:

```text
Android Studio gray-window issue
→ RESOLVED

GitHub ruleset issue
→ RESOLVED

Color Match Dark Mode issue
→ Cause identified
→ Source code modified
→ Awaiting rebuild and device testing

Old APK
→ Still contains the original implementation

New APK
→ Not built yet
```

The next step is to reconstruct a clean Android Studio project from the preserved source files, build a new APK, and test it on an Android phone in both Light Mode and Dark Mode.

---


## Issue 4 -- Rebuilding the Android Project and APK

After fixing the source code, I created a new Android Studio project because the original repository did not contain a complete buildable Android project.

The new project was created with:

```text
Template: Empty Views Activity
Language: Java
Package: com.example.color_match
Minimum SDK: API 23
Build configuration: Kotlin DSL
````

Before importing the old Color Match files, the new empty project was built successfully. This confirmed that the current JDK, Gradle, Android SDK, and project environment were working correctly.

### Migrating the Original Files

The original `activity_main.xml` was copied into:

```text
app/src/main/res/layout/activity_main.xml
```

The first build failed because the newly generated `MainActivity.java` still contained Android Studio template code that referenced:

```java
R.id.main
```

The old Color Match layout did not contain an element with this ID.

Instead of modifying the old layout, the generated `MainActivity.java` was replaced with the updated Color Match code.

The updated version also removed the original dependency on:

```java
card_ary[0].getBackground()
```

and now explicitly uses a gray background for hidden cards.

After replacing `MainActivity.java`, the project built successfully:

```text
BUILD SUCCESSFUL
```

### Removing Dark Mode Theme Overrides

The new Android Studio project automatically generated two theme locations:

```text
res/values/themes.xml
res/values-night/themes.xml
```

Both used a Day/Night Material theme.

Since Color Match depends on consistent card colors, the main theme was changed from:

```xml
Theme.Material3.DayNight.NoActionBar
```

to:

```xml
Theme.Material3.Light.NoActionBar
```

The automatically generated Dark Mode override was then removed:

```bash
rm app/src/main/res/values-night/themes.xml
rmdir app/src/main/res/values-night
```

The project was built again and completed successfully:

```text
BUILD SUCCESSFUL
```

### New APK

A new debug APK was generated at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

The generated APK was approximately:

```text
6.6 MB
```

A copy was saved as:

```text
~/ColorMatch-new.apk
```
---

## Issue 5 -- Device Testing and UI Compatibility Fixes

After generating the new APK, I installed it on an Android 11 test device using ADB.

Run:

```bash
~/Android/Sdk/platform-tools/adb kill-server
~/Android/Sdk/platform-tools/adb start-server
~/Android/Sdk/platform-tools/adb devices
```

A successful restart may look like:

```text
* daemon not running; starting now at tcp:5037
* daemon started successfully
List of devices attached
DEVICE_ID    device
```



The normal streamed installation became stuck at:

```text
Performing Streamed Install
````

Using a non-streaming installation worked successfully:

```bash
 ./gradlew assembleDebug

cp app/build/outputs/apk/debug/app-debug.apk ~/ColorMatch-new.apk

~/Android/Sdk/platform-tools/adb install -r --no-streaming ~/ColorMatch-new.apk
```

Result:

```text
Success
```

### First Device Test

The rebuilt application successfully displayed the intended card colors in both Light Mode and Dark Mode.

However, two new issues appeared:

1. Switching between Light Mode and Dark Mode reshuffled the cards.
2. The card buttons appeared completely round.

### Preventing Reshuffling During Theme Changes

Changing the system Light/Dark setting caused Android to recreate `MainActivity`.

Because `onCreate()` contains:

```java
shuffle_color_ary();
```

the cards were shuffled again whenever the Activity was recreated.

To prevent this, `AndroidManifest.xml` was updated:

```xml
<activity
    android:name=".MainActivity"
    android:exported="true"
    android:configChanges="uiMode"
    android:windowSoftInputMode="adjustResize">
```

After rebuilding and testing again, switching between Light Mode and Dark Mode no longer reshuffled the game.

**Status: RESOLVED**

### Fixing the Card Shape

The new project uses Material 3, whose default Button style has much larger rounded corners than the original application.

This caused the nearly square card buttons to appear circular.

Instead of defining the appearance separately for all 16 cards, I created a shared style file at:

```text
app/src/main/res/values/styles.xml
````

The file defines a reusable style named:

```xml
<style name="CardButton">
    <item name="cornerRadius">8dp</item>
    <item name="android:insetTop">0dp</item>
    <item name="android:insetBottom">0dp</item>
    <item name="android:minWidth">0dp</item>
    <item name="android:minHeight">0dp</item>
    <item name="android:padding">0dp</item>
</style>
```

This gives the cards a small, controlled corner radius instead of using the Material 3 default shape.

The style was then applied to each of the 16 card buttons in:

```text
app/src/main/res/layout/activity_main.xml
```

For example:

```xml
<Button
    android:id="@+id/one"
    style="@style/CardButton"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:layout_margin="2dp"
    android:layout_weight="1"
    android:onClick="onClick"
    android:text=""
    tools:ignore="SpeakableTextPresentCheck" />
```

The same:

```xml
style="@style/CardButton"
```

was added to all 16 card buttons.

Only the individual Button IDs remain different:

```text
one
two
three
...
sixteen
```

The Shuffle button does not use `CardButton`, so its original Material 3 rounded appearance is preserved.

### Editing Error During Style Migration

While applying the shared style to all 16 buttons, several Button IDs were accidentally overwritten.

This caused build errors such as:

```text
cannot find symbol
R.id.two
R.id.three
...
R.id.sixteen
```

After restoring the correct individual IDs, the project built successfully again:

```text
BUILD SUCCESSFUL
```

### Card Spacing

The original layout used:

```xml
android:layout_marginHorizontal="2dp"
```

which only added spacing on the left and right sides.

After removing the default Material Button insets, the cards therefore appeared connected vertically.

This was changed to:

```xml
android:layout_margin="2dp"
```

so every card now receives equal spacing on all four sides.

The final design therefore uses:

```text
Shared CardButton style
→ controls card shape

cornerRadius = 8dp
→ slightly rounded corners

layout_margin = 2dp
→ equal vertical and horizontal spacing
```

---

### Second Device Test

The second test confirmed that the Light/Dark Mode reshuffling problem was fixed.

However, removing the default Button insets also caused the cards to visually connect vertically, creating long rectangular columns.

The card style was therefore adjusted again to use:

```xml
<item name="cornerRadius">8dp</item>
```

and each card now uses equal spacing on all sides:

```xml
android:layout_margin="2dp"
```

instead of horizontal-only spacing:

```xml
android:layout_marginHorizontal="2dp"
```

This should provide:

* Slightly rounded card corners
* Equal horizontal and vertical spacing
* A consistent grid layout
* No dependency on the Material 3 default Button shape

### Current Status

```text
APK installation on Android 11
→ SUCCESS

Light/Dark color consistency
→ PASSED

Theme change reshuffling
→ RESOLVED

Material 3 circular card appearance
→ Cause identified

Card spacing and corner radius
→ Updated
→ Final device verification pending
```

The next step is to rebuild and reinstall the APK once more to verify the final card shape and spacing on the Android test device.






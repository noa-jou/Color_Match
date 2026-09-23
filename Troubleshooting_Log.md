
# Color Match – Troubleshooting Log

This document records the process of revisiting and troubleshooting **Color Match**, my first Android application.

The original project was created as a small Java/Android learning exercise. Years later, I revisited the application and discovered several issues related to Android Dark Mode and my current Android Studio environment on ChromeOS.

---

## 2026-09-23 – Revisiting the Project

I returned to this old project while reorganizing my GitHub portfolio.

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

The application itself is a simple color-matching game with 16 buttons representing cards.

---

# Issue 1 – The App Behaves Incorrectly in Android Dark Mode

## Original Observation

I had previously noticed that the application behaved strangely when the Android phone was using **Dark Mode**.

The game appeared to work normally in Light Mode, but the card appearance and UI could behave incorrectly when the system theme was dark.

At the time, I did not know why.

---

## Investigation

While reviewing the three preserved source files, I found an important detail in `themes.xml`.

The application theme was:

```xml
<style
    name="Theme.Color_match"
    parent="Theme.MaterialComponents.DayNight.NoActionBar.Bridge">
```

The important part is:

```text
DayNight
```

This means the application theme automatically reacts to the Android system's Light or Dark Mode.

However, the game logic was also manually controlling button colors in Java.

The two approaches were therefore interacting with each other.

The game itself did not intentionally implement a Dark Mode interface, even though the selected Material theme supported one.

---

## Original Card Background Implementation

Another important issue was found in `MainActivity.java`.

Originally, the program stored the background of the first card button:

```java
Drawable original;

original = card_ary[0].getBackground();
```

The intention was simple:

1. Remember what a card looks like before it is flipped.
2. Show a color when the player selects a card.
3. Restore the original background when the card needs to be hidden again.

For example:

```java
card_ary[btn_index].setBackgroundColor(
    Color.rgb(252, 45, 55)
);
```

would reveal a red card.

When the card needed to be hidden again, the original code used:

```java
card_ary[onpick[0]].setBackground(original);
```

The problem was that `original` was not a card background explicitly defined by the game.

It was simply whatever background Android had assigned to the first Button.

Because the Buttons did not define their own fixed backgrounds, their appearance could be affected by the active Android theme.

Therefore:

```java
card_ary[0].getBackground();
```

could return a different-looking button background depending on whether the system was using Light Mode or Dark Mode.

---

# Proposed Fix

Instead of asking Android for the default Button background, the application now defines its own card-back appearance.

The `Drawable` dependency was removed.

These were removed:

```java
import android.graphics.drawable.Drawable;
```

```java
Drawable original;
```

and:

```java
original = card_ary[0].getBackground();
```

A new method was introduced:

```java
private void hideCard(Button card) {
    card.setBackgroundColor(Color.GRAY);
    card.setClickable(true);
}
```

Now the application explicitly defines:

```text
Hidden card = Gray
```

instead of:

```text
Hidden card = Whatever Android's current theme makes a Button look like
```

---

## Initial Card State

The application now also explicitly hides every card when the Activity starts:

```java
for (Button card : card_ary) {
    hideCard(card);
}
```

This makes the initial state independent of the system theme.

---

## Shuffle Behavior

The original reset logic used:

```java
for (Button a : card_ary) {
    a.setBackground(original);
    a.setClickable(true);
}
```

It was replaced with:

```java
for (Button card : card_ary) {
    hideCard(card);
}
```

This ensures that restarting the game uses the same fixed card-back appearance.

---

## Hiding a Card

The old implementation:

```java
public void hideTheFirst() {
    card_ary[onpick[0]].setBackground(original);
    card_ary[onpick[0]].setClickable(true);
}
```

was replaced by:

```java
public void hideTheFirst() {
    hideCard(card_ary[onpick[0]]);
}
```

This removes the dependency on Android's default Button drawable.

---

# Theme Change

Because Color Match is a game in which colors have functional meaning, supporting an automatically changing Day/Night theme is unnecessary.

The theme can therefore also be changed from:

```xml
parent="Theme.MaterialComponents.DayNight.NoActionBar.Bridge"
```

to:

```xml
parent="Theme.MaterialComponents.Light.NoActionBar.Bridge"
```

This keeps the visual environment consistent even when the phone itself is using Dark Mode.

---

# Current Status of the Dark Mode Fix

The source of the problem has been identified and the code has been modified to avoid relying on theme-controlled Button backgrounds.

However, this fix is not considered fully verified yet.

The next step is to rebuild the application as a new APK and test it on a real Android device under both:

```text
Android Light Mode
Android Dark Mode
```

If both behave consistently, the old APK in the repository can be replaced with the rebuilt version.

---

# Issue 2 – Android Studio Window Appears Gray on Chromebook

## Environment

The project is currently being revisited using Android Studio installed inside the Linux development environment on a Chromebook.

Android Studio is installed at:

```text
/opt/android-studio/
```

The launcher exists at:

```text
/opt/android-studio/bin/studio
```

---

## Symptom

Android Studio could start successfully, and UI elements were technically clickable.

However, much of the application window appeared covered by a gray layer.

The program was running, but the interface was not being rendered correctly.

This initially made Android Studio almost unusable.

---

# Investigation

The behavior appeared to be related to the graphics/UI toolkit used by the JetBrains Runtime inside the ChromeOS Linux environment.

To test this, Android Studio was launched manually from the terminal with:

```bash
/opt/android-studio/bin/studio -Dawt.toolkit.name=XToolkit
```

---

# Result

The test was successful.

Android Studio opened normally without the gray overlay.

This confirmed that the Android Studio installation itself was working and that the problem was related to the graphical toolkit/rendering environment.

Using:

```text
-Dawt.toolkit.name=XToolkit
```

forced Android Studio to use the XToolkit instead of the problematic default graphical path.

---

# Making the Workaround Permanent

Instead of launching Android Studio from the terminal every time, the VM option was added permanently.

In the newer Android Studio UI, the traditional menu bar is hidden inside the main menu.

The setting can be found through:

```text
Main Menu
→ Help
→ Edit Custom VM Options
```

Alternatively:

```text
Ctrl + Shift + A
```

and search for:

```text
Edit Custom VM Options
```

The following line was added:

```text
-Dawt.toolkit.name=XToolkit
```

Android Studio was then restarted normally.

---

# Result

The permanent configuration worked.

Android Studio can now be launched normally from ChromeOS without the gray overlay.

Status:

```text
Android Studio gray-window issue: RESOLVED
```

---

# Current Project Status

As of 2026-09-23:

```text
Android Studio rendering issue
→ Identified
→ Workaround tested
→ Permanent configuration added
→ RESOLVED

Color Match Dark Mode issue
→ Investigated
→ Likely cause identified
→ Source code modified
→ Awaiting rebuild and device testing

Old APK
→ Still represents the original implementation

New APK
→ Not built yet
```

The next step is to reconstruct a clean Android Studio project from the preserved source files, build a new APK, and test the application on an Android phone in both Light Mode and Dark Mode.

---


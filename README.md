# MindTracker — Android Mental Health & Wellness Companion 🧠📱

[![Android SDK](https://img.shields.io/badge/Platform-Android_24+-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Language](https://img.shields.io/badge/Language-Java_8-ED8B00?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/)
[![Database](https://img.shields.io/badge/Storage-SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![Build Status](https://img.shields.io/badge/Build-Passing-10B981?style=for-the-badge&logo=githubactions&logoColor=white)](.github/workflows/android.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-F59E0B?style=for-the-badge)](LICENSE)

> **A native Android wellness companion engineered for daily mood logging, cognitive reflection journaling, and guided deep breathing relaxation exercises.**

---

## 🎯 The Problem It Solves

Modern college students and working professionals frequently encounter academic stress, cognitive overload, and burnout without a quick, private, offline-accessible tool to gauge their daily emotional state and decompress.

**MindTracker** provides a zero-latency, 100% private, on-device application that requires no accounts, third-party cloud tracking, or internet connectivity. Users can log their emotional baseline in under 5 seconds, capture daily gratitude reflections, and regulate their nervous system using the clinically backed **4-7-8 breathing technique**.

---

## 📸 Application Screenshots & User Flow

```
+--------------------+   +--------------------+   +--------------------+
|  MindTracker 🧠   |   | Guided Breathe 🌬️  |   | Mood History 📊   |
+--------------------+   +--------------------+   +--------------------+
| Good Evening,      |   |                    |   | Recent Logs:       |
| Prachi             |   |      ( INHALE )    |   |                    |
|                    |   |      4 Seconds     |   | 📅 Nov 14: 😊 Great|
| How do you feel?   |   |                    |   | Note: Finished ML  |
| 😢 😕 😐 🙂 😄     |   |   O  Expand Circle |   |                    |
| [Selected: Happy]  |   |                    |   | 📅 Nov 13: 😐 Calm |
|                    |   | [Pause]   [Stop]   |   | Note: Midterms prep|
| Quick Note:        |   +--------------------+   |                    |
| "Finished project" |   | 5-Min Relax Session|   | [Export Summary]   |
| [Log My Mood]      |   | 🎧 Ambient Audio   |   +--------------------+
+--------------------+   +--------------------+
```
*(Screenshot placeholders: `./docs/screenshots/dashboard.png`, `./docs/screenshots/breathing.png`, `./docs/screenshots/history.png`)*

---

## ✨ Key Features

* 😊 **Instant 5-Tier Mood Logger:** Visual emoji picker (Very Sad, Sad, Neutral, Happy, Very Happy) with optional context notes and automatic timestamping.
* 📝 **Cognitive Reflection & Gratitude Journal:** Clean, distraction-free journaling interface to write thoughts, accomplishments, and daily reflections.
* 🌬️ **Guided 4-7-8 Breathing Pacer:** Custom animated canvas/drawable circle that rhythmically expands (Inhale 4s), holds (7s), and contracts (Exhale 8s) to stimulate parasympathetic nervous relaxation.
* ⏳ **5-Minute Relaxation Timers:** Built-in micro-break timer encouraging mindful pauses during intense study and coding sessions.
* 🔒 **Local SQLite Privacy Shield:** All entries are stored strictly on the local device storage (`MentalHealth.db`). Zero telemetry, zero analytics tracking, zero cloud data leakage.
* 🧪 **Automated Testing Suite:** Unit tests for schema validity, query syntax, date parsing, and model contract integrity.

---

## 🛠️ Technical Stack & Architecture

- **Operating System:** Android 7.0 (API level 24) to Android 14 (API level 34)
- **Programming Language:** Java 8+
- **Persistence Layer:** Android SQLite OpenHelper (`DatabaseHelper.java`)
- **UI Architecture:** Android Native Views & ViewGroups, ConstraintLayout, CardView, Material Design Components
- **Animation Framework:** Android Property Animation API (`ValueAnimator` & `ObjectAnimator`) for breathing cycles
- **Build System:** Gradle 8.9 with Android Gradle Plugin (AGP)

---

## 📂 Project Structure

```
mental-health-tracker-android/
│
├── .github/workflows/
│   └── android.yml            # Automated CI build & test workflow
├── app/
│   ├── build.gradle           # App-level dependencies & SDK configs
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/mentalhealth/tracker/
│       │   │   ├── MainActivity.java        # Home dashboard & quick mood logger
│       │   │   ├── BreathingActivity.java   # Animated 4-7-8 breathing engine
│       │   │   ├── JournalActivity.java     # Daily gratitude reflection notebook
│       │   │   ├── HistoryActivity.java     # SQLite mood trend list & filtering
│       │   │   ├── RelaxActivity.java       # Relaxation menu
│       │   │   ├── RelaxSessionActivity.java# 5-minute focused timer
│       │   │   └── DatabaseHelper.java      # SQLite schema, tables & queries
│       │   └── res/                         # Layout XMLs, drawables, and strings
│       └── test/java/com/mentalhealth/tracker/
│           └── DatabaseContractTest.java    # Automated unit tests
│
├── gradle/wrapper/
│   └── gradle-wrapper.properties
├── build.gradle               # Project-level Gradle build configuration
├── settings.gradle            # Repository module inclusion
└── README.md
```

---

## 🚀 Installation & Local Setup

### Prerequisites
- [Android Studio Hedgehog (2023.1.1) or newer](https://developer.android.com/studio)
- JDK 17
- Android SDK Platform 34

### Steps
1. **Clone the repository:**
   ```bash
   git clone https://github.com/prachizala/mental-health-tracker-android.git
   cd mental-health-tracker-android
   ```
2. **Open in Android Studio:**
   - Launch Android Studio.
   - Select **Open an existing project** and choose the `mental-health-tracker-android` directory.
   - Allow Gradle to sync dependencies.
3. **Run Unit Tests:**
   ```bash
   ./gradlew test
   ```
4. **Deploy to Device or Emulator:**
   - Connect an Android device with USB Debugging enabled, or launch an Android Virtual Device (AVD).
   - Click **Run (Shift + F10)**.

---

## 🔮 Roadmap & Future Enhancements
- [ ] Add rolling 30-day sentiment trend graph using MPAndroidChart.
- [ ] Integrate biometric fingerprint lock for personal journal privacy.
- [ ] Implement CSV/JSON data backup and restore functionality.

---

## 👤 Author & Academic Details

**Prachi Zala**  
- **Role:** Lead Developer & Android Engineer  
- **Degree:** Bachelor of Computer Applications (BCA)  
- **GitHub:** [@prachizala](https://github.com/prachizala)  
- **LinkedIn:** [Prachi Zala](https://www.linkedin.com/in/prachi-zala-74a08b334/)

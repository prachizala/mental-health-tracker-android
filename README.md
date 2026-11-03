# MindTracker - Mental Health Tracker App 🧠

A complete Android Studio mental health app with mood tracking and 5-minute relaxation tools.


> [!IMPORTANT]
> **Medical Disclaimer:** This application is a self-care wellness and habit-tracking project developed strictly for educational and academic purposes. It is **NOT** a clinical diagnostic tool, medical device, or substitute for professional mental healthcare, psychiatric consultation, or crisis intervention. If you are experiencing a mental health emergency, please contact professional emergency support services or a certified healthcare provider.

---

## 📌 Project Status & Architecture Note
- **Platform:** Native Android Application (Java 8+, Android SDK API 34)
- **Local Storage:** SQLite (`DatabaseHelper.java`) — 100% offline-first, zero telemetry or cloud tracking for user privacy.
- **Status:** Functional Academic Wellness MVP
---

## 📱 Features

### 🏠 Home Dashboard
- Smart greeting based on time of day
- Daily mood logger with emoji selector (5 levels)
- Optional mood notes
- Streak tracker (consecutive days logged)
- Quick-access cards for all features

### 💨 Breathing Exercises
- **4-7-8 Pattern** – Inhale 4s, Hold 7s, Exhale 8s (calming)
- **Box Breathing** – 4s each phase (focus/stress relief)
- **4-4-2 Pattern** – Inhale 4s, Hold 4s, Exhale 2s (relaxation)
- Animated circle that expands/contracts with your breath
- Configurable cycles with countdown timer

### 🧘 5-Min Relaxation Sessions (fully timed/guided)
- **Mindfulness Meditation** – Guided awareness practice
- **Body Scan** – Tension release from feet to head
- **Peaceful Visualization** – Beach/nature mental escape
- **Gratitude Practice** – Mindset shift through appreciation
- **Positive Affirmations** – Inner strength building

### 📓 Journal
- Write daily thoughts/feelings
- Random daily prompts to inspire writing
- View all past entries with preview

### 📊 Mood History
- View all logged moods with notes
- Stats: total logs, average mood level
- Chronological history list

---

## 🛠️ Setup Instructions

### Requirements
- Android Studio Hedgehog (2023.1.1) or later
- Android SDK API 34
- Java 8+

### Steps to Open in Android Studio
1. Download/unzip all files
2. Open Android Studio → **File > Open**
3. Select the `MentalHealthApp` folder
4. Let Gradle sync complete (~2-3 minutes)
5. Run on emulator (API 24+) or physical device

### Project Structure
```
MentalHealthApp/
├── app/
│   ├── src/main/
│   │   ├── java/com/mentalhealth/tracker/
│   │   │   ├── MainActivity.java         ← Home screen
│   │   │   ├── BreathingActivity.java    ← Breathing exercise
│   │   │   ├── RelaxActivity.java        ← Relax menu
│   │   │   ├── RelaxSessionActivity.java ← Guided sessions
│   │   │   ├── JournalActivity.java      ← Journal
│   │   │   ├── HistoryActivity.java      ← Mood history
│   │   │   └── DatabaseHelper.java       ← SQLite database
│   │   ├── res/
│   │   │   ├── layout/                   ← All XML layouts
│   │   │   ├── drawable/                 ← Custom shapes
│   │   │   └── values/                   ← Colors, strings, styles
│   │   └── AndroidManifest.xml
│   └── build.gradle
├── build.gradle
└── settings.gradle
```

---

## 🎨 Customization

- **Colors**: Edit `res/values/colors.xml` → Change `primary` color
- **Add sessions**: In `RelaxSessionActivity.java`, add new `case` in `loadSession()` method
- **More moods**: Extend the `moodEmojis` arrays in `MainActivity.java`

---

## 📦 Dependencies Used
```gradle
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.10.0'
implementation 'androidx.cardview:cardview:1.0.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
```

No external libraries required for core functionality. SQLite is built-in to Android.

# Mental Health Tracker — On-Device CBT & Mindfulness Suite

<div align="center">

  <img src="./app_poster.jpg" alt="Mental Health Tracker Branding Poster" width="680" style="border-radius: 16px; margin-bottom: 16px; box-shadow: 0 8px 24px rgba(0,0,0,0.3);"/>

  <br/>

[![Platform: Android](https://img.shields.io/badge/Platform-Android%20SDK%20(API%2034)-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com/)
[![Language: Java](https://img.shields.io/badge/Language-Java%208+-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Database: SQLite](https://img.shields.io/badge/Storage-100%25%20Offline%20SQLite-003B57?style=flat-square&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![CBT & Sentiment](https://img.shields.io/badge/AI%2FNLP-On--Device%20CBT%20Engine-9C27B0?style=flat-square&logo=thealgorithms&logoColor=white)](https://github.com/prachizala/mental-health-tracker-android)
[![CI/CD](https://img.shields.io/badge/CI%2FCD-100%25%20Passing-brightgreen?style=flat-square&logo=githubactions&logoColor=white)](https://github.com/prachizala/mental-health-tracker-android/actions)
[![Material Design](https://img.shields.io/badge/UI-Material%20Design%203-795548?style=flat-square&logo=materialdesign&logoColor=white)](https://material.io/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](LICENSE)

> **Privacy-first, 100% offline native Android mindfulness and mood journaling suite powered by a pure-Java on-device Cognitive Distortion (CBT) and sentiment analysis engine.**

</div>

---

> [!IMPORTANT]
> **Academic & Self-Care Disclaimer:** This application is developed strictly for educational, academic, and personal wellness self-tracking purposes. It is **NOT** a clinical diagnostic medical device or a replacement for licensed psychiatric healthcare, therapy, or emergency crisis intervention.

---

## 🎯 The Problem It Solves

* **Cloud Privacy Concerns:** Most modern mental wellness applications transmit sensitive personal journal entries to remote third-party cloud LLMs and servers, raising severe privacy and data telemetry concerns.
* **Our Solution:** A **zero-cloud, 100% local-first** mobile architecture. All lexical sentiment scoring, Cognitive Behavioral Therapy (CBT) distortion pattern detection, and therapeutic reframing occur directly on the phone's CPU with zero network permissions.

---

## 🚀 Key Features

* 🧠 **On-Device CBT Distortion Detector:** Algorithmic pure-Java clinical evaluator identifying 5 common cognitive distortion patterns:
  1. **Catastrophizing:** Anticipating worst-case catastrophic outcomes.
  2. **All-or-Nothing Thinking:** Rigid black-and-white dichotomous judgments.
  3. **Overgeneralization:** Drawing universal negative rules from isolated incidents.
  4. **Emotional Reasoning:** Assuming negative feelings reflect objective reality.
  5. **Should-Statements:** Internalized pressure and perfectionistic expectations.
* 🔄 **Live Therapeutic Reframing:** Pairs detected distortions with contextual clinical reframing prompts in real time.
* ⚖️ **Lexical Valence & Negation Analyzer:** Rule-based sentiment scoring engine handling booster multipliers ("deeply", "extremely") and negation window flipping ("not happy", "never calm").
* 🫁 **Hardware-Accelerated Breathing Visualizer:** ValueAnimator-driven lung expansion simulation supporting clinical **4-7-8 relaxing breath** and **Box breathing** protocols.
* 🔒 **100% Private Offline SQLite:** Local SQLite v2 schema storing timestamps, mood levels, sentiment scores, and distortion tags with zero external network tracking.
* 🧪 **CI/CD & JUnit Test Suite:** 12/12 passing unit tests covering all algorithmic edge cases and automated GitHub Actions verification.

---

## 🛠️ Tech Stack

| Component | Technologies & Tools |
| :--- | :--- |
| **Operating System** | Android (API Level 24+ Android 7.0 up to Android 14) |
| **Language** | Java 8+ |
| **Architecture** | Native Android Activities, MVC, Encapsulated DAO |
| **UI Components** | Material Design 3, CardView, ConstraintLayout |
| **Animation Engine** | Hardware-Accelerated ValueAnimator & AccelerateDecelerateInterpolator |
| **Database** | SQLite v2 (Local on-device persistence) |
| **Testing** | JUnit 4, Robolectric, AndroidX Runner |
| **DevOps / CI** | Gradle 8.5, Android Gradle Plugin (AGP) 8.1.0, GitHub Actions |

---

## 📂 Project Structure

```
mental-health-tracker-android/
├── app/
│   ├── build.gradle            # App-level build configuration and dependencies
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/mentalhealth/tracker/
│       │   │   ├── MainActivity.java           # Dashboard, mood logger & streaks
│       │   │   ├── BreathingActivity.java      # 4-7-8 & Box breathing visualizer
│       │   │   ├── JournalActivity.java        # Journal editor & live CBT reframe dialog
│       │   │   ├── RelaxActivity.java          # Guided decompression sessions
│       │   │   ├── RelaxSessionActivity.java   # Timed audio/text session runner
│       │   │   ├── HistoryActivity.java        # Historical analytics & mood logs
│       │   │   ├── DatabaseHelper.java         # SQLite v2 DAO & schema migrations
│       │   │   └── cbt/                        # Pure-Java on-device clinical engine
│       │   │       ├── SentimentAnalyzer.java
│       │   │       ├── CognitiveDistortionDetector.java
│       │   │       └── JournalAnalysisResult.java
│       │   └── res/
│       │       ├── layout/                     # Material 3 XML layouts
│       │       ├── mipmap-*/                   # 3D glowing lotus brain launcher icons
│       │       └── values/                     # Strings, colors, styles
│       └── test/java/com/mentalhealth/tracker/
│           ├── BreathingAndMoodLogicTest.java  # Breathing phase & mood math tests
│           └── SentimentAndCbtTest.java        # Sentiment & CBT clinical engine tests
├── gradle/wrapper/             # Gradle 8.5 binary wrapper
├── .github/workflows/ci.yml    # Automated Android CI/CD workflow
├── ARCHITECTURE.md             # In-depth system architecture specification
└── build.gradle                # Project-level root configuration
```

---

## 💻 Installation & Run Guide (Tested)

### 1. Prerequisites
* **Android Studio Iguana / Jellyfish** (or higher)
* **JDK 17** (Standard for AGP 8.x)
* **Android SDK** (API 24 to API 34 installed)

### 2. Build via Command Line
```bash
# Clone the repository
git clone https://github.com/prachizala/mental-health-tracker-android.git
cd mental-health-tracker-android

# Run unit test suite (12/12 passing)
./gradlew test

# Assemble release-ready Debug APK
./gradlew assembleDebug
```
* Output APK generated at: `app/build/outputs/apk/debug/app-debug.apk`

### 3. Install on Physical Device or Emulator
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

---

## 📸 Screenshots & Demo

| 🫁 4-7-8 Breathing Visualizer | 📝 CBT Journaling & Reframing | 📊 Historical Mood Analytics |
| :---: | :---: | :---: |
| *(Placeholder: Breathing Flow)* | *(Placeholder: CBT Dialog)* | *(Placeholder: History Activity)* |

*(Branding poster available at `./app_poster.jpg`)*

---

## 🔮 Future Improvements

1. **Biometric App Lock:** Optional Android Keystore / BiometricPrompt fingerprint authentication for journal privacy.
2. **Encrypted SQLite (SQLCipher):** Full AES-256 database encryption at rest.
3. **Audio-Guided Breathing:** Offline soundscapes and soothing voice-over prompts.
4. **Encrypted PDF Export:** Allow users to export clinical journal summaries to share with certified therapists.

---

## 👩‍💻 Author & Maintainer

**Prachi Zala**  
*BCA Student | Python, Distributed Systems & Mobile Developer*  
*Shanti Business School, Ahmedabad, Gujarat, India*  

* **LinkedIn:** [linkedin.com/in/prachi-zala-74a08b334](https://www.linkedin.com/in/prachi-zala-74a08b334)  
* **GitHub:** [github.com/prachizala](https://github.com/prachizala)  
* **Portfolio:** [prachizala.github.io/prachi-portfolio](https://prachizala.github.io/prachi-portfolio/)

---

## 📄 License

This project is open-source under the [MIT License](LICENSE).

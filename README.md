# Mental Health Tracker — Native Android Mindfulness & Emotional Intelligence Suite 🧠📱

<div align="center">

  <img src="./app_poster.jpg" alt="Mental Health Tracker Branding Poster" width="680" style="border-radius: 16px; margin-bottom: 16px; box-shadow: 0 8px 24px rgba(0,0,0,0.3);"/>

  <br/>

[![Android SDK](https://img.shields.io/badge/Platform-Android%20SDK%20(API%2034)-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Language-Java%208+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Database](https://img.shields.io/badge/Storage-100%25%20Offline%20SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![CBT & Sentiment](https://img.shields.io/badge/AI%2FNLP-On--Device%20CBT%20Engine-9C27B0?style=for-the-badge&logo=thealgorithms&logoColor=white)](https://github.com/prachizala/mental-health-tracker-android)
[![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions%20Passing-brightgreen?style=for-the-badge&logo=githubactions&logoColor=white)](https://github.com/prachizala/mental-health-tracker-android/actions)
[![Material Design](https://img.shields.io/badge/UI-Material%20Design%203-795548?style=for-the-badge&logo=materialdesign&logoColor=white)](https://material.io/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

<p align="center">
  A production-ready, native Android mindfulness, mood analytics, and habit-tracking suite engineered with <b>Java 8+</b>, <b>Android SDK API 34</b>, custom interpolator-driven UI animations, an <b>on-device NLP sentiment and Cognitive Distortion (CBT) reframing engine</b>, and <b>100% private, zero-telemetry offline SQLite storage</b>.
</p>

</div>

---

> [!IMPORTANT]
> **Academic & Self-Care Disclaimer:** This application is developed strictly for educational, academic, and personal wellness self-tracking purposes. It is **NOT** a clinical diagnostic medical device or a replacement for licensed psychiatric healthcare, therapy, or emergency crisis intervention.

---

## 🌟 Key Highlights & Engineering Features

- 🧠 **On-Device Sentiment & CBT Reframe Engine:** Evaluates journal entries in real-time using a pure-Java lexical analyzer and detects clinical cognitive distortions (All-or-Nothing thinking, Catastrophizing, Overgeneralization, Should/Must pressure, Emotional Reasoning). Serves immediate therapeutic CBT reframing perspectives with **zero cloud dependencies**.
- 🔒 **100% Private & Zero-Cloud Telemetry:** In an era of invasive data harvesting, all mood logs, private journal reflections, and emotional scores remain **strictly on the user's local device** in SQLite. No external trackers, no analytics SDKs, zero internet permission required.
- 💨 **Dynamic Breathing Synchronizer:** Real-time animated breathing circle utilizing Android `ValueAnimator` with `AccelerateDecelerateInterpolator` simulating physiological lung expansion and contraction across 3 clinical patterns (4-7-8, Box Breathing, 4-4-2).
- 🧘 **5-Minute Guided Decompression Sessions:** Fully timed state-machine guided sessions (Mindfulness, Body Scan, Nature Visualization, Gratitude, Affirmations) with custom phase instructions and progress tracking.
- 📊 **Streak & Habit Intelligence Engine:** Serverless, chronological date-traversal algorithm calculating consecutive daily logging streaks and mood frequency distributions.
- 🧪 **Test-Driven Reliability & CI/CD:** Rigorously tested with 12 isolated JUnit 4 tests and continuously verified via automated GitHub Actions CI.

---

## 🏗️ Architecture & Component Topology

The application follows clean architectural separation between Android UI Activities, domain timing state machines, the on-device CBT/NLP engine, and the encapsulated SQLite Data Access Object (DAO) layer:

```mermaid
flowchart TD
    subgraph UI ["📱 Presentation Layer (Android Activities & Material UI)"]
        MA["MainActivity<br/>(Dashboard, Mood Logger, Streaks)"]
        BA["BreathingActivity<br/>(Visual Animated Synchronizer)"]
        RA["RelaxActivity & RelaxSessionActivity<br/>(5-Min Guided Audio/Text Sessions)"]
        JA["JournalActivity<br/>(Reflective Thought Prompts & CBT Dialogue)"]
        HA["HistoryActivity<br/>(Mood Distribution & Timeline)"]
    end

    subgraph CBT ["🧠 On-Device NLP & Cognitive Distortion Engine"]
        SA["SentimentAnalyzer<br/>(Lexical valence, booster & negation scoring)"]
        CDD["CognitiveDistortionDetector<br/>(Regex clinical pattern matcher)"]
        JAR["JournalAnalysisResult<br/>(Score, Emotion, Distortions, Reframe)"]
    end

    subgraph Domain ["⚡ Business Logic & State Machines"]
        CT["CountDownTimer State Controller<br/>(Phase transitions & cycle counters)"]
        SK["Streak Algorithm<br/>(Consecutive day delta verification)"]
        VI["ValueAnimator Engine<br/>(AccelerateDecelerate Interpolation)"]
    end

    subgraph Data ["💾 Data Persistence Layer (100% Offline SQLite)"]
        DH["DatabaseHelper (SQLiteOpenHelper v2)"]
        TM[("TABLE: mood_logs<br/>id, mood, note, date, timestamp")]
        TJ[("TABLE: journal_entries<br/>id, title, content, sentiment, emotion, date, timestamp")]
    end

    MA --> SK
    MA --> DH
    BA --> CT
    BA --> VI
    RA --> CT
    JA --> SA
    SA --> CDD
    SA --> JAR
    JA --> DH
    HA --> DH
    DH --> TM
    DH --> TJ
```

---

## 📱 Core Functional Modules

### 1. 🏠 Home Dashboard & Daily Habit Streak
- **Time-Aware Dynamic Greeting:** Adapts interface context based on morning, afternoon, or evening system time.
- **5-Tier Likert Mood Selector:** Fast 1-tap logging ranging from *Awful (1)* to *Great (5)* with optional descriptive notes.
- **Consecutive Logging Streak Engine:** Evaluates daily delta timestamps to reward habit consistency.
- **Quick-Access Navigation Matrix:** Instant routing to all mindfulness sub-modules via Material CardViews.

### 2. 💨 Breathing Synchronizer Engine
Visual pulse animation with configurable target cycles (default: 5 cycles) and automated countdown transitions:
| Pattern | Cadence | Primary Clinical / Mindfulness Benefit |
| :--- | :--- | :--- |
| **4-7-8 Breathing** | Inhale: 4s • Hold: 7s • Exhale: 8s | Parasympathetic nervous system activation, anxiety reduction |
| **Box Breathing** | Inhale: 4s • Hold: 4s • Exhale: 4s • Hold: 4s | Tactical stress relief, heightened cognitive focus |
| **4-4-2 Energizer** | Inhale: 4s • Hold: 4s • Exhale: 2s | Rapid tension release and midday alertness |

### 3. 🧘 5-Minute Guided Relaxation Sessions
Fully orchestrated multi-phase exercises with dedicated instruction cards and countdown timers:
- 🌿 **Mindfulness Meditation:** Present-moment breath grounding.
- 🧘‍♂️ **Progressive Body Scan:** Head-to-toe somatic tension release.
- 🌊 **Peaceful Visualization:** Nature and coastal sensory decompression.
- 🙏 **Gratitude Practice:** Cognitive reframing through appreciative recall.
- ✨ **Positive Affirmations:** Internal resilience and cognitive empowerment.

### 4. 🧠 On-Device CBT Thought Journal & Distortion Detector
Whenever an entry is saved, the on-device NLP engine inspects the text for sentiment polarity and cognitive distortion archetypes:

| Cognitive Distortion | Pattern Triggers | Therapeutic CBT Reframe Perspective |
| :--- | :--- | :--- |
| **All-or-Nothing Thinking** | *always, never, nothing works, completely ruined* | Prompts the user to identify grey area: even a 5% positive is meaningful evidence. |
| **Catastrophizing** | *worst day ever, disaster, hopeless, unbearable* | De-escalates worst-case magnification toward realistic, actionable small steps. |
| **Overgeneralization** | *everyone hates me, nobody cares, every single time* | Challenges sweeping assumptions by recalling exceptions to the rule. |
| **Should / Must Pressure** | *I should have known, I must be perfect* | Replaces rigid internal guilt with self-compassion and realistic effort framing. |
| **Emotional Reasoning** | *I feel like a failure, I feel so stupid* | Teaches emotional detachment: feelings are biological data points, not immutable facts. |

Entries in the journal feed are tagged with their detected emotional badge (`Positive & Uplifting ✨`, `Calm & Reflective 🌿`, `Mild Stress / Anxious 🌧️`, `Overwhelmed ⛈️`).

### 5. 📊 Mood History & Descriptive Analytics
- Frequency distribution array calculating logs across all 5 emotional tiers.
- Chronological timeline cards displaying historical mood levels, custom annotations, and formatted dates.

---

## 🗄️ Database Schema & Data Models

Managed locally via `DatabaseHelper.java` (`SQLiteOpenHelper` Version 2):

```sql
-- Mood tracking entries
CREATE TABLE mood_logs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    mood INTEGER NOT NULL,          -- Integer 1 (Very Bad) to 5 (Very Good)
    note TEXT,                      -- User reflection annotation
    date TEXT NOT NULL,             -- ISO yyyy-MM-dd
    timestamp LONG NOT NULL         -- Epoch milliseconds
);

-- Reflective journal entries with CBT sentiment
CREATE TABLE journal_entries (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT,                     -- Prompt topic or user title
    content TEXT NOT NULL,          -- Body reflection
    sentiment TEXT,                 -- e.g. 'Calm & Reflective 🌿'
    emotion TEXT,                   -- e.g. 'Grateful', 'Anxious', 'Calm'
    date TEXT NOT NULL,             -- ISO yyyy-MM-dd
    timestamp LONG NOT NULL         -- Epoch milliseconds
);
```

---

## 🧪 Unit Testing & Verification

The project includes 12 comprehensive unit tests:

1. **`BreathingAndMoodLogicTest.java`**:
   - `testBreathingPatternDurations`: Verifies exact phase sum calculations (4-7-8 = 19s, Box = 16s, 4-4-2 = 10s).
   - `testMoodLevelBoundaries`: Asserts strict boundary enforcement for mood levels 1 through 5.
   - `testStreakCalculationLogic`: Validates consecutive multi-day streak accumulation algorithm.
   - `testStreakBreakLogic`: Tests streak reset behavior on skipped calendar dates.

2. **`SentimentAndCbtTest.java`**:
   - `testPositiveSentimentDetection`: Validates positive valence, booster scaling, and emotion mapping.
   - `testNegationInversion`: Tests negation modifiers ('not happy') properly inverting sentiment score.
   - `testAllOrNothingThinkingDetection`: Validates black-and-white thinking detection and reframe prompts.
   - `testCatastrophizingDetection`: Asserts worst-case magnification triggers clinical guidance.
   - `testOvergeneralizationDetection`: Validates sweeping negative generalization checks.
   - `testShouldMustPressureDetection`: Asserts rigid expectations trigger self-compassion prompts.
   - `testEmotionalReasoningDetection`: Validates separation of emotional feelings from factual reality.
   - `testEmptyOrWhitespaceInputHandling`: Confirms graceful fallback on blank inputs.

Run tests locally via the bundled Gradle wrapper:
```bash
./gradlew testDebugUnitTest
```

---

## 🚀 Getting Started & Local Development

### Prerequisites
- **Android Studio** Hedgehog (2023.1.1+) or newer
- **JDK:** OpenJDK 17+
- **Android SDK:** API Level 34 (Compile & Target), API 24+ (Minimum)

### Build & Run
1. **Clone the repository:**
   ```bash
   git clone https://github.com/prachizala/mental-health-tracker-android.git
   cd mental-health-tracker-android
   ```
2. **Compile and execute unit tests:**
   ```bash
   # On macOS / Linux
   ./gradlew testDebugUnitTest

   # On Windows
   .\gradlew.bat testDebugUnitTest
   ```
3. **Assemble Debug APK:**
   ```bash
   .\gradlew.bat assembleDebug
   ```
   The compiled APK will be located at `app/build/outputs/apk/debug/app-debug.apk`.

---

## 📂 Project Structure

```
mental-health-tracker-android/
├── .github/
│   └── workflows/
│       └── android-ci.yml                 ← Automated GitHub Actions CI pipeline
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/mentalhealth/tracker/
│   │   │   │   ├── MainActivity.java             ← Dashboard & streak engine
│   │   │   │   ├── BreathingActivity.java        ← Animated breathing synchronizer
│   │   │   │   ├── RelaxActivity.java            ← Session menu
│   │   │   │   ├── RelaxSessionActivity.java     ← Timed guided relaxation
│   │   │   │   ├── JournalActivity.java          ← Offline thought journal with CBT prompts
│   │   │   │   ├── HistoryActivity.java          ← Mood history & analytics
│   │   │   │   ├── DatabaseHelper.java           ← SQLite DAO v2 & schema manager
│   │   │   │   └── cbt/                          ← On-Device CBT & Sentiment Engine
│   │   │   │       ├── SentimentAnalyzer.java
│   │   │   │       ├── CognitiveDistortionDetector.java
│   │   │   │       └── JournalAnalysisResult.java
│   │   │   ├── res/
│   │   │   │   ├── layout/                       ← Material UI XML layouts
│   │   │   │   ├── drawable/                     ← Custom shapes & vector assets
│   │   │   │   ├── mipmap-*/                     ← Adaptive launcher icons
│   │   │   │   └── values/                       ← Themes, colors, styles
│   │   │   └── AndroidManifest.xml
│   │   └── test/java/com/mentalhealth/tracker/
│   │       ├── BreathingAndMoodLogicTest.java    ← Breathing & streak tests
│   │       └── SentimentAndCbtTest.java          ← 8 on-device CBT & sentiment tests
│   └── build.gradle
├── gradle/wrapper/                               ← Official Gradle 8.5 wrapper
├── gradlew & gradlew.bat
├── gradle.properties
├── build.gradle
└── settings.gradle
```

---

## 📄 License

This project is licensed under the [MIT License](LICENSE) — free for educational, personal, and research usage.

---

<div align="center">
  <sub>Developed with ❤️ by <a href="https://github.com/prachizala">Prachi Zala</a> • BCA Student @ Shanti Business School, Ahmedabad</sub>
</div>

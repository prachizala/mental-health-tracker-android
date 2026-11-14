# 🏗️ Mental Health Tracker — System Architecture & Design Specification

This document provides a comprehensive technical blueprint of **Mental Health Tracker**, an offline-first native Android mindfulness and cognitive journaling application built with Java 8+ and Material Design 3.

---

## 🏛️ High-Level Architectural Flow

\+------------------------------------------------------------------------+
|                          Presentation Layer                            |
|  +--------------------+  +---------------------+  +-----------------+  |
|  |    MainActivity    |  |  BreathingActivity  |  | JournalActivity |  |
|  | (Mood & Navigation)|  |  (4-7-8 & Box Flow) |  | (CBT Dialogs)   |  |
|  +--------------------+  +---------------------+  +-----------------+  |
+------------------------------------------------------------------------+
                                    |
                                    v
+------------------------------------------------------------------------+
|                      On-Device Clinical Engine                         |
|  +-------------------------+         +-------------------------------+ |
|  |    SentimentAnalyzer    | <-----> |  CognitiveDistortionDetector  | |
|  | (Valence, Boost, Negate)|         | (5 Distortion Regex Patterns) | |
|  +-------------------------+         +-------------------------------+ |
|                               |                                        |
|                               v                                        |
|                  +--------------------------+                          |
|                  |  JournalAnalysisResult   |                          |
|                  | (Score, Emotion, Reframe)|                          |
|                  +--------------------------+                          |
+------------------------------------------------------------------------+
                                    |
                                    v
+------------------------------------------------------------------------+
|                           Data Persistence                             |
|               +--------------------------------------+                 |
|               |            DatabaseHelper            |                 |
|               |   SQLite v2 (Moods, Journals, CBT)   |                 |
|               +--------------------------------------+                 |
+------------------------------------------------------------------------+
\
---

## 🧩 Core Subsystems

### 1. On-Device Pure-Java NLP & CBT Engine (\com.mentalhealth.tracker.cbt\)
To ensure **100% user privacy**, all journal text analysis is executed entirely on-device without cloud dependencies, API keys, or network telemetry.

* **\SentimentAnalyzer.java\**:
  * Lexical valence scoring dictionary covering positive/negative sentiment primitives.
  * Context-aware booster multiplier handling (\ery\, \extremely\, \deeply\).
  * Negation window inversion (ot\, ever\, \can't\).
* **\CognitiveDistortionDetector.java\**:
  * Evaluates text across 5 clinically recognized Cognitive Behavioral Therapy (CBT) distortion patterns:
    1. **Catastrophizing:** Worst-case scenario fixation (uined\, \end of the world\).
    2. **All-or-Nothing:** Dichotomous black-and-white thinking (\lways\, ever\, \ailure\).
    3. **Overgeneralization:** Broad negative conclusions from single events (\everyone\, obody\).
    4. **Emotional Reasoning:** Mistaking feelings for objective truth (\I feel like a failure\).
    5. **Should Statements:** Unrealistic internalized expectations (\should have\, \must\).
  * Pairs each detected distortion with actionable, evidence-based **reframing prompts**.

### 2. Guided Breathing Visualizer (\BreathingActivity.java\)
* Built with hardware-accelerated Android \ValueAnimator\.
* Implements two medically validated breathing protocols:
  * **4-7-8 Relaxing Breath:** Inhale (4s), Hold (7s), Exhale (8s).
  * **Box Breathing:** Inhale (4s), Hold (4s), Exhale (4s), Hold (4s).
* Haptic feedback integration at phase boundaries to guide breathing without looking at the screen.

### 3. SQLite Persistence & Schema Evolution (\DatabaseHelper.java\)
* **Version 1:** \moods\ table storing timestamp, mood level (1-5), and notes.
* **Version 2 Migration:** \journals\ table upgraded with:
  * \sentiment\ (REAL): Computed sentiment score (-1.0 to +1.0).
  * \emotion\ (TEXT): Inferred emotional state (e.g., Hopeful, Overwhelmed, Reflective).
  * \distortion\ (TEXT): Primary detected cognitive distortion pattern.

---

## 🧪 Testing & CI/CD Pipeline

* **Unit Testing:** Comprehensive JUnit 4 test suite (\BreathingAndMoodLogicTest\ & \SentimentAndCbtTest\) validating 100% of algorithmic edge cases, negation flips, and whitespace handling.
* **Automated CI/CD:** GitHub Actions workflow (\.github/workflows/ci.yml\) running automated Gradle 8.5 build and test execution on every pull request and push to \master\.

# Changelog & Design Explained

This document explains recent documentation and project classification improvements in simple terms so the project owner can comfortably discuss them in technical screenings and interviews.

---

## 1. Summary of Changes

| Component | What Changed | Why It Was Changed | How It Works |
|---|---|---|---|
| **Medical Disclaimer** | Added prominent warning callout in `README.md` | Mental wellness apps must clearly establish ethical boundaries. | Clarifies to recruiters and users that the app is an educational self-care habit tracker, not a medical or psychiatric diagnostic tool. |
| **Language Classification** | Added `.gitattributes` | GitHub often misclassifies Android apps as "HTML" or "XML" because layout XML files make up a large fraction of lines. | Instructs GitHub's Linguist engine to ignore XML for language detection and identify the project as **Java**. |
| **Architecture Clarification** | Documented offline SQLite storage in `README.md` | Highlight technical design choices (privacy-first, offline-ready). | Explains that all journal entries and mood scores stay on-device in local SQLite (`DatabaseHelper.java`), ensuring total user privacy. |

---

## 2. Interview Q&A Cheatsheet

### Q: Why did you build this as a native Android app in Java instead of a hybrid framework?
> **Answer:** "I wanted to understand native Android fundamentals from scratch — Activities, Intents, Android lifecycle, ConstraintLayout, and the built-in SQLiteOpenHelper. Building it natively in Java with zero external third-party dependencies ensured it stayed lightweight, fast, and completely offline."

### Q: How is user data stored and protected?
> **Answer:** "All user inputs — mood logs, timestamps, notes, and journal entries — are stored locally in an embedded SQLite database using `DatabaseHelper.java`. No user data is sent over the network, providing complete privacy by design."

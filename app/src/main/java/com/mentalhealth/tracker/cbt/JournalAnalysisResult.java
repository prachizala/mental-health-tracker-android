package com.mentalhealth.tracker.cbt;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates the on-device sentiment, emotional classification,
 * detected cognitive distortions, and tailored CBT reframing prompts.
 */
public class JournalAnalysisResult {

    private final double sentimentScore; // Normalized between -1.0 and +1.0
    private final String sentimentLabel; // e.g. "Positive & Uplifting", "Calm & Reflective", "Anxious / Stressed"
    private final String primaryEmotion;  // e.g. "Grateful", "Anxious", "Low", "Calm", "Balanced"
    private final List<String> detectedDistortions;
    private final String cbtReframe;

    public JournalAnalysisResult(
            double sentimentScore,
            String sentimentLabel,
            String primaryEmotion,
            List<String> detectedDistortions,
            String cbtReframe) {
        this.sentimentScore = sentimentScore;
        this.sentimentLabel = sentimentLabel;
        this.primaryEmotion = primaryEmotion;
        this.detectedDistortions = detectedDistortions != null ? detectedDistortions : new ArrayList<>();
        this.cbtReframe = cbtReframe;
    }

    public double getSentimentScore() {
        return sentimentScore;
    }

    public String getSentimentLabel() {
        return sentimentLabel;
    }

    public String getPrimaryEmotion() {
        return primaryEmotion;
    }

    public List<String> getDetectedDistortions() {
        return detectedDistortions;
    }

    public String getCbtReframe() {
        return cbtReframe;
    }

    public boolean hasDistortions() {
        return detectedDistortions != null && !detectedDistortions.isEmpty();
    }
}

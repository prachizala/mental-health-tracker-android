package com.mentalhealth.tracker;

import org.junit.Test;
import static org.junit.Assert.*;

import com.mentalhealth.tracker.cbt.CognitiveDistortionDetector;
import com.mentalhealth.tracker.cbt.JournalAnalysisResult;
import com.mentalhealth.tracker.cbt.SentimentAnalyzer;
import java.util.List;

public class SentimentAndCbtTest {

    @Test
    public void testPositiveSentimentDetection() {
        String entry = "I had a wonderful day today. I feel so grateful and deeply peaceful.";
        JournalAnalysisResult result = SentimentAnalyzer.analyze(entry);

        assertTrue("Positive score should be greater than 0.3", result.getSentimentScore() > 0.3);
        assertTrue("Label should indicate positive tone", result.getSentimentLabel().contains("Positive"));
        assertEquals("Grateful", result.getPrimaryEmotion());
        assertFalse("No distortion expected in positive text", result.hasDistortions());
    }

    @Test
    public void testNegationInversion() {
        String negatedPositive = "I am not happy and not relaxed today.";
        JournalAnalysisResult result = SentimentAnalyzer.analyze(negatedPositive);

        assertTrue("Negated positive tokens must invert score to negative", result.getSentimentScore() < 0.0);
    }

    @Test
    public void testAllOrNothingThinkingDetection() {
        String entry = "Nothing ever works out for me and everything is ruined.";
        List<CognitiveDistortionDetector.DistortionMatch> matches = CognitiveDistortionDetector.detect(entry);

        boolean foundAllOrNothing = false;
        for (CognitiveDistortionDetector.DistortionMatch m : matches) {
            if ("All-or-Nothing Thinking".equals(m.name)) {
                foundAllOrNothing = true;
                assertTrue(m.reframePrompt.contains("always', 'never', or 'nothing'"));
            }
        }
        assertTrue("Should detect All-or-Nothing distortion", foundAllOrNothing);

        JournalAnalysisResult analysis = SentimentAnalyzer.analyze(entry);
        assertTrue(analysis.hasDistortions());
        assertTrue(analysis.getDetectedDistortions().contains("All-or-Nothing Thinking"));
    }

    @Test
    public void testCatastrophizingDetection() {
        String entry = "Today was the worst day ever and an absolute disaster. I feel hopeless.";
        JournalAnalysisResult result = SentimentAnalyzer.analyze(entry);

        assertTrue(result.hasDistortions());
        assertTrue(result.getDetectedDistortions().contains("Catastrophizing"));
        assertTrue(result.getCbtReframe().contains("Take a slow breath"));
    }

    @Test
    public void testOvergeneralizationDetection() {
        String entry = "Everyone hates me and nobody understands what I am going through.";
        JournalAnalysisResult result = SentimentAnalyzer.analyze(entry);

        assertTrue(result.hasDistortions());
        assertTrue(result.getDetectedDistortions().contains("Overgeneralization"));
    }

    @Test
    public void testShouldMustPressureDetection() {
        String entry = "I should have known better and I must be perfect at all times.";
        JournalAnalysisResult result = SentimentAnalyzer.analyze(entry);

        assertTrue(result.hasDistortions());
        assertTrue(result.getDetectedDistortions().contains("Should / Must Pressure"));
    }

    @Test
    public void testEmotionalReasoningDetection() {
        String entry = "I feel like a failure, and I feel so stupid.";
        JournalAnalysisResult result = SentimentAnalyzer.analyze(entry);

        assertTrue(result.hasDistortions());
        assertTrue(result.getDetectedDistortions().contains("Emotional Reasoning"));
    }

    @Test
    public void testEmptyOrWhitespaceInputHandling() {
        JournalAnalysisResult result = SentimentAnalyzer.analyze("   ");
        assertEquals(0.0, result.getSentimentScore(), 0.001);
        assertEquals("Balanced", result.getPrimaryEmotion());
        assertFalse(result.hasDistortions());
    }
}

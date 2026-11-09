package com.mentalhealth.tracker.cbt;

import java.util.*;

/**
 * On-device lexical sentiment analyzer with negation and booster awareness.
 * 100% offline, zero external dependencies.
 */
public class SentimentAnalyzer {

    private static final Map<String, Double> LEXICON = new HashMap<>();
    private static final Set<String> NEGATIONS = new HashSet<>(Arrays.asList(
        "not", "no", "never", "hardly", "barely", "scarcely", "without", "don't", "didn't", "won't", "can't"
    ));
    private static final Map<String, Double> BOOSTERS = new HashMap<>();

    static {
        // Boosters
        BOOSTERS.put("very", 1.5);
        BOOSTERS.put("really", 1.4);
        BOOSTERS.put("extremely", 1.8);
        BOOSTERS.put("so", 1.3);
        BOOSTERS.put("super", 1.5);
        BOOSTERS.put("deeply", 1.4);
        BOOSTERS.put("completely", 1.5);

        // Positive & Uplifting Lexicon (+0.5 to +2.0)
        LEXICON.put("happy", 1.5);
        LEXICON.put("grateful", 1.8);
        LEXICON.put("peace", 1.6);
        LEXICON.put("peaceful", 1.6);
        LEXICON.put("calm", 1.4);
        LEXICON.put("relaxed", 1.5);
        LEXICON.put("proud", 1.6);
        LEXICON.put("joy", 1.8);
        LEXICON.put("blessed", 1.7);
        LEXICON.put("hopeful", 1.5);
        LEXICON.put("loved", 1.8);
        LEXICON.put("relief", 1.4);
        LEXICON.put("relieved", 1.4);
        LEXICON.put("confident", 1.6);
        LEXICON.put("excited", 1.6);
        LEXICON.put("smile", 1.3);
        LEXICON.put("good", 1.0);
        LEXICON.put("great", 1.5);
        LEXICON.put("wonderful", 1.8);
        LEXICON.put("accomplished", 1.7);
        LEXICON.put("progress", 1.3);
        LEXICON.put("healthy", 1.4);
        LEXICON.put("content", 1.3);
        LEXICON.put("optimistic", 1.6);
        LEXICON.put("kind", 1.2);
        LEXICON.put("friend", 1.2);

        // Negative & Stress / Anxiety Lexicon (-0.5 to -2.0)
        LEXICON.put("sad", -1.5);
        LEXICON.put("anxious", -1.8);
        LEXICON.put("anxiety", -1.8);
        LEXICON.put("stress", -1.5);
        LEXICON.put("stressed", -1.6);
        LEXICON.put("panic", -2.0);
        LEXICON.put("tired", -1.0);
        LEXICON.put("exhausted", -1.5);
        LEXICON.put("overwhelmed", -1.8);
        LEXICON.put("angry", -1.6);
        LEXICON.put("cry", -1.4);
        LEXICON.put("crying", -1.5);
        LEXICON.put("lonely", -1.6);
        LEXICON.put("hopeless", -2.0);
        LEXICON.put("worthless", -2.0);
        LEXICON.put("hurt", -1.4);
        LEXICON.put("pain", -1.4);
        LEXICON.put("scared", -1.6);
        LEXICON.put("afraid", -1.5);
        LEXICON.put("guilty", -1.4);
        LEXICON.put("bad", -1.0);
        LEXICON.put("worst", -1.8);
        LEXICON.put("awful", -1.7);
        LEXICON.put("terrible", -1.8);
        LEXICON.put("struggling", -1.4);
        LEXICON.put("depressed", -2.0);
        LEXICON.put("nervous", -1.3);
        LEXICON.put("worried", -1.4);
    }

    /**
     * Performs end-to-end sentiment scoring, emotional classification,
     * and distortion detection on journal text.
     */
    public static JournalAnalysisResult analyze(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new JournalAnalysisResult(
                0.0,
                "Neutral ⚪",
                "Reflective",
                Collections.emptyList(),
                "Writing is the first step toward mental clarity. Be gentle with yourself today."
            );
        }

        // Clean and tokenize
        String cleaned = text.toLowerCase(Locale.ENGLISH).replaceAll("[^a-z'\\s-]", " ");
        String[] tokens = cleaned.split("\\s+");

        double totalScore = 0.0;
        int matchedWords = 0;
        boolean negated = false;
        double currentBooster = 1.0;

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            if (token.isEmpty()) continue;

            if (NEGATIONS.contains(token)) {
                negated = true;
                continue;
            }

            if (BOOSTERS.containsKey(token)) {
                currentBooster = BOOSTERS.get(token);
                continue;
            }

            if (LEXICON.containsKey(token)) {
                double wordScore = LEXICON.get(token) * currentBooster;
                if (negated) {
                    wordScore = -wordScore * 0.75; // inverted valence with moderate dampening
                }
                totalScore += wordScore;
                matchedWords++;

                // Reset modifiers
                negated = false;
                currentBooster = 1.0;
            } else {
                // If 2 words passed without hitting a sentiment word, reset negation
                if (negated && i > 0 && !NEGATIONS.contains(tokens[i - 1])) {
                    negated = false;
                }
                currentBooster = 1.0;
            }
        }

        // Normalize score between -1.0 and 1.0 using hyperbolic tangent approximation
        double normalizedScore = 0.0;
        if (matchedWords > 0) {
            double avgScore = totalScore / Math.sqrt(matchedWords);
            normalizedScore = Math.max(-1.0, Math.min(1.0, avgScore / 2.0));
        }

        // Detect cognitive distortions
        List<CognitiveDistortionDetector.DistortionMatch> distortionMatches = CognitiveDistortionDetector.detect(text);
        List<String> distortionNames = new ArrayList<>();
        StringBuilder cbtReframe = new StringBuilder();

        for (CognitiveDistortionDetector.DistortionMatch match : distortionMatches) {
            distortionNames.add(match.name);
            if (cbtReframe.length() > 0) cbtReframe.append("\n\n");
            cbtReframe.append("💡 ").append(match.name).append(":\n").append(match.reframePrompt);
        }

        // Emotional Categorization
        String sentimentLabel;
        String primaryEmotion;

        if (normalizedScore >= 0.35) {
            sentimentLabel = "Positive & Uplifting ✨";
            primaryEmotion = text.toLowerCase().contains("grateful") || text.toLowerCase().contains("thank") ? "Grateful" : "Optimistic";
            if (cbtReframe.length() == 0) {
                cbtReframe.append("🌟 Wonderful reflection! Savor this positive moment—it builds emotional resilience.");
            }
        } else if (normalizedScore >= 0.10) {
            sentimentLabel = "Calm & Reflective 🌿";
            primaryEmotion = "Calm";
            if (cbtReframe.length() == 0) {
                cbtReframe.append("🌿 Balanced perspective. Staying present with your thoughts is an act of mindfulness.");
            }
        } else if (normalizedScore > -0.15) {
            sentimentLabel = "Neutral & Observant ⚪";
            primaryEmotion = "Balanced";
            if (cbtReframe.length() == 0) {
                cbtReframe.append("📝 A thoughtful and grounded reflection. Every feeling is valid.");
            }
        } else if (normalizedScore > -0.45) {
            sentimentLabel = "Mild Stress / Anxious 🌧️";
            primaryEmotion = "Stressed";
            if (cbtReframe.length() == 0) {
                cbtReframe.append("🌧️ You seem to be carrying tension today. Remember to pause and take a 4-7-8 breathing session.");
            }
        } else {
            sentimentLabel = "Overwhelmed / Low ⛈️";
            primaryEmotion = "Overwhelmed";
            if (cbtReframe.length() == 0) {
                cbtReframe.append("⛈️ This feels heavy right now. Acknowledge the feeling without judgment—it will pass with time.");
            }
        }

        return new JournalAnalysisResult(
            normalizedScore,
            sentimentLabel,
            primaryEmotion,
            distortionNames,
            cbtReframe.toString()
        );
    }
}

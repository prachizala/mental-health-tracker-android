package com.mentalhealth.tracker.cbt;

import java.util.*;
import java.util.regex.Pattern;

/**
 * On-device rule-based detector for common cognitive distortions
 * (All-or-Nothing, Catastrophizing, Overgeneralization, Should/Must statements, Emotional Reasoning)
 * accompanied by clinically grounded Cognitive Behavioral Therapy (CBT) reframing prompts.
 */
public class CognitiveDistortionDetector {

    public static class DistortionMatch {
        public final String name;
        public final String patternTrigger;
        public final String reframePrompt;

        public DistortionMatch(String name, String patternTrigger, String reframePrompt) {
            this.name = name;
            this.patternTrigger = patternTrigger;
            this.reframePrompt = reframePrompt;
        }
    }

    private static final Map<String, String> REFRAMES = new LinkedHashMap<>();
    private static final Map<String, List<Pattern>> PATTERNS = new LinkedHashMap<>();

    static {
        // 1. All-or-Nothing / Black-and-White Thinking
        REFRAMES.put(
            "All-or-Nothing Thinking",
            "Notice words like 'always', 'never', or 'nothing'. Is the situation truly 100% one-sided, or is there grey area? Even a 5% positive moment is real."
        );
        PATTERNS.put("All-or-Nothing Thinking", Arrays.asList(
            Pattern.compile("\\b(nothing ever works|everything is ruined|always fails|never goes right|total failure|completely useless)\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\b(always happen to me|never succeed|nothing good)\\b", Pattern.CASE_INSENSITIVE)
        ));

        // 2. Catastrophizing (Magnifying worst-case scenarios)
        REFRAMES.put(
            "Catastrophizing",
            "When our nervous system feels overwhelmed, it predicts disaster. Take a slow breath: What is the most realistic outcome, and what is one small thing you can control right now?"
        );
        PATTERNS.put("Catastrophizing", Arrays.asList(
            Pattern.compile("\\b(worst day ever|unbearable|can't take this anymore|everything is falling apart|hopeless|end of the world|ruined my life)\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\b(disaster|my life is over|no point anymore)\\b", Pattern.CASE_INSENSITIVE)
        ));

        // 3. Overgeneralization
        REFRAMES.put(
            "Overgeneralization",
            "Our brain often magnifies one difficult incident into an overarching pattern. Can you recall one moment or one person where this assumption did not apply?"
        );
        PATTERNS.put("Overgeneralization", Arrays.asList(
            Pattern.compile("\\b(everyone hates me|no one cares|nobody understands|every single time|everyone else is better)\\b", Pattern.CASE_INSENSITIVE)
        ));

        // 4. Should / Must Statements
        REFRAMES.put(
            "Should / Must Pressure",
            "Using rigid 'shoulds' creates unnecessary internal guilt. Try gently shifting from 'I should have done better' to 'I am doing my best with the energy and tools I have today'."
        );
        PATTERNS.put("Should / Must Pressure", Arrays.asList(
            Pattern.compile("\\b(i should have known|i must be perfect|i shouldn't feel this way|i ought to be better|i must not make mistakes)\\b", Pattern.CASE_INSENSITIVE)
        ));

        // 5. Emotional Reasoning
        REFRAMES.put(
            "Emotional Reasoning",
            "Strong feelings can feel like undeniable truths, but emotions are physiological responses, not permanent facts. Feeling overwhelmed doesn't mean you are incapable."
        );
        PATTERNS.put("Emotional Reasoning", Arrays.asList(
            Pattern.compile("\\b(i feel like a failure|i feel so stupid|i feel completely worthless|i feel unlovable)\\b", Pattern.CASE_INSENSITIVE)
        ));
    }

    /**
     * Inspects text for cognitive distortion patterns.
     */
    public static List<DistortionMatch> detect(String text) {
        List<DistortionMatch> matches = new ArrayList<>();
        if (text == null || text.trim().isEmpty()) {
            return matches;
        }

        String lower = text.toLowerCase(Locale.ENGLISH);

        for (Map.Entry<String, List<Pattern>> entry : PATTERNS.entrySet()) {
            String distortionName = entry.getKey();
            for (Pattern pattern : entry.getValue()) {
                if (pattern.matcher(lower).find()) {
                    matches.add(new DistortionMatch(
                        distortionName,
                        pattern.pattern(),
                        REFRAMES.get(distortionName)
                    ));
                    break; // one match per distortion category
                }
            }
        }

        return matches;
    }
}

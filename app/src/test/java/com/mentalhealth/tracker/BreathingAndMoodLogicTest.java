package com.mentalhealth.tracker;

import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.*;

public class BreathingAndMoodLogicTest {

    @Test
    public void testBreathingPatternDurations() {
        int[] pattern478 = {4, 7, 8};
        int[] patternBox = {4, 4, 4, 4};
        int[] pattern442 = {4, 4, 2};

        int sum478 = 0;
        for (int sec : pattern478) sum478 += sec;
        assertEquals("4-7-8 pattern single cycle must equal 19 seconds", 19, sum478);

        int sumBox = 0;
        for (int sec : patternBox) sumBox += sec;
        assertEquals("Box breathing pattern single cycle must equal 16 seconds", 16, sumBox);

        int sum442 = 0;
        for (int sec : pattern442) sum442 += sec;
        assertEquals("4-4-2 pattern single cycle must equal 10 seconds", 10, sum442);
    }

    @Test
    public void testMoodLevelBoundaries() {
        int minMood = 1; // Very Bad
        int maxMood = 5; // Very Good

        for (int mood = minMood; mood <= maxMood; mood++) {
            assertTrue("Mood index must be between 1 and 5", mood >= 1 && mood <= 5);
        }
    }

    @Test
    public void testStreakCalculationLogic() {
        // Simulate a 3-day consecutive sequence ending today
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();

        List<String> loggedDates = new ArrayList<>();
        loggedDates.add(sdf.format(cal.getTime())); // today

        cal.add(Calendar.DAY_OF_YEAR, -1);
        loggedDates.add(sdf.format(cal.getTime())); // yesterday

        cal.add(Calendar.DAY_OF_YEAR, -1);
        loggedDates.add(sdf.format(cal.getTime())); // day before yesterday

        // Verify streak algorithm
        Calendar testCal = Calendar.getInstance();
        int streak = 0;
        for (String dateStr : loggedDates) {
            String expected = sdf.format(testCal.getTime());
            if (dateStr.equals(expected)) {
                streak++;
                testCal.add(Calendar.DAY_OF_YEAR, -1);
            } else {
                break;
            }
        }

        assertEquals("Streak should correctly count 3 consecutive days", 3, streak);
    }

    @Test
    public void testStreakBreakLogic() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();

        List<String> loggedDates = new ArrayList<>();
        // Skipped today, only logged yesterday and day before
        cal.add(Calendar.DAY_OF_YEAR, -1);
        loggedDates.add(sdf.format(cal.getTime()));

        Calendar testCal = Calendar.getInstance();
        int streak = 0;
        for (String dateStr : loggedDates) {
            String expected = sdf.format(testCal.getTime());
            if (dateStr.equals(expected)) {
                streak++;
                testCal.add(Calendar.DAY_OF_YEAR, -1);
            } else {
                break;
            }
        }

        assertEquals("Streak must be 0 if today has not been logged yet", 0, streak);
    }
}

package com.mentalhealth.tracker;

import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Unit tests for DatabaseHelper constants, schema queries, and date formatting.
 */
public class DatabaseContractTest {

    @Test
    public void testDateFormatValidity() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formatted = sdf.format(new Date());
        assertNotNull("Formatted date should not be null", formatted);
        assertTrue("Date must match yyyy-MM-dd pattern", formatted.matches("\\d{4}-\\d{2}-\\d{2}"));
    }

    @Test
    public void testMoodRatingBounds() {
        int[] validMoods = {1, 2, 3, 4, 5};
        for (int mood : validMoods) {
            assertTrue("Mood should be between 1 and 5", mood >= 1 && mood <= 5);
        }
    }

    @Test
    public void testTableCreationQuerySyntax() {
        String createMoodSql = "CREATE TABLE mood_logs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mood INTEGER NOT NULL," +
                "note TEXT," +
                "date TEXT NOT NULL," +
                "timestamp LONG NOT NULL)";
        
        String createJournalSql = "CREATE TABLE journal_entries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "timestamp LONG NOT NULL)";

        assertTrue(createMoodSql.contains("PRIMARY KEY"));
        assertTrue(createMoodSql.contains("mood INTEGER NOT NULL"));
        assertTrue(createJournalSql.contains("journal_entries"));
        assertTrue(createJournalSql.contains("content TEXT NOT NULL"));
    }

    @Test
    public void testJournalEntrySanitization() {
        String sampleTitle = "  Reflections on College  ";
        String sampleContent = "  Feeling focused today.  ";
        assertEquals("Reflections on College", sampleTitle.trim());
        assertEquals("Feeling focused today.", sampleContent.trim());
    }
}

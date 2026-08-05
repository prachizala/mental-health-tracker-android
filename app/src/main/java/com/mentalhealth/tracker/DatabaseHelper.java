package com.mentalhealth.tracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.SimpleDateFormat;
import java.util.*;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "MentalHealth.db";
    private static final int DB_VERSION = 1;

    // Tables
    private static final String TABLE_MOOD = "mood_logs";
    private static final String TABLE_JOURNAL = "journal_entries";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_MOOD + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mood INTEGER NOT NULL," +
                "note TEXT," +
                "date TEXT NOT NULL," +
                "timestamp LONG NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_JOURNAL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT," +
                "content TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "timestamp LONG NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOURNAL);
        onCreate(db);
    }

    private String getToday() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
    }

    public void insertMood(int mood, String note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("mood", mood);
        values.put("note", note);
        values.put("date", getToday());
        values.put("timestamp", System.currentTimeMillis());
        db.insert(TABLE_MOOD, null, values);
        db.close();
    }

    public int getTodayMood() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT mood FROM " + TABLE_MOOD + " WHERE date=? ORDER BY timestamp DESC LIMIT 1",
                new String[]{getToday()});
        int mood = -1;
        if (cursor.moveToFirst()) {
            mood = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return mood;
    }

    public int getStreak() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT DISTINCT date FROM " + TABLE_MOOD + " ORDER BY date DESC",
                null);
        int streak = 0;
        if (cursor.moveToFirst()) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            do {
                String dateStr = cursor.getString(0);
                String expectedDate = sdf.format(cal.getTime());
                if (dateStr.equals(expectedDate)) {
                    streak++;
                    cal.add(Calendar.DAY_OF_YEAR, -1);
                } else {
                    break;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return streak;
    }

    public List<Map<String, String>> getAllMoods() {
        List<Map<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT mood, note, date FROM " + TABLE_MOOD + " ORDER BY timestamp DESC",
                null);
        while (cursor.moveToNext()) {
            Map<String, String> row = new HashMap<>();
            row.put("mood", String.valueOf(cursor.getInt(0)));
            row.put("note", cursor.getString(1));
            row.put("date", cursor.getString(2));
            list.add(row);
        }
        cursor.close();
        db.close();
        return list;
    }

    public void insertJournal(String title, String content) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("content", content);
        values.put("date", getToday());
        values.put("timestamp", System.currentTimeMillis());
        db.insert(TABLE_JOURNAL, null, values);
        db.close();
    }

    public List<Map<String, String>> getAllJournals() {
        List<Map<String, String>> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT id, title, content, date FROM " + TABLE_JOURNAL + " ORDER BY timestamp DESC",
                null);
        while (cursor.moveToNext()) {
            Map<String, String> row = new HashMap<>();
            row.put("id", String.valueOf(cursor.getInt(0)));
            row.put("title", cursor.getString(1));
            row.put("content", cursor.getString(2));
            row.put("date", cursor.getString(3));
            list.add(row);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int[] getMoodStats() {
        // returns count for each mood level 1-5
        int[] stats = new int[5];
        SQLiteDatabase db = getReadableDatabase();
        for (int i = 1; i <= 5; i++) {
            Cursor cursor = db.rawQuery(
                    "SELECT COUNT(*) FROM " + TABLE_MOOD + " WHERE mood=?",
                    new String[]{String.valueOf(i)});
            if (cursor.moveToFirst()) stats[i - 1] = cursor.getInt(0);
            cursor.close();
        }
        db.close();
        return stats;
    }
}

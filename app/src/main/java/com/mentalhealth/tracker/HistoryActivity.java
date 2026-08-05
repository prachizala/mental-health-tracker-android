package com.mentalhealth.tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class HistoryActivity extends AppCompatActivity {

    private LinearLayout historyList;
    private TextView tvAvgMood, tvTotalLogs;
    private DatabaseHelper dbHelper;
    private String[] moodEmojis = {"😢", "😟", "😐", "🙂", "😄"};
    private String[] moodLabels = {"Very Bad", "Bad", "Okay", "Good", "Great"};
    private String[] moodColors = {"#FF5252", "#FF9800", "#FFC107", "#8BC34A", "#4CAF50"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        dbHelper = new DatabaseHelper(this);
        initViews();
        loadHistory();
        loadStats();
    }

    private void initViews() {
        historyList = findViewById(R.id.historyList);
        tvAvgMood = findViewById(R.id.tvAvgMood);
        tvTotalLogs = findViewById(R.id.tvTotalLogs);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void loadHistory() {
        historyList.removeAllViews();
        List<Map<String, String>> moods = dbHelper.getAllMoods();
        if (moods.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("No mood logs yet. Start tracking from the home screen!");
            tv.setTextColor(getResources().getColor(R.color.text_secondary));
            tv.setPadding(16, 24, 16, 24);
            historyList.addView(tv);
            return;
        }
        for (Map<String, String> mood : moods) {
            int moodVal = Integer.parseInt(mood.get("mood"));
            View item = getLayoutInflater().inflate(R.layout.item_mood_history, historyList, false);
            TextView tvEmoji = item.findViewById(R.id.itemMoodEmoji);
            TextView tvLabel = item.findViewById(R.id.itemMoodLabel);
            TextView tvDate = item.findViewById(R.id.itemMoodDate);
            TextView tvNote = item.findViewById(R.id.itemMoodNote);

            tvEmoji.setText(moodEmojis[moodVal - 1]);
            tvLabel.setText(moodLabels[moodVal - 1]);
            tvDate.setText(mood.get("date"));
            String note = mood.get("note");
            if (note != null && !note.isEmpty()) {
                tvNote.setText(note);
                tvNote.setVisibility(View.VISIBLE);
            } else {
                tvNote.setVisibility(View.GONE);
            }

            historyList.addView(item);
        }
    }

    private void loadStats() {
        int[] stats = dbHelper.getMoodStats();
        int total = 0, sum = 0;
        for (int i = 0; i < 5; i++) {
            total += stats[i];
            sum += stats[i] * (i + 1);
        }
        tvTotalLogs.setText("Total logs: " + total);
        if (total > 0) {
            float avg = (float) sum / total;
            int avgIndex = Math.round(avg) - 1;
            if (avgIndex < 0) avgIndex = 0;
            if (avgIndex > 4) avgIndex = 4;
            tvAvgMood.setText("Average mood: " + moodEmojis[avgIndex] + " " + String.format("%.1f", avg) + "/5");
        } else {
            tvAvgMood.setText("Average mood: —");
        }
    }
}

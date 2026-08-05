package com.mentalhealth.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends AppCompatActivity {

    private TextView tvGreeting, tvDate, tvCurrentMood, tvStreak;
    private LinearLayout moodContainer;
    private DatabaseHelper dbHelper;
    private String[] moodEmojis = {"😢", "😟", "😐", "🙂", "😄"};
    private String[] moodLabels = {"Very Bad", "Bad", "Okay", "Good", "Great"};
    private int selectedMood = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        initViews();
        setupMoodSelector();
        loadDashboard();
    }

    private void initViews() {
        tvGreeting = findViewById(R.id.tvGreeting);
        tvDate = findViewById(R.id.tvDate);
        tvCurrentMood = findViewById(R.id.tvCurrentMood);
        tvStreak = findViewById(R.id.tvStreak);
        moodContainer = findViewById(R.id.moodContainer);

        // Set greeting based on time
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String greeting = hour < 12 ? "Good Morning ☀️" : hour < 17 ? "Good Afternoon 🌤️" : "Good Evening 🌙";
        tvGreeting.setText(greeting);

        // Set date
        String date = new SimpleDateFormat("EEEE, MMM dd", Locale.getDefault()).format(new Date());
        tvDate.setText(date);

        // Navigation buttons
        CardView cardRelax = findViewById(R.id.cardRelax);
        CardView cardHistory = findViewById(R.id.cardHistory);
        CardView cardJournal = findViewById(R.id.cardJournal);
        CardView cardBreath = findViewById(R.id.cardBreath);

        cardRelax.setOnClickListener(v -> startActivity(new Intent(this, RelaxActivity.class)));
        cardHistory.setOnClickListener(v -> startActivity(new Intent(this, HistoryActivity.class)));
        cardJournal.setOnClickListener(v -> startActivity(new Intent(this, JournalActivity.class)));
        cardBreath.setOnClickListener(v -> startActivity(new Intent(this, BreathingActivity.class)));

        Button btnSaveMood = findViewById(R.id.btnSaveMood);
        btnSaveMood.setOnClickListener(v -> saveMood());
    }

    private void setupMoodSelector() {
        moodContainer.removeAllViews();
        for (int i = 0; i < moodEmojis.length; i++) {
            final int index = i;
            LinearLayout moodItem = new LinearLayout(this);
            moodItem.setOrientation(LinearLayout.VERTICAL);
            moodItem.setGravity(android.view.Gravity.CENTER);
            moodItem.setPadding(12, 12, 12, 12);

            TextView emoji = new TextView(this);
            emoji.setText(moodEmojis[i]);
            emoji.setTextSize(28);
            emoji.setGravity(android.view.Gravity.CENTER);

            TextView label = new TextView(this);
            label.setText(moodLabels[i]);
            label.setTextSize(10);
            label.setGravity(android.view.Gravity.CENTER);
            label.setTextColor(getResources().getColor(R.color.text_secondary));

            moodItem.addView(emoji);
            moodItem.addView(label);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
            moodItem.setLayoutParams(params);

            moodItem.setOnClickListener(v -> {
                selectedMood = index + 1;
                highlightMood(index);
            });

            moodContainer.addView(moodItem);
        }
    }

    private void highlightMood(int selected) {
        for (int i = 0; i < moodContainer.getChildCount(); i++) {
            View child = moodContainer.getChildAt(i);
            if (i == selected) {
                child.setBackgroundResource(R.drawable.mood_selected_bg);
            } else {
                child.setBackground(null);
            }
        }
    }

    private void saveMood() {
        if (selectedMood == -1) {
            Toast.makeText(this, "Please select your mood", Toast.LENGTH_SHORT).show();
            return;
        }
        EditText etNote = findViewById(R.id.etMoodNote);
        String note = etNote.getText().toString().trim();
        dbHelper.insertMood(selectedMood, note);
        Toast.makeText(this, "Mood saved! " + moodEmojis[selectedMood - 1], Toast.LENGTH_SHORT).show();
        loadDashboard();
        selectedMood = -1;
        setupMoodSelector();
        etNote.setText("");
    }

    private void loadDashboard() {
        int todayMood = dbHelper.getTodayMood();
        if (todayMood > 0) {
            tvCurrentMood.setText("Today's mood: " + moodEmojis[todayMood - 1] + " " + moodLabels[todayMood - 1]);
        } else {
            tvCurrentMood.setText("No mood logged today yet");
        }
        int streak = dbHelper.getStreak();
        tvStreak.setText("🔥 " + streak + " day streak");
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboard();
    }
}

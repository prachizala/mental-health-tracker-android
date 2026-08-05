package com.mentalhealth.tracker;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.*;

public class JournalActivity extends AppCompatActivity {

    private EditText etTitle, etContent;
    private Button btnSave;
    private LinearLayout journalList;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);

        dbHelper = new DatabaseHelper(this);
        initViews();
        loadJournals();
    }

    private void initViews() {
        etTitle = findViewById(R.id.etJournalTitle);
        etContent = findViewById(R.id.etJournalContent);
        btnSave = findViewById(R.id.btnSaveJournal);
        journalList = findViewById(R.id.journalList);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveJournal());

        // Prompts
        String[] prompts = {
            "What made you smile today?",
            "What are you grateful for right now?",
            "What challenged you today?",
            "How are you feeling in this moment?",
            "What did you learn about yourself today?"
        };
        TextView tvPrompt = findViewById(R.id.tvPrompt);
        Random rand = new Random();
        tvPrompt.setText("💭 " + prompts[rand.nextInt(prompts.length)]);
    }

    private void saveJournal() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if (content.isEmpty()) {
            Toast.makeText(this, "Please write something first", Toast.LENGTH_SHORT).show();
            return;
        }
        if (title.isEmpty()) title = "My Thoughts";
        dbHelper.insertJournal(title, content);
        etTitle.setText("");
        etContent.setText("");
        Toast.makeText(this, "Journal entry saved! 📓", Toast.LENGTH_SHORT).show();
        loadJournals();
    }

    private void loadJournals() {
        journalList.removeAllViews();
        List<Map<String, String>> journals = dbHelper.getAllJournals();
        if (journals.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("No entries yet. Start writing your thoughts!");
            tv.setTextColor(getResources().getColor(R.color.text_secondary));
            tv.setPadding(16, 24, 16, 24);
            journalList.addView(tv);
            return;
        }
        for (Map<String, String> journal : journals) {
            View card = getLayoutInflater().inflate(R.layout.item_journal, journalList, false);
            TextView tvTitle = card.findViewById(R.id.itemJournalTitle);
            TextView tvDate = card.findViewById(R.id.itemJournalDate);
            TextView tvPreview = card.findViewById(R.id.itemJournalPreview);

            tvTitle.setText(journal.get("title"));
            tvDate.setText(journal.get("date"));
            String content = journal.get("content");
            tvPreview.setText(content.length() > 100 ? content.substring(0, 100) + "..." : content);

            journalList.addView(card);
        }
    }
}

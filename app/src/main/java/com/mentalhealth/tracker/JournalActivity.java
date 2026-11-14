package com.mentalhealth.tracker;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.mentalhealth.tracker.cbt.JournalAnalysisResult;
import com.mentalhealth.tracker.cbt.SentimentAnalyzer;
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

        btnSave.setOnClickListener(v -> promptCbtAnalysisAndSave());

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

    private void promptCbtAnalysisAndSave() {
        final String title = etTitle.getText().toString().trim();
        final String content = etContent.getText().toString().trim();

        if (content.isEmpty()) {
            Toast.makeText(this, "Please write something first", Toast.LENGTH_SHORT).show();
            return;
        }

        final String finalTitle = title.isEmpty() ? "My Thoughts" : title;

        // Perform 100% on-device sentiment & cognitive distortion analysis
        final JournalAnalysisResult analysis = SentimentAnalyzer.analyze(content);

        // Build interactive CBT reflection dialog
        StringBuilder dialogBody = new StringBuilder();
        dialogBody.append("Emotional Tone: ").append(analysis.getSentimentLabel()).append("\n\n");

        if (analysis.hasDistortions()) {
            dialogBody.append("💡 Cognitive Thought Pattern Detected:\n");
            for (String distortion : analysis.getDetectedDistortions()) {
                dialogBody.append("• ").append(distortion).append("\n");
            }
            dialogBody.append("\n🌿 CBT Reframing Perspective:\n");
            dialogBody.append(analysis.getCbtReframe());
        } else {
            dialogBody.append("🌿 Mindful Reflection:\n");
            dialogBody.append(analysis.getCbtReframe());
        }

        new AlertDialog.Builder(this)
            .setTitle("🧠 Thought Insight & Reflection")
            .setMessage(dialogBody.toString())
            .setPositiveButton("Save Entry", (dialog, which) -> {
                dbHelper.insertJournal(
                    finalTitle,
                    content,
                    analysis.getSentimentLabel(),
                    analysis.getPrimaryEmotion()
                );
                etTitle.setText("");
                etContent.setText("");
                Toast.makeText(this, "Journal entry saved with insight! 📓", Toast.LENGTH_SHORT).show();
                loadJournals();
            })
            .setNegativeButton("Keep Editing", (dialog, which) -> dialog.dismiss())
            .show();
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
            TextView tvSentiment = card.findViewById(R.id.itemJournalSentiment);
            TextView tvPreview = card.findViewById(R.id.itemJournalPreview);

            tvTitle.setText(journal.get("title"));
            tvDate.setText(journal.get("date"));

            String sentiment = journal.get("sentiment");
            if (sentiment != null && !sentiment.isEmpty()) {
                tvSentiment.setVisibility(View.VISIBLE);
                tvSentiment.setText(sentiment);
            } else {
                tvSentiment.setVisibility(View.GONE);
            }

            String content = journal.get("content");
            tvPreview.setText(content.length() > 100 ? content.substring(0, 100) + "..." : content);

            journalList.addView(card);
        }
    }
}

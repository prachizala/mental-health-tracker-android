package com.mentalhealth.tracker;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RelaxSessionActivity extends AppCompatActivity {

    private TextView tvTitle, tvStep, tvTimer, tvProgress;
    private ProgressBar progressBar;
    private Button btnStartSession, btnNext;
    private LinearLayout sessionLayout;

    private List<String[]> steps = new ArrayList<>(); // {duration_sec, instruction}
    private int currentStep = 0;
    private CountDownTimer timer;
    private boolean sessionStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax_session);

        String type = getIntent().getStringExtra("type");
        loadSession(type);
        initViews();
    }

    private void loadSession(String type) {
        if (type == null) type = "meditation";
        switch (type) {
            case "meditation":
                tvTitleStr = "5-Min Mindfulness";
                steps.add(new String[]{"30", "Close your eyes and sit comfortably. Take 3 deep breaths to begin..."});
                steps.add(new String[]{"60", "Focus on your breathing. Notice each inhale and exhale. Don't control it, just observe."});
                steps.add(new String[]{"60", "Thoughts will come. Acknowledge them gently, then let them float away like clouds."});
                steps.add(new String[]{"60", "Now focus on sensations in your body. Notice any tension and breathe into those areas."});
                steps.add(new String[]{"60", "Expand your awareness to sounds around you without judging them."});
                steps.add(new String[]{"30", "Slowly bring your awareness back. Wiggle your fingers. Open your eyes when ready."});
                break;
            case "bodyscan":
                tvTitleStr = "Body Scan Relaxation";
                steps.add(new String[]{"20", "Lie down or sit comfortably. Close your eyes. Take 3 slow deep breaths."});
                steps.add(new String[]{"40", "Bring attention to your feet and toes. Relax any tension you feel there."});
                steps.add(new String[]{"40", "Move attention up to your calves and knees. Let them soften and release."});
                steps.add(new String[]{"40", "Notice your thighs and hips. Allow them to feel heavy and relaxed."});
                steps.add(new String[]{"40", "Your stomach and chest. Feel them rise and fall with each breath."});
                steps.add(new String[]{"40", "Your shoulders, arms, and hands. Let all tension melt away."});
                steps.add(new String[]{"40", "Your neck, face, and scalp. Unclench your jaw. Soften your eyes."});
                steps.add(new String[]{"20", "Your whole body is relaxed. Rest here. You are calm and safe."});
                break;
            case "visualize":
                tvTitleStr = "Peaceful Visualization";
                steps.add(new String[]{"20", "Close your eyes. Take three slow, deep breaths."});
                steps.add(new String[]{"60", "Imagine yourself on a peaceful beach. Feel warm sand beneath your feet."});
                steps.add(new String[]{"60", "Hear gentle waves lapping the shore. Feel a soft breeze on your face."});
                steps.add(new String[]{"60", "The water is a perfect shade of blue. The sky is clear and bright."});
                steps.add(new String[]{"60", "You feel completely safe, at ease, and free. Let this peace fill you."});
                steps.add(new String[]{"40", "Slowly begin returning. Carry this calm feeling with you."});
                break;
            case "gratitude":
                tvTitleStr = "Gratitude Practice";
                steps.add(new String[]{"30", "Find a comfortable position. Close your eyes and take a deep breath."});
                steps.add(new String[]{"60", "Think of ONE person who has positively impacted your life. Feel genuine appreciation."});
                steps.add(new String[]{"60", "Think of ONE thing about your body you are grateful for today."});
                steps.add(new String[]{"60", "Think of ONE simple pleasure you experienced today, however small."});
                steps.add(new String[]{"60", "Think of ONE challenge that taught you something valuable."});
                steps.add(new String[]{"30", "Place your hand on your heart. Feel grateful for this moment."});
                break;
            case "affirmations":
                tvTitleStr = "Positive Affirmations";
                steps.add(new String[]{"20", "Take 3 deep breaths. Open your mind to receive these affirmations."});
                steps.add(new String[]{"40", "Repeat slowly: \"I am worthy of love and belonging.\""});
                steps.add(new String[]{"40", "Repeat slowly: \"I am capable of handling whatever comes my way.\""});
                steps.add(new String[]{"40", "Repeat slowly: \"I choose peace over worry.\""});
                steps.add(new String[]{"40", "Repeat slowly: \"I am growing and becoming better each day.\""});
                steps.add(new String[]{"40", "Repeat slowly: \"I deserve happiness and I allow myself to receive it.\""});
                steps.add(new String[]{"60", "Sit quietly. Feel the truth in these words settling into your heart."});
                break;
        }
    }

    private String tvTitleStr = "Relaxation";

    private void initViews() {
        tvTitle = findViewById(R.id.tvSessionTitle);
        tvStep = findViewById(R.id.tvStep);
        tvTimer = findViewById(R.id.tvSessionTimer);
        tvProgress = findViewById(R.id.tvStepProgress);
        progressBar = findViewById(R.id.progressSession);
        btnStartSession = findViewById(R.id.btnStartSession);
        btnNext = findViewById(R.id.btnNext);
        sessionLayout = findViewById(R.id.sessionLayout);

        tvTitle.setText(tvTitleStr);
        tvStep.setText("Press Start to begin your 5-minute session");
        tvProgress.setText("Step 0 / " + steps.size());
        progressBar.setMax(steps.size());
        progressBar.setProgress(0);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnStartSession.setOnClickListener(v -> {
            sessionStarted = true;
            btnStartSession.setVisibility(View.GONE);
            currentStep = 0;
            runStep();
        });

        btnNext.setVisibility(View.GONE);
        btnNext.setOnClickListener(v -> {
            if (timer != null) timer.cancel();
            currentStep++;
            btnNext.setVisibility(View.GONE);
            if (currentStep < steps.size()) {
                runStep();
            } else {
                finishSession();
            }
        });
    }

    private void runStep() {
        if (currentStep >= steps.size()) {
            finishSession();
            return;
        }
        String[] step = steps.get(currentStep);
        int duration = Integer.parseInt(step[0]) * 1000;
        tvStep.setText(step[1]);
        tvProgress.setText("Step " + (currentStep + 1) + " / " + steps.size());
        progressBar.setProgress(currentStep + 1);

        timer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(millisUntilFinished / 1000 + "s");
            }
            @Override
            public void onFinish() {
                tvTimer.setText("");
                currentStep++;
                if (currentStep < steps.size()) {
                    runStep();
                } else {
                    finishSession();
                }
            }
        }.start();
    }

    private void finishSession() {
        if (timer != null) timer.cancel();
        tvTitle.setText("Session Complete! 🌟");
        tvStep.setText("Wonderful! You've completed this relaxation session.\n\nTake a moment to notice how you feel. Carry this calm with you.");
        tvTimer.setText("");
        tvProgress.setText("Complete ✓");
        progressBar.setProgress(steps.size());
        btnNext.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}

package com.mentalhealth.tracker;

import android.animation.*;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BreathingActivity extends AppCompatActivity {

    private TextView tvPhase, tvInstruction, tvTimer, tvCycles;
    private View breathCircle;
    private Button btnStart, btnStop;
    private CardView card478, cardBox, card442;

    private CountDownTimer countDownTimer;
    private boolean isRunning = false;
    private int currentCycle = 0;
    private int totalCycles = 5;
    private String selectedPattern = "478"; // default

    // Breathing patterns: inhale, hold, exhale (in seconds)
    private final int[] PATTERN_478 = {4, 7, 8};
    private final int[] PATTERN_BOX = {4, 4, 4, 4}; // inhale, hold, exhale, hold
    private final int[] PATTERN_442 = {4, 4, 2};

    private int[] currentPattern = PATTERN_478;
    private int phaseIndex = 0;
    private String[] phaseNames478 = {"Inhale", "Hold", "Exhale"};
    private String[] phaseNamesBox = {"Inhale", "Hold", "Exhale", "Hold"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breathing);

        initViews();
        setupPatternSelector();
    }

    private void initViews() {
        tvPhase = findViewById(R.id.tvPhase);
        tvInstruction = findViewById(R.id.tvInstruction);
        tvTimer = findViewById(R.id.tvTimer);
        tvCycles = findViewById(R.id.tvCycles);
        breathCircle = findViewById(R.id.breathCircle);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        card478 = findViewById(R.id.card478);
        cardBox = findViewById(R.id.cardBox);
        card442 = findViewById(R.id.card442);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        btnStart.setOnClickListener(v -> startBreathing());
        btnStop.setOnClickListener(v -> stopBreathing());

        tvCycles.setText("Cycles: 0 / " + totalCycles);
    }

    private void setupPatternSelector() {
        card478.setOnClickListener(v -> {
            selectedPattern = "478";
            currentPattern = PATTERN_478;
            highlightPattern(card478);
            stopBreathing();
        });
        cardBox.setOnClickListener(v -> {
            selectedPattern = "box";
            currentPattern = PATTERN_BOX;
            highlightPattern(cardBox);
            stopBreathing();
        });
        card442.setOnClickListener(v -> {
            selectedPattern = "442";
            currentPattern = PATTERN_442;
            highlightPattern(card442);
            stopBreathing();
        });

        highlightPattern(card478);
    }

    private void highlightPattern(CardView selected) {
        card478.setCardBackgroundColor(getResources().getColor(R.color.card_bg));
        cardBox.setCardBackgroundColor(getResources().getColor(R.color.card_bg));
        card442.setCardBackgroundColor(getResources().getColor(R.color.card_bg));
        selected.setCardBackgroundColor(getResources().getColor(R.color.primary));
    }

    private void startBreathing() {
        if (isRunning) return;
        isRunning = true;
        currentCycle = 0;
        phaseIndex = 0;
        btnStart.setVisibility(View.GONE);
        btnStop.setVisibility(View.VISIBLE);
        runPhase();
    }

    private void runPhase() {
        if (!isRunning) return;
        if (currentCycle >= totalCycles) {
            stopBreathing();
            Toast.makeText(this, "Great job! Session complete 🎉", Toast.LENGTH_LONG).show();
            return;
        }

        String[] phaseNames = selectedPattern.equals("box") ? phaseNamesBox : phaseNames478;
        String phase = phaseNames[phaseIndex % phaseNames.length];
        int duration = currentPattern[phaseIndex % currentPattern.length] * 1000;

        tvPhase.setText(phase);
        setInstruction(phase);
        animateCircle(phase, duration);

        countDownTimer = new CountDownTimer(duration, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText(String.format("%.1f", millisUntilFinished / 1000.0));
            }
            @Override
            public void onFinish() {
                phaseIndex++;
                if (phaseIndex >= currentPattern.length) {
                    phaseIndex = 0;
                    currentCycle++;
                    tvCycles.setText("Cycles: " + currentCycle + " / " + totalCycles);
                }
                runPhase();
            }
        }.start();
    }

    private void setInstruction(String phase) {
        switch (phase) {
            case "Inhale":
                tvInstruction.setText("Breathe in slowly through your nose...");
                break;
            case "Hold":
                tvInstruction.setText("Hold your breath gently...");
                break;
            case "Exhale":
                tvInstruction.setText("Breathe out slowly through your mouth...");
                break;
        }
    }

    private void animateCircle(String phase, int duration) {
        ObjectAnimator scaleX, scaleY;
        if (phase.equals("Inhale")) {
            scaleX = ObjectAnimator.ofFloat(breathCircle, "scaleX", 0.5f, 1.0f);
            scaleY = ObjectAnimator.ofFloat(breathCircle, "scaleY", 0.5f, 1.0f);
        } else if (phase.equals("Exhale")) {
            scaleX = ObjectAnimator.ofFloat(breathCircle, "scaleX", 1.0f, 0.5f);
            scaleY = ObjectAnimator.ofFloat(breathCircle, "scaleY", 1.0f, 0.5f);
        } else {
            return; // Hold - no animation
        }
        scaleX.setDuration(duration);
        scaleY.setDuration(duration);
        scaleX.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleY.setInterpolator(new AccelerateDecelerateInterpolator());
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(scaleX, scaleY);
        animSet.start();
    }

    private void stopBreathing() {
        isRunning = false;
        if (countDownTimer != null) countDownTimer.cancel();
        phaseIndex = 0;
        currentCycle = 0;
        breathCircle.setScaleX(0.7f);
        breathCircle.setScaleY(0.7f);
        tvPhase.setText("Ready");
        tvInstruction.setText("Choose a pattern and press Start");
        tvTimer.setText("");
        tvCycles.setText("Cycles: 0 / " + totalCycles);
        btnStart.setVisibility(View.VISIBLE);
        btnStop.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}

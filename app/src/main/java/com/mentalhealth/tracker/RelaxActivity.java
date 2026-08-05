package com.mentalhealth.tracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class RelaxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relax);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        CardView cardBreathing = findViewById(R.id.cardBreathingRelax);
        CardView cardMeditation = findViewById(R.id.cardMeditation);
        CardView cardBodyScan = findViewById(R.id.cardBodyScan);
        CardView cardVisualize = findViewById(R.id.cardVisualize);
        CardView cardGratitude = findViewById(R.id.cardGratitude);
        CardView cardAffirmations = findViewById(R.id.cardAffirmations);

        cardBreathing.setOnClickListener(v -> startActivity(new Intent(this, BreathingActivity.class)));
        cardMeditation.setOnClickListener(v -> startRelax("meditation"));
        cardBodyScan.setOnClickListener(v -> startRelax("bodyscan"));
        cardVisualize.setOnClickListener(v -> startRelax("visualize"));
        cardGratitude.setOnClickListener(v -> startRelax("gratitude"));
        cardAffirmations.setOnClickListener(v -> startRelax("affirmations"));
    }

    private void startRelax(String type) {
        Intent intent = new Intent(this, RelaxSessionActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }
}

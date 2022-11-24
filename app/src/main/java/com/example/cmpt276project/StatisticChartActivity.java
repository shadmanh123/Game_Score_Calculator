package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class StatisticChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_chart);
        int index = getIntent().getIntExtra("index", 0);

        setOnClick(index);
    }

    private void setOnClick(int index) {
        Button table = findViewById(R.id.statsTable);
        table.setOnClickListener(v -> {
            finish();
        });
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, StatisticChartActivity.class);
        intent.putExtra("index", gameIndex);
        return intent;
    }
}
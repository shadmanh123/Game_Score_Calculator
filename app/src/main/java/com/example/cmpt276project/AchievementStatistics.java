package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;

public class AchievementStatistics extends AppCompatActivity {
    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private GameManager gameManager;
    private JsonReader jsonReader;
    private Game game;
    private final int COLUMN_SIZE = 2;
    private final int ROW_SIZE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_statistics);
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        int index = getIntent().getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        game = gameManager.getGame(index);
        populateText();

        setOnClick(index);
    }

    private void setOnClick(int index) {
        FloatingActionButton back = findViewById(R.id.floatingBackButton2);
        back.setOnClickListener(v -> {
            finish();
        });

        Button table = findViewById(R.id.statsCharts);
        table.setOnClickListener(v -> {
            Intent intent = StatisticChartActivity.makeIntent(this, index);
            startActivity(intent);
        });
    }

    private void populateText() {
        TableLayout table = findViewById(R.id.statisticTable);
        for (int row = 0; row < ROW_SIZE; row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for (int col = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tv.setTextSize(16);
                tv.setPadding(0, 30,0, 30);
                tableRow.addView(tv);
                String tvText = fillInLevelText(col, row);
                tv.setText(tvText);
                tv.setGravity(Gravity.CENTER);
            }
        }
    }

    private String fillInLevelText(int col, int row) {
        HashMap<Integer, Integer> lvlStats = game.achievementStatistics();
        String text;
        switch(col) {
            case 0:
                text = "Level " + (row + 1);
                break;
            case 1:
                text = "" + lvlStats.get(row + 1);
                break;
            default:
                text = "";
                break;
        }
        return text;
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, AchievementStatistics.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }
}
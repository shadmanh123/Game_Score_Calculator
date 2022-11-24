package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;

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
    }


    private void populateText() {
        TableLayout table = findViewById(R.id.tableForHistory);
        for (int row = 0; row < ROW_SIZE; row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for (int col = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tv.setTextSize(12);
                tableRow.addView(tv);
                String tvText = fillInLevelText(col, row);
                tv.setText(tvText);
               // tv.setBackgroundColor(Color.parseColor("#CBF4F1"));
                tv.setGravity(Gravity.CENTER);
            }
        }
    }

    private String fillInLevelText(int col, int row) {
        HashMap<Integer, Integer> lvlStats = game.achievementStatistics();
        String text;
        switch(col) {
            case 0:
                text = "Level " + lvlStats.get(row);
                break;
            case 1:
                text = "" + lvlStats.get(row);
                break;
            default:
                text = "";
                break;
        }
        return "0";
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, AchievementStatistics.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }
}
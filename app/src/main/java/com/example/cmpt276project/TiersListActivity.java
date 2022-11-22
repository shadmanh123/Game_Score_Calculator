package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.tiers.Land;
import com.example.cmpt276project.model.tiers.Ocean;
import com.example.cmpt276project.model.tiers.Sky;
import com.example.cmpt276project.model.tiers.Tier;
import com.example.cmpt276project.persistence.JsonReader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TiersListActivity extends AppCompatActivity {
    private GameManager gameManager;
    private String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiers_list);

        JsonReader jsonReader = new JsonReader(this);
        gameManager = jsonReader.readFromJson();

        FloatingActionButton back = findViewById(R.id.floatingBackButton5);
        back.setOnClickListener(v -> onBackClick());

        Intent intent = getIntent();
        //int position = intent.getIntExtra("position", 0);
       // int numPlayers = intent.getIntExtra("players", 0);
        int numPlayers = 2;
        int position = 0;

        theme = OptionsActivity.getThemeSelected(this);
        setThemeTitle(theme);
        setLevels(theme, numPlayers, position);
    }

    private void setThemeTitle(String theme) {
        TextView title = findViewById(R.id.theme);
        title.setText(theme);
    }

    private void onBackClick() {
        finish();
    }

    public static Intent tiersIntent(Context context) {
        Intent intent = new Intent(context, TiersListActivity.class);
//        intent.putExtra("position", position);
//        intent.putExtra("players", numPlayers);
        return intent;
    }

    private Double setIntervals(int numPlayers, int row, int pos) {
        Game game = gameManager.getGame(pos);
        String difficultyLevel = OptionsActivity.getDifficultySelected(this);
        HashMap<Tier, Double> achievements = new HashMap<>();
        List<Double> scores = Play.getListOfAchievements(game, numPlayers, difficultyLevel, theme, achievements);
        return scores.get(row);
    }

    private void setLevels(String theme, int numPlayers, int pos) {
        TableLayout table = findViewById(R.id.tiersTable);
        final int ROW_SIZE = 10;
        for (int row = 0; row < ROW_SIZE; row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            final int COLUMN_SIZE = 2;
            for (int col = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tableRow.addView(tv);
                List<Tier> tiers = getTier(theme);
                Tier tier = tiers.get(row);
                String tvText = fillInTierTable(tier, col, row, numPlayers, pos);
                tv.setText(tvText);
                tv.setPadding(2, 25, 2, 25);
                tv.setGravity(Gravity.CENTER);
            }
        }
    }

    private String fillInTierTable(Tier tier, int col, int row, int numPlayers, int pos) {
        String tierLevel;
        switch(col) {
            case 0:
                tierLevel = tier.getLevel();
                break;
            case 1:
                tierLevel = "" + setIntervals(numPlayers, row, pos);
                break;
            default:
                tierLevel = "";
        }
        return tierLevel;
    }

    private List<Tier> getTier(String theme) {
        List<Tier> tiers;
        switch(theme) {
            case "Land":
                tiers = Arrays.asList(Land.values());
                break;
            case "Sky":
                tiers = Arrays.asList(Sky.values());
                break;
            default:
                tiers = Arrays.asList(Ocean.values());
                break;
        }
        return tiers;
    }

}
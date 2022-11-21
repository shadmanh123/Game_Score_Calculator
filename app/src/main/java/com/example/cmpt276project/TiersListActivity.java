package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Land;
import com.example.cmpt276project.model.Oceans;
import com.example.cmpt276project.model.Sky;
import com.example.cmpt276project.persistence.JsonReader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TiersListActivity extends AppCompatActivity {
    GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiers_list);

        JsonReader jsonReader = new JsonReader(this, gameManager);
        gameManager = jsonReader.readFromJson();

        FloatingActionButton back = findViewById(R.id.floatingBackButton5);
        back.setOnClickListener(v -> onBackClick());

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        int numPlayers = intent.getIntExtra("players", 0);

        String theme = OptionsActivity.getThemeSelected(this);
        setThemeTitle(theme);
        setLevels(theme);
        setIntervals(numPlayers, position);
    }

    private void setThemeTitle(String theme) {
        TextView title = findViewById(R.id.theme);
        title.setText(theme);
    }

    private void onBackClick() {
        finish();
    }

    public static Intent tiersIntent(Context context, int position, int numPlayers) {
        Intent intent = new Intent(context, TiersListActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("players", numPlayers);
        return intent;
    }

    public double getDifficultyLevel(String difficultyLevel) {
        double difficulty_value;
        if (difficultyLevel == "easy") {
            difficulty_value = 0.75;
        } else if (difficultyLevel == "normal") {
            difficulty_value = 1;
        } else {
            difficulty_value = 1.25;
        }
        return difficulty_value;
    }

    private void setIntervals(int numPlayers, int pos) {
        Game game = gameManager.getGame(pos);       //i need to pass in num of players and position

        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(OptionsActivity.getDifficultySelected(this));
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(OptionsActivity.getDifficultySelected(this));
        double scoreInterval = (max - min) / 8;
        double minScore = max;

        int[] scores = new int[10];

        for (int i = 0; i < 10; i++) {
            if (i == 9) {
                minScore = 0;
            } else if (minScore - scoreInterval <= min) {
                if (minScore >= 0) {
                    minScore /= 2;
                } else {
                    minScore = 0;
                }
            } else {
                minScore -= scoreInterval;
            }
            int intScore = (int) minScore;
            scores[i] = intScore;
        }

        //setting all the values
        TextView levelOne = findViewById(R.id.level_1_score);
        levelOne.setText(scores[9]);

        TextView leveltwo = findViewById(R.id.level_2_score);
        leveltwo.setText(scores[8]);

        TextView levelthree = findViewById(R.id.level_3_score);
        levelthree.setText(scores[7]);

        TextView levelfour = findViewById(R.id.level_4_score);
        levelfour.setText(scores[6]);

        TextView levelfive = findViewById(R.id.level_5_score);
        levelfive.setText(scores[6]);

        TextView levelsix = findViewById(R.id.level_6_score);
        levelsix.setText(scores[5]);

        TextView levelSeven = findViewById(R.id.level_7_score);
        levelSeven.setText(scores[4]);

        TextView leveleight = findViewById(R.id.level_8_score);
        leveleight.setText(scores[3]);

        TextView levelnine = findViewById(R.id.level_9_score);
        levelnine.setText(scores[2]);

        TextView levelten = findViewById(R.id.level_10_score);
        levelten.setText(scores[1]);

    }

    //not the best way to do this I don't think so try to find another way
    private void setLevels(String theme) {
        if (theme.equals("Ocean")) {
            TextView levelOne = findViewById(R.id.level_1);
            levelOne.setText(Oceans.LEVEL1.getLevel());

            TextView leveltwo = findViewById(R.id.level_2);
            leveltwo.setText(Oceans.LEVEL2.getLevel());

            TextView levelthree = findViewById(R.id.level_3);
            levelthree.setText(Oceans.LEVEL3.getLevel());

            TextView levelfour = findViewById(R.id.level_4);
            levelfour.setText(Oceans.LEVEL4.getLevel());

            TextView levelfive = findViewById(R.id.level_5);
            levelfive.setText(Oceans.LEVEL5.getLevel());

            TextView levelsix = findViewById(R.id.level_6);
            levelsix.setText(Oceans.LEVEL6.getLevel());

            TextView levelseven = findViewById(R.id.level_7);
            levelseven.setText(Oceans.LEVEL7.getLevel());

            TextView leveleight = findViewById(R.id.level_8);
            leveleight.setText(Oceans.LEVEL8.getLevel());

            TextView levelnine = findViewById(R.id.level_9);
            levelnine.setText(Oceans.LEVEL9.getLevel());

            TextView levelten = findViewById(R.id.level_10);
            levelten.setText(Oceans.LEVEL10.getLevel());
        } else if(theme.equals("Land")) {
            TextView levelOne = findViewById(R.id.level_1);
            levelOne.setText(Land.LEVEL1.getLevel());

            TextView leveltwo = findViewById(R.id.level_2);
            leveltwo.setText(Land.LEVEL2.getLevel());

            TextView levelthree = findViewById(R.id.level_3);
            levelthree.setText(Land.LEVEL3.getLevel());

            TextView levelfour = findViewById(R.id.level_4);
            levelfour.setText(Land.LEVEL4.getLevel());

            TextView levelfive = findViewById(R.id.level_5);
            levelfive.setText(Land.LEVEL5.getLevel());

            TextView levelsix = findViewById(R.id.level_6);
            levelsix.setText(Land.LEVEL6.getLevel());

            TextView levelseven = findViewById(R.id.level_7);
            levelseven.setText(Land.LEVEL7.getLevel());

            TextView leveleight = findViewById(R.id.level_8);
            leveleight.setText(Land.LEVEL8.getLevel());

            TextView levelnine = findViewById(R.id.level_9);
            levelnine.setText(Land.LEVEL9.getLevel());

            TextView levelten = findViewById(R.id.level_10);
            levelten.setText(Land.LEVEL10.getLevel());

        } else {
            TextView levelOne = findViewById(R.id.level_1);
            levelOne.setText(Sky.LEVEL1.getLevel());

            TextView leveltwo = findViewById(R.id.level_2);
            leveltwo.setText(Sky.LEVEL2.getLevel());

            TextView levelthree = findViewById(R.id.level_3);
            levelthree.setText(Sky.LEVEL3.getLevel());

            TextView levelfour = findViewById(R.id.level_4);
            levelfour.setText(Sky.LEVEL4.getLevel());

            TextView levelfive = findViewById(R.id.level_5);
            levelfive.setText(Sky.LEVEL5.getLevel());

            TextView levelsix = findViewById(R.id.level_6);
            levelsix.setText(Sky.LEVEL6.getLevel());

            TextView levelseven = findViewById(R.id.level_7);
            levelseven.setText(Sky.LEVEL7.getLevel());

            TextView leveleight = findViewById(R.id.level_8);
            leveleight.setText(Sky.LEVEL8.getLevel());

            TextView levelnine = findViewById(R.id.level_9);
            levelnine.setText(Sky.LEVEL9.getLevel());

            TextView levelten = findViewById(R.id.level_10);
            levelten.setText(Sky.LEVEL10.getLevel());
        }
    }
}
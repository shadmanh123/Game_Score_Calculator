package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Options Activity: This activity allows users
 * to chose the difficulty and theme for that play
 * of the game
 */
public class OptionsActivity extends AppCompatActivity {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();

        jsonWriter = new JsonWriter(getApplicationContext());

        createDifficultyMenu();
        createThemeMenu();

        String savedDiff = getDifficultySelected(this);
        String savedTheme = getThemeSelected(this);
        Toast.makeText(OptionsActivity.this, savedDiff + " + " + savedTheme, Toast.LENGTH_SHORT).show();

        FloatingActionButton back = findViewById(R.id.floatingBackButton4);
        back.setOnClickListener(v -> onBackClick());
    }

    private void createDifficultyMenu() {
        RadioGroup diffMenu = (RadioGroup) findViewById(R.id.difficultyOptions);

        String[] difficulty = getResources().getStringArray(R.array.difficulty);
        //creating the buttons
        for (int i = 0; i < difficulty.length; i++) {
            final String level = difficulty[i];

            RadioButton levelbutton = new RadioButton(this);
            levelbutton.setText(level);

            //setting onclick callbacks
            levelbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo: save the data
                    saveDifficultySelected(level);
                    jsonWriter.writeToJson(gameManager);
                }
            });

            //adding to radio group
            diffMenu.addView(levelbutton);

            //setting the default
            if (level.equals(getDifficultySelected(this))) {
                levelbutton.setChecked(true);
            }
        }
    }

    private void createThemeMenu() {
        RadioGroup themeMenu = (RadioGroup) findViewById(R.id.themeOptions);

        String[] themes = getResources().getStringArray(R.array.themes);
        //creating the buttons
        for (int i = 0; i < themes.length; i++){
            final String theme = themes[i];

            RadioButton themeButton = new RadioButton(this);
            themeButton.setText(theme);

            //setting onclick callbacks
            themeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo: save the data
                    saveThemeSelected(theme);
                    jsonWriter.writeToJson(gameManager);
                }
            });

            //adding to radio group
            themeMenu.addView(themeButton);

            //setting the default
            if (theme.equals(getThemeSelected(this))) {
                themeButton.setChecked(true);
            }
        }
    }

    private void saveDifficultySelected(String diff) {
        SharedPreferences prefs = this.getSharedPreferences("difficulty", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("difficulty", diff);
        editor.apply();
    }

    public static String getDifficultySelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("difficulty", MODE_PRIVATE);
        String defaultTheme = "Normal";
        return prefs.getString("difficulty", defaultTheme);
    }

    private void saveThemeSelected(String theme) {
        SharedPreferences prefs = this.getSharedPreferences("theme", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("theme", theme);
        editor.apply();
    }

    public static String getThemeSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("theme", MODE_PRIVATE);
        String defaultTheme = "Ocean";
        return prefs.getString("theme", defaultTheme);
    }

    private void onBackClick() {
        finish();
    }

    public static Intent optionsIntent(Context context) {
        Intent intent = new Intent(context, OptionsActivity.class);
        return intent;
    }
}
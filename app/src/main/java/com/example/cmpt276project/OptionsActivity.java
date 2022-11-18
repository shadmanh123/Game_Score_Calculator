package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        initializeJson();

        createDifficultyMenu();
        createThemeMenu();

        FloatingActionButton back = findViewById(R.id.floatingBackButton4);
        back.setOnClickListener(v -> onBackClick());
    }

    private void createDifficultyMenu() {
        RadioGroup diffMenu = (RadioGroup) findViewById(R.id.difficultyOptions);

        String[] difficulty = getResources().getStringArray(R.array.difficulty);
        //creating the buttons
        for (int i = 0; i < difficulty.length; i++){
            final String level = difficulty[i];

            RadioButton button = new RadioButton(this);
            button.setText(level);

            //setting onclick callbacks
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo: save the data
                    jsonWriter.writeToJson(gameManager);
                }
            });

            //adding to radio group
            diffMenu.addView(button);
        }
    }

    private void createThemeMenu() {
        RadioGroup themeMenu = (RadioGroup) findViewById(R.id.themeOptions);

        String[] themes = getResources().getStringArray(R.array.themes);
        //creating the buttons
        for (int i = 0; i < themes.length; i++){
            final String theme = themes[i];

            RadioButton button = new RadioButton(this);
            button.setText(theme);

            //setting onclick callbacks
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo: save the data
                    jsonWriter.writeToJson(gameManager);

                }
            });

            //adding to radio group
            themeMenu.addView(button);
        }
    }

    private void onBackClick() {
        finish();
    }

    //todo: set up Json reader in here to read out past options for that play

    private void initializeJson() {
        jsonReader = new JsonReader(getApplicationContext(), gameManager);
        gameManager = jsonReader.readFromJson();
        jsonWriter = new JsonWriter(getApplicationContext());
    }

    public static Intent optionsIntent(Context context) {
        Intent intent = new Intent(context, OptionsActivity.class);
        return intent;
    }
}
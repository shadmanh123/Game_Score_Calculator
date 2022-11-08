package com.example.cmpt276project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class GameHistoryActivity extends AppCompatActivity {

    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private static final String JSON_STORE = "m.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;
    private GameManager gameManager;
    private final int COLUMN_SIZE = 4;
    Dialog dialog;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
        int index = getIntent().getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        readFromJson();

        Game game = gameManager.getGame(index);
        checkEmptyState(game);
        populateButtons(game);
        Button newGame = findViewById(R.id.btnNewGame);
        FloatingActionButton back = findViewById(R.id.floatingBackButton2);

        back.setOnClickListener(v -> {
            Intent i = GameCategoriesActivity.makeIntent(this);
            startActivity(i);
            finish();
        });

        newGame.setOnClickListener(v -> {
            Intent intent = AddEditGameHistoryActivity.makeIntent(GameHistoryActivity.this, index);
            startActivity(intent);
        });
    }

    private void readFromJson() {
        try {
            gameManager = jsonReader.read(getApplicationContext());
            System.out.println("Loaded" + " from " + JSON_STORE);
        } catch (JSONException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Had to make a new one");
        } catch (IOException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Couldn't read file");
        }
    }

    private void writeToJson() {
        try {
            jsonWriter.open(getApplicationContext());
            jsonWriter.write(gameManager);
            jsonWriter.close();
            System.out.println("Saved" + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("JSON Problem: " + JSON_STORE);
        }
    }


    private void checkEmptyState(Game game) {
        if(game.playSize() == 0){
            dialog = new Dialog(GameHistoryActivity.this);
            dialog.setContentView(R.layout.adding_game_tutorial);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setWindowAnimations(R.style.AnimationsForDialog);
            dialog.show();
        }
    }

    private void populateButtons(Game game) {
        TableLayout table = findViewById(R.id.tableForHistory);

        for (int row = 0; row < game.playSize(); row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for(int col  = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tableRow.addView(tv);
                String tvText = game.displayPlayInfo(row, col);
                tv.setText(tvText);
                tv.setBackgroundColor(Color.parseColor("#CBF4F1"));
                tv.setGravity(Gravity.CENTER);
            }
        }
    }

    public static Intent makeIntent(Context context, int gameIndex){
        Intent intent = new Intent(context, GameHistoryActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }

}
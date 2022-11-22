package com.example.cmpt276project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Displays all the game plays for a particular game category
 */
public class PlayActivity extends AppCompatActivity {

    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private GameManager gameManager;
    private JsonReader jsonReader;
    private final int COLUMN_SIZE = 5;
    Dialog dialog;
    private int GameIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        GameIndex = getIntent().getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        setUpGame(GameIndex);
        setUpOnClickListeners(GameIndex);
    }

    private void setUpGame(int index) {
        Game game = gameManager.getGame(index);
        checkEmptyState(game);
        populateButtons(game);
    }

    private void setUpOnClickListeners(int index) {
        Button newGame = findViewById(R.id.btnNewGame);
        FloatingActionButton back = findViewById(R.id.floatingBackButton2);
        back.setOnClickListener(v -> {
            Intent i = GameCategoriesActivity.makeIntent(this);
            startActivity(i);
            finish();
        });

        newGame.setOnClickListener(v -> {
            Intent intent = AddEditPlayActivity.makeIntent(PlayActivity.this, index, false, -1);
            startActivity(intent);
        });
    }

    private void checkEmptyState(Game game) {
        if (game.playSize() == 0) {
            dialog = new Dialog(PlayActivity.this);
            dialog.setContentView(R.layout.adding_game_tutorial);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setWindowAnimations(R.style.AnimationsForDialog);
            dialog.show();
        }
    }

    private void populateButtons(Game game) {
        TableLayout table = findViewById(R.id.tableForHistory);
        int pos = 0;
        for (int row = 0; row < game.playSize(); row++) {
            pos = row;
            TableRow tableRow = new TableRow(this);
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int rowIndex = table.indexOfChild(view) - 1;
                    Intent intent = AddEditPlayActivity.makeIntent(PlayActivity.this, GameIndex, true, rowIndex);
                    startActivity(intent);
                }
            });
            table.addView(tableRow);

            for (int col = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tv.setTextSize(12);
                tableRow.addView(tv);
                String tvText = game.displayPlayInfo(row, col);
                tv.setText(tvText);
                tv.setBackgroundColor(Color.parseColor("#CBF4F1"));
                tv.setGravity(Gravity.CENTER);
            }

        }
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }

}
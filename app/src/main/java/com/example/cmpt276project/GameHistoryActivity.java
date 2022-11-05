package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;

import java.util.ArrayList;

public class GameHistoryActivity extends AppCompatActivity {

    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
        populateButtons();
    }

    final int COLUMN_SIZE = 4;
    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForHistory);
        int index = getIntent().getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        Game game = gameManager.getInstance().getGame(index);


        for (int row = 0; row < game.playSize(); row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);

            for(int col  = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tableRow.addView(tv);
                String tvText = game.displayPlayInfo(row, col);
                tv.setText(tvText);
            }
        }
    }

    public static Intent makeIntent(Context context, int gameIndex){
        Intent intent = new Intent(context, GameHistoryActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }


}
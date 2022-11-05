package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Play;

import java.util.ArrayList;

public class GameHistoryActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
        populateButtons();
    }
    public static Intent makeIntent(Context context){
        return new Intent(context, GameHistoryActivity.class);
    }

    // TODO: Change variable to something else
    final int gameManagerSize = 10;
    final int COLUMN_SIZE = 4;
    private void populateButtons() {
        TableLayout table = findViewById(R.id.tableForHistory);
        for (int row = 1; row < gameManagerSize; row++) {
            TableRow tableRow = new TableRow(this);
            table.addView(tableRow);
            for(int col  = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tableRow.addView(tv);


            }
        }
    }


}
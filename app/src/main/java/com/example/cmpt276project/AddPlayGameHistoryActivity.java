package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddPlayGameHistoryActivity extends AppCompatActivity {
    EditText etTotalPlayers = findViewById(R.id.etTotalPlayers);
    EditText etTotalScore = findViewById(R.id.etTotalScore);
    int totalPlayers;
    int totalScore;

    public static Intent makeIntent(Context context){
        return new Intent(context, AddPlayGameHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_game_history);
        getNumberOfPlayers();
        getTotalScore();
    }


    private void getNumberOfPlayers() {
        String players = etTotalPlayers.getText().toString();
        totalPlayers = Integer.parseInt(players);
        validateNumberOfPlayers();
    }

    private void validateNumberOfPlayers() {
        if(totalPlayers <= 0){
            Toast.makeText(this, "Total Players must be greater than 0",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void getTotalScore() {
        String score = etTotalScore.getText().toString();
        totalScore = Integer.parseInt(score);
    }

}
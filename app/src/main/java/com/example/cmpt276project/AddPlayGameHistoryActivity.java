package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.Play;

public class AddPlayGameHistoryActivity extends AppCompatActivity {
    Button enter = findViewById(R.id.btnEnter);
    public static Intent makeIntent(Context context){
        return new Intent(context, AddPlayGameHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_game_history);
        getNumberOfPlayers();
        getTotalScore();
        //enter.setOnClickListener(v -> createPlay(getNumberOfPlayers(),getTotalScore()));
    }

    private int getNumberOfPlayers() {
        EditText etTotalPlayers = findViewById(R.id.etTotalPlayers);
        String players = etTotalPlayers.getText().toString();
        int totalPlayers = Integer.parseInt(players);
        while(!validateNumberOfPlayers(totalPlayers)){
            players = etTotalPlayers.getText().toString();
            totalPlayers = Integer.parseInt(players);
        }
        return totalPlayers;
    }

    private boolean validateNumberOfPlayers(int numberOfPlayers) {
        if(numberOfPlayers <= 0){
            Toast.makeText(this, "Total Number of Players must be greater than 0",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    private int getTotalScore() {
        EditText etTotalScore = findViewById(R.id.etTotalScore);
        String score = etTotalScore.getText().toString();
        int totalScore = Integer.parseInt(score);
        while(!validateTotalScore(totalScore)){
            score = etTotalScore.getText().toString();
            totalScore = Integer.parseInt(score);
        }
        return totalScore;
    }

    private boolean validateTotalScore(int totalScore) {
        if(totalScore < 0){
            Toast.makeText(this, "Combined Score must be at least 0",
                    Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void createPlay(int numberOfPlayers, int totalScore) {
        Play game;

    }

}
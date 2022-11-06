package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;

public class AddEditGameHistoryActivity extends AppCompatActivity {
//    int index;
    public static Intent makeIntent(Context context, int gameIndex){
        Intent intent = new Intent(context, AddEditGameHistoryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_game_history);
//        Intent intent = getIntent();
//        String strIndex = intent.getStringExtra("index");
//        int index = Integer.parseInt(strIndex);
        findViewById(R.id.btnEnter).setOnClickListener(v -> onRegisterClick());
    }

    private void onRegisterClick() {
        EditText etTotalPlayers = findViewById(R.id.etTotalPlayers);
        String players = etTotalPlayers.getText().toString();
        int totalPlayers = Integer.parseInt(players);
        if(totalPlayers == 0){
            Toast.makeText(this, "Total Number of Players must be greater than 0",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        EditText etTotalScore = findViewById(R.id.etTotalScore);
        String score = etTotalScore.getText().toString();
        int totalScore = Integer.parseInt(score);
//        Play play = new Play(GameManager.getInstance().getGame(index), totalPlayers,totalScore);
        finish();
    }

//    private int getNumberOfPlayers() {
//        EditText etTotalPlayers = findViewById(R.id.etTotalPlayers);
//        String players = etTotalPlayers.getText().toString();
//        int totalPlayers = Integer.parseInt(players);
//        if(totalPlayers == 0){
//            Toast.makeText(this, "Total Number of Players must be greater than 0",
//                    Toast.LENGTH_SHORT).show();
//        }
//        return totalPlayers;
//    }
//
//    private boolean validateNumberOfPlayers(int numberOfPlayers) {
//        if(numberOfPlayers <= 0){
//            Toast.makeText(this, "Total Number of Players must be greater than 0",
//                    Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        else{
//            return true;
//        }
//    }
//
//    private int getTotalScore() {
//        EditText etTotalScore = findViewById(R.id.etTotalScore);
//        String score = etTotalScore.getText().toString();
//        int totalScore = Integer.parseInt(score);
//        while(!validateTotalScore(totalScore)){
//            score = etTotalScore.getText().toString();
//            totalScore = Integer.parseInt(score);
//        }
//        return totalScore;
//    }
//
//    private boolean validateTotalScore(int totalScore) {
//        if(totalScore < 0){
//            Toast.makeText(this, "Combined Score must be at least 0",
//                    Toast.LENGTH_LONG).show();
//            return false;
//        }
//        else{
//            return true;
//        }
//    }

//    private void createPlay(int numberOfPlayers, int totalScore) {
//        Play game;
//
//    }

}
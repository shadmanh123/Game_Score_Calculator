package com.example.cmpt276project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditGameHistoryActivity extends AppCompatActivity {

    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_game_history);
        Intent intent = getIntent();
        int index = intent.getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        FloatingActionButton back = findViewById(R.id.floatingBackButton3);
        back.setOnClickListener(v -> onBackClick());
        findViewById(R.id.btnEnter).setOnClickListener(v -> onRegisterClick(index));

    }

    private void onRegisterClick(int index) {
        EditText etTotalPlayers = findViewById(R.id.etTotalPlayers);
        String players = etTotalPlayers.getText().toString();
        int totalPlayers = Integer.parseInt(players);
        if (totalPlayers == 0) {
            Toast.makeText(this, "Total Number of Players must be greater than 0",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        EditText etTotalScore = findViewById(R.id.etTotalScore);
        String score = etTotalScore.getText().toString();
        int totalScore = Integer.parseInt(score);
        Game game = GameManager.getInstance().getGame(index);
        Play play = new Play(game, totalPlayers, totalScore);
        game.addPlay(play);
        Intent intent = GameHistoryActivity.makeIntent(this, index);
        startActivity(intent);
    }

    private void onBackClick() {
        FragmentManager manager = getSupportFragmentManager();
        BackPopUpGCFragment dialog = new BackPopUpGCFragment();
        dialog.show(manager, "message");
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, AddEditGameHistoryActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }
}
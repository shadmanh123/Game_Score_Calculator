package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * AddEditGameHistory: Class that allows game plays to be added
 * of a particular game category
 */

public class AddEditPlayActivity extends AppCompatActivity {

    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;
    private Button enter;
    private EditText etTotalPlayers;
    private EditText etTotalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_play);
        jsonInitialize();
        initialization();
    }

    private void jsonInitialize() {
        jsonReader = new JsonReader(getApplicationContext(), gameManager);
        jsonWriter = new JsonWriter(getApplicationContext());
        gameManager = jsonReader.readFromJson();
    }

    private void initialization() {
        Intent intent = getIntent();
        int index = intent.getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        FloatingActionButton back = findViewById(R.id.floatingBackButton3);
        back.setOnClickListener(v -> onBackClick());
        enter = findViewById(R.id.btnEnter);
        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        enter.setOnClickListener(v -> onRegisterClick(index));
        etTotalScore = findViewById(R.id.etTotalScore);
        etTotalPlayers.addTextChangedListener(inputTextWatcher);
        etTotalScore.addTextChangedListener(inputTextWatcher);
    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String players = etTotalPlayers.getText().toString().trim();
            String score = etTotalScore.getText().toString().trim();
            enter.setEnabled(!players.isEmpty() && !score.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void onRegisterClick(int index) {
        String players = etTotalPlayers.getText().toString();
        int totalPlayers = Integer.parseInt(players);
        if (totalPlayers == 0) {
            Toast.makeText(this, "Total Number of Players must be greater than 0",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        /*
        String score = etTotalScore.getText().toString();
        int totalScore = Integer.parseInt(score);
        Game game = gameManager.getGame(index);
        /*
        Play play = new Play(game, totalPlayers, totalScore);
        game.addPlay(play);


        jsonWriter.writeToJson(gameManager);
        */

        Intent i = AddScoresActivity.makeIntent(this);
        Intent intent = PlayActivity.makeIntent(this, index);
        startActivity(intent);
        finish();
    }

    private void onBackClick() {
        FragmentManager manager = getSupportFragmentManager();
        BackPopUpGCFragment dialog = new BackPopUpGCFragment();
        dialog.show(manager, "message");
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, AddEditPlayActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }
}
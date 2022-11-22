package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Options;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.tiers.Tier;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * AddEditGameHistory: Class that allows game plays to be added
 * of a particular game category
 */

public class AddEditPlayActivity extends AppCompatActivity {
    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    public static final String BOOL_IS_EDIT = "Index of Selected Game";
    public static final String INT_PLAY_POSITION = "Index of Selected play";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;
    private Button enter;
    private EditText etTotalPlayers;

    private int index;
    private boolean isEdit;
    private int playPosition;
    private int numOfPlayers;

    private ArrayAdapter<Double> adapter;
    private List<Double> tempMyPlayScores;
    private List<Double> myScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_play);

        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();

        String name = gameManager.getGame(index).getName();
        jsonWriter = new JsonWriter(getApplicationContext());
        initialization();

        tempMyPlayScores = new ArrayList<>();
        myScores = new ArrayList<>();
        editPlay(name);

        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        etTotalPlayers.setText("" + tempMyPlayScores.size());

        populateList();
        populateListView();
    }

    private void editPlay(String name) {
        if(isEdit) {

            Play play = gameManager.getGame(index).getPlay(playPosition);
            Toast.makeText(AddEditPlayActivity.this, ""+ name, Toast.LENGTH_SHORT).show();
            for(int i = 0; i < play.getScoreSize(); i++) {
                tempMyPlayScores.add(play.getScore(i));
            }
        }
    }

    private void populateListView() {
        adapter = new MyListAdapter();
        ListView list = findViewById(R.id.playerList);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<Double>{
        public MyListAdapter(){
            super(AddEditPlayActivity.this, R.layout.playitemlayout, myScores);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.playitemlayout, parent, false);
            }

            TextView player = itemView.findViewById(R.id.txtPlayer);
            player.setText("player " + (position + 1));

            EditText score = itemView.findViewById(R.id.txtScore);
            score.setText("" + myScores.get(position));
            View saveBtn = itemView.findViewById(R.id.btnSave);
            saveBtn.setOnClickListener(view -> {
                Toast.makeText(AddEditPlayActivity.this, score.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                double numScore;
                try {
                    numScore = Double.parseDouble(score.getText().toString().trim());
                } catch (NumberFormatException e) {
                    return;

                }

                tempMyPlayScores.set(position, numScore);
                populateList();
                adapter.notifyDataSetChanged();
            });

            score.addTextChangedListener(new inputScoreWatcher(position, saveBtn));

            return itemView;
        }
    }

    private void populateList() {
        myScores.clear();
        for(int i = 0; i < tempMyPlayScores.size(); i++){
            myScores.add(tempMyPlayScores.get(i));
        }
    }

    private void initialization() {
        Intent intent = getIntent();
        index = intent.getIntExtra(INDEX_OF_SELECTED_GAME, 0);

        isEdit = intent.getBooleanExtra(BOOL_IS_EDIT, false);
        playPosition = intent.getIntExtra(INT_PLAY_POSITION, 0);

        FloatingActionButton back = findViewById(R.id.floatingBackButton3);
        back.setOnClickListener(v -> onBackClick());

        enter = findViewById(R.id.btnEnter);
        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        enter.setOnClickListener(v -> onRegisterClick());
        etTotalPlayers.addTextChangedListener(inputTextWatcher);

        Button options = findViewById(R.id.optionsButton);
        options.setOnClickListener(v -> onOptionsClick());
        getOptions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getOptions();
    }
    private void onOptionsClick() {
        Intent i = OptionsActivity.optionsIntentPlay(AddEditPlayActivity.this);
        startActivity(i);
        onStart();
    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String players = etTotalPlayers.getText().toString().trim();
            enter.setEnabled(!players.isEmpty() || players.equals("0"));

            try {
                numOfPlayers = Integer.parseInt(players);
            } catch (NumberFormatException e) {
                return;
            }

            updateTempPlayer();
            onStart();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void updateTempPlayer() {
        if(numOfPlayers > tempMyPlayScores.size()){
            for(int i = tempMyPlayScores.size(); i < numOfPlayers; i++){
                tempMyPlayScores.add(0.0);
            }
        } else {
            int i = tempMyPlayScores.size();
            int j = numOfPlayers;
            while(i != j) {
                tempMyPlayScores.remove(tempMyPlayScores.size()-1);
                i--;
            }
        }
    }

    public class inputScoreWatcher implements TextWatcher {
        private int position;
        private View saveBtn;

        public inputScoreWatcher(int position, View saveBtn){
            super();
            this.position = position;
            this.saveBtn = saveBtn;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                Integer.parseInt(s.toString().trim());
            } catch (NumberFormatException e) {
                return;

            }

            saveBtn.setEnabled(true);
        }
        @Override
        public void afterTextChanged(Editable s) {}
    };
    private void onRegisterClick() {
        Game game = gameManager.getGame(index);
        String players = etTotalPlayers.getText().toString();
        int totalPlayers = Integer.parseInt(players);
        if (totalPlayers == 0) {
            Toast.makeText(this, "Total Number of Players must be greater than 0",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        validateScore();
        List<Double> scores = new ArrayList<>();
        for(int i = 0; i < tempMyPlayScores.size(); i++){
            double convert = tempMyPlayScores.get(i);
            scores.add(convert);
        }

        Options option = getOptions();
        Play play;
        if(isEdit) {
            play = gameManager.getGame(index).getPlay(playPosition);
            play.setOptions(option);
            play.setNumPlayers(totalPlayers);
            play.setScores(scores);
        } else {
            play = new Play(game, totalPlayers, scores, option);
            game.addPlay(play);
        }

        jsonWriter.writeToJson(gameManager);

        Intent intent = PlayActivity.makeIntent(AddEditPlayActivity.this, index);
        startActivity(intent);

        Intent animationIntent = AchievementAnimationActivity.makeIntent(AddEditPlayActivity.this, play.getAchievementScore(), option.getDifficulty());
        startActivity(animationIntent);
        finish();

    }

    private void validateScore() {
        int sum = 0;
        for(int i = 0 ; i < numOfPlayers; i++) {
            sum += myScores.get(i);
        }

        Toast.makeText(AddEditPlayActivity.this,"" + sum, Toast.LENGTH_SHORT).show();
    }
    @NonNull
    private Options getOptions() {
        String difficulty = OptionsActivity.getDifficultySelected(this);
        String tierString = OptionsActivity.getThemeSelected(this);
        Tier tier = Play.getTier(tierString);
        Options option = new Options(difficulty, tier);
        return option;
    }

    private void onBackClick() {
        FragmentManager manager = getSupportFragmentManager();
        BackPopUpGCFragment dialog = new BackPopUpGCFragment();
        dialog.show(manager, "message");
    }

    public static Intent makeIntent(Context context, int gameIndex, boolean isEdit, int playPosition) {
        Intent intent = new Intent(context, AddEditPlayActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        intent.putExtra(BOOL_IS_EDIT, isEdit);
        intent.putExtra(INT_PLAY_POSITION, playPosition);
        return intent;
    }

    @Override
    protected void onStart() {
        super.onStart();
        myScores.clear();
        populateList();
        populateListView();
        adapter.notifyDataSetChanged();
    }
}
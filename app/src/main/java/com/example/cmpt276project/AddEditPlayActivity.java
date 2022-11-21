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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
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

    private static final String JSON_STORE = "gameManager.json";
    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;
    private Button enter;
    private EditText etTotalPlayers;
    private EditText etTotalScore;
    private String difficulty;
    private String tierString;
    private int index;


    private int numOfPlayers;

    private ArrayAdapter<Integer> adapter;

    private List<Integer> tempMyPlayScores;
    private List<Integer> myScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_play);
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        jsonWriter = new JsonWriter(getApplicationContext());
        initialization();

        tempMyPlayScores = new ArrayList<Integer>();

        myScores = new ArrayList<Integer>();
        tempMyPlayScores.add(1);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(4);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(9);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(9);
        tempMyPlayScores.add(1);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(4);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(9);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(3);
        tempMyPlayScores.add(9);

        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        etTotalPlayers.setText(""+ tempMyPlayScores.size());


        populateList();
        populateListView();

//        tempMyPlayScores.add(6);


    }
    private void populateListView() {
        adapter = new MyListAdapter();
        ListView list = findViewById(R.id.playerList);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<Integer>{
        public MyListAdapter(){
            super(AddEditPlayActivity.this, R.layout.playitemlayout, myScores);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.playitemlayout, parent, false);
            }
//            itemView.setId(position);

            TextView player = itemView.findViewById(R.id.txtPlayer);
            player.setText("player " + (position+1));

            EditText score = itemView.findViewById(R.id.txtScore);
            score.setText(""+myScores.get(position));
//            score.setTag(position);
//            score.setId(position);
//            EditText editText = itemView.findViewById(position);


//            int id = View.generateViewId();
//            score.setId(position);
//            idList.add(id);
//            score.setId(position);
            View saveBtn = itemView.findViewById(R.id.btnSave);
            saveBtn.setOnClickListener(view -> {
                Toast.makeText(AddEditPlayActivity.this, score.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                int numScore;
                try {
                    numScore = Integer.parseInt(score.getText().toString().trim());
                } catch (NumberFormatException e) {
//                Toast.makeText(AddEditPlayActivity.this, "this works", Toast.LENGTH_SHORT).show();
                    return;

                }

                tempMyPlayScores.set(position, numScore);
//                onStart();
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

        FloatingActionButton back = findViewById(R.id.floatingBackButton3);
        back.setOnClickListener(v -> onBackClick());

        enter = findViewById(R.id.btnEnter);
        etTotalPlayers = findViewById(R.id.etTotalPlayers);
        enter.setOnClickListener(v -> onRegisterClick());
//        etTotalScore = findViewById(R.id.etTotalScore);
        etTotalPlayers.addTextChangedListener(inputTextWatcher);
//        etTotalScore.addTextChangedListener(inputTextWatcher);

        Button options = findViewById(R.id.optionsButton);
        options.setOnClickListener(v -> onOptionsClick());
        difficulty = "easy";
        tierString = "Sky";
        getOptions();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getOptions();
    }
    private void onOptionsClick() {
        Intent i = OptionsActivity.optionsIntent(AddEditPlayActivity.this);
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
//            String score = etTotalScore.getText().toString().trim();
            enter.setEnabled(!players.isEmpty() || players == "0"/*&& !score.isEmpty()*/);

            try {
                numOfPlayers = Integer.parseInt(players);
            } catch (NumberFormatException e) {
//                Toast.makeText(AddEditPlayActivity.this, "this works", Toast.LENGTH_SHORT).show();
                return;

            }

            updateTempPlayer();
            onStart();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void saveScoreToTemp() {
        tempMyPlayScores.clear();
        for(int i = 0; i < myScores.size(); i++){
            tempMyPlayScores.add(myScores.get(i));
        }
    }

    private void updateTempPlayer() {


        if(numOfPlayers > tempMyPlayScores.size()){
            for(int i = tempMyPlayScores.size(); i < numOfPlayers; i++){
                tempMyPlayScores.add(0);
            }
        }else{
            int i = tempMyPlayScores.size();
            int j = numOfPlayers;
            while(i != j){
                tempMyPlayScores.remove(tempMyPlayScores.size()-1);
                i--;
            }
        }
    }

    public class inputScoreWatcher implements TextWatcher{
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
            int score;
            try {
                score = Integer.parseInt(s.toString().trim());
            } catch (NumberFormatException e) {
//                Toast.makeText(AddEditPlayActivity.this, "this works", Toast.LENGTH_SHORT).show();
                saveBtn.setEnabled(false);
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
//        String score = etTotalScore.getText().toString();
//        int totalScore = Integer.parseInt(score);
        for(int i = 0; i < tempMyPlayScores.size(); i++){
            double convert = tempMyPlayScores.get(i);
            scores.add(convert);
        }

        //why
        scores.add(10.0);
        scores.add(25.0);

        Options option = getOptions();

        Play play = new Play(game, totalPlayers, scores, option);
        game.addPlay(play);

        jsonWriter.writeToJson(gameManager);

        Intent intent = PlayActivity.makeIntent(AddEditPlayActivity.this, index);
        startActivity(intent);
        finish();

    }
    private void validateScore() {
        int sum = 0;
        for(int i = 0 ; i < numOfPlayers; i++){
            sum += myScores.get(i);
        }

        Toast.makeText(AddEditPlayActivity.this,""+sum, Toast.LENGTH_SHORT).show();
    }
    @NonNull
    private Options getOptions() {
        difficulty = OptionsActivity.getDifficultySelected(this);
        tierString = OptionsActivity.getThemeSelected(this);
        Tier tier = Play.getTier(tierString);
        Options option = new Options(difficulty, tier);
        return option;
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
    @Override
    protected void onStart() {
        super.onStart();
        myScores.clear();
        populateList();

//        getState();
        populateListView();
        adapter.notifyDataSetChanged();
    }
}
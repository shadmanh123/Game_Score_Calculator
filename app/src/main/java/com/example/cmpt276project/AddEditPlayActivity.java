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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.FragmentManager;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
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
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;
    private Button enter;
    private EditText etTotalPlayers;
    private EditText etTotalScore;


    private int numOfPlayers;

    private ArrayAdapter<Integer> adapter;

    private List<Integer> myPlayers = new ArrayList<Integer>();
    private List<Integer> idList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_play);
        jsonInitialize();
        initialization();
        
        populateList();
        populateListView();


    }

    private void populateListView() {
        adapter = new MyListAdapter();
        ListView list = findViewById(R.id.playerList);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<Integer>{
        public MyListAdapter(){
            super(AddEditPlayActivity.this, R.layout.playitemlayout, myPlayers);
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
            score.setText(""+myPlayers.get(position));
//            score.setTag(position);
//            score.setId(position);
//            EditText editText = itemView.findViewById(position);
            score.addTextChangedListener(new inputScoreWatcher(position));

//            int id = View.generateViewId();
//            score.setId(position);
//            idList.add(id);
//            score.setId(position);

            return itemView;
        }
    }

    private void populateList() {
        for(int i = 0; i < numOfPlayers; i++){
            myPlayers.add(0);
        }
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
//        etTotalScore = findViewById(R.id.etTotalScore);
        etTotalPlayers.addTextChangedListener(inputTextWatcher);
//        etTotalScore.addTextChangedListener(inputTextWatcher);


        Button options = findViewById(R.id.optionsButton);
        options.setOnClickListener(v -> onOptionsClick());
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
            enter.setEnabled(!players.isEmpty() /*&& !score.isEmpty()*/);

            try {
                numOfPlayers = Integer.parseInt(players);
            } catch (NumberFormatException e) {
//                Toast.makeText(AddEditPlayActivity.this, "this works", Toast.LENGTH_SHORT).show();
//                return;
                numOfPlayers = 0;
            }
            myPlayers.clear();
            populateList();
            populateListView();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public class inputScoreWatcher implements TextWatcher{
        private int position;

        public inputScoreWatcher(int position){
            super();
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int score = 0;
            Toast.makeText(AddEditPlayActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            try {
                score = Integer.parseInt(s.toString().trim());

            } catch (NumberFormatException e) {
//                Toast.makeText(AddEditPlayActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                return;
            }
            myPlayers.set(position, score);

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
        validateScore();
        /*
        String score = etTotalScore.getText().toString();
        int totalScore = Integer.parseInt(score);
        Game game = gameManager.getGame(index);
        /*
        Play play = new Play(game, totalPlayers, totalScore);
        game.addPlay(play);


        jsonWriter.writeToJson(gameManager);
        */

//        Intent i = AddScoresActivity.makeIntent(this);
//        Intent intent = PlayActivity.makeIntent(this, index);
//        startActivity(intent);
//        startActivity(new Intent(AddEditPlayActivity.this, AchievementAnimationActivity.class));
//
//        finish();
    }

    private void validateScore() {
        int sum = 0;
        for(int i = 0 ; i < numOfPlayers; i++){
            sum += myPlayers.get(i);
        }

        Toast.makeText(AddEditPlayActivity.this,""+sum, Toast.LENGTH_SHORT).show();
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
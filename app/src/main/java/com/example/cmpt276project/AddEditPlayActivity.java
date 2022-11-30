package com.example.cmpt276project;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
    public static final String INDEX_OF_SELECTED_GAME = "Index";

    public static final String BOOL_IS_EDIT = "Index of Selected Game";
    public static final String INT_PLAY_POSITION = "Index of Selected play";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;
    private Button enter;

    private int index;
    private boolean isEdit;
    private int playPosition;


    private List<Double> tempMyPlayScores;

    Button add;
    AlertDialog dialog;
    LinearLayout layout;
    ArrayList<Double> scores;
    boolean editing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_play);

        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        initialization();
        String name = gameManager.getGame(index).getName();

        jsonWriter = new JsonWriter(getApplicationContext());
        tempMyPlayScores = new ArrayList<>();
        scores = new ArrayList<>();
        add = findViewById(R.id.add);
        layout = findViewById(R.id.container);

        buildDialog();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
        editGame();






    }

    private void editGame() {
        if(isEdit) {
            Play play = gameManager.getGame(index).getPlay(playPosition);
            for(int i = 0; i < play.getScoreSize(); i++) {
                tempMyPlayScores.add(play.getScore(i));
                scores.add(tempMyPlayScores.get(i));
            }

            editing = true;
            for(int i = 0; i < scores.size(); i++){
                addCard(""+scores.get(i));
            }
            editing = false;

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

        enter.setOnClickListener(v -> onRegisterClick());


        Button options = findViewById(R.id.optionsButton);
        options.setOnClickListener(v -> onOptionsClick());
    }

    private void onOptionsClick() {
        Intent i = OptionsActivity.optionsIntentPlay(AddEditPlayActivity.this);
        startActivity(i);
        onStart();
    }

    private void onRegisterClick() {
        Game game = gameManager.getGame(index);
        int totalPlayers = scores.size();
        if(totalPlayers == 0) {
            Toast.makeText(this, "Total Number of Players must be greater than 0",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        List<Double> scoresSubmit = new ArrayList<>();
        for(int i = 0; i < scores.size(); i++) {
            double convert = scores.get(i);
            scoresSubmit.add(convert);
        }

        Options option = getOptions();
        Play play;

        if(isEdit) {
            play = gameManager.getGame(index).getPlay(playPosition);
            play.setOptions(option);
            play.setNumPlayers(totalPlayers);
            play.setScores(scoresSubmit);
        } else {
            play = new Play(game, totalPlayers, scoresSubmit, option);
            game.addPlay(play);
        }

        jsonWriter.writeToJson(gameManager);

        Intent intent = PlayActivity.makeIntent(AddEditPlayActivity.this, index);
        startActivity(intent);

        Intent animationIntent = AchievementAnimationActivity.makeIntent(AddEditPlayActivity.this,index, play.getAchievementScore(option.getTheme()), option.getDifficulty());
        startActivity(animationIntent);
        finish();
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
        intent.putExtra("Index", gameIndex);
        intent.putExtra(BOOL_IS_EDIT, isEdit);
        intent.putExtra(INT_PLAY_POSITION, playPosition);
        return intent;
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog, null);

        final EditText name = view.findViewById(R.id.nameEdit);

        builder.setView(view);
        builder.setTitle("Enter name")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCard(name.getText().toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
    }
    private void addCard(String name) {
        final View view = getLayoutInflater().inflate(R.layout.card, null);

        TextView nameView = view.findViewById(R.id.name);
        Button delete = view.findViewById(R.id.delete);
        double numScore;
        try {
            numScore = Double.parseDouble(name);
        } catch (NumberFormatException e) {
            return;
        }
        if(!editing){
            scores.add(numScore);
        }

        nameView.setText(""+numScore);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(view);
                scores.remove(scores.indexOf(numScore));
            }

        });

        layout.addView(view);
        Toast.makeText(AddEditPlayActivity.this, ""+getSum(), Toast.LENGTH_SHORT).show();
    }

    private double getSum(){
        double sum = 0;
        for(int i = 0; i < scores.size(); i++){
            sum+= scores.get(i);
        }
        return sum;
    }

}
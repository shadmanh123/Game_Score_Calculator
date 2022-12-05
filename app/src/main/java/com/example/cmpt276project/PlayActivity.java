package com.example.cmpt276project;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.tiers.Tier;
import com.example.cmpt276project.persistence.JsonReader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays all the game plays for a particular game category
 */
public class PlayActivity extends AppCompatActivity {

    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private GameManager gameManager;
    private JsonReader jsonReader;
    private final int COLUMN_SIZE = 5;
    private Dialog dialog;
    private int gameIndex;
    private List<Play> theList;
    private List<Integer> clickedItems;
    private ArrayAdapter<Play> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play1);
        gameIndex = getIntent().getIntExtra(INDEX_OF_SELECTED_GAME, 0);
        initialize();
        setUpGame(gameIndex);
        setUpOnClickListeners(gameIndex);
    }

    private void initialize() {
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        theList = new ArrayList<>();
        clickedItems = new ArrayList<>();
        adapter = new PlayActivity.MyListAdapter();
    }

    private void setUpGame(int index) {
        Game game = gameManager.getGame(index);
        checkEmptyState(game);
        onStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        theList.clear();
        populateTheList();
        populateListView();
        adapter.notifyDataSetChanged();
    }

    private void populateTheList() {
        Game game = gameManager.getGame(gameIndex);
        for (int i = 0; i < game.playSize(); i++) {
            theList.add(game.getPlay(i));
        }
    }

    private void populateListView() {
        ListView list = findViewById(R.id.playTable);
        list.setAdapter(adapter);
    }

    // TODO: Bug with achievement names
    private class MyListAdapter extends ArrayAdapter<Play> {
        public MyListAdapter() {
            super(PlayActivity.this, R.layout.play_item, theList);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.play_item, parent, false);
            }

            Play play = theList.get(position);

            TextView achievement = itemView.findViewById(R.id.playTier);
            Tier theme = play.getOptions().getTheme();
            Tier tier = play.getAchievementScore(theme);
            achievement.setText("" + tier.getLevel());

            TextView date = itemView.findViewById(R.id.playDate);
            date.setText("" + play.getCreationDate());

            TextView score = itemView.findViewById(R.id.playScore);
            score.setText("Score: " + play.getTotalScore());

            TextView numPlayers = itemView.findViewById(R.id.playerNum);
            numPlayers.setText("Number of Players: " + play.getTotalScore());

            TextView difficulty = itemView.findViewById(R.id.playDifficulty);
            difficulty.setText("" + play.getDifficultyLevel());

            itemView.setOnClickListener(v -> {
                clickedItems.remove(Integer.valueOf(position));
                Intent intent = AddEditPlayActivity.makeIntent(PlayActivity.this, gameIndex, true, position);
                startActivity(intent);
            });

            return itemView;
        }
    }

    private void setUpOnClickListeners(int index) {
        Button newGame = findViewById(R.id.btnNewGame);
        FloatingActionButton back = findViewById(R.id.floatingBackButton2);
        back.setOnClickListener(v -> {
            Intent i = GameCategoriesActivity.makeIntent(this);
            startActivity(i);
            finish();
        });

        newGame.setOnClickListener(v -> {
            Intent intent = AddEditPlayActivity.makeIntent(PlayActivity.this, index, false, -1);
            startActivity(intent);
        });

        Button statistics = findViewById(R.id.btnStatistics);
        statistics.setOnClickListener(v -> {
            Intent sIntent = AchievementStatistics.makeIntent(this, index);
            startActivity(sIntent);
        });
    }

    private void checkEmptyState(Game game) {
        if (game.playSize() == 0) {
            dialog = new Dialog(PlayActivity.this);
            dialog.setContentView(R.layout.adding_game_tutorial);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.getWindow().setWindowAnimations(R.style.AnimationsForDialog);
            dialog.show();
        }
    }

    private void populateButtons(Game game) {
        TableLayout table = findViewById(R.id.tableForHistory);
        for (int row = 0; row < game.playSize(); row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int rowIndex = table.indexOfChild(view) - 1;
                    Intent intent = AddEditPlayActivity.makeIntent(PlayActivity.this, gameIndex, true, rowIndex);
                    startActivity(intent);
                }
            });
            table.addView(tableRow);

            for (int col = 0; col < COLUMN_SIZE; col++) {
                TextView tv = new TextView(this);
                tv.setTextSize(12);
                tableRow.addView(tv);
                String tvText = game.displayPlayInfo(row, col);
                tv.setText(tvText);
                tv.setBackgroundColor(Color.parseColor("#CBF4F1"));
                tv.setGravity(Gravity.CENTER);
            }
        }
    }

    public static Intent makeIntent(Context context, int gameIndex) {
        Intent intent = new Intent(context, PlayActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }

}
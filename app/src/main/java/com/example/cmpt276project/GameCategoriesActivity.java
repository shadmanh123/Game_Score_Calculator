package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;

import java.util.ArrayList;
import java.util.List;

/**
 * GameCategoriesActivity: This activity displays all saved game configs
 * and offers navigation to; add and edit game config, tiers for that
 * config, the configs history and the apps credits
 */
public class GameCategoriesActivity extends AppCompatActivity {

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private List<Game> theList;
    private GameManager gameManager;
    private List<Integer> clickedItems;

    ArrayAdapter<Game> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_categories);

        initialize();

        getState();
        findViewById(R.id.btnAdd).setOnClickListener(v -> onClick());
        findViewById(R.id.btnCredits).setOnClickListener((v -> onCredits()));

        onStart();
        registerClickCallback();
    }

    private void initialize() {
        theList = new ArrayList<>();
        clickedItems = new ArrayList<>();
        jsonReader = new JsonReader(getApplicationContext(), gameManager);
        jsonWriter = new JsonWriter(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        adapter = new MyListAdapter();
    }

    private void onCredits() {
        Intent i = CreditsActivity.makeIntent(GameCategoriesActivity.this);
        startActivity(i);
    }

    private void getState() {
        if (gameManager.getNumberOfGames() == 0) {
            View listBack = findViewById(R.id.listViewMain);
            listBack.setVisibility(View.GONE);

            View list = findViewById(R.id.gameCategoriesList);
            list.setVisibility(View.GONE);

            View text = findViewById(R.id.emptyStateText);
            text.setVisibility(View.VISIBLE);

            View arrow = findViewById(R.id.emptyStateArrow);
            arrow.setVisibility(View.VISIBLE);
        } else {
            View listBack = findViewById(R.id.listViewMain);
            listBack.setVisibility(View.VISIBLE);

            View list = findViewById(R.id.gameCategoriesList);
            list.setVisibility(View.VISIBLE);

            View text = findViewById(R.id.emptyStateText);
            text.setVisibility(View.GONE);

            View arrow = findViewById(R.id.emptyStateArrow);
            arrow.setVisibility(View.GONE);
        }
    }

    private void onClick() {
        Intent i = new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class);
        startActivity(i);
        onStart();
    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.gameCategoriesList);
        list.setOnItemClickListener((adapterView, view, i, l) -> {
            clickedItems.add(i);
            onStart();
        });
    }

    private class MyListAdapter extends ArrayAdapter<Game> {
        public MyListAdapter() {
            super(GameCategoriesActivity.this, R.layout.gamecategoryitem, theList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.gamecategoryitem, parent, false);
            }

            Game currentGame = theList.get(position);
            if (clickedItems.contains(position)) {
                itemView = getLayoutInflater().inflate(R.layout.gamecategoryitem_two, parent, false);
                ImageView imageView = itemView.findViewById(R.id.item_img);
                imageView.setImageResource(getIcon(currentGame.getName()));

                TextView name = itemView.findViewById(R.id.item_name);
                name.setText(currentGame.getName());

                TextView maxScore = itemView.findViewById(R.id.item_max_score);
                maxScore.setText("Max Score: " + currentGame.getMaxScore());

                TextView minScore = itemView.findViewById(R.id.item_min_score);
                minScore.setText("Min Score: " + currentGame.getMinScore());

                View btnHistory = itemView.findViewById(R.id.btnHistory);
                btnHistory.setOnClickListener(v -> onHistory(position));

                View btnEdit = itemView.findViewById(R.id.btnEdit);
                btnEdit.setOnClickListener(v -> onEdit(position));

                View btnTiers = itemView.findViewById(R.id.btnTiers);
                btnTiers.setOnClickListener(v -> onTiers(position));

                itemView.setOnClickListener(v -> {
                    clickedItems.remove(Integer.valueOf(position));
                    onStart();
                });

            } else {
                ImageView imageView = itemView.findViewById(R.id.item_img);
                imageView.setImageResource(getIcon(currentGame.getName()));

                TextView name = itemView.findViewById(R.id.item_name);
                name.setText(currentGame.getName());

                TextView maxScore = itemView.findViewById(R.id.item_max_score);
                maxScore.setText("Max Score: " + currentGame.getMaxScore());

                TextView minScore = itemView.findViewById(R.id.item_min_score);
                minScore.setText("Min Score: " + currentGame.getMinScore());
            }
            return itemView;
        }
    }

    private void onTiers(int pos) {
//        FragmentManager manager = getSupportFragmentManager();
//        TiersFragment dialog = TiersFragment.newInstance(pos);
//        dialog.show(manager, "MessageDialog");
//        Log.i("Tag", "Just showed the dialog");
        Intent i = OptionsActivity.optionsIntent(GameCategoriesActivity.this, pos);
        startActivity(i);
        onStart();
    }

    private void onEdit(int position) {
        Intent i = AddEditGameCategoryActivity.makeIntent(GameCategoriesActivity.this, 100, position);
        startActivity(i);
        onStart();
    }

    private void onHistory(int index) {
        Intent i = PlayActivity.makeIntent(GameCategoriesActivity.this, index);
        startActivity(i);
        onStart();
    }

    private int getIcon(String name) {
        name = name.toLowerCase().trim();
        if (name.equals("chess")) {
            return R.drawable.chess;
        } else if (name.equals("blackjack")) {
            return R.drawable.card;
        } else {
            return R.drawable.gameboy;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        theList.clear();
        populateTheList();
        adapter.notifyDataSetChanged();
        getState();
        populateListView();
    }

    private void populateTheList() {
        for (int i = 0; i < gameManager.getNumberOfGames(); i++) {
            theList.add(gameManager.getGame(i));
        }
    }

    private void populateListView() {
        ListView list = findViewById(R.id.gameCategoriesList);
        list.setAdapter(adapter);
    }

    @Override
    protected void onUserLeaveHint() {
        jsonWriter.writeToJson(gameManager);
        super.onUserLeaveHint();
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, GameCategoriesActivity.class);
        return intent;
    }
}
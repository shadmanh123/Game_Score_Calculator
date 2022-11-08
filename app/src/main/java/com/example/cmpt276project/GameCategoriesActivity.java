package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameCategoriesActivity extends AppCompatActivity {

    private static final String JSON_STORE = "m.json";
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

        theList = new ArrayList<>();
        clickedItems = new ArrayList<>();

        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        gameManager = GameManager.getInstance();

        try {
            gameManager = jsonReader.read(getApplicationContext());
            System.out.println(gameManager.getGame(0).getName());
            System.out.println("Loaded" + " from " + JSON_STORE);
            onStart();
            populateListView();
        } catch (JSONException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Had to make a new one");
        } catch (IOException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Couldn't read file");
        } finally {
            getState();
            findViewById(R.id.btnAdd).setOnClickListener(v -> onClick());
            findViewById(R.id.btnCredits).setOnClickListener((v -> onCredits()));

            populateListView();
            registerClickCallback();
        }

    }

    private void onCredits() {
        startActivity(new Intent(GameCategoriesActivity.this, CreditsActivity.class));
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
        startActivity(new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class));
        onStart();
    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.gameCategoriesList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickedItems.add(i);
                onStart();
                populateListView();
            }
        });
    }

    private void populateListView() {
        adapter = new MyListAdapter();
        ListView list = findViewById(R.id.gameCategoriesList);
        list.setAdapter(adapter);
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

                itemView.setOnClickListener(v -> {
                    clickedItems.remove(Integer.valueOf(position));
                    onStart();
                    populateListView();
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

    private void onEdit(int position) {
        Intent intent = new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class);
        intent.putExtra("edit", 100);
        intent.putExtra("index", position);
        startActivity(intent);
        onStart();
        populateListView();
    }

    private void onHistory(int index) {
        Intent intent = GameHistoryActivity.makeIntent(GameCategoriesActivity.this, index);
        startActivity(intent);
        onStart();
        populateListView();
    }

    private void onAdd(int index) {
        Intent intent = AddEditGameHistoryActivity.makeIntent(GameCategoriesActivity.this, index);
        startActivity(intent);
        onStart();
        getState();
        populateListView();
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

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, GameCategoriesActivity.class);
        return intent;
    }

    private void populateTheList() {
        for (int i = 0; i < gameManager.getNumberOfGames(); i++) {
            theList.add(gameManager.getGame(i));

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        theList.clear();
        for (int i = 0; i < gameManager.getNumberOfGames(); i++) {
            theList.add(gameManager.getGame(i));
        }
        getState();
    }

    @Override
    public void onBackPressed() {
        writeToJson();
        super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint() {
        writeToJson();
        super.onUserLeaveHint();
    }

    private void writeToJson() {
        try {
            jsonWriter.open(getApplicationContext());
            jsonWriter.write(gameManager);
            jsonWriter.close();
            System.out.println("Saved" + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("JSON Problem: " + JSON_STORE);
        }
    }



}
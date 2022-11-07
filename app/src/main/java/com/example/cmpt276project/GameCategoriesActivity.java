package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;

import java.util.ArrayList;
import java.util.List;


public class GameCategoriesActivity extends AppCompatActivity {

    private List<Game> theList;
    private GameManager gameManager;

    private List<Integer> clickedItems;

    ArrayAdapter<Game> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_categories);

        gameManager = GameManager.getInstance();
        theList = new ArrayList<>();

        clickedItems = new ArrayList<>();

        gameManager.addGame(new Game("Chess", 0, 100));
        gameManager.addGame(new Game("BlackJack", 50, 200));
        gameManager.addGame(new Game("Snakes and Ladders", 50, 200));

        getState();
        findViewById(R.id.btnAdd).setOnClickListener(v -> onClick());
        findViewById(R.id.btnCredits).setOnClickListener((v -> onCredits()));

//        populateTheList();
        populateListView();
        registerClickCallback();

    }

    private void onCredits() {
        startActivity(new Intent(GameCategoriesActivity.this, CreditsActivity.class));
    }

    private void getState() {
        if (gameManager.getNumbeOfGames() == 0){
            View listBack = findViewById(R.id.listViewMain);
            listBack.setVisibility(View.GONE);

            View list = findViewById(R.id.gameCategoriesList);
            list.setVisibility(View.GONE);

            View text = findViewById(R.id.emptyStateText);
            text.setVisibility(View.VISIBLE);

            View arrow = findViewById(R.id.emptyStateArrow);
            arrow.setVisibility(View.VISIBLE);
        } else{
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
//        gameManager.addGame(new Game("BlackJack", 0, 100));
        onStart();

        //TODO: add intent


    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.gameCategoriesList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(GameCategoriesActivity.this, "this works", Toast.LENGTH_LONG).show();
//                startActivity(new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class));

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
//            Toast.makeText(GameCategoriesActivity.this, "this", Toast.LENGTH_LONG).show();
            if (clickedItems.contains(position)){

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
                btnHistory.setOnClickListener(v->onHistory(position));

                View btnEdit = itemView.findViewById(R.id.btnEdit);
                btnEdit.setOnClickListener(v->onEdit(position));

                itemView.setOnClickListener(v->{
                    clickedItems.remove(Integer.valueOf(position));
                    onStart();
                    populateListView();
//                    registerClickCallback();
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
        //TODO: add intent
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
        populateListView();
    }

    private int getIcon(String name) {
        name = name.toLowerCase().trim();
//        Toast.makeText(GameCategoriesActivity.this, "-" + name + "-", Toast.LENGTH_LONG).show();
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
        for (int i = 0; i < gameManager.getNumbeOfGames(); i++) {
            theList.add(gameManager.getGame(i));

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        theList.clear();
        for(int i = 0; i < GameManager.getInstance().getNumbeOfGames(); i++){
            theList.add(GameManager.getInstance().getGame(i));
        }
//
//        populateListView();
        adapter.notifyDataSetChanged();
//        determineState();
    }

}
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
import android.widget.Toast;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GameCategoriesActivity extends AppCompatActivity {

    private List<Game> theList;
    private GameManager gameManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_categories);

        gameManager = new GameManager();
        theList = new ArrayList<Game>();

        gameManager.addGame(new Game("Chess", 0, 100));
        gameManager.addGame(new Game("BlackJack", 50, 200));
        gameManager.addGame(new Game("Snakes and Ladders", 50, 200));


        findViewById(R.id.btnAdd).setOnClickListener(v->onClick());


        populateTheList();
        populateListView();
        registerClickCallback();


    }

    private void onClick() {
//        startActivity(new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class));
        gameManager.addGame(new Game("BlackJack", 0, 100));
        theList.clear();
        populateTheList();
        populateListView();

    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.gameCategoriesList);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(GameCategoriesActivity.this,"this works", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class));
            }
        });
    }

    private void populateListView() {
        ArrayAdapter<Game> adapter= new MyListAdapter();
        ListView list = findViewById(R.id.gameCategoriesList);
        list.setAdapter(adapter);
    }
    private class MyListAdapter extends ArrayAdapter<Game>{
        public MyListAdapter(){
            super(GameCategoriesActivity.this, R.layout.gamecategoryitem, theList);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            View itemView = convertView;
            if(itemView == null){
                itemView = getLayoutInflater().inflate(R.layout.gamecategoryitem, parent, false);
            }
            Game currentGame = theList.get(position);

            ImageView imageView = itemView.findViewById(R.id.item_img);
            imageView.setImageResource(getIcon(currentGame.getName()));

            TextView name = itemView.findViewById(R.id.item_name);
            name.setText(currentGame.getName());

            TextView maxScore = itemView.findViewById(R.id.item_max_score);
            maxScore.setText("Max Score: " + currentGame.getMaxScore());

            TextView minScore = itemView.findViewById(R.id.item_min_score);
            minScore.setText("Min Score: " + currentGame.getMinScore());

            return itemView;
        }
    }

    private int getIcon(String name) {
        name = name.toLowerCase().trim();
        Toast.makeText(GameCategoriesActivity.this,"-"+ name +"-", Toast.LENGTH_LONG).show();
        if(name.equals("chess")){
            return R.drawable.chess;
        }else if(name.equals("blackjack")){
            return R.drawable.card;
        }else{
            return R.drawable.gameboy;
        }

    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, GameCategoriesActivity.class);
        return intent;
    }

    private void populateTheList(){
        for(int i = 0; i < gameManager.getNumbeOfGames(); i++){
            theList.add(gameManager.getGame(i));

        }
    }
//    private class MyListAdapter extends ArrayAdapter<String> {
//        public MyListAdapter(){
//            super(GameCategoriesActivity.this, R.layout.gamecategoryitem, myItems);
//        }
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent){
//            View itemView = convertView;
//            if(itemView == null){
//                itemView = getLayoutInflater().inflate(R.layout.gamecategoryitem, parent, false);
//            }
//
//            return itemView;
//        }
//    }
}
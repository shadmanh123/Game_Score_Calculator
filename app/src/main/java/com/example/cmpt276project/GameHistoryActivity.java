package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.Play;

import java.util.ArrayList;

public class GameHistoryActivity extends AppCompatActivity {
    GridView plays = findViewById(R.id.gvGameHistory);
    ArrayList<TextView> display = new ArrayList<>();
    ArrayAdapter<TextView> adapter = new ArrayAdapter<>(this,
            android.R.layout.simple_list_item_1, display);

    public static Intent makeIntent(Context context){
        return new Intent(context, GameHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_history);
//        final String[][] play = Game.displayGames();
//        CustomAdapter customAdapter = new CustomAdapter();
//        plays.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
//                play));
        //Game.displayGames();

    }


}
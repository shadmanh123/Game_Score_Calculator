package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class GameCategoriesActivity extends AppCompatActivity {

    private ArrayList<String> myItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_categories);
        findViewById(R.id.btnAdd).setOnClickListener(v->onClick());

        populateListView();
        registerClickCallback();

//        myItems.add("red");
//        myItems.add("blue");
    }

    private void onClick() {
        startActivity(new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class));
    }

    private void registerClickCallback() {
        ListView list = findViewById(R.id.gameCategories);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(GameCategoriesActivity.this,"this works", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GameCategoriesActivity.this, AddEditGameCategoryActivity.class));
            }
        });
    }

    private void populateListView() {
        String[] myItems = {"blue", "green", "red"};
       ArrayAdapter<String> adapter = new ArrayAdapter<String>(
               this,
               R.layout.gamecategoryitem,
               myItems
       );

        ListView list = findViewById(R.id.gameCategories);

        list.setAdapter(adapter);
    }

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, GameCategoriesActivity.class);
        return intent;
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
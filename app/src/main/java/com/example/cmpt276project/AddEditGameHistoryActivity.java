package com.example.cmpt276project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditGameHistoryActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context){
        return new Intent(context, AddEditGameHistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_game_history);
    }
}
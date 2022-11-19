package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TiersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiers_list);

        FloatingActionButton back = findViewById(R.id.floatingBackButton5);
        back.setOnClickListener(v -> onBackClick());
    }

    private void onBackClick() {finish();}

    public static Intent tiersIntent(Context context) {
        Intent intent = new Intent(context, TiersListActivity.class);
        return intent;
    }
}
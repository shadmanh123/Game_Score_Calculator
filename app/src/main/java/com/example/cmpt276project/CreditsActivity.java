package com.example.cmpt276project;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        FloatingActionButton back = findViewById(R.id.floatingBackButton);
        back.setOnClickListener(v -> finish());
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, CreditsActivity.class);
    }
}
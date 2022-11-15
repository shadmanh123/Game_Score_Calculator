package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class OptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }

    public static Intent optionsIntent(Context context) {
        Intent intent = new Intent(context, OptionsActivity.class);
        return intent;
    }
}
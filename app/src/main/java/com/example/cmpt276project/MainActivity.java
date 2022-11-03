package com.example.cmpt276project;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        setTitle("Debugger");

        new Handler().postDelayed(new Runnable() {
            //this is the coolest thing I ever done
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, GameCategoriesActivity.class));
                finish();
            }
        },4000);
    }
}
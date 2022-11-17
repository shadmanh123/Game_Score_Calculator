package com.example.cmpt276project;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * MainActivity: Opening activity that gives a warm welcome to the user
 */

public class MainActivity extends AppCompatActivity {
    Button btn;
    Animation bubble;
    Bubblechange bubblechange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_start);
        bubble = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        bubblechange = new Bubblechange(0.2, 20);
        bubble.setInterpolator(bubblechange);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.startAnimation(bubble);
                new Handler().postDelayed(new Runnable() {
//            this is the coolest thing I ever done
                    @Override
                    public void run() {
                        Intent i = GameCategoriesActivity.makeIntent(MainActivity.this);
                        startActivity(i);
                        finish();
                    }
                },500);
            }
        });

        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

    }
}
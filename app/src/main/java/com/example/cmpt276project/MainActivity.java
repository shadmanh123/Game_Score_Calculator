package com.example.cmpt276project;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Animation bubble;
    bubbleInterpolation bubbleInterpolation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn_start);
        bubble = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        bubbleInterpolation = new bubbleInterpolation(0.2, 20);
        bubble.setInterpolator(bubbleInterpolation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn.startAnimation(bubble);
            }
        });




        ConstraintLayout constraintLayout = findViewById(R.id.mainLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
//        new Handler().postDelayed(new Runnable() {
//            //this is the coolest thing I ever done
//            @Override
//            public void run() {
//                startActivity(new Intent(MainActivity.this, GameCategoriesActivity.class));
//                finish();
//            }
//        },4000);
    }
}
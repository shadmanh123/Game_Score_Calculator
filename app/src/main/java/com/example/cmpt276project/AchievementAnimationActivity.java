package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class AchievementAnimationActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int sound1;
    public static final String INDEX_OF_SELECTED_GAME = "Index of Selected Game";
    private String achievement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_animation);

        Intent intent = getIntent();
        achievement = intent.getStringExtra(INDEX_OF_SELECTED_GAME);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
        }

        sound1 = soundPool.load(this, R.raw.win, 1);











        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(AchievementAnimationActivity.this, achievement, Toast.LENGTH_SHORT).show();
                soundPool.play(sound1, 1, 1, 0 , 0, 1);
                startActivity(new Intent(AchievementAnimationActivity.this, PlayActivity.class));
                finish();
            }
        },3000);
    }
    public static Intent makeIntent(Context context, String gameIndex) {
        Intent intent = new Intent(context, AchievementAnimationActivity.class);
        intent.putExtra(INDEX_OF_SELECTED_GAME, gameIndex);
        return intent;
    }
}
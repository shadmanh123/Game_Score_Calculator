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
import android.widget.TextView;
import android.widget.Toast;

public class AchievementAnimationActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int sound1;
    public static final String ACHIEVEMENT= "achievement of play";
    public static final String THEME = "theme of play";
    private String theme;
    private String achievement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_animation);

        Intent intent = getIntent();
        achievement = intent.getStringExtra(ACHIEVEMENT);
        theme = intent.getStringExtra(THEME);

        TextView difficulty = findViewById(R.id.txtDificulty);
        difficulty.setText(theme);

        Toast.makeText(this,theme, Toast.LENGTH_SHORT).show();

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

                soundPool.play(sound1, 1, 1, 0 , 0, 1);
                startActivity(new Intent(AchievementAnimationActivity.this, PlayActivity.class));
                finish();
            }
        },3000);
    }
    public static Intent makeIntent(Context context, String achievement, String theme) {
        Intent intent = new Intent(context, AchievementAnimationActivity.class);
        intent.putExtra(ACHIEVEMENT, achievement);
        intent.putExtra(THEME, theme);
        return intent;
    }

    public void setIcon(String achievement){
        switch(achievement) {
            case "Blue Whale":
                // code block
                break;
            case "Great White Shark":
                // code block
                break;
            case "Colossal Squid":
                // code block
                break;
            case "Bottlenose Dolphin":
                // code block
                break;
            case "Lion's Mane Jellyfish":
                // code block
                break;
            case "Giant Pacific Octopus":
                // code block
                break;
            case "Peacock Mantis Shrimp":
                // code block
                break;
            case "Flamingo Tongue Snail":
                // code block
                break;
            case "Frogfish":
                // code block
                break;
            case "Blobfish":
                // code block
                break;
            case "African Bush Elephant":
                // code block
                break;
            case "White Rhinoceros":
                // code block
                break;
            case "Siberian Tiger":
                // code block
                break;
            case "Impala Antelope":
                // code block
                break;
            case "Red Kangaroo":
                // code block
                break;
            case "Chimpanzee":
                // code block
                break;
            case "Blue Peacock":
                // code block
                break;
            case "Queensland Koala":
                // code block
                break;
            case "Northern Flying Squirrel":
                // code block
                break;
            case "Etruscan shrew":
                // code block
                break;
            case "Ruppell’s Griffon Vulture":
                // code block
                break;
            case "Peregrine Falcon":
                // code block
                break;
            case "Black Crowned Crane":
                // code block
                break;
            case "Whooper Swan":
                // code block
                break;
            case "Alpine Chough":
                // code block
                break;
            case "Bald Eagle":
                // code block
                break;
            case "Barn Owl":
                // code block
                break;
            case "Little Brown Bat":
                // code block
                break;
            case "Red Carneau Pigeon":
                // code block
                break;
            case "Dorking Chicken":
                // code block
                break;
            default:
                // code block
        }

    }
}
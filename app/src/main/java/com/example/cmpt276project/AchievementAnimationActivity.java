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
import android.widget.ImageView;
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

        setIcon(achievement);

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
        ImageView img = findViewById(R.id.blueBug);
        img.setImageResource(R.drawable.whale);
        switch(achievement) {
            case "Blue Whale":
                // code block
                img.setImageResource(R.drawable.whale);
                break;
            case "Great White Shark":
                img.setImageResource(R.drawable.shark);
                // code block
                break;
            case "Colossal Squid":
                img.setImageResource(R.drawable.squid);
                // code block
                break;
            case "Bottlenose Dolphin":
                // code block
                img.setImageResource(R.drawable.dolphing);
                break;
            case "Lion's Mane Jellyfish":
                img.setImageResource(R.drawable.jellyfish);
                // code block
                break;
            case "Giant Pacific Octopus":
                img.setImageResource(R.drawable.octopus);
                // code block
                break;
            case "Peacock Mantis Shrimp":
                img.setImageResource(R.drawable.shrimp);
                // code block
                break;
            case "Flamingo Tongue Snail":
                img.setImageResource(R.drawable.snail);
                // code block
                break;
            case "Frogfish":
                img.setImageResource(R.drawable.frogfish);
                // code block
                break;
            case "Blobfish":
                img.setImageResource(R.drawable.blobfish);
                // code block
                break;
            case "African Bush Elephant":
                img.setImageResource(R.drawable.elephant);
                // code block
                break;
            case "White Rhinoceros":
                img.setImageResource(R.drawable.rhino);
                // code block
                break;
            case "Siberian Tiger":
                img.setImageResource(R.drawable.tiger);
                // code block
                break;
            case "Impala Antelope":
                img.setImageResource(R.drawable.antelope);
                // code block
                break;
            case "Red Kangaroo":
                img.setImageResource(R.drawable.kangaroo);
                // code block
                break;
            case "Chimpanzee":
                img.setImageResource(R.drawable.chimp);
                // code block
                break;
            case "Blue Peacock":
                img.setImageResource(R.drawable.peacock);
                // code block
                break;
            case "Queensland Koala":
                img.setImageResource(R.drawable.kowala);
                // code block
                break;
            case "Northern Flying Squirrel":
                img.setImageResource(R.drawable.flyingsquirrel);
                // code block
                break;
            case "Etruscan shrew":
                img.setImageResource(R.drawable.shrew);
                // code block
                break;
            case "Ruppell’s Griffon Vulture":
                img.setImageResource(R.drawable.vulture);
                // code block
                break;
            case "Peregrine Falcon":
                img.setImageResource(R.drawable.falcon);
                // code block
                break;
            case "Black Crowned Crane":
                img.setImageResource(R.drawable.crane);
                // code block
                break;
            case "Whooper Swan":
                img.setImageResource(R.drawable.swan);
                // code block
                break;
            case "Alpine Chough":
                img.setImageResource(R.drawable.crow);
                // code block
                break;
            case "Bald Eagle":
                img.setImageResource(R.drawable.eagle);
                // code block
                break;
            case "Barn Owl":
                img.setImageResource(R.drawable.owl);
                // code block
                break;
            case "Little Brown Bat":
                img.setImageResource(R.drawable.bat);
                // code block
                break;
            case "Red Carneau Pigeon":
                img.setImageResource(R.drawable.pigeon);
                // code block
                break;
            case "Dorking Chicken":
                img.setImageResource(R.drawable.chicken);
                // code block
                break;
            default:
                // code block
        }

    }
}
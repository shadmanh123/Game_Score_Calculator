package com.example.cmpt276project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Options;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.tiers.Tier;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;

/*
animation class for display achievement lever and difficulty
 */

public class AchievementAnimationActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int sound1;
    public static final String ACHIEVEMENT= "achievement of play";
    public static final String DIFFICULTY = "difficulty of play";
    public static final String GAME_INDEX = "game index";
    public static final String PLAY_POSITION = "play index";

    private String difficulty;
    public static final String NEXT_ACHIEVEMENT_LEVEL = "next achievement level";
    public static final String POINTS_AWAY = "points away from next achievement level";
    private String theme;
    private String achievement;
    private int gameIndex;
    private int playPosition;
    private GameManager gameManager;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String nextAchievementLevel;
    private String pointsAway;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_animation);

        jsonReader = new JsonReader(getApplicationContext());
        gameManager = jsonReader.readFromJson();
        jsonWriter = new JsonWriter(getApplicationContext());

        Intent intent = getIntent();
        achievement = intent.getStringExtra(ACHIEVEMENT);
        difficulty = intent.getStringExtra(DIFFICULTY);
        gameIndex = intent.getIntExtra(GAME_INDEX, 0);
        playPosition = intent.getIntExtra(PLAY_POSITION, 0);


        TextView txtDifficulty = findViewById(R.id.txtDificulty);
        txtDifficulty.setText(difficulty);
        nextAchievementLevel = intent.getStringExtra(NEXT_ACHIEVEMENT_LEVEL);
        pointsAway = intent.getStringExtra(POINTS_AWAY);

        TextView txtAchievement = findViewById(R.id.txtAchievement);
        txtAchievement.setText(achievement);

        setIcon(achievement);

        View replayBtn = findViewById(R.id.btnReplay);
        replayBtn.setOnClickListener(v->onReplay());

        View continueBtn = findViewById(R.id.btnContinue);
        continueBtn.setOnClickListener(v -> onContinue());

        View optionsBtn = findViewById(R.id.btnOptions);
        optionsBtn.setOnClickListener(v -> onOptions());

        TextView nextAchievement = findViewById(R.id.txtNextAchievement);
        displayNextAchievementLevel(nextAchievement);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(6)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(6, AudioManager.STREAM_MUSIC,0);
        }

        sound1 = soundPool.load(this, R.raw.win, 1);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                soundPool.play(sound1, 1, 1, 0 , 0, 1);
                Intent intent = PlayActivity.makeIntent(AchievementAnimationActivity.this, gameIndex);
                startActivity(intent);
                finish();
                replayBtn.setVisibility(View.VISIBLE);

            }
        },5000);
    }

    private void displayNextAchievementLevel(TextView nextAchievement) {
        if(nextAchievementLevel == null){
            nextAchievement.setText(R.string.highest_level_achieved_text);
        }
        else {
            nextAchievement.setText("The total score was " + pointsAway + " points away from the next achievement level " +
                    nextAchievementLevel + ".");
        }
    }

    private void onOptions() {
        Intent i = OptionsActivity.optionsIntentPlay(AchievementAnimationActivity.this);
        startActivity(i);
        onStart();
    }

    private void onContinue() {

        finish();

    }

    private void onReplay() {
        recreate();
    }

    public static Intent makeIntent(Context context,int gameIndex, String achievement, String difficulty,
                                    String nextAchievementLevel, String pointsAway int playPosition) {
        Intent intent = new Intent(context, AchievementAnimationActivity.class);
        intent.putExtra(ACHIEVEMENT, achievement);
        intent.putExtra("difficulty of play", difficulty);
        intent.putExtra(GAME_INDEX, gameIndex);
        intent.putExtra(NEXT_ACHIEVEMENT_LEVEL, nextAchievementLevel);
        intent.putExtra(POINTS_AWAY, pointsAway);
        intent.putExtra(PLAY_POSITION, playPosition);
        return intent;
    }
    @Override
    public void recreate(){
        if (android.os.Build.VERSION.SDK_INT >=  11){
            super.recreate();
        }else{
            startActivity(getIntent());
            finish();
        }
    }
    @Override
    public void onResume(){
        super.onResume();

        Options option = getOptions();
        Play play;
        play = gameManager.getGame(gameIndex).getPlay(playPosition);
        play.setOptions(option);
        jsonWriter.writeToJson(gameManager);

        difficulty = OptionsActivity.getDifficultySelected(this);
        achievement = play.getAchievementScore(option.getTheme());

//        Toast.makeText(AchievementAnimationActivity.this,OptionsActivity.getDifficultySelected(this), Toast.LENGTH_SHORT).show();

        TextView txtDifficulty = findViewById(R.id.txtDificulty);
        txtDifficulty.setText(difficulty);

        TextView txtAchievement = findViewById(R.id.txtAchievement);
        txtAchievement.setText(achievement);

        setIcon(achievement);
    }

    @NonNull
    private Options getOptions() {
        String difficulty = OptionsActivity.getDifficultySelected(this);
        String tierString = OptionsActivity.getThemeSelected(this);
        Tier tier = Play.getTier(tierString);
        Options option = new Options(difficulty, tier);
        return option;
    }

    private void saveThemeSelected(String theme) {
        SharedPreferences prefs = this.getSharedPreferences("theme", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("theme", theme);
        editor.apply();
    }

    public static String getThemeSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("theme", MODE_PRIVATE);
        String defaultTheme = "Ocean";
        return prefs.getString("theme", defaultTheme);
    }

    public void setIcon(String achievement) {
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
                break;
                // code block
        }

    }


}
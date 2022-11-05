package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;

public class AddEditGameCategoryActivity extends AppCompatActivity {

    private GameManager gameManager;

    public static Intent makeIntent(Context context) {
        Intent intent = new Intent(context, AddEditGameCategoryActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_game);

        //have a way to get to prev display if needed

        Button back = findViewById(R.id.BackButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick();
            }
        });


        Button save = findViewById(R.id.SaveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveClick();
            }
        });
    }

    private void prevDisplay(){
        //has slots filled in already because we are editing that game
    }

    private void onBackClick() {
        //has a pop up saying that things are not saved
        finish();
    }


    private void onSaveClick() {
        EditText n = findViewById(R.id.GameName);
        String name = n.getText().toString();
        //checking if the name is empty
        if ("".equals(name)){
            Toast.makeText(this,"Every slot must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText min = findViewById(R.id.MinScore);
        String minimum = min.getText().toString();
        int minScore;
        try{
            minScore = Integer.parseInt(minimum);
        }catch(NumberFormatException e){
            Toast.makeText(this,"Every slot must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText max = findViewById(R.id.MaxScore);
        String maximum = max.getText().toString();
        int maxScore;
        try{
            maxScore = Integer.parseInt(maximum);
        }catch(NumberFormatException e){
            Toast.makeText(this, "Every slot must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        //needs to make it so that saves the specific game

        //save into game manager - new game
        //this will be in an else
        Game game = new Game(name, minScore, maxScore);
        gameManager.getInstance().addGame(game);

        //go back to main page - I think it is Game Category activity
        finish();

    }
}
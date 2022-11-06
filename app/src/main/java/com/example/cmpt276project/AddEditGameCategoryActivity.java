package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

        //have a way to get to prev display
        Button delete = findViewById(R.id.deleteButton);
        Intent intent = getIntent();
        int edit = intent.getIntExtra("edit", 0);
        if (edit == 100){
            prevDisplay();
            delete.setVisibility(View.VISIBLE);
        }
//        delete.setVisibility(View.VISIBLE);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });

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

    private void onDelete() {
        FragmentManager manager = getSupportFragmentManager();
        DeleteGCFragment dialog = new DeleteGCFragment();
        dialog.show(manager, "message");
    }

    private void prevDisplay(){
        //has slots filled in already because we are editing that game
        Intent intent = getIntent();
        int index = intent.getIntExtra("index", 0);

        EditText name = (EditText)findViewById(R.id.GameName);
        name.setText(""+ gameManager.getInstance().getGame(index).getName(), TextView.BufferType.EDITABLE);

        EditText min = (EditText)findViewById(R.id.MinScore);
        min.setText(""+gameManager.getInstance().getGame(index).getMinScore(), TextView.BufferType.EDITABLE);

        EditText max = (EditText)findViewById(R.id.MaxScore);
        max.setText(""+gameManager.getInstance().getGame(index).getMaxScore(), TextView.BufferType.EDITABLE);
    }

    private void onBackClick() {
        //has a pop up saying that things are not saved
        FragmentManager manager = getSupportFragmentManager();
        BackPopUpGCFragment dialog = new BackPopUpGCFragment();
        dialog.show(manager, "message");

//        Log.i("TAG", "just show");
//        finish();
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
        Intent intent = getIntent();
        int edit = intent.getIntExtra("edit", 0);
        if (edit == 100){
            int index = intent.getIntExtra("index", 0);
            gameManager.getInstance().getGame(index).setName(name);
            gameManager.getInstance().getGame(index).setMinScore(minScore);
            gameManager.getInstance().getGame(index).setMaxScore(maxScore);
        }
        else{
            Game game = new Game(name, minScore, maxScore);
            gameManager.getInstance().addGame(game);
        }

        //go back to main page - I think it is Game Category activity
        finish();
    }
}
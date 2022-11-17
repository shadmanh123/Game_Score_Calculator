package com.example.cmpt276project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;

/**
 * AddEditGameCategory: This activity allows users to add edit and
 * delete game configs the game manage, with a custom game name,
 * and min and max score
 */

public class AddEditGameCategoryActivity extends AppCompatActivity {

    private GameManager gameManager;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_game);
        initialize();
        Button delete = prevDisplaySetUp();
        setUpOnClickListeners(delete);
    }

    // have a way to get to prev display
    private Button prevDisplaySetUp() {
        Button delete = findViewById(R.id.deleteButton);
        Intent intent = getIntent();
        int edit = intent.getIntExtra("edit", 0);
        if (edit == 100) {
            prevDisplay();
            delete.setVisibility(View.VISIBLE);
        }
        return delete;
    }

    private void initialize() {
        jsonReader = new JsonReader(getApplicationContext(), gameManager);
        jsonWriter = new JsonWriter(getApplicationContext());
        gameManager = jsonReader.readFromJson();
    }

    private void setUpOnClickListeners(Button delete) {
        delete.setOnClickListener(v -> onDelete());

        FloatingActionButton back = findViewById(R.id.floatingBackButton4);
        back.setOnClickListener(v -> onBackClick());

        Button save = findViewById(R.id.SaveButton);
        save.setOnClickListener(v -> onSaveClick());
    }

    private void onDelete() {
        Intent intent = getIntent();
        int index = intent.getIntExtra("index", 0);
        FragmentManager manager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        DeleteGCFragment dialog = new DeleteGCFragment();
        dialog.setArguments(bundle);
        dialog.show(manager, "message");
    }

    private void prevDisplay(){
        //has slots filled in already because we are editing that game
        Intent intent = getIntent();
        int index = intent.getIntExtra("index", 0);
        Game game = gameManager.getGame(index);

        EditText name = findViewById(R.id.GameName);
        name.setText(game.getName(), TextView.BufferType.EDITABLE);

        EditText min = findViewById(R.id.MinScore);
        min.setText("" + game.getMinScore(), TextView.BufferType.EDITABLE);

        EditText max = findViewById(R.id.MaxScore);
        max.setText("" + game.getMaxScore(), TextView.BufferType.EDITABLE);
    }

    private void onBackClick() {
        FragmentManager manager = getSupportFragmentManager();
        BackPopUpGCFragment dialog = new BackPopUpGCFragment();
        dialog.show(manager, "message");
    }

    private void onSaveClick() {
        EditText n = findViewById(R.id.GameName);
        String name = n.getText().toString();
        //checking if the name is empty
        if (name.equals("")) {
            Toast.makeText(this,"Every slot must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText min = findViewById(R.id.MinScore);
        String minimum = min.getText().toString();
        int minScore;
        try {
            minScore = Integer.parseInt(minimum);
        } catch(NumberFormatException e) {
            Toast.makeText(this,"Every slot must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText max = findViewById(R.id.MaxScore);
        String maximum = max.getText().toString();
        int maxScore;
        try {
            maxScore = Integer.parseInt(maximum);
        } catch(NumberFormatException e) {
            Toast.makeText(this, "Every slot must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = getIntent();
        int edit = intent.getIntExtra("edit", 0);
        if (edit == 100){
            int index = intent.getIntExtra("index", 0);
            gameManager.getGame(index).setName(name);
            gameManager.getGame(index).setMinScore(minScore);
            gameManager.getGame(index).setMaxScore(maxScore);
        } else {
            Game game = new Game(name, minScore, maxScore);
            gameManager.addGame(game);
        }
        jsonWriter.writeToJson(gameManager);
        Intent i = GameCategoriesActivity.makeIntent(AddEditGameCategoryActivity.this);
        startActivity(i);
        finish();
    }

    public static Intent makeIntent(Context context, int edit, int position) {
        Intent intent = new Intent(context, AddEditGameCategoryActivity.class);
        intent.putExtra("edit", edit);
        intent.putExtra("index", position);
        return intent;
    }

}
package com.example.cmpt276project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;

import org.json.JSONException;

import java.io.IOException;

/**
 * DeleteGCFragment: Displays a dialog when deleting a game config to the user
 * asking if they are sure they want to delete
 */
public class DeleteGCFragment extends AppCompatDialogFragment {
    private static final String JSON_STORE = "gameManager.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the view to show - load the message
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.delete_gc_fragement, null);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        //create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int index = getArguments().getInt("index");
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        readFromJson();
                        Game game = gameManager.getGame(index);
                        gameManager.removeGame(game);
                        writeToJson();
                        Intent i = GameCategoriesActivity.makeIntent(getActivity());
                        startActivity(i);
                        getActivity().finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        getActivity().finish();
                        break;
                }
            }
        };
        //build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Delete")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }

    private void readFromJson() {
        try {
            gameManager = jsonReader.read(getActivity());
            System.out.println("Loaded" + " from " + JSON_STORE);
        } catch (JSONException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Had to make a new one");
        } catch (IOException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Couldn't read file");
        }
    }

    private void writeToJson() {
        try {
            jsonWriter.open(getActivity());
            if(gameManager == null) {
                return;
            } else {
                jsonWriter.write(gameManager);
            }
            jsonWriter.close();
            System.out.println("Saved" + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("JSON Problem: " + JSON_STORE);
        }
    }
}

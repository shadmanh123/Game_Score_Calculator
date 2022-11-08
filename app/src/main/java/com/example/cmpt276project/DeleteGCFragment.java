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
import com.example.cmpt276project.persistence.JsonWriter;

import org.json.JSONException;

import java.io.IOException;

public class DeleteGCFragment extends AppCompatDialogFragment {
    private static final String JSON_STORE = "m.json";
    private JsonWriter jsonWriter;
    private GameManager gameManager;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the view to show - load the message
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.delete_gc_fragement, null);
        jsonWriter = new JsonWriter(JSON_STORE);

        //create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Log.i("Tag", "you clicked the dialog button");
                int index =  getArguments().getInt("index");

//                int index = intent.getIntExtra("index", 0);
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Game game = gameManager.getInstance().getGame(index);
                        gameManager.getInstance().removeGame(game);
                        writeToJson();
                        getActivity().finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
//                getActivity().finish();
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

    private void writeToJson() {
        try {
            jsonWriter.open(getActivity());
            jsonWriter.write(gameManager.getInstance());
            jsonWriter.close();
            System.out.println("Saved" + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("JSON Problem: " + JSON_STORE);
        }
    }
}

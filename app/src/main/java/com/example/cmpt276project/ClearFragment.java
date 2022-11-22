package com.example.cmpt276project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;

/**
 * Clear Fragment: used to make sure that user wanted to delete all games
 */
public class ClearFragment extends AppCompatDialogFragment {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;

    @SuppressLint("SetTextI18n")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the view to show - load the message
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.fragment_clear, null);
        //create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        jsonWriter = new JsonWriter(getActivity());
                        jsonReader = new JsonReader(getActivity());
                        gameManager = jsonReader.readFromJson();
                        gameManager.clearGames();
                        jsonWriter.writeToJson(gameManager);
                        getActivity().finish();
                        break;


                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        //build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Clear")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }
}
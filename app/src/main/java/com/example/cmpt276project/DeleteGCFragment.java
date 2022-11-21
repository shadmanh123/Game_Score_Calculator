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

/**
 * DeleteGCFragment: Displays a dialog when deleting a game config to the user
 * asking if they are sure they want to delete
 */
public class DeleteGCFragment extends AppCompatDialogFragment {
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GameManager gameManager;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the view to show - load the message
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.delete_gc_fragement, null);

        //create a button listener
        DialogInterface.OnClickListener listener = (dialog, which) -> {
            int index = getArguments().getInt("index");
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    jsonWriter = new JsonWriter(getActivity());
                    jsonReader = new JsonReader(getActivity());
                    gameManager = jsonReader.readFromJson();
                    Game game = gameManager.getGame(index);
                    gameManager.removeGame(game);
                    jsonWriter.writeToJson(gameManager);

                    Intent i = GameCategoriesActivity.makeIntent(getActivity());
                    startActivity(i);
                    getActivity().finish();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    getActivity().finish();
                    break;
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

}

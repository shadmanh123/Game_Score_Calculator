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

public class DeleteGCFragment extends AppCompatDialogFragment {
    private GameManager gameManager;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the view to show - load the message
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.delete_gc_fragement, null);

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
}

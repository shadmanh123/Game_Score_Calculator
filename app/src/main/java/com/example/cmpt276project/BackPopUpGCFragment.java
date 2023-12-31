package com.example.cmpt276project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.BreakIterator;
/**
 * BackPopUpGCFragment: Creates a dialog when a user tries to go back
 * without saving changes they made to a game config
 */

/**
 * Class provides warning about unsaved changes
 */
public class BackPopUpGCFragment extends AppCompatDialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //create the view to show - load the message
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.backbutton_pop_up_gc, null);

        //create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        getActivity().finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        //build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Back")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }
}

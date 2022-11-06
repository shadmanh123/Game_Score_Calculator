package com.example.cmpt276project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DeleteGCFragment extends AppCompatDialogFragment {
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
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
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

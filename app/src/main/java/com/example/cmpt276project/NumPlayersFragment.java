package com.example.cmpt276project;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

/**
 * numPlayers Fragment: This fragments asks users the number of players
 * so that it gives an accurate tier example for what they want to see
 */
public class NumPlayersFragment extends AppCompatDialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //create the view
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.num_players_fragment, null);

        //create a button listener
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        EditText num = v.findViewById(R.id.editTextNumber);
                        String numPlayers = num.getText().toString();
                        try {
                            int Players = Integer.parseInt(numPlayers);

                            //shared preferences to hold players
                            SharedPreferences prefs = getActivity().getSharedPreferences("numPlayers", MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt("numPlayers", Players);
                            editor.apply();

                            System.out.println("num of players " + Players);
                            int position = getArguments().getInt("position");
                            Intent i = TiersListActivity.tiersIntent(getActivity(), position);
                            startActivity(i);
                            getActivity().finish();
                            break;
                        } catch(NumberFormatException e) {
                            break;
                        }
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        //build the alert dialog
        return new AlertDialog.Builder(getActivity())
                .setTitle("Number of Players")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .setNegativeButton(android.R.string.cancel, listener)
                .create();
    }

    public static int getNumPlayersSelected(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("numPlayers", MODE_PRIVATE);
        return prefs.getInt("numPlayers", 0);
    }
}

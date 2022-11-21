package com.example.cmpt276project;

import static android.view.View.inflate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.DataInput;

public class numPlayersFragment extends AppCompatDialogFragment{

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
                        EditText num = findViewById(R.id.editTextNumber);
                        String numPlayers = num.getText().toString();

                        int Players;
                        try {
                            Players = Integer.parseInt(numPlayers);
                            int position = getArguments().getInt("position");

                            Intent i = TiersListActivity.tiersIntent(getActivity(), position, Players);
                            startActivity(i);
                            getActivity().finish();
                            break;
                        } catch(NumberFormatException e) {
//                            Toast.makeText(OptionsActivity.class,"Every slot must be filled", Toast.LENGTH_SHORT).show();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.num_players_fragment, container, false);
        View num = (EditText) view.findViewById(R.id.editTextNumber);
//      EditText num = findViewById(R.id.editTextNumber);
//        String numPlayers = num.getText().toString();
        return num;
    }
}

package com.example.cmpt276project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.cmpt276project.R;
import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;

import java.util.HashMap;

public class TiersFragment extends AppCompatDialogFragment {
    private HashMap<Integer, String> achievements;
    private static int pos;
    public static TiersFragment newInstance (int position){
        TiersFragment tiersFragment = new TiersFragment();
        pos = position;
        return tiersFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.tiers_layout, null);

        setIntervals(v);




        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){

            }
        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Tiers")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }

    public void setIntervals (View v){
        Game game = GameManager.getInstance().getGame(pos);
        int max = game.getMaxScore();
        int min = game.getMinScore();
        //check
        int scoreInterval = (int) Math.floor((double) (max - min) / 10);
//        int score = max - scoreInterval;

        int score = min;
        TextView levelOne = v.findViewById(R.id.level1);
        levelOne.setText(""+min +" - " + ""+(min+scoreInterval));

        score = score+scoreInterval;
        TextView levelTwo = v.findViewById(R.id.level2);
        levelTwo.setText(""+(score)+" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelThree = v.findViewById(R.id.level3);
        levelThree.setText(""+(score) +" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelFour = v.findViewById(R.id.level4);
        levelFour.setText(""+(score)+" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelFive = v.findViewById(R.id.level5);
        levelFive.setText(""+(score) +" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelSix = v.findViewById(R.id.level6);
        levelSix.setText(""+(score) +" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelSeven = v.findViewById(R.id.level7);
        levelSeven.setText(""+(score) +" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelEight = v.findViewById(R.id.level8);
        levelEight.setText(""+(score) +" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelNine = v.findViewById(R.id.level9);
        levelNine.setText(""+(score) +" - " + ""+(score+scoreInterval));

        score += scoreInterval;
        TextView levelTen = v.findViewById(R.id.level10);
        levelTen.setText(""+(score) +" - " + ""+(max));
    }
}

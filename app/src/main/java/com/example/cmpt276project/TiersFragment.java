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
import com.example.cmpt276project.model.Tiers;
import com.example.cmpt276project.persistence.JsonReader;
import com.example.cmpt276project.persistence.JsonWriter;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * TiersFragment: Creates a dialog to view tiers and their corresponding
 * scores
 */
public class TiersFragment extends AppCompatDialogFragment {
    private JsonReader jsonReader;
    private static int pos;
    GameManager gameManager;

    public static TiersFragment newInstance(int position) {
        TiersFragment tiersFragment = new TiersFragment();
        pos = position;
        return tiersFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.tiers_layout, null);

        jsonReader = new JsonReader(getActivity(), gameManager);
        gameManager = jsonReader.readFromJson();
        setIntervals(v);

        DialogInterface.OnClickListener listener = (dialog, which) -> {

        };

        return new AlertDialog.Builder(getActivity())
                .setTitle("Tiers")
                .setView(v)
                .setPositiveButton(android.R.string.ok, listener)
                .create();
    }

    public void setIntervals(View v) {
        Game game = gameManager.getGame(pos);

        TextView levelOne = v.findViewById(R.id.level1);
        TextView levelTwo = v.findViewById(R.id.level2);
        TextView levelThree = v.findViewById(R.id.level3);
        TextView levelFour = v.findViewById(R.id.level4);
        TextView levelFive = v.findViewById(R.id.level5);
        TextView levelSix = v.findViewById(R.id.level6);
        TextView levelSeven = v.findViewById(R.id.level7);
        TextView levelEight = v.findViewById(R.id.level8);
        TextView levelNine = v.findViewById(R.id.level9);
        TextView levelTen = v.findViewById(R.id.level10);

        Tiers[] tiers = Tiers.values();
        int max = game.getMaxScore();
        int min = game.getMinScore();
        int scoreInterval = (int) Math.floor((double) (max - min) / 8);
        int minScore = max;

        ArrayList<Integer> ranges = new ArrayList<>();

        for (Tiers tier : tiers) {
            if (tier == Tiers.LEVEL1) {
                minScore = 0;

            } else if (minScore - scoreInterval <= min) {
                if (minScore >= 0) {
                    minScore /= 2;
                } else {
                    minScore = 0;
                }
            } else {
                minScore -= scoreInterval;
            }
            ranges.add(minScore);
        }

        levelTen.setText(ranges.get(0) + "+");
        levelNine.setText(ranges.get(1) + " - " + (ranges.get(0) - 1));
        levelEight.setText(ranges.get(2) + " - " + (ranges.get(1) - 1));
        levelSeven.setText(ranges.get(3) + " - " + (ranges.get(2) - 1));
        levelSix.setText(ranges.get(4) + " - " + (ranges.get(3) - 1));
        levelFive.setText(ranges.get(5) + " - " + (ranges.get(4) - 1));
        levelFour.setText(ranges.get(6) + " - " + (ranges.get(5) - 1));
        levelThree.setText(ranges.get(7) + " - " + (ranges.get(6) - 1));
        levelTwo.setText(ranges.get(8) + " - " + (ranges.get(7) - 1));
        levelOne.setText(ranges.get(9) + " - " + (ranges.get(8) - 1));
    }
}

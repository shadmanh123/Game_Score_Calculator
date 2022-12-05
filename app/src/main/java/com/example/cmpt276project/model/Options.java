package com.example.cmpt276project.model;

import com.example.cmpt276project.model.tiers.Tier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Options: allows for choices of difficulty and theme for a particular play
 */
public class Options {
    // choosing the play difficulty level and the play theme
    private String difficulty;
    private Tier theme;

    // going to have default has normal difficulty and ocean theme - just since that was our original
    public Options(String difficulty, Tier theme) {
        this.difficulty = difficulty;
        this.theme = theme;
    }

    public String getDifficulty() {
        return difficulty;
    }
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    public Tier getTheme() {
        return theme;
    }
    public String getThemeName() {
        return theme.getClassName();
    }
    public void setTheme(Tier theme) {
        this.theme = theme;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Difficulty", difficulty);
        json.put("Theme", getThemeName());
        return json;
    }
}

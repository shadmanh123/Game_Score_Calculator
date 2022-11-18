package com.example.cmpt276project.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Options: allows for choices of difficulty and theme for a particular play
 */
public class Options {
    //choosing the play difficulty level and the play theme
    private String difficulty;
    private String theme;

    //going to have default has normal difficulty and ocean theme - just since that was our original
    public Options(String difficulty, String theme) {
        this.difficulty = difficulty;
        this.theme = theme;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Difficulty", difficulty);
        json.put("Theme", theme);

        return json;
    }
}

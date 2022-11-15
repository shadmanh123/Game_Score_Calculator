package com.example.cmpt276project.model;

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
}

package com.example.cmpt276project.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.cmpt276project.model.tiers.Land;
import com.example.cmpt276project.model.tiers.Ocean;
import com.example.cmpt276project.model.tiers.Sky;
import com.example.cmpt276project.model.tiers.Tier;
import com.example.cmpt276project.persistence.Writable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Play: Class for a single play through that takes in the number of Players and Total Score achieved
 */
public class Play implements Writable {
    private final int NUM_TIERS_ABOVE_MIN = 8;
    private LocalDateTime creationDate;
    private Game game;
    private int numPlayers;
    private Double totalScore;
    private String difficulty_level;
    private HashMap<Tier, Double> achievements;
    String tierString;
    private List<Double> scores;

    //todo: add options in here - for the single play
    private Options options;

    public Play(Game game, int numPlayers, List<Double> scores, Options options) {
        this.creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.scores = scores;
        this.totalScore = -1.0;
        this.achievements = new HashMap<>();
        this.options = options;
        this.difficulty_level = options.getDifficulty();
        this.tierString = options.getThemeName();
    }

    private Double calculateTotalScore() {
        Double totalScore = 0.0;
        for (Double score : scores) {
            totalScore += score;
        }
        return totalScore;
    }

    // subdivide scores into 10 tiers
    public void getListOfAchievements() {
        List<Tier> tiers = getTierValues();
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double minScore = max;

        for (Tier tier : tiers) {
            if (isTierLevel1(tier)) {
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
            achievements.put(tier, minScore);
        }
    }

    private boolean isTierLevel1(Tier tier) {
        boolean isLevel1;
        switch (tierString) {
            case "Land":
                isLevel1 = (tier == Land.LEVEL1);
                break;
            case "Sky":
                isLevel1 = (tier == Sky.LEVEL1);
                break;
            default:
                isLevel1 = (tier == Ocean.LEVEL1);
                break;
        }
        return isLevel1;
    }

    @Nullable
    private List<Tier> getTierValues() {
        List<Tier> tiers;
        switch (tierString) {
            case "Land":
                tiers = Arrays.asList(Land.values());
                break;
            case "Sky":
                tiers = Arrays.asList(Sky.values());
                break;
            default:
                tiers = Arrays.asList(Ocean.values());
                break;
        }
        return tiers;
    }

    // subdivide scores into 10 tiers
    public String getAchievementScore() {
        getListOfAchievements();
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double score = max - scoreInterval;
        int i = 1;

        if (totalScore == -1.0) {
            this.totalScore = calculateTotalScore();
        }

        while (score > totalScore) {
            i++;
            if (i == 10) {
                score = 0;
                break;
            } else if (score - scoreInterval <= min) {
                if (score >= 0) {
                    score /= 2;
                } else {
                    score = 0;
                }
            } else {
                score -= scoreInterval;
            }
        }

        Tier levelAchieved = null;

        for (Tier tier: achievements.keySet()) {
            if (achievements.get(tier).equals(score)) {
                levelAchieved = tier;
                break;
            }
        }

        return levelAchieved.getLevel();
    }

    public double getDifficultyLevel(String difficultyLevel) {
        double difficulty_value;
        if (difficultyLevel == "easy") {
            difficulty_value = 0.75;
        } else if (difficultyLevel == "hard") {
            difficulty_value = 1.25;
        } else {
            difficulty_value = 1;
        }
        return difficulty_value;
    }

    public String getCreationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy MMM dd HH:mm");
        return creationDate.format(dtf);
    }

    public void setCreationDate(String creationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy MMM dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(creationDate, formatter);
        this.creationDate = dateTime;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public Double getTotalScore() {
        return calculateTotalScore();
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    @NonNull
    public static Tier getTier(String tierString) {
        Tier tiers;
        switch(tierString) {
            case "Land":
                tiers = Land.LEVEL1;
                break;
            case "Sky":
                tiers = Sky.LEVEL1;
                break;
            default:
                tiers = Ocean.LEVEL1;
                break;
        }
        return tiers;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Time", getCreationDate());
        json.put("NumPlayers", numPlayers);
        json.put("Option", options.toJson());
        json.put("Scores", scoresToJson());

        return json;
    }

    private JSONArray scoresToJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Double s : scores) {
            jsonArray.put(s);
        }
        return jsonArray;
    }

}

package com.example.cmpt276project.model;

import androidx.annotation.Nullable;

import com.example.cmpt276project.persistence.Writable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * Play: Class for a single play through that takes in the number of Players and Total Score achieved
 */
public class Play implements Writable {
    private final int NUM_TIERS_ABOVE_MIN = 8;
    private LocalDateTime creationDate;
    private final Game game;
    private final int numPlayers;
    private Double totalScore;
    private String difficulty_level;
    private HashMap<Tier, Double> achievements;
    private Tier tier;
    String tierString;
    private List<Double> scores;

    //todo: add options in here - for the single play
    private Options options;

    public Play(Game game, int numPlayers, List<Double> scores, Tier tier, String difficulty_level) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.difficulty_level = difficulty_level;
        achievements = new HashMap<>();
        scores = new ArrayList<>();
        this.options = new Options("Normal", "Ocean"); //these are the default themes
        totalScore = -1.0;
        this.scores = scores;
        this.tier = tier;
        this.tierString = tier.getClassName();
    }

    public Double calculateTotalScore() {
        Double totalScore = 0.0;
        for (Double score : scores) {
            totalScore += score;
        }
        return totalScore;
    }

    // subdivide scores into 10 tiers
    public void getListOfAchievements() {
        Tier tiers[] = getTierValues();
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
            case "OCEAN":
                isLevel1 = (tier == Ocean.LEVEL1);
                break;
            case "LAND":
                isLevel1 = (tier == Land.LEVEL1);
                break;
            default:
                isLevel1 = (tier == Sky.LEVEL1);
                break;
        }
        return isLevel1;
    }

    @Nullable
    private Tier[] getTierValues() {
        Tier[] tiers;
        switch (tierString) {
            case "OCEAN":
                tiers = Ocean.values();
                break;
            case "LAND":
                tiers = Land.values();
                break;
            default:
                tiers = Sky.values();
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
        } else if (difficultyLevel == "normal") {
            difficulty_value = 1;
        } else {
            difficulty_value = 1.25;
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
        return totalScore;
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

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Time", getCreationDate());
        json.put("NumPlayers", numPlayers);
        json.put("TotalScore", totalScore);
        json.put ("option", options);
        json.put("Scores", scoresToJson());
        json.put("Tier", tierString);

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

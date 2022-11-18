package com.example.cmpt276project.model;

import androidx.annotation.Nullable;

import com.example.cmpt276project.persistence.Writable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private int totalScore;
    private Tier tier;
    String tierString;
    private HashMap<Tier, Integer> achievements;
    private List<Integer> scores;


    public Play(Game game, int numPlayers, List<Integer> scores, Tier tier) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        achievements = new HashMap<>();
        totalScore = -1;
        this.scores = scores;
        this.tier = tier;
        this.tierString = tier.getClassName();
    }

    public Integer calculateTotalScore() {
        int totalScore = 0;
        for (Integer score: scores) {
            totalScore += score;
    // subdivide scores into 10 tiers
    public void getListOfAchievements() {
        Tier tiers[] = getTierValues();
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / NUM_TIERS_ABOVE_MIN);
        int minScore = max;

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

    public Integer calculateTotalScore() {
        int totalScore = 0;
        for (Integer score : scores) {
            totalScore += score;
        }
        return totalScore;
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

    public String getAchievementScore() {
        getListOfAchievements();
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / game.getNumTiersAboveMin());
        int score = max - scoreInterval;
        int i = 1;

        if(totalScore == -1) {
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

        for (Tier tier : achievements.keySet()) {
            if (achievements.get(tier).equals(score)) {
                levelAchieved = tier;
                break;
            }
        }

        return levelAchieved.getLevel();
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

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Time", getCreationDate());
        json.put("NumPlayers", numPlayers);
        json.put("Scores", scoresToJson());
        json.put("Tier", tierString);

        return json;
    }

    private JSONArray scoresToJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Integer s : scores) {
            jsonArray.put(s);
        }
        return jsonArray;
    }
}


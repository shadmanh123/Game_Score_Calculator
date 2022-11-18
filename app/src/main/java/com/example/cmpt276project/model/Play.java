package com.example.cmpt276project.model;

import androidx.annotation.Nullable;

import com.example.cmpt276project.persistence.Writable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Play: Class for a single play through that takes in the number of Players and Total Score achieved
 */
public class Play implements Writable {

    private final int NUM_TIERS_ABOVE_MIN = 8;

    private LocalDateTime creationDate;
    private Game game;
    private int numPlayers;
    private int totalScore;
    private HashMap<Integer, String> achievements;
    private Tier tier;
    String tierString;


    public Play(Game game, int numPlayers, int totalScore, Tier tier) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
        achievements = new HashMap<>();
        this.tier = tier;
        this.tierString = tier.getClassName();
    }

    // subdivide scores into 10 tiers
    public void getListOfAchievements() {
        Tier tiers[] = getTierValues();
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / NUM_TIERS_ABOVE_MIN);
        int minScore = max;

        for (Tier tier: tiers) {
            if(isTierLevel1(tier)) {
                minScore = 0;
            } else if (minScore - scoreInterval <= min) {
                if (minScore >= 0){
                    minScore /= 2;
                } else {
                    minScore = 0;
                }
            } else {
                minScore -= scoreInterval;
            }
            achievements.put(minScore, tier.getLevel());
        }
    }

    private boolean isTierLevel1(Tier tier) {
        boolean isLevel1;
        switch(tierString) {
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
        switch(tierString) {
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
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / NUM_TIERS_ABOVE_MIN);
        int score = max - scoreInterval;
        int i = 1;

        while (score > totalScore) {
            i++;
            if (i == 10) {
                score = 0;
                break;
            } else if (score - scoreInterval <= min) {
                if (score >= 0){
                    score /= 2;
                } else {
                    score = 0;
                }
            } else {
                score -= scoreInterval;
            }
        }

        return achievements.get(score);
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
        json.put("TotalScore", totalScore);
        json.put("Tier", tierString);
        return json;
    }

}

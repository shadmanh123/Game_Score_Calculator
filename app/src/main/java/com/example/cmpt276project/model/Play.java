package com.example.cmpt276project.model;

import com.example.cmpt276project.persistence.Writable;

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
    private String difficulty_level;
    private HashMap<Double, String> achievements;


    public Play(Game game, int numPlayers, int totalScore, String difficulty_level) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
        this.difficulty_level = difficulty_level;
        achievements = new HashMap<>();
    }

    // subdivide scores into 10 tiers
    public void getListOfAchievements() {
        Tiers tiers[] = Tiers.values();
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double minScore = max;

        for (Tiers tier: tiers) {
            if(tier == Tiers.LEVEL1) {
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

    // subdivide scores into 10 tiers
    public String getAchievementScore() {
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double score = max - scoreInterval;
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

    public double getDifficultyLevel(String difficultyLevel){
        double difficulty_value;
        if(difficultyLevel == "easy"){
            difficulty_value = 0.75;
        } else if(difficultyLevel == "normal" ){
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

        return json;
    }

}

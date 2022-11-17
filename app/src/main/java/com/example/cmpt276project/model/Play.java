package com.example.cmpt276project.model;

import com.example.cmpt276project.persistence.Writable;

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

    private LocalDateTime creationDate;
    private Game game;
    private int numPlayers;
    private int totalScore;
    private String difficulty_level;
    private HashMap<Tiers,Double> achievements;
    private List<Integer> scores;

    public Play(Game game, int numPlayers, int totalScore, String difficulty_level) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
        this.difficulty_level = difficulty_level;
        achievements = new HashMap<>();
        scores = new ArrayList<>();
    }

    //TODO: add function to calculate totalScore

    public String getAchievementScore() {
        this.achievements = game.getListOfAchievements(numPlayers);
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double scoreInterval = (max - min) / game.getNumTiersAboveMin();
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

        Tiers levelAchieved = null;

        for (Tiers tier: achievements.keySet()) {
            if(achievements.get(tier).equals(score)) {
                levelAchieved = tier;
                break;
            }
        }

        return levelAchieved.getLevel();
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

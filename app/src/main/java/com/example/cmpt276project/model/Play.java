package com.example.cmpt276project.model;

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

    private LocalDateTime creationDate;
    private final Game game;
    private final int numPlayers;
    private int totalScore;
    private HashMap<Tiers, Integer> achievements;
    private List<Integer> scores;

    public Play(Game game, int numPlayers, List<Integer> scores) { // List<Integer> scores
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        achievements = new HashMap<>();
        this.scores = scores;
        totalScore = -1;
    }

    public Integer calculateTotalScore() {
        int totalScore = 0;
        for (Integer score: scores) {
            totalScore += score;
        }
        return totalScore;
    }

    public String getAchievementScore() {
        this.achievements = game.getListOfAchievements(numPlayers);
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


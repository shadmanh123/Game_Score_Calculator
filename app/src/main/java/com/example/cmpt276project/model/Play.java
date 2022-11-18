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
    private final Game game;
    private final int numPlayers;
    private int totalScore;
    private String difficulty_level;
    private HashMap<Double, String> achievements;
    private Tier tier;
    String tierString;
    private HashMap<Tier, Integer> achievements;
    private List<Integer> scores;


    public Play(Game game, int numPlayers, int totalScore, String difficulty_level) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
        achievements = new HashMap<>();
        scores = new ArrayList<>();
    }
    public Play(Game game, int numPlayers, List<Integer> scores, Tier tier) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
        this.difficulty_level = difficulty_level;
        achievements = new HashMap<>();
        totalScore = -1;
        this.scores = scores;
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

    // subdivide scores into 10 tiers
    public void getListOfAchievements() {
        Tiers tiers[] = Tiers.values();
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double minScore = max;
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
        getListOfAchievements();
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / game.getNumTiersAboveMin());
        int score = max - scoreInterval;
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficulty_level);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double score = max - scoreInterval;
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

        Tier levelAchieved = null;

        for (Tier tier : achievements.keySet()) {
            if (achievements.get(tier).equals(score)) {
                levelAchieved = tier;
                break;
            }
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


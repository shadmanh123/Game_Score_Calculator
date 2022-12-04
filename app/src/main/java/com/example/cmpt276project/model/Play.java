package com.example.cmpt276project.model;

import androidx.annotation.NonNull;

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
import java.util.Objects;

/**
 * Play: Class for a single play through that takes in the number of Players and Total Score achieved
 */
public class Play implements Writable {
    private static final int NUM_TIERS_ABOVE_MIN = 8;
    private LocalDateTime creationDate;
    private static Game game;
    private int numPlayers;
    private Double totalScore;
    private final String difficultyLevel;
    private static HashMap<Tier, Double> achievements;
    private List<Double> scores;
    private Options options;
    private String nextAchievement;
    private double pointsAway;

    public Play(Game game, int numPlayers, List<Double> scores, Options options) {
        this.creationDate = LocalDateTime.now();
        Play.game = game;
        this.numPlayers = numPlayers;
        this.scores = scores;
        this.totalScore = calculateTotalScore();
        achievements = new HashMap<>();
        this.options = options;
        this.difficultyLevel = options.getDifficulty();
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public void setNumPlayers(int num){
        this.numPlayers = num;
    }
    public void setScores(List<Double> scores) {
        this.scores = scores;
    }

    private Double calculateTotalScore() {
        Double totalScore = 0.0;
        for (Double score : scores) {
            totalScore += score;
        }
        return totalScore;
    }

    public int getScoreSize() { return scores.size(); }
    public double getScore(int i) { return scores.get(i); }

    // subdivide scores into 10 tiers
    public static List<Double> getListOfAchievements(Game game, int numPlayers, Options options, HashMap<Tier, Double> achievements) {
        String theme = options.getThemeName();
        String difficultyLevel = options.getDifficulty();
        List<Tier> tiers = getTierValues(theme);
        List<Double> scores = new ArrayList<>();
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficultyLevel);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficultyLevel);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double minScore = max;

        for (Tier tier : tiers) {
            if (isTierLevel1(tier, theme)) {
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
            scores.add(minScore);
        }
        Play.achievements = achievements;
        return scores;
    }

    private static boolean isTierLevel1(Tier tier, String theme) {
        boolean isLevel1;
        switch (theme) {
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

    public static List<Tier> getTierValues(String theme) {
        List<Tier> tiers;
        switch (theme) {
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
    public Tier getAchievementScore() {
        getListOfAchievements(game, numPlayers, options, achievements);
        double max = game.getMaxScore() * numPlayers * getDifficultyLevel(difficultyLevel);
        double min = game.getMinScore() * numPlayers * getDifficultyLevel(difficultyLevel);
        double scoreInterval = (max - min) / NUM_TIERS_ABOVE_MIN;
        double score = max - scoreInterval;
        int i = 1;

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
            if (Objects.equals(achievements.get(tier), score)) {
            if (achievements.get(tier).equals(score)) {
                pointsAway = (score + scoreInterval) - totalScore;
                for(Tier category:achievements.keySet()){
                    if(achievements.get(category).equals(score+scoreInterval)){
                        nextAchievement = category.getLevel();
                        break;
                    }
                }
                levelAchieved = tier;
                break;
            }
        }

        return levelAchieved;
    }

    public static double getDifficultyLevel(String difficultyLevel) {
        double difficultyValue;
        if (difficultyLevel.equals("easy")) {
            difficultyValue = 0.75;
        } else if (difficultyLevel.equals("hard")) {
            difficultyValue = 1.25;
        } else {
            difficultyValue = 1;
        }
        return difficultyValue;
    }

    public String getCreationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yy MMM dd HH:mm");
        return creationDate.format(dtf);
    }

    public void setCreationDate(String creationDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy MMM dd HH:mm");
        this.creationDate = LocalDateTime.parse(creationDate, formatter);
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

    public String getDifficultyLevel() {
        return options.getDifficulty();
    }

    public String getNextAchievement() {
        return nextAchievement;
    }

    public Double getPointsAway(){
        return pointsAway;
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

    private JSONArray scoresToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Double s : scores) {
            jsonArray.put(s);
        }
        return jsonArray;
    }

}

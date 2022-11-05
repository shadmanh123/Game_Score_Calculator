package com.example.cmpt276project.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Play {

    private final int NUM_TIERS_ABOVE_MIN = 8;
    private LocalDateTime creationDate;
    private Game game;
    private int numPlayers;
    private int totalScore;
    private HashMap<Integer, String> achievements;

    public Play(Game game, int numPlayers, int totalScore) {
        creationDate = LocalDateTime.now();
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
        achievements = new HashMap<>();
    }

    // subdivide scores into 10 tiers
    public void achievementsAndScores() {
        Tiers tiers[] = Tiers.values();
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / NUM_TIERS_ABOVE_MIN);
        int score = 0;

        for (Tiers tier: tiers) {
            achievements.put(score, tier.getLevel());
            System.out.println(score);
            score += scoreInterval;
        }

        System.out.println("finish");
    }

    // subdivide scores into 10 tiers
    public String calculateAchievementForGroupScore() {
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / NUM_TIERS_ABOVE_MIN);
        int score = 0;

        while (score < totalScore) {
            if(score > max + scoreInterval) {
                break;
            }
            score += scoreInterval;
            System.out.println(score);
        }

        System.out.println("After");
        System.out.println(score);

        return achievements.get(score);
    }

    public String getCreationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("@yyyy-MM-dd HH:mm");
        return creationDate.format(dtf);
    }


    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getNUM_TIERS_ABOVE_MIN() {
        return NUM_TIERS_ABOVE_MIN;
    }
}

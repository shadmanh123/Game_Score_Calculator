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

        for (Tiers tier : tiers) {
            max -= scoreInterval;
            achievements.put(max, tier.getLevel());
        }
    }

    // subdivide scores into 10 tiers
    public String calculateAchievementForGroupScore() {
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / NUM_TIERS_ABOVE_MIN);
        int i = max;

        while (i >= totalScore) {
            i -= scoreInterval;
        }

        return achievements.get(i - scoreInterval);
    }

    public String getCreationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("@yyyy-MM-dd HH:mm");
        return creationDate.format(dtf);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
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

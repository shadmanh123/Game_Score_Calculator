package com.example.cmpt276project.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Play {
    private LocalDateTime creationDate;
    final int NUM_TIERS = 10;
    final int NUM_TIERS_ABOVE_MIN = 8;
    Game game;
    int numPlayers;
    int totalScore;
    HashMap<Integer, String> achievements;

    public Play(Game game, int numPlayers, int totalScore) {
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
        achievements = new HashMap<>();
        creationDate = LocalDateTime.now();
    }

    public String getCreationDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("@yyyy-MM-dd HH:mm");
        return creationDate.format(dtf);
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
        while (i > totalScore) {
            i -= scoreInterval;
        }

        return achievements.get(i - scoreInterval);
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


}

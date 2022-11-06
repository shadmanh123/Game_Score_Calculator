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
        int minScore = max;
        System.out.println("min"+min);

        for (Tiers tier: tiers){
            if(tier == Tiers.LEVEL1) {
                minScore = 0;
            } else if (minScore - scoreInterval <= min){
                if (minScore >= 0){
                    minScore /= 2;
                }
            } else{
                minScore -= scoreInterval;
            }
            System.out.println("max: "+minScore);
            achievements.put(minScore, tier.getLevel());
        }

        System.out.println("finish");
    }

    // subdivide scores into 10 tiers
    public String calculateAchievementForGroupScore() {
        int max = game.getMaxScore() * numPlayers;
        int min = game.getMinScore() * numPlayers;
        int scoreInterval = (int) Math.floor((double) (max - min) / NUM_TIERS_ABOVE_MIN);
        int score = max - scoreInterval;
        int i = 1;

        System.out.println("totalscore: " + totalScore);
        System.out.println("min: "+min);

        while (score >= totalScore){
            i++;
            if(i == 10) {
                score = 0;
                break;
            }else if (score - scoreInterval <= min){
                if (score >= 0){
                    score /= 2;
                }
            }
            else{
                score -= scoreInterval;
            }
            System.out.println("score: " +score);
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

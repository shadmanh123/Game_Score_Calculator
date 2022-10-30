package com.example.cmpt276project.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    String name;
    int minScore;
    int maxScore;
    List<Play> plays;

    public Game(String name, int minScore, int maxScore) {
        this.name = name;
        this.minScore = minScore;
        this.maxScore = maxScore;
        plays = new ArrayList<>();
    }

    public void addPlay(Play play) {
        plays.add(play);
    }

    // subdivide scores into 10 tiers
    public List<String> calculateAchievement(int numPlayers) {
        List<String> tiers = new ArrayList<>(); // Maybe will change to hashmap
        if(minScore > 0) {

        }

        return null;

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

}

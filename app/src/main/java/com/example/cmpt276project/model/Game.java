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
    // TODO: change later
    public void getPlay(int index) {
        plays.get(index);
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

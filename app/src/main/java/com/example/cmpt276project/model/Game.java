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

}

package com.example.cmpt276project.model;

public class Play {
    Game game;
    int numPlayers;
    int totalScore;

    public Play(Game game, int numPlayers, int totalScore) {
        this.game = game;
        this.numPlayers = numPlayers;
        this.totalScore = totalScore;
    }

}

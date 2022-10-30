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

    public int calculateScoreForEachPlayer() {
        int playerScore = totalScore / numPlayers;
        return playerScore;
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

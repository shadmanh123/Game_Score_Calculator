package com.example.cmpt276project.model;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private String name;
    private int minScore;
    private int maxScore;
    private List<Play> plays;

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
    public Play getPlay(int index) {
        return plays.get(index);
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

    //TODO change 4 into constant
    public String[][] displayGames(){
        String[][] games = new String[plays.size()][4];
        int numberOfPlays = 0;
        for (int rows = 0; rows < plays.size(); rows++) {
            for(int columns = 0; columns < 4; columns++){
                Play play = plays.get(numberOfPlays);
                if(columns == 0){
                    games[rows][columns] = play.getCreationDate();
                }
                else if(columns == 1){
                    games[rows][columns] = ""+play.getNumPlayers();
                }
                else if(columns == 2){
                    games[rows][columns] = ""+play.getTotalScore();
                } else{
                    games[rows][columns] = play.calculateAchievementForGroupScore();
                }
            }
            numberOfPlays++;
        }
        return null;
    }

}

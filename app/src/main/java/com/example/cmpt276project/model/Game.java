package com.example.cmpt276project.model;

import java.util.ArrayList;
import java.util.List;
/**
 * Game: Class that consists of a list of plays that can
 *
 */
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

    public int playSize() {
        return plays.size();
    }
    public void addPlay(Play play) {
        plays.add(play);
    }

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

    public String displayPlayInfo(int playIndex, int column){
        String display;
        Play play = getPlay(playIndex);
        switch(column) {
            case 0:
                display = play.getCreationDate();
                break;
            case 1:
                display = "" + play.getNumPlayers();
                break;
            case 2:
                display = "" + play.getTotalScore();
                break;
            case 3:
                display = play.getAchievements();
                break;
            default:
                display = "";
                break;
        }

        return display;
    }

}

package com.example.cmpt276project.model;

import com.example.cmpt276project.model.tiers.Land;
import com.example.cmpt276project.model.tiers.Ocean;
import com.example.cmpt276project.model.tiers.Sky;
import com.example.cmpt276project.model.tiers.Tier;
import com.example.cmpt276project.persistence.Writable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
/**
 * Game: Class of a single game that contains the play history
 */
public class Game implements Writable {
    private String name;
    private int minScore;
    private int maxScore;
    private List<Play> plays;
    private List<Integer> level;

    public Game(String name, int minScore, int maxScore) {
        this.name = name;
        this.minScore = minScore;
        this.maxScore = maxScore;
        plays = new ArrayList<>();
        level = new ArrayList<>();
    }

    public List<Play> getPlays() {
        return plays;
    }

    public int playSize() {
        return plays.size();
    }

    public void clearPlays() {
        plays.clear();
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

    public HashMap<Ocean, Integer> achievementStatistics() {
        List<Tier> tiers = new ArrayList<>();
        for (Play p: plays) {
            Tier achievement = p.getAchievementScore();
            tiers.add(achievement);
        }

        for (Ocean ocean : Ocean.values()) {
            level.add(Collections.frequency(tiers, ocean));
        }
        print();

        addLevelFreq(tiers, Land.LEVEL1.getClassName());
        print();

        addLevelFreq(tiers, Sky.LEVEL1.getClassName());
        print();

        HashMap<Ocean, Integer> statistics = new HashMap<>();

        int i = 0;
        for (Ocean ocean : Ocean.values()) {
            statistics.put(ocean, level.get(i));
            i++;
        }

        return statistics;
    }

    private void print() {
        int i = 0;
        for (Integer j: level) {
            System.out.println("level " + i + j);
        }
    }

    private void addLevelFreq(List<Tier> tiers, String theme) {
        int i = 0;
        for (Tier tier : Play.getTierValues(theme)) {
            int lvl = level.get(i) + Collections.frequency(tiers, tier);
            level.set(i, lvl);
            i++;
        }
    }


    public String displayPlayInfo(int playIndex, int column) {
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
                Tier tier = play.getAchievementScore();
                display = tier.getLevel();
                break;
            case 4:
                display = play.getDifficultyLevel();
                break;
            default:
                display = "";
                break;
        }
        return display;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Name", name);
        json.put("Min", minScore);
        json.put("Max", maxScore);
        json.put("Plays", playsToJson());
        return json;
    }

    private JSONArray playsToJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Play p : plays) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}


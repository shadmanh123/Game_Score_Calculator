package com.example.cmpt276project.model;

import com.example.cmpt276project.persistence.Writable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * GameManager: Singleton class that manages all the different games in the application
 */
public class GameManager implements Writable {
    private List<Game> games;

    //making the singleton
    private static GameManager instance;
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public GameManager() {
        games = new ArrayList<>();
    }

    public List<Game> gamesList() {
        return games;
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public void removeGame(Game game) {
        games.remove(game);
    }

    public void clearGames() {
        games.clear();
    }

    public void editGame(Game game) {
        int index = games.indexOf(game);
        games.set(index, game);
    }

    public Game getGame(int index){
        return games.get(index);
    }

    public int getNumberOfGames(){
        return games.size();
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("Games", gamesToJson());
        return json;
    }

    private JSONArray gamesToJson() throws JSONException {
        JSONArray jsonArray = new JSONArray();

        for (Game g : games) {
            jsonArray.put(g.toJson());
        }

        return jsonArray;
    }
}

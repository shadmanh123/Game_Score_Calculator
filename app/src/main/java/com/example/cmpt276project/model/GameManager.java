package com.example.cmpt276project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * GameManager: Singleton class that manages all the different games in the application
 */
public class GameManager {
    private List<Game> games;

    //making the singleton
    private static GameManager instance;
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager() {
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

    public int getNumbeOfGames(){
        return games.size();
    }

}

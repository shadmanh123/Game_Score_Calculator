package com.example.cmpt276project.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameManager {
    private List<Game> games;


    public GameManager() {
        games = new ArrayList<>();
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

}

package com.example.cmpt276project.persistence;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Land;
import com.example.cmpt276project.model.Ocean;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.model.Sky;
import com.example.cmpt276project.model.Tier;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Code based on a demo from Tiffanie's class at UBC CPSC 210
// Represents a reader that reads workroom from JSON data stored in file
// Code based on Tiffanie's class at UBC CPSC 210

public class JsonReader {

    private static final String JSON_STORE = "gameManager.json";
    private Context context;
    private GameManager gameManager;

    public JsonReader(Context context, GameManager gm) {
        this.context = context;
        this.gameManager = gm;
    }

    public GameManager readFromJson() {
        try {
            gameManager = read(context);
            System.out.println("Loaded" + " from " + JSON_STORE);
        } catch (JSONException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Had to make a new one");
        } catch (IOException e) {
            gameManager = GameManager.getInstance();
            System.out.println("Couldn't read file");
        }
        return gameManager;
    }


    public GameManager read(Context context) throws IOException, JSONException {
        String jsonData = readFile(JSON_STORE, context);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameManager(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source, Context context) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(context.getFilesDir() + "/" + source), StandardCharsets.UTF_8)) {
            System.out.println(context.getFilesDir() + "/" + source);
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private GameManager parseGameManager(JSONObject jsonObject) throws JSONException {
        GameManager gm = new GameManager();
        addGames(gm, jsonObject);
        System.out.println("Size: " + gm.gamesList().size());
        return gm;
    }

    private void addGames(GameManager gm, JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("Games");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject nextGame = json;
            addGame(gm, nextGame);
        }
    }

    private void addGame(GameManager gm, JSONObject jsonObject) throws JSONException {
        String name = jsonObject.getString("Name");
        int min = jsonObject.getInt("Min");
        int max = jsonObject.getInt("Max");

        Game game = new Game(name, min, max);
        addPlays(game, jsonObject);
        gm.addGame(game);
    }

    private void addPlays(Game game, JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("Plays");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject nextPlay = json;
            addPlay(game, nextPlay);
        }
    }

    private void addPlay(Game game, JSONObject nextPlay) throws JSONException {
        String time = nextPlay.getString("Time");
        int numPlayers = nextPlay.getInt("NumPlayers");
        int totalScore = nextPlay.getInt("TotalScore");
        String tier = nextPlay.getString("Tier");
        Tier tiers = getTier(tier);

        Play play = new Play(game, numPlayers, totalScore, tiers);
        play.setCreationDate(time);
        game.addPlay(play);
    }

    @NonNull
    private Tier getTier(String tier) {
        Tier tiers;
        switch(tier) {
            case "OCEAN":
                tiers = Ocean.LEVEL1;
                break;
            case "LAND":
                tiers = Land.LEVEL1;
                break;
            default:
                tiers = Sky.LEVEL1;
                break;
        }
        return tiers;
    }

}

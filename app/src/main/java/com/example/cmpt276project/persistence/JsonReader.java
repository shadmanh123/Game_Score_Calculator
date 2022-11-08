package com.example.cmpt276project.persistence;

import android.content.Context;
import android.util.Log;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

    public GameManager read(Context context, String fileName) throws IOException, JSONException {
        GameManager gm;
        String jsonFileString = Utils.getJsonFromAssets(context, fileName);
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<GameManager>() { }.getType();

        gm = gson.fromJson(jsonFileString, listUserType);

//        String jsonData = readFile(source);
//        JSONObject jsonObject = new JSONObject(jsonData);
        return gm;
    }

//    private String readFile(String source) throws IOException {
//        StringBuilder contentBuilder = new StringBuilder();
//
//        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
//            stream.forEach(s -> contentBuilder.append(s));
//        }
//
//        return contentBuilder.toString();
//    }

    private GameManager parseWorkRoom(JSONObject jsonObject) throws JSONException {
        GameManager gm = new GameManager();
        addGames(gm, jsonObject);
        return gm;
    }

    private void addGames(GameManager gm, JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("Game");
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
            JSONObject nextPlay = (JSONObject) json;
            addPlay(game, nextPlay);
        }
    }

    private void addPlay(Game gm, JSONObject nextPlay) throws JSONException {
        int numPlayers = nextPlay.getInt("NumPlayers");
        int totalScore = nextPlay.getInt("TotalScore");
        Play play = new Play(gm, numPlayers, totalScore);
        gm.addPlay(play);
    }

}

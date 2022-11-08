package com.example.cmpt276project.persistence;

import android.content.Context;
import android.util.Log;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;

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

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {

    private String source;

    public JsonReader(String source) {
        this.source = source;
    }

/*
    // Code based on: https://medium.com/@nayantala259/android-how-to-read-and-write-parse-data-from-json-file-226f821e957a
    public GameManager read(Context context) throws JSONException, IOException {
        File file = new File(context.getFilesDir(), source);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();

        while (line != null) {
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }

        bufferedReader.close();

        String jsonData = stringBuilder.toString();
        JSONObject jsonObject = new JSONObject(jsonData);
        Log.i("data", jsonData);

        return parseGameManager(jsonObject);
    }

 */

    public GameManager read(Context context) throws IOException, JSONException {
        String jsonData = readFile(source, context);
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
            JSONObject nextPlay = (JSONObject) json;
            addPlay(game, nextPlay);
        }
    }

    private void addPlay(Game gm, JSONObject nextPlay) throws JSONException {
        String time = nextPlay.getString("Time");
        int numPlayers = nextPlay.getInt("NumPlayers");
        int totalScore = nextPlay.getInt("TotalScore");
        Play play = new Play(gm, numPlayers, totalScore);
        play.setCreationDate(time);

        gm.addPlay(play);
    }

}

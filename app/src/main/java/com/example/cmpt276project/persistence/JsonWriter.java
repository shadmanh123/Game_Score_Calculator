package com.example.cmpt276project.persistence;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.cmpt276project.model.GameManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Code based on a demo from Tiffanie's class at UBC CPSC 210
// Represents a writer that writes JSON representation of workroom to file
// Code based on Tiffanie's class at UBC CPSC 210

public class JsonWriter {

    private static final String JSON_STORE = "gameManager.json";
    private static final int TAB = 4;
    private PrintWriter writer;
    private Context context;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(Context context) {
        this.context = context;
    }

    public void writeToJson(GameManager gameManager) {
        try {
            open(context);
            write(gameManager);
            close();
            System.out.println("Saved" + " to " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("JSON Problem: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open(Context context) throws FileNotFoundException {
        writer = new PrintWriter(new File(context.getFilesDir(), JSON_STORE));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(GameManager gm) throws JSONException {
        JSONObject json = gm.toJson();
        saveToFile(json.toString(TAB));
        Log.i("data", String.valueOf(json));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}

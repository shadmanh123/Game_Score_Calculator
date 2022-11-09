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
public class JsonWriter {
/*
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // Code based on: https://medium.com/@nayantala259/android-how-to-read-and-write-parse-data-from-json-file-226f821e957a

    public void write(GameManager gm, Context context) throws JSONException, IOException {
        JSONObject json = gm.toJson();
        String userString = json.toString();
        File file = new File(context.getFilesDir(), destination);
        System.out.println(context.getFilesDir());
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userString);
        Log.i("data", String.valueOf(json));
        bufferedWriter.close();
    }

 */

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }


    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open(Context context) throws FileNotFoundException {
        writer = new PrintWriter(new File(context.getFilesDir(), destination));
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

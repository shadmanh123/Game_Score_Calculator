package com.example.cmpt276project.persistence;

import android.content.res.AssetManager;

import com.example.cmpt276project.model.GameManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of workroom to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    public void open() throws IOException {
        //AssetManager assetManager = getAssets();
        //assetManager.open(destination);
        writer = new PrintWriter(new File(destination));
    }

    public void write(GameManager gm) throws JSONException {
        JSONObject json = gm.toJson();
        saveToFile(json.toString(TAB));
    }

    public void close() {
        writer.close();
    }

    private void saveToFile(String json) {
        writer.print(json);
    }
}

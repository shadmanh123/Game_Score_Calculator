package com.example.cmpt276project;

import com.example.cmpt276project.model.Game;
import com.example.cmpt276project.model.GameManager;
import com.example.cmpt276project.model.Play;
import com.example.cmpt276project.persistence.JsonWriter;

import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class PersistenceTest {
    private static final String JSON_STORE = "./gameManager.json";
    private JsonWriter jsonWriter;

    @Test
    public void test() {
        //File path = this.getExternalFilesDir(null);
        File f = new File(JSON_STORE);
        if (f.exists()) {
            // Show if the file exists
            System.out.println("Exists");
        } else {
            // Show if the file does not exists
            System.out.println("Does not Exist");
        }

        jsonWriter = new JsonWriter(JSON_STORE);

        GameManager gm = new GameManager();
        Game g = new Game("Chess", 0, 100);
        Play p = new Play(g, 2, 323);
        g.addPlay(p);
        gm.addGame(g);

        try {
            jsonWriter.open();
            jsonWriter.write(gm);
            jsonWriter.close();
            System.out.println("Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to write file: " + JSON_STORE);
        } catch (JSONException e) {
            System.out.println("JSON Problem: " + JSON_STORE);
        }
    }

}

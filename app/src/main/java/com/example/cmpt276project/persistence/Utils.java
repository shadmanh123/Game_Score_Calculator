package com.example.cmpt276project.persistence;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

// Code from Website: https://www.bezkoder.com/java-android-read-json-file-assets-gson/
public class Utils {

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");

            System.out.println("Success");
        } catch (IOException e) {
            System.out.println("Fail");
            return null;
        }

        return jsonString;
    }
}

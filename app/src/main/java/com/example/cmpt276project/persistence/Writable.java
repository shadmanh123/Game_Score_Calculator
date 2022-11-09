package com.example.cmpt276project.persistence;

import org.json.JSONException;
import org.json.JSONObject;

// Code based on Tiffanie's class at UBC CPSC 210

public interface Writable {

    JSONObject toJson() throws JSONException;
}

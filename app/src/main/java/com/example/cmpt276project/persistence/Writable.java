package com.example.cmpt276project.persistence;

import org.json.JSONException;
import org.json.JSONObject;

public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson() throws JSONException;
}

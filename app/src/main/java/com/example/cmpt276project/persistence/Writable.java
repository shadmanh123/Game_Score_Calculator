package com.example.cmpt276project.persistence;

import org.json.JSONException;
import org.json.JSONObject;

public interface Writable {

    JSONObject toJson() throws JSONException;
}

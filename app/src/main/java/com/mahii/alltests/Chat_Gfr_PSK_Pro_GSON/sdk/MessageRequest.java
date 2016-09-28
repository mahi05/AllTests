package com.mahii.alltests.Chat_Gfr_PSK_Pro_GSON.sdk;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MessageRequest {

    private String text;
    private int sourceId;
    private Map<String, String> context;

    public Map<String, String> getContext() {
        return context;
    }

    public void setContext(Map<String, String> context) {
        this.context = context;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public JSONObject toJsonObject() {
        Gson gson = new Gson();
        String jsonString = gson.toJson(this, MessageRequest.class);
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
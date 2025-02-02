package me.rex.vanguard.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public interface Jsonable {
    JsonObject toJson();
    void fromJson(JsonElement json);
    String getFileName();
}

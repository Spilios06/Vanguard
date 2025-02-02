package me.rex.vanguard.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.rex.vanguard.config.Jsonable;

import java.util.Set;

public class FriendManager implements Jsonable {
    public static FriendManager INSTANCE = new FriendManager();
    public Set<String> friends;

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        for (String friend : friends) {
            array.add(friend);
        }
        object.add("friends", array);
        return object;
    }

    @Override
    public void fromJson(JsonElement json) {
        for (JsonElement element : json.getAsJsonObject().get("friends").getAsJsonArray()) {
            friends.add(element.getAsString());
        }
    }

    @Override
    public String getFileName() {
        return "friends.json";
    }
}

package me.rex.vanguard.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.rex.vanguard.VanguardClient;
import me.rex.vanguard.config.Jsonable;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.Setting;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ConfigManager {
    public static ConfigManager INSTANCE = new ConfigManager();
    private static final Path ClientPath = FabricLoader.getInstance().getGameDir().resolve("vanguard");
    private static final Gson gson = new GsonBuilder()
            .setLenient()
            .setPrettyPrinting()
            .create();
    private final List<Jsonable> jsonables = List.of(FriendManager.INSTANCE, ModuleManager.INSTANCE);

    public static void setValueFromJson(Setting setting, JsonElement element) {
        String str;
        switch (setting.getType()) {
            case "Boolean" -> {
                setting.setValue(element.getAsBoolean());
            }
            case "Double" -> {
                setting.setValue(element.getAsDouble());
            }
            case "String" -> {
                str = element.getAsString();
                setting.setValue(str.replace("_", " "));
            }
            case "Bind" -> {
                setting.setValue(element.getAsInt());
            }
            case "Enum" -> {
                str = element.getAsString();
                setting.setValue(str.replace("_", " "));
            }
        }
    }

    public void load() {
        if (!ClientPath.toFile().exists()) ClientPath.toFile().mkdirs();
        for (Jsonable jsonable : jsonables) {
            try {
                String read = Files.readString(ClientPath.resolve(jsonable.getFileName()));
                jsonable.fromJson(JsonParser.parseString(read));
            } catch (Throwable ignored) {
            }
        }
    }

    public void save() {
        if (!ClientPath.toFile().exists()) ClientPath.toFile().mkdirs();
        for (Jsonable jsonable : jsonables) {
            try {
                JsonElement json = jsonable.toJson();
                Files.writeString(ClientPath.resolve(jsonable.getFileName()), gson.toJson(json));
            } catch (Throwable ignored) {
            }
        }
    }
}

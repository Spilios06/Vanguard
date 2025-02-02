package me.rex.vanguard.module;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.rex.vanguard.VanguardClient;
import me.rex.vanguard.config.Jsonable;
import me.rex.vanguard.event.events.KeyPressEvent;
import me.rex.vanguard.manager.ConfigManager;
import me.rex.vanguard.settings.Setting;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module implements Jsonable {
    public String description;
    public String name;
    public Category category;
    public int key;
    public boolean enabled = false;
    public List<Setting> settings = new ArrayList<>();
    protected MinecraftClient mc = MinecraftClient.getInstance();
    public Module(String name, Category category, int key){
        this.name = name;
        this.category = category;
        this.key = key;
        VanguardClient.eventManager.getEventBus().register(this);
    }
    public void toggle(){
        this.enabled = !this.enabled;
        if(enabled){
            onEnable();
        }else{
            onDisable();
        }
    }
    public void onEnable(){
    }
    public void onDisable(){
    }
    public void onTick(){
    }
    public void addSettings(Setting... settings){
        this.settings.addAll(Arrays.asList(settings));
    }

    @Subscribe
    public void onKeyPress(KeyPressEvent event){
        if(event.key == key){
            toggle();
        }
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        for (Setting setting : settings) {
            try {
                object.addProperty(setting.name, setting.getValueAsString());
            } catch (Throwable ignored) {
            }
        }
        return object;
    }

    @Override
    public void fromJson(JsonElement json) {
        JsonObject object = json.getAsJsonObject();
        String enabled = object.get("Enabled").getAsString();
        if (Boolean.parseBoolean(enabled)) toggle();
        for (Setting setting : settings) {
            try {
                ConfigManager.setValueFromJson(this, setting, object.get(setting.name));
            }catch (Throwable ignored) {
            }
        }
    }

    @Override
    public String getFileName() {
        return "";
    }
}
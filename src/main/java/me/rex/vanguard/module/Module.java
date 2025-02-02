package me.rex.vanguard.module;

import com.google.common.eventbus.EventBus;
import me.rex.vanguard.VanguardClient;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.Setting;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Module {
    private final EventBus EVENT_BUS = VanguardClient.eventManager.getEventBus();
    public String description;
    public String name;
    public Category category;
    public int key;
    public boolean enabled = false;
    public boolean hasListener;
    public List<Setting> settings = new ArrayList<>();
    protected MinecraftClient mc = MinecraftClient.getInstance();
    public Module(String name, Category category, int key, boolean hasListener){
        this.name = name;
        this.category = category;
        this.key = key;
        this.hasListener = hasListener;
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
        if(hasListener){
            EVENT_BUS.register(this);
        }
    }
    public void onDisable(){
        if(hasListener){
            EVENT_BUS.unregister(this);
        }
    }
    public void onTick(){
    }
    public void addSettings(Setting... settings){
        this.settings.addAll(Arrays.asList(settings));
    }
}
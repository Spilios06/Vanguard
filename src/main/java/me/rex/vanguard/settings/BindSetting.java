package me.rex.vanguard.settings;

import me.rex.vanguard.module.Module;

public class BindSetting extends Setting{
    public int value;
    public BindSetting(int key, String name) {
        super(name);
        this.value = key;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value, Module module) {
        module.key = value;
    }
}

package me.rex.vanguard.settings;

import me.rex.vanguard.module.Module;

public class BindSetting extends Setting{
    public int value;
    public Module module;

    public BindSetting(int key, String name) {
        super(name);
        this.value = key;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value, Module module) {
        this.value = value;
        module.key = value;
    }

    @Override
    public String getType() {
        return "Bind";
    }

    @Override
    public void setValue(Object value) {
        this.value = (int) value;
        if (module != null) module.key = (int) value;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }
}

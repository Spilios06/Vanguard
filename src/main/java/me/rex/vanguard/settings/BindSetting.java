package me.rex.vanguard.settings;

public class BindSetting extends Setting{
    public int value;
    public BindSetting(int key, String name) {
        super(name);
        this.value = key;
    }

    public int getValue() {
        return this.value;
    }
}

package me.rex.vanguard.settings;

public abstract class Setting {
    public String name;
    public boolean visible;

    public Setting(String name){
        this.name = name;
        this.visible = true;
    }

    public abstract String getType();

    public abstract void setValue(Object value);

    public abstract String getValueAsString();
}
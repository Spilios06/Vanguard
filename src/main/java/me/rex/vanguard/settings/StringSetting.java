package me.rex.vanguard.settings;

public class StringSetting extends Setting{
    public String value;

    public StringSetting(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    public String getType() {
        return "String";
    }

    @Override
    public void setValue(Object value) {
        this.value = (String) value;
    }

    @Override
    public String getValueAsString() {
        return value;
    }
}

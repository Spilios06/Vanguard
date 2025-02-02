package me.rex.vanguard.settings;

public class NumberSetting extends Setting{
    public double min, max, defaultValue;
    public double value;

    public NumberSetting(String name, double min, double max, double defaultValue) {
        super(name);
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }
    public static double clamp(double value, double min, double max){
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }
    public float getValueFloat(){
        return (float)value;
    }

    public int getValueInt(){
        return (int)value;
    }

    @Override
    public String getType() {
        return "Double";
    }

    public void setValueDouble(double value){
        value = clamp(value, this.min, this.max);
        value = Math.round(value);
        this.value = value;
    }

    @Override
    public void setValue(Object value) {
        value = clamp((double) value, this.min, this.max);
        value  = Math.round((double) value);
        this.value = (double) value;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }
}

package me.rex.vanguard.settings;

public class NumberSetting extends Setting{
    public double min, max, defaultValue, increment;
    public double value;

    public NumberSetting(String name, double min, double max, double defaultValue, double increment) {
        super(name);
        this.min = min;
        this.max = max;
        this.defaultValue = defaultValue;
        this.increment = increment;
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
        value = Math.round(value / this.increment) * this.increment;
        value = clamp(value, this.min, this.max);
        this.value = value;
    }

    @Override
    public void setValue(Object value) {
        double v = Math.round((double) value / this.increment) * this.increment;
        v = clamp(v, this.min, this.max);
        this.value = v;
    }

    @Override
    public String getValueAsString() {
        return String.valueOf(value);
    }
}

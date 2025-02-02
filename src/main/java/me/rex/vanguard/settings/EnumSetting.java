package me.rex.vanguard.settings;

import java.util.Arrays;
import java.util.List;

public class EnumSetting extends Setting{
    public List<Enum> modes;
    public Enum defaultMode;
    private int index;
    
    public EnumSetting(String name, Enum defaultMode, Enum... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.defaultMode = defaultMode;
        this.index = this.modes.indexOf(defaultMode);
    }

    public void cycle(){
        if(index < modes.size() - 1){
            index++;
            defaultMode = modes.get(index);
        } else if(index >= modes.size() - 1){
            index = 0;
            defaultMode = modes.get(0);
        }
    }

    @Override
    public String getType() {
        return "Enum";
    }

    @Override
    public void setValue(Object value) {
        this.defaultMode = (Enum) value; //TODO: Implement this somehow
    }

    @Override
    public String getValueAsString() {
        return defaultMode.name();
    }


}
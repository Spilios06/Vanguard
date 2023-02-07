package me.spilios.vanguard.module;

import me.spilios.vanguard.module.modules.movement.*;

import java.util.ArrayList;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList<Module>();

    public ModuleManager(){
        //COMBAT

        //MOVEMENT
        modules.add(new Sprint());
        modules.add(new Flight());

        //RENDER

        //PLAYER

        //MISC

        //CLIENT

    }

    public Module getModuleByName(String name){
        return modules.stream().filter(module -> module.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}

package me.rex.vanguard.module;

import me.rex.vanguard.module.modules.client.ClickGUI;
import me.rex.vanguard.module.modules.combat.AutoCrystal;
import me.rex.vanguard.module.modules.combat.KillAura;
import me.rex.vanguard.module.modules.misc.FastPlace;
import me.rex.vanguard.module.modules.misc.TestingPlayer;
import me.rex.vanguard.module.modules.movement.*;
import me.rex.vanguard.module.modules.player.AutoRespawn;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    public ArrayList<Module> modules = new ArrayList<Module>();
    public ModuleManager(){
        //COMBAT
        modules.add(new AutoCrystal());
        modules.add(new KillAura());

        //MOVEMENT
        modules.add(new Sprint());
        modules.add(new Flight());
        modules.add(new Velocity());
        modules.add(new Step());
        modules.add(new ReverseStep());

        //RENDER

        //PLAYER
        modules.add(new AutoRespawn());

        //MISC
        modules.add(new TestingPlayer());
        modules.add(new FastPlace());

        //CLIENT
        modules.add(new ClickGUI());

    }

    public Module getModuleByName(String name){
        return modules.stream().filter(module -> module.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<Module> getEnabledModules(){
        ArrayList<Module> enabled = new ArrayList<>();
        for(Module module : modules){
            if (module.enabled){
                enabled.add(module);
            }
        }
        return enabled;
    }
    public List<Module> getModulesInCategory(Category category){
        List<Module> categoryModules = new ArrayList<>();
        for(Module module : modules){
            if(module.category == category){
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }
}

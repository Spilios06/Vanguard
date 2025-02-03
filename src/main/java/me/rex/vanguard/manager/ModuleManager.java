package me.rex.vanguard.manager;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.rex.vanguard.config.Jsonable;
import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.module.modules.client.ClickGUI;
import me.rex.vanguard.module.modules.combat.AutoCrystal;
import me.rex.vanguard.module.modules.combat.Criticals;
import me.rex.vanguard.module.modules.combat.KillAura;
import me.rex.vanguard.module.modules.misc.FastPlace;
import me.rex.vanguard.module.modules.misc.TestingPlayer;
import me.rex.vanguard.module.modules.movement.*;
import me.rex.vanguard.module.modules.player.AutoRespawn;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager implements Jsonable {
    public static final ModuleManager INSTANCE = new ModuleManager();
    public ArrayList<me.rex.vanguard.module.Module> modules = new ArrayList<me.rex.vanguard.module.Module>();
    public ModuleManager(){
        //COMBAT
        modules.add(new AutoCrystal());
        modules.add(new KillAura());
        modules.add(new Criticals());

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

    public me.rex.vanguard.module.Module getModuleByName(String name){
        return modules.stream().filter(module -> module.name.equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public ArrayList<me.rex.vanguard.module.Module> getEnabledModules(){
        ArrayList<me.rex.vanguard.module.Module> enabled = new ArrayList<>();
        for(me.rex.vanguard.module.Module module : modules){
            if (module.enabled){
                enabled.add(module);
            }
        }
        return enabled;
    }
    public List<me.rex.vanguard.module.Module> getModulesInCategory(Category category){
        List<me.rex.vanguard.module.Module> categoryModules = new ArrayList<>();
        for(Module module : modules){
            if(module.category == category){
                categoryModules.add(module);
            }
        }
        return categoryModules;
    }

    @Override
    public JsonObject toJson() {
        JsonObject object = new JsonObject();
        for (Module module : modules) {
            object.add(module.name, module.toJson());
        }
        return object;
    }

    @Override
    public void fromJson(JsonElement json) {
        for (Module module : modules) {
            module.fromJson(json.getAsJsonObject().get(module.name));
        }
    }

    @Override
    public String getFileName() {
        return "settings.json";
    }
}

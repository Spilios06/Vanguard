package me.rex.vanguard.module.modules.misc;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.utils.TestPlayerUtil;

import java.util.ArrayList;
import java.util.List;

public class TestingPlayer extends Module{
    public BindSetting bind = new BindSetting(-1, "Bind");
    private static final List<TestPlayerUtil> ENTITIES = new ArrayList<>();
    public TestingPlayer(){
        super("TestingPlayer", Category.MISC, -1);
        addSettings(bind);
    }
    @Override
    public void onEnable(){
        TestPlayerUtil testPlayer = new TestPlayerUtil(mc.player, name, 20, false);
        testPlayer.spawn();
        ENTITIES.add(testPlayer);
    }
    @Override
    public void onDisable(){
        ENTITIES.removeIf(fp1 -> {
            if (fp1 != null) {
                fp1.despawn();
                return true;
            }
            return false;
        });
    }
}
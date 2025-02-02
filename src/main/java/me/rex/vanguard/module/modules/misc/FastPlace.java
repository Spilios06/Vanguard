package me.rex.vanguard.module.modules.misc;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import net.minecraft.item.Items;

public class FastPlace extends Module {
    public FastPlace() {
        super("FastPlace", Category.MISC, -1);
    }
    @Override
    public void onTick() {
        if (mc.player.isHolding(Items.EXPERIENCE_BOTTLE) || mc.player.isHolding(Items.END_CRYSTAL)) {
            mc.itemUseCooldown = 0;
        }
    }
}
package me.rex.vanguard.module.modules.combat;

import me.rex.vanguard.manager.TargetManager;
import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.EnumSetting;
import me.rex.vanguard.settings.NumberSetting;
import me.rex.vanguard.utils.RotationUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;

public class KillAura extends Module {
    public BindSetting bind = new BindSetting(-1, "Keybind");
    public NumberSetting distance = new NumberSetting("Distance", 1, 6, 5, 0.5f);
    public NumberSetting targetDistance = new NumberSetting("Target Range", 1, 12, 10, 0.5f);
    public NumberSetting wallsDistance = new NumberSetting("Walls Distance", 1, 6, 4, 0.5f);
    public EnumSetting targetMode = new EnumSetting("Target Type", TargetMode.NEAREST, TargetMode.NEAREST, TargetMode.LOWEST_HEALTH);

    private enum TargetMode {
        NEAREST,
        LOWEST_HEALTH
    }

    public KillAura() {
        super("Kill Aura", Category.COMBAT, -1);
        addSettings(distance, targetDistance, wallsDistance, targetMode, bind);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null) return;
        if (!(mc.player.getMainHandStack().getItem() instanceof SwordItem)) return;
        if (mc.player.getAttackCooldownProgress(0.5f) < 1.0f) return;

        PlayerEntity target = targetMode.getValue() == TargetMode.NEAREST
                ? TargetManager.INSTANCE.getNearestTarget()
                : TargetManager.INSTANCE.getLowestHealthTarget();
        if (target == null || !target.isAlive()) return;
        if (mc.player.distanceTo(target) > targetDistance.getValueFloat()) return;

        double dist = mc.player.distanceTo(target);
        double maxDist = mc.player.canSee(target) ? distance.getValueFloat() : wallsDistance.getValueFloat();
        if (dist > maxDist) return;

        float[] rotations = RotationUtil.getRotations(target);
        mc.player.swingHand(Hand.MAIN_HAND);
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], mc.player.isOnGround()));
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(mc.player.getYaw(), mc.player.getPitch(), mc.player.isOnGround()));
        TargetManager.INSTANCE.focusOnTarget(target);
    }


}
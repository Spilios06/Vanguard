package me.rex.vanguard.module.modules.combat;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class Surround extends Module {
    public static Surround INSTANCE;
    public BindSetting bind = new BindSetting(-1, "Keybind");

    public Surround() {
        super("Surround", Category.COMBAT, -1);
        INSTANCE = this;
        addSettings(bind);
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        int obsidianSlot = -1;
        for (int i = 0; i < 9; i++) {
            if (mc.player.getInventory().main.get(i).getItem() == Items.OBSIDIAN) {
                obsidianSlot = i;
                break;
            }
        }
        if (obsidianSlot == -1) return;

        BlockPos center = mc.player.getBlockPos();
        if (center == null) return;

        float yaw = mc.player.getYaw();
        Direction forwardDir;
        if (yaw >= -45 && yaw < 45) forwardDir = Direction.SOUTH;
        else if (yaw >= 45 && yaw < 135) forwardDir = Direction.WEST;
        else if (yaw >= -135 && yaw < -45) forwardDir = Direction.EAST;
        else forwardDir = Direction.NORTH;

        Direction rightDir = forwardDir.rotateYClockwise();
        Direction backDir = forwardDir.getOpposite();
        Direction leftDir = forwardDir.rotateYCounterclockwise();

        BlockPos[] targets = {
            center.offset(forwardDir),
            center.offset(backDir),
            center.offset(rightDir),
            center.offset(leftDir)
        };

        int originalSlot = mc.player.getInventory().selectedSlot;
        if (mc.player.getInventory().selectedSlot != obsidianSlot) {
            mc.player.getInventory().selectedSlot = obsidianSlot;
            mc.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket(obsidianSlot));
        }

        float originalYaw = mc.player.getYaw();
        float originalPitch = mc.player.getPitch();

        for (BlockPos targetPos : targets) {
            if (mc.world.getBlockState(targetPos).isAir() && !mc.world.getBlockState(targetPos.down()).isAir()) {
                double dx = targetPos.getX() + 0.5 - mc.player.getX();
                double dz = targetPos.getZ() + 0.5 - mc.player.getZ();
                double dy = targetPos.getY() + 0.5 - mc.player.getEyeY();
                double distXZ = Math.sqrt(dx * dx + dz * dz);
                float targetYaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
                float targetPitch = (float) -Math.toDegrees(Math.atan2(dy, distXZ));

                mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(targetYaw, targetPitch, mc.player.isOnGround()));

                Vec3d hitPos = Vec3d.ofCenter(targetPos);
                BlockHitResult hit = new BlockHitResult(hitPos, Direction.UP, targetPos.down(), false);
                mc.interactionManager.interactBlock(mc.player, Hand.MAIN_HAND, hit);
            }
        }

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(originalYaw, originalPitch, mc.player.isOnGround()));

        if (mc.player.getInventory().selectedSlot != originalSlot) {
            mc.player.getInventory().selectedSlot = originalSlot;
            mc.player.networkHandler.sendPacket(new net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket(originalSlot));
        }
    }
}

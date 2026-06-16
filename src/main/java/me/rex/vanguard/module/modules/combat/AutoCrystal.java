package me.rex.vanguard.module.modules.combat;

import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.BindSetting;
import me.rex.vanguard.settings.BoolSetting;
import me.rex.vanguard.settings.EnumSetting;
import me.rex.vanguard.settings.NumberSetting;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import me.rex.vanguard.utils.RotationUtil;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.RaycastContext;

import java.util.ArrayList;
import java.util.List;

public class AutoCrystal extends Module {
    public NumberSetting placeDistance = new NumberSetting("Place Distance", 1, 8, 6, 0.5f);
    public NumberSetting breakDistance = new NumberSetting("Break Distance", 1, 8, 6, 0.5f);
    public NumberSetting breakDistanceWalls = new NumberSetting("Break Distance Through Walls", 1, 8, 6, 0.5f);
    public NumberSetting placeDistanceWalls = new NumberSetting("Place Distance Through Walls", 1, 8, 6, 0.5f);
    public NumberSetting minDmg = new NumberSetting("Min Damage", 0, 20, 8, 0.1f);
    public static EnumSetting renderType = new EnumSetting("Render Mode", renderMode.BOTH, renderMode.FILL, renderMode.OUTLINE, renderMode.BOTH);
    public BindSetting bind = new BindSetting(-1, "Keybind");

    private int breakTimer = 0;

    public AutoCrystal() {
        super("AutoCrystal", Category.COMBAT, -1);
        addSettings(placeDistance, breakDistance, breakDistanceWalls, placeDistanceWalls, minDmg, renderType, bind);
    }

    public enum renderMode {
        FILL,
        OUTLINE,
        BOTH
    }

    @Override
    public void onTick() {
        if (mc.player == null || mc.world == null || mc.interactionManager == null) return;

        boolean hasCrystal = mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL
                || mc.player.getOffHandStack().getItem() == Items.END_CRYSTAL;
        if (!hasCrystal) return;

        if (tryBreakCrystal()) return;

        if (tryPlaceCrystal()) return;
    }

    private boolean tryBreakCrystal() {
        if (breakTimer > 0) {
            breakTimer--;
            return false;
        }

        EndCrystalEntity bestCrystal = null;
        float bestDamage = 0;

        for (Entity entity : mc.world.getEntities()) {
            if (!(entity instanceof EndCrystalEntity crystal)) continue;

            double dist = mc.player.getPos().distanceTo(crystal.getPos());
            boolean canSee = mc.player.canSee(crystal);
            float maxDist = canSee ? breakDistance.getValueFloat() : breakDistanceWalls.getValueFloat();
            if (dist > maxDist) continue;

            float damage = getMaxDamageToPlayers(crystal.getPos());
            if (damage >= minDmg.getValueFloat() && damage > bestDamage) {
                bestDamage = damage;
                bestCrystal = crystal;
            }
        }

        if (bestCrystal != null) {
            breakCrystal(bestCrystal);
            breakTimer = 1;
            return true;
        }
        return false;
    }

    private void breakCrystal(EndCrystalEntity crystal) {
        Vec3d pos = crystal.getPos();
        float[] rotations = RotationUtil.getRotations(pos);
        float originalYaw = mc.player.getYaw();
        float originalPitch = mc.player.getPitch();

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], mc.player.isOnGround()));
        mc.interactionManager.attackEntity(mc.player, crystal);
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(originalYaw, originalPitch, mc.player.isOnGround()));
    }

    private boolean tryPlaceCrystal() {
        BlockPos bestPos = null;
        float bestDamage = 0;

        for (PlayerEntity target : mc.world.getPlayers()) {
            if (target == mc.player || !target.isAlive()) continue;
            if (mc.player.distanceTo(target) > placeDistance.getValueFloat() + 4) continue;

            BlockPos targetPos = target.getBlockPos();
            List<BlockPos> positions = findValidPositions(targetPos, (int) Math.ceil(placeDistance.getValueFloat()));

            for (BlockPos pos : positions) {
                Vec3d crystalPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                float damage = getMaxDamageToPlayers(crystalPos);

                if (damage >= minDmg.getValueFloat() && damage > bestDamage) {
                    bestDamage = damage;
                    bestPos = pos;
                }
            }
        }

        if (bestPos != null) {
            placeCrystal(bestPos);
            return true;
        }
        return false;
    }

    private void placeCrystal(BlockPos pos) {
        Hand hand = mc.player.getMainHandStack().getItem() == Items.END_CRYSTAL ? Hand.MAIN_HAND : Hand.OFF_HAND;

        Vec3d targetCenter = new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        float[] rotations = RotationUtil.getRotations(targetCenter);
        float originalYaw = mc.player.getYaw();
        float originalPitch = mc.player.getPitch();

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(rotations[0], rotations[1], mc.player.isOnGround()));

        Vec3d hitPos = new Vec3d(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
        BlockHitResult hit = new BlockHitResult(hitPos, Direction.UP, pos, false);
        mc.interactionManager.interactBlock(mc.player, hand, hit);

        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.LookAndOnGround(originalYaw, originalPitch, mc.player.isOnGround()));

        mc.player.swingHand(hand);
    }

    private List<BlockPos> findValidPositions(BlockPos center, int radius) {
        List<BlockPos> positions = new ArrayList<>();
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -1; y <= 1; y++) {
                    BlockPos pos = center.add(x, y, z);
                    if (center.getSquaredDistance(pos) > radius * radius) continue;
                    if (isValidCrystalPos(pos)) {
                        positions.add(pos);
                    }
                }
            }
        }
        return positions;
    }

    private boolean isValidCrystalPos(BlockPos pos) {
        if (!mc.world.getBlockState(pos).isOf(Blocks.OBSIDIAN)
                && !mc.world.getBlockState(pos).isOf(Blocks.BEDROCK)) return false;
        BlockPos above = pos.up();
        if (!mc.world.getBlockState(above).isAir()) return false;
        Box box = new Box(above.getX(), above.getY(), above.getZ(),
                above.getX() + 1, above.getY() + 2, above.getZ() + 1);
        return mc.world.getOtherEntities(null, box).isEmpty();
    }

    private float getMaxDamageToPlayers(Vec3d crystalPos) {
        float maxDmg = 0;
        for (PlayerEntity player : mc.world.getPlayers()) {
            if (player == mc.player || !player.isAlive()) continue;
            float dmg = calculateDamage(crystalPos, player);
            if (dmg > maxDmg) maxDmg = dmg;
        }
        return maxDmg;
    }

    private float calculateDamage(Vec3d crystalPos, PlayerEntity target) {
        float power = 6.0F;
        float f = power * 2.0F;

        double distance = Math.sqrt(target.getPos().distanceTo(crystalPos));
        double d = distance / f;
        if (d > 1.0) return 0;

        double exposure = getExposure(crystalPos, target);
        double e = (1.0 - d) * exposure;

        return (float) ((e * e + e) / 2.0 * 7.0 * f + 1.0);
    }

    private float getExposure(Vec3d source, Entity entity) {
        Box box = entity.getBoundingBox();
        double d = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
        double e = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
        double f = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
        double g = (1.0 - Math.floor(1.0 / d) * d) / 2.0;
        double h = (1.0 - Math.floor(1.0 / f) * f) / 2.0;

        if (d < 0.0 || e < 0.0 || f < 0.0) return 0;

        int i = 0;
        int j = 0;

        for (double k = 0.0; k <= 1.0; k += d) {
            for (double l = 0.0; l <= 1.0; l += e) {
                for (double m = 0.0; m <= 1.0; m += f) {
                    double n = MathHelper.lerp(k, box.minX, box.maxX);
                    double o = MathHelper.lerp(l, box.minY, box.maxY);
                    double p = MathHelper.lerp(m, box.minZ, box.maxZ);
                    Vec3d vec3d = new Vec3d(n + g, o, p + h);
                    if (mc.world.raycast(new RaycastContext(vec3d, source,
                            RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity))
                            .getType() == net.minecraft.util.hit.HitResult.Type.MISS) {
                        i++;
                    }
                    j++;
                }
            }
        }

        return (float) i / (float) j;
    }

}

package me.rex.vanguard.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import me.rex.vanguard.event.events.PacketEvent;
import me.rex.vanguard.event.events.SendPacketEvent;
import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.settings.EnumSetting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

import java.util.Timer;

public class Criticals extends Module {
    private enum Mode {
        PACKET,
        JUMP
    }

    private final EnumSetting mode = new EnumSetting("Mode", Mode.PACKET, Mode.PACKET, Mode.JUMP);

    public Criticals() {
        super("Criticals", Category.COMBAT, -1);
        addSettings(mode);
    }

    @Subscribe
    private void onPacketSend(SendPacketEvent event) {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket packet && packet.type.getType() == PlayerInteractEntityC2SPacket.InteractType.ATTACK && mode.getValue() == Mode.PACKET) {
            Entity entity = mc.world.getEntityById(packet.entityId);
            if (entity == null || entity instanceof EndCrystalEntity || !mc.player.isOnGround() || !(entity instanceof LivingEntity)) return;
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + (double) 0.1f, mc.player.getZ(), false));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
            mc.player.addCritParticles(entity);
        }
    }
}

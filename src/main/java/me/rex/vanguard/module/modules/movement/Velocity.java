package me.rex.vanguard.module.modules.movement;

import me.rex.vanguard.event.events.RecievePacketEvent;
import me.rex.vanguard.module.Category;
import me.rex.vanguard.module.Module;
import me.rex.vanguard.event.events.PacketEvent;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import com.google.common.eventbus.Subscribe;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", Category.MOVEMENT, -1);
    }

    @Subscribe
    private void onPacketReceive(RecievePacketEvent event) {
        if (event.getPacket() instanceof EntityVelocityUpdateS2CPacket || event.getPacket() instanceof ExplosionS2CPacket) {
            event.cancel();
        }
    }
}
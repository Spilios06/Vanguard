package me.rex.vanguard.event.events;

import net.minecraft.network.packet.Packet;

public class SendPacketEvent extends PacketEvent {
    public SendPacketEvent(Packet<?> packet) {
        super(packet);
    }
}

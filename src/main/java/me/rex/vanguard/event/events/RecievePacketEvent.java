package me.rex.vanguard.event.events;

import net.minecraft.network.packet.Packet;

public class RecievePacketEvent extends PacketEvent{
    public RecievePacketEvent(Packet<?> packet) {
        super(packet);
    }
}

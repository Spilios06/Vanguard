package me.rex.vanguard.event.events;

import me.rex.vanguard.event.Event;

import net.minecraft.network.packet.Packet;

public abstract class PacketEvent extends Event {

    private final Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return packet;
    }
}
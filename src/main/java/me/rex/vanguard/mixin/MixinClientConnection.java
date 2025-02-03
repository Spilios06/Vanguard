package me.rex.vanguard.mixin;

import com.google.common.eventbus.EventBus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import me.rex.vanguard.VanguardClient;
import me.rex.vanguard.event.events.PacketEvent;
import me.rex.vanguard.event.events.RecievePacketEvent;
import me.rex.vanguard.event.events.SendPacketEvent;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin( ClientConnection.class )
public class MixinClientConnection {
    @Unique
    private final EventBus EVENT_BUS = VanguardClient.eventManager.getEventBus();

    @Shadow private Channel channel;
    @Shadow @Final private NetworkSide side;

    @Inject(method = "channelRead0*", at = @At("HEAD"), cancellable = true)
    public void channelRead0(ChannelHandlerContext chc, Packet<?> packet, CallbackInfo ci) {
        if (this.channel.isOpen() && packet != null) {
            try {
                RecievePacketEvent event = new RecievePacketEvent(packet);
                EVENT_BUS.post(event);
                if (event.isCancelled()) ci.cancel();
            } catch (Exception ignored) {}
        }
    }

    @Inject(method = "sendImmediately", at = @At("HEAD"), cancellable = true)
    private void sendImmediately(Packet<?> packet, PacketCallbacks callbacks, boolean flush, CallbackInfo ci) {
        if (this.side != NetworkSide.CLIENTBOUND) return;
        try {
            SendPacketEvent event = new SendPacketEvent(packet);
            EVENT_BUS.post(event);
            if (event.isCancelled()) ci.cancel();
        } catch (Exception ignored) {}
    }
}
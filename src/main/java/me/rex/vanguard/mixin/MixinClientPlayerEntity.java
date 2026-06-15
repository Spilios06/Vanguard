package me.rex.vanguard.mixin;

import me.rex.vanguard.module.modules.misc.NoSlow;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"))
    private boolean noSlowIsUsingItem(ClientPlayerEntity instance) {
        if (NoSlow.INSTANCE != null && NoSlow.INSTANCE.enabled) {
            return false;
        }
        return ((ClientPlayerEntity)(Object)this).isUsingItem();
    }
}

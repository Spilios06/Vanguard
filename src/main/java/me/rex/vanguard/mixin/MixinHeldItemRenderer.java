package me.rex.vanguard.mixin;

import me.rex.vanguard.module.modules.render.ViewmodelChanger;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class MixinHeldItemRenderer {

    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void onRenderFirstPersonItemHead(
            AbstractClientPlayerEntity player,
            float tickDelta,
            float pitch,
            Hand hand,
            float swingProgress,
            ItemStack item,
            float equipProgress,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            CallbackInfo ci
    ) {
        if (player.isUsingSpyglass()) return;
        ViewmodelChanger vmc = ViewmodelChanger.INSTANCE;
        if (vmc == null || !vmc.enabled) return;
        matrices.push();
        matrices.translate(vmc.posX.getValueFloat(), vmc.posY.getValueFloat(), vmc.posZ.getValueFloat());
        float s = vmc.scale.getValueFloat();
        matrices.scale(s, s, s);
    }

    @Inject(method = "renderFirstPersonItem", at = @At("RETURN"))
    private void onRenderFirstPersonItemReturn(
            AbstractClientPlayerEntity player,
            float tickDelta,
            float pitch,
            Hand hand,
            float swingProgress,
            ItemStack item,
            float equipProgress,
            MatrixStack matrices,
            VertexConsumerProvider vertexConsumers,
            int light,
            CallbackInfo ci
    ) {
        if (player.isUsingSpyglass()) return;
        ViewmodelChanger vmc = ViewmodelChanger.INSTANCE;
        if (vmc == null || !vmc.enabled) return;
        matrices.pop();
    }
}

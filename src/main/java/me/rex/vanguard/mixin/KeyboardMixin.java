package me.rex.vanguard.mixin;

import me.rex.vanguard.VanguardClient;
import org.spongepowered.asm.mixin.Mixin;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeyboardMixin {
	@Inject(method = "onKey", at = @At("HEAD"))
	public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci){
		VanguardClient.INSTANCE.onKeyPress(key, action);
	}
}
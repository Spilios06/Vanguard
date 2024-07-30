package me.rex.vanguard;

import me.rex.vanguard.event.EventManager;
import me.rex.vanguard.module.ModuleManager;
import me.rex.vanguard.module.Module;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanguardClient implements ClientModInitializer, ModInitializer{
	public static final String MOD_ID = "Vanguard";
	public static final VanguardClient INSTANCE = new VanguardClient();
	public static final Logger logger = LoggerFactory.getLogger("vanguard");
	public MinecraftClient mc = MinecraftClient.getInstance();
	public static EventManager eventManager = new EventManager();

	@Override
	public void onInitialize() {

	}

	@Override
	public void onInitializeClient() {
		logger.info("Vanguard initialized");
		eventManager.init();
	}
	public void onKeyPress(int key, int action){
		if(action == GLFW.GLFW_PRESS) {
			for(Module module : ModuleManager.INSTANCE.modules){
				if(key == module.key){
					module.toggle();
				}
			}
		}
	}
	public void onTick(){
		if(mc.player != null){
			for(Module module : ModuleManager.INSTANCE.getEnabledModules()){
				module.onTick();
			}
		}
	}
}
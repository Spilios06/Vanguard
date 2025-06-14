package me.rex.vanguard;

import me.rex.vanguard.manager.ConfigManager;
import me.rex.vanguard.manager.EventManager;
import me.rex.vanguard.event.events.KeyPressEvent;
import me.rex.vanguard.manager.ModuleManager;
import me.rex.vanguard.module.Module;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanguardClient implements ClientModInitializer, ModInitializer{
	public static final VanguardClient INSTANCE = new VanguardClient();
	public static final Logger logger = LoggerFactory.getLogger("vanguard");
	public MinecraftClient mc = MinecraftClient.getInstance();
	public static EventManager eventManager = EventManager.INSTANCE;
	public static ConfigManager configManager = ConfigManager.INSTANCE;

	@Override
	public void onInitialize() {
		configManager.load();
		logger.info("Config Loaded");
		Runtime.getRuntime().addShutdownHook(new Thread(() -> configManager.save()));
	}

	@Override
	public void onInitializeClient() {
		logger.info("Vanguard initialized");
		eventManager.init();
	}

	public void onKeyPress(int key, int action){
		if(mc.player == null) return;
		if(action == GLFW.GLFW_PRESS) EventManager.INSTANCE.getEventBus().post(new KeyPressEvent(key));
	}

	public void onTick(){
		if(mc.player != null){
			for(Module module : ModuleManager.INSTANCE.getEnabledModules()){
				module.onTick();
			}
		}
	}
}
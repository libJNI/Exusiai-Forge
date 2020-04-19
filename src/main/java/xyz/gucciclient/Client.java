package xyz.gucciclient;

import java.util.Iterator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import org.lwjgl.input.Keyboard;
import xyz.gucciclient.gui.ClickGUI;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.modules.ModuleManager;
import xyz.gucciclient.utils.Wrapper;

@Mod(modid = Client.MODID, version = Client.VERSION)
public class Client {
	public static final String MODID = "betterfps";
	public static final String VERSION = "0.1";
	private static ClickGUI clickGUI;
	public static Client INSTANCE;

	public Client() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLCommonHandler.instance().bus().register(this);
		clickGUI = new ClickGUI();
	}

	public static Client getGucci() {
		return INSTANCE;
	}

	public void zibi() {
		MinecraftForge.EVENT_BUS.unregister(this);
		FMLCommonHandler.instance().bus().unregister(this);
	}

	@SubscribeEvent
	public void keyInput(KeyInputEvent event) {
		if (Wrapper.getPlayer() != null) {
			if (!Keyboard.getEventKeyState()) {
				return;
			}

			Iterator var2 = ModuleManager.getModules().iterator();

			while(var2.hasNext()) {
				Module mod = (Module)var2.next();
				if (mod.getKey() == Keyboard.getEventKey()) {
					mod.setState(!mod.getState());
				}
			}
		}

	}

	public static ClickGUI getClickGUI() {
		return clickGUI;
	}
}

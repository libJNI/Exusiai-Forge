package today.exusiai;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.util.Iterator;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import scala.reflect.internal.Trees.New;
import today.exusiai.event.EventSystem;
import today.exusiai.event.events.EventAttack;
import today.exusiai.event.events.EventChat;
import today.exusiai.event.events.EventRender3D;
import today.exusiai.event.events.EventRenderGui;
import today.exusiai.event.events.EventTick;
import today.exusiai.event.events.EventUpdate;
import today.exusiai.gui.ClickGUI;
import today.exusiai.guihook.PacketHandler;
import today.exusiai.guihook.impl.ModifyGuiIngameMenu;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.ModuleManager;
import today.exusiai.notifications.Notifications;
import today.exusiai.utils.TrayHelper;
import today.exusiai.utils.Wrapper;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import io.netty.channel.ChannelHandler;

public class Client {
	
	public static final Client INSTANCE = new Client();
	private static ClickGUI clickGUI;
	public static boolean clientDestruct = false;
	public static boolean initialization = false;
	public Client() {
		if(clickGUI == null) {
			clickGUI = new ClickGUI();
			//ResourceLoader.init();
			TrayHelper.displayTray();
			MinecraftForge.EVENT_BUS.register(this);
			FMLCommonHandler.instance().bus().register(this);
			initialization = true;
			System.out.println("Client Initialization");
		}
	}
	
	public static Client getClient() {
		return INSTANCE;
	}

	public static ClickGUI getClickGUI() {
		return clickGUI;
	}
	
	public void selfDestruct() {
		if (Wrapper.getPlayer() != null) {
	 		clientDestruct = true;
	         if (Wrapper.getMinecraft().currentScreen == Client.getClickGUI()) {
	            Wrapper.getMinecraft().displayGuiScreen((GuiScreen) null);
	         }
	         for (AbstractModule mod : ModuleManager.getModules()) {
	            if (mod == null || !mod.getState()) continue;
	            mod.setName("");
	            mod.setState(false);
	            mod = null;
	            EventSystem.unregister(mod);
	         }
	         //ModuleManager.getModules().clear();
	         //ModuleManager.getModulesSorted().clear();
	 		MinecraftForge.EVENT_BUS.unregister(this);
	 		FMLCommonHandler.instance().bus().unregister(this);
	         Wrapper.getNetManager().channel().pipeline().remove("QuicklyClientByMargele");
	         System.gc();
	         System.runFinalization();
	      }
	}
	
	@SubscribeEvent
	public void onAttack(AttackEntityEvent event) {
		EventSystem.call(new EventAttack(event.entityLiving));
	}
	
	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		EventSystem.call(new EventChat(event.message.getUnformattedText()));
	}
	
	@SubscribeEvent
	public void onGui(GuiOpenEvent event) {		
		try {			
			if((event != null && event.gui != null && event.gui.getClass() != null && event.gui.getClass().getName() != null) && event.gui instanceof GuiIngameMenu) {
		        if(!Client.clientDestruct) {
					event.gui = new ModifyGuiIngameMenu();
				} else {
					event.gui = new GuiIngameMenu();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public void onRender2D(RenderGameOverlayEvent event) {
		if(event.type == RenderGameOverlayEvent.ElementType.TEXT) {
	        EventSystem.call(new EventRenderGui());
	        Notifications.getManager().updateAndRender();
		}
	}
	
	@SubscribeEvent
	public void onUpdate(LivingEvent.LivingUpdateEvent event) {
		if(event.entityLiving == null) {
			return;
		}
		if(event.entityLiving instanceof EntityPlayerSP) {
			EntityPlayerSP p = (EntityPlayerSP) event.entityLiving;
	        EventSystem.call(new EventUpdate());
		}
	}
	
	@SubscribeEvent
	public void onNetHandlerHookTick(ClientTickEvent event) {
        if (Wrapper.getMinecraft().getNetHandler() != null && Wrapper.getMinecraft().getNetHandler().getNetworkManager() != null && Wrapper.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().get("QuicklyClientByMargele") == null) {
        	Wrapper.getMinecraft().getNetHandler().getNetworkManager().channel().pipeline().addBefore("packet_handler", "QuicklyClientByMargele", (ChannelHandler)new PacketHandler(Wrapper.getMinecraft().getNetHandler().getNetworkManager()));
        }
	}
	
	@SubscribeEvent
	public void onRender3D(RenderWorldLastEvent event) {
        EventSystem.call(new EventRender3D(event.partialTicks));
	}
	
	@SubscribeEvent
	public void onTick(ClientTickEvent event) {
        EventSystem.call(new EventTick());
	}
	
	@SubscribeEvent
	public void keyInput(KeyInputEvent event) {
		if (Wrapper.getPlayer() != null) {
			if (!Keyboard.getEventKeyState()) {
				return;
			}

			Iterator var2 = ModuleManager.getModules().iterator();

			while(var2.hasNext()) {
				AbstractModule mod = (AbstractModule)var2.next();
				if (mod.getKey() == Keyboard.getEventKey() && Keyboard.getEventKey() != 0) {
					mod.setState(!mod.getState());
				}
			}
		}
	}
}

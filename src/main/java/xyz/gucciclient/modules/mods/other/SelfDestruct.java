package xyz.gucciclient.modules.mods.other;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.Client;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;

public class SelfDestruct extends Module {
   public SelfDestruct() {
      super("SelfDestruct", 0, Module.Category.Other);
   }
   
   public void onEnable() {
	   Client.INSTANCE.zibi();
	   System.gc();
   }
}

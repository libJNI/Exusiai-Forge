package xyz.gucciclient.modules.mods.other;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;

public class FastBridge extends Module {
   public FastBridge() {
      super(Module.Modules.FastPlace.name(), 0, Module.Category.Other);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) throws Exception, Throwable {
      Field f = Minecraft.class.getDeclaredField("field_71467_ac");
      if (f != null && f.getName().equalsIgnoreCase("field_71467_ac")) {
         f.setAccessible(true);
         f.set(Wrapper.getMinecraft(), 0);
      }
   }
}

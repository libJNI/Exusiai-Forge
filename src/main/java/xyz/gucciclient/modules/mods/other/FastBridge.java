package xyz.gucciclient.modules.mods.other;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.ReflectionHelper;
import xyz.gucciclient.utils.Wrapper;

public class FastBridge extends Module {
   public FastBridge() {
      super(Module.Modules.FastPlace.name(), 0, Module.Category.Other);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) throws Exception, Throwable {
      Field field = ReflectionHelper.getField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac", "E");
      if(field != null) {
         field.set(Wrapper.getMinecraft(), 0);
      }
   }
}

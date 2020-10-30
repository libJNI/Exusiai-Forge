package today.exusiai.modules.collection.other;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.ReflectionHelper;
import today.exusiai.utils.Wrapper;

public class FastBridge extends AbstractModule {
   public FastBridge() {
      super("FastPlace", 0, AbstractModule.Category.Other);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) throws Exception, Throwable {
      Field field = ReflectionHelper.getField(Minecraft.class, "rightClickDelayTimer", "field_71467_ac", "E");
      if(field != null) {
         field.set(Wrapper.getMinecraft(), 0);
      }
   }
}

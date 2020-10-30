package today.exusiai.modules.collection.other;

import java.lang.reflect.Field;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.ReflectionHelper;
import today.exusiai.utils.Wrapper;

public class FastBreeeak extends AbstractModule {
   public FastBreeeak() {
      super("FastBreak", 0, AbstractModule.Category.Other);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      try {
      Field field = ReflectionHelper.getField(PlayerControllerMP.class, "blockHitDelay", "field_78781_i", "E");
      if(field != null) {
         field.set(Wrapper.getPlayerController(), 0);
      }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      this.mc.thePlayer.addPotionEffect(new PotionEffect(3, 9999, 1));
   }

   @Override
   public void onDisable() {
      this.mc.thePlayer.removePotionEffect(3);
   }
}

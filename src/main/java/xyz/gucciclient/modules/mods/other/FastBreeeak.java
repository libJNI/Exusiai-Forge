package xyz.gucciclient.modules.mods.other;

import java.lang.reflect.Field;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;

public class FastBreeeak extends Module {
   public FastBreeeak() {
      super("FastBreak", 0, Module.Category.Other);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) throws Exception, Throwable {
      try {
         Field f = PlayerControllerMP.class.getDeclaredField("field_78781_i");
         if (f == null || !f.getName().equalsIgnoreCase("field_78781_i")) {
            return;
         }

         f.setAccessible(true);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      this.mc.thePlayer.addPotionEffect(new PotionEffect(3, 9999, 1));
   }

   public void onDisable() {
      this.mc.thePlayer.removePotionEffect(3);
   }
}

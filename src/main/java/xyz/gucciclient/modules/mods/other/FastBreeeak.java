package xyz.gucciclient.modules.mods.other;

import java.lang.reflect.Field;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.ReflectionHelper;
import xyz.gucciclient.utils.Wrapper;

public class FastBreeeak extends Module {
   public FastBreeeak() {
      super("FastBreak", 0, Module.Category.Other);
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

   public void onDisable() {
      this.mc.thePlayer.removePotionEffect(3);
   }

}

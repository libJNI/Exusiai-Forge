package xyz.gucciclient.modules.mods.combat;

import java.util.Iterator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.values.BooleanValue;

public class Antibot extends Module {
   private BooleanValue hypixel = new BooleanValue("Hypixel", false);
   private BooleanValue mineplex = new BooleanValue("Mineplex", false);

   public Antibot() {
      super("AntiBot", 0, Module.Category.Combat);
      this.addBoolean(this.hypixel);
      this.addBoolean(this.mineplex);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent ev3nt) {
      Iterator var2;
      Object object;
      EntityPlayer entityPlayer;
      if (this.hypixel.getState()) {
         var2 = this.mc.theWorld.playerEntities.iterator();

         while(var2.hasNext()) {
            object = var2.next();
            entityPlayer = (EntityPlayer)object;
            if (entityPlayer != null && entityPlayer != this.mc.thePlayer && entityPlayer.getDisplayName().getFormattedText().equalsIgnoreCase(entityPlayer.getName() + "Â§r") && !this.mc.thePlayer.getDisplayName().getFormattedText().equalsIgnoreCase(this.mc.thePlayer.getName() + "¡ìr")) {
               this.mc.theWorld.removeEntity(entityPlayer);
            }
         }
      }

      if (this.mineplex.getState()) {
         var2 = this.mc.theWorld.playerEntities.iterator();

         while(var2.hasNext()) {
            object = var2.next();
            entityPlayer = (EntityPlayer)object;
            if (entityPlayer != null && entityPlayer != this.mc.thePlayer) {
               if (entityPlayer.getName().startsWith("Body #")) {
                  this.mc.theWorld.removeEntity(entityPlayer);
               }

               if (entityPlayer.getMaxHealth() == 20.0F) {
                  this.mc.theWorld.removeEntity(entityPlayer);
               }
            }
         }
      }

   }
}

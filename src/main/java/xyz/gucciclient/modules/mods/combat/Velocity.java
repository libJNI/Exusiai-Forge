package xyz.gucciclient.modules.mods.combat;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.NumberValue;

public class Velocity extends Module {
   private NumberValue HorizontalModifier = new NumberValue("Horizontal", 100.0D, 0.0D, 100.0D);
   private NumberValue VerticalModifier = new NumberValue("Vertical", 100.0D, 0.0D, 100.0D);

   public Velocity() {
      super(Module.Modules.Velocity.name(), 0, Module.Category.Combat);
      this.addValue(this.HorizontalModifier);
      this.addValue(this.VerticalModifier);
   }

   @SubscribeEvent
   public void onTick(TickEvent event) {
      if (Wrapper.getPlayer() != null) {
         if (Wrapper.getWorld() != null) {
            double vertmodifier = this.VerticalModifier.getValue() / 100.0D;
            double horzmodifier = this.HorizontalModifier.getValue() / 100.0D;
            if (Wrapper.getPlayer().hurtTime == Wrapper.getPlayer().maxHurtTime && Wrapper.getPlayer().maxHurtTime > 0) {
               EntityPlayerSP player = Wrapper.getPlayer();
               player.motionX *= horzmodifier;
               EntityPlayerSP player2 = Wrapper.getPlayer();
               player2.motionZ *= horzmodifier;
               EntityPlayerSP player3 = Wrapper.getPlayer();
               player3.motionY *= vertmodifier;
            }

         }
      }
   }
}

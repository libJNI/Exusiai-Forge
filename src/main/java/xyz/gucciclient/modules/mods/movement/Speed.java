package xyz.gucciclient.modules.mods.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.MovementUtils;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Speed extends Module {
   private NumberValue vanillaspeed = new NumberValue("Vanilla speed", 1.0D, 0.0D, 100.0D);

   public Speed() {
      super(Module.Modules.Speed.name(), 0, Module.Category.Movement);
      this.addValue(this.vanillaspeed);
   }

   public void onDisable() {
      MovementUtils.setSpeed(1.0D);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (this.getState() && this.mc.thePlayer.onGround) {
         if (!(this.mc.thePlayer.isSneaking() || this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f)) {
            float f;
            double d;
            if (this.mc.thePlayer.moveForward > 0.0f && !this.mc.thePlayer.isCollidedHorizontally) {
               this.mc.thePlayer.setSprinting(true);
            }
            this.mc.thePlayer.motionX *= 5.0;
            this.mc.thePlayer.motionZ *= 5.0;
            double d2 = Math.sqrt(Math.pow(this.mc.thePlayer.motionX, 2.0) + Math.pow(this.mc.thePlayer.motionZ, 2.0));
            if (d2 > (d = (f = (float) vanillaspeed.getValue()) / 5.0 * 0.8)) {
               this.mc.thePlayer.motionX = this.mc.thePlayer.motionX / d2 * d;
               this.mc.thePlayer.motionZ = this.mc.thePlayer.motionZ / d2 * d;
            }
         } else {
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
         }
      }
   }
}

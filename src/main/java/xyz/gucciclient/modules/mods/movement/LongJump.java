package xyz.gucciclient.modules.mods.movement;

import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.MovementUtils;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class LongJump extends Module {
   private NumberValue timerspeed = new NumberValue("Speed", 1.0D, 1.0D, 100.0D);
   public LongJump() {
      super("LongJump" , 0, Category.Movement);
      this.addValue(this.timerspeed);
   }

   public void onDisable() {
      MovementUtils.setSpeed(1.0D);
   }

   public float getDirection() {
      float f = this.mc.thePlayer.rotationYaw;
      float f2 = this.mc.thePlayer.moveForward;
      float f3 = this.mc.thePlayer.moveStrafing;
      f += (f2 < 0.0f ? 180 : 0);
      if (f3 < 0.0f) {
         f += (f2 < 0.0f ? -45 : (f2 == 0.0f ? 90 : 45));
      }
      if (f3 > 0.0f) {
         f -= (f2 < 0.0f ? -45 : (f2 == 0.0f ? 90 : 45));
      }
      return f * 0.017453292f;
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (this.getState() && this.mc.gameSettings.keyBindJump.isKeyDown()) {
         float f = (float) timerspeed.getValue();
         f = 100.0f - f + 1.0f;
         this.mc.thePlayer.setSprinting(true);
         double d = Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ + 10.0 / (10.0 * f));
         this.mc.thePlayer.motionX = (-MathHelper.sin(this.getDirection())) * d;
         this.mc.thePlayer.motionZ = MathHelper.cos(this.getDirection()) * d;
         if (this.mc.thePlayer.onGround) {
            this.mc.thePlayer.jump();
            this.mc.thePlayer.motionY *= 0.94356256;
         } else if (this.mc.thePlayer.isAirBorne && !this.mc.thePlayer.onGround) {
            double d2 = Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ + 10.0 / (10.0 * f)) + (f / (f * 1000.0f));
            this.mc.thePlayer.motionX = (-MathHelper.sin(this.getDirection())) * d2;
            this.mc.thePlayer.motionZ = MathHelper.cos(this.getDirection()) * d2;
         }
      }
   }
}

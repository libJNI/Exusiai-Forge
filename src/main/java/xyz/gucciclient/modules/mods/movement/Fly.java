package xyz.gucciclient.modules.mods.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.values.NumberValue;

public class Fly extends Module {
   private double maxPosY = 0.0D;
   private NumberValue speed = new NumberValue("Fly speed", 0.5D, 0.1D, 2.0D);
   public double x;
   public double y;
   public double z;

   public Fly() {
      super("Fly", 0, Module.Category.Movement);
      this.addValue(this.speed);
   }

   public void EventMove(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public double getMotionX() {
      return this.x;
   }

   public double getMotionY() {
      return this.y;
   }

   public double getMotionZ() {
      return this.z;
   }

   public void setMotionX(double motionX) {
      this.x = motionX;
   }

   public void setMotionY(double motionY) {
      this.y = motionY;
   }

   public void setMotionZ(double motionZ) {
      this.z = motionZ;
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
      double forward = (double)this.mc.thePlayer.movementInput.moveForward;
      double strafe = (double)this.mc.thePlayer.movementInput.moveStrafe;
      if (this.mc.thePlayer.isSneaking()) {
         this.y = this.mc.thePlayer.motionY = -0.4D;
      } else if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
         this.y = this.mc.thePlayer.motionY = 0.4D;
      } else {
         this.y = this.mc.thePlayer.motionY = 0.0D;
      }

      float yaw = this.mc.thePlayer.rotationYaw;
      if (forward == 0.0D && strafe == 0.0D) {
         this.x = 0.0D;
         if (this.mc.thePlayer.isSneaking()) {
            this.y = this.mc.thePlayer.motionY = -0.4D;
         } else if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
            this.y = this.mc.thePlayer.motionY = 0.4D;
         } else {
            this.y = this.mc.thePlayer.motionY = 0.0D;
         }

         this.z = 0.0D;
      } else {
         if (forward != 0.0D) {
            if (strafe > 0.0D) {
               yaw += (float)(forward > 0.0D ? -45 : 45);
            } else if (strafe < 0.0D) {
               yaw += (float)(forward > 0.0D ? 45 : -45);
            }

            strafe = 0.0D;
            if (forward > 0.0D) {
               forward = 1.0D;
            } else if (forward < 0.0D) {
               forward = -1.0D;
            }
         }

         this.x = forward * this.speed.getValue() * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * this.speed.getValue() * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         this.z = forward * this.speed.getValue() * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * this.speed.getValue() * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      }

   }

   public void onEnable() {
      super.onEnable();
      this.maxPosY = this.mc.thePlayer.posY;
   }

   public void onDisable() {
      super.onDisable();
      this.mc.thePlayer.capabilities.isFlying = false;
   }
}

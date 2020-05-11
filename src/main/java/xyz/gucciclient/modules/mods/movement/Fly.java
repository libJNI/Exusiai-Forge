package xyz.gucciclient.modules.mods.movement;

import net.minecraft.potion.Potion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.NumberValue;

public class Fly extends Module {
   private NumberValue flyspeed = new NumberValue("Fly speed", 1.0D, 0.1D, 10.0D);

   public Fly() {
      super("Fly", 0, Module.Category.Movement);
      this.addValue(this.flyspeed);
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
      if (this.getState()) {
         double speed = Math.max(flyspeed.getValue(), getBaseMoveSpeed());
         double speed2 = flyspeed.getValue();
         if (mc.thePlayer.movementInput.jump) {
            mc.thePlayer.motionY = speed * 0.6;
         } else if (mc.thePlayer.movementInput.sneak) {
            mc.thePlayer.motionY = -speed * 0.6;
         } else {
            mc.thePlayer.motionY = 0;
         }

         double forward = mc.thePlayer.movementInput.moveForward;
         double strafe = mc.thePlayer.movementInput.moveStrafe;
         float yaw = mc.thePlayer.rotationYaw;
         if ((forward == 0.0D) && (strafe == 0.0D)) {
            Wrapper.getPlayer().motionX = 0.0D;
            Wrapper.getPlayer().motionZ = 0.0D;
         } else {
            if (forward != 0.0D) {
               if (strafe > 0.0D) {
                  yaw += (forward > 0.0D ? -45 : 45);
               } else if (strafe < 0.0D) {
                  yaw += (forward > 0.0D ? 45 : -45);
               }
               strafe = 0.0D;
               if (forward > 0.0D) {
                  forward = 1;
               } else if (forward < 0.0D) {
                  forward = -1;
               }
            }
            Wrapper.getPlayer().motionX = forward * speed2 * Math.cos(Math.toRadians(yaw + 90.0F))
                    + strafe * speed2 * Math.sin(Math.toRadians(yaw + 90.0F));
            Wrapper.getPlayer().motionZ = forward * speed2 * Math.sin(Math.toRadians(yaw + 90.0F))
                    - strafe * speed2 * Math.cos(Math.toRadians(yaw + 90.0F));
         }
      }
   }

   public void onEnable() {
      Wrapper.getPlayer().motionY += 0.42;
      super.onEnable();
   }

   public void onDisable() {
      setMotion(0.2);
      this.mc.thePlayer.capabilities.allowFlying = false;
      this.mc.thePlayer.capabilities.isFlying = false;
      super.onDisable();
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      if (Wrapper.getPlayer().isPotionActive(Potion.moveSpeed)) {
         int amplifier = Wrapper.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
      }
      return baseSpeed;
   }

   public static void setMotion(double speed) {
      double forward = Wrapper.getPlayer().movementInput.moveForward;
      double strafe = Wrapper.getPlayer().movementInput.moveStrafe;
      float yaw = Wrapper.getPlayer().rotationYaw;
      if ((forward == 0.0D) && (strafe == 0.0D)) {
         Wrapper.getPlayer().motionX = 0;
         Wrapper.getPlayer().motionZ = 0;
      } else {
         if (forward != 0.0D) {
            if (strafe > 0.0D) {
               yaw += (forward > 0.0D ? -45 : 45);
            } else if (strafe < 0.0D) {
               yaw += (forward > 0.0D ? 45 : -45);
            }
            strafe = 0.0D;
            if (forward > 0.0D) {
               forward = 1;
            } else if (forward < 0.0D) {
               forward = -1;
            }
         }
         Wrapper.getPlayer().motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F));
         Wrapper.getPlayer().motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F));
      }
   }
}

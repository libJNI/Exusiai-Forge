package today.exusiai.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class MovementUtils {
	   
   public static boolean isMoving() {
      return Wrapper.getPlayer().moveForward != 0.0F || Wrapper.getPlayer().moveStrafing != 0.0F;
   }

   public static float getSpeed() {
      float speed = (float)Math.sqrt(Wrapper.getPlayer().motionX * Wrapper.getPlayer().motionX + Wrapper.getPlayer().motionZ * Wrapper.getPlayer().motionZ);
      return speed;
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

   /*public static void setSpeed(double speed) {
      Wrapper.getPlayer().motionX = -(Math.sin((double)getDirection()) * speed);
      Wrapper.getPlayer().motionZ = Math.cos((double)getDirection()) * speed;
   }*/

   public static float getDirection() {
      float var1 = Wrapper.getPlayer().rotationYaw;
      if (Wrapper.getPlayer().moveForward < 0.0F) {
         var1 += 180.0F;
      }

      float forward = 1.0F;
      if (Wrapper.getPlayer().moveForward < 0.0F) {
         forward = -0.5F;
      } else if (Wrapper.getPlayer().moveForward > 0.0F) {
         forward = 0.5F;
      } else {
         forward = 1.0F;
      }

      if (Wrapper.getPlayer().moveStrafing > 0.0F) {
         var1 -= 90.0F * forward;
      }

      if (Wrapper.getPlayer().moveStrafing < 0.0F) {
         var1 += 90.0F * forward;
      }

      var1 *= 0.017453292F;
      return var1;
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2875D;
      if (Wrapper.getPlayer().isPotionActive(Potion.moveSpeed)) {
         baseSpeed *= 1.0D + 0.2D * (double)(Wrapper.getPlayer().getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
      }

      return baseSpeed;
   }

   public static void setSpeed(double moveSpeed) {
	      setSpeed(moveSpeed, Wrapper.getPlayer().rotationYaw, (double)Wrapper.getPlayer().movementInput.moveStrafe, (double)Wrapper.getPlayer().movementInput.moveForward);
	   }

	   public static void setSpeed(double moveSpeed, float pseudoYaw, double pseudoStrafe, double pseudoForward) {
	      double forward = pseudoForward;
	      double strafe = pseudoStrafe;
	      float yaw = pseudoYaw;
	      if (pseudoForward != 0.0D) {
	         if (pseudoStrafe > 0.0D) {
	            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? -45 : 45);
	         } else if (pseudoStrafe < 0.0D) {
	            yaw = pseudoYaw + (float)(pseudoForward > 0.0D ? 45 : -45);
	         }

	         strafe = 0.0D;
	         if (pseudoForward > 0.0D) {
	            forward = 1.0D;
	         } else if (pseudoForward < 0.0D) {
	            forward = -1.0D;
	         }
	      }

	      if (strafe > 0.0D) {
	         strafe = 1.0D;
	      } else if (strafe < 0.0D) {
	         strafe = -1.0D;
	      }

	      double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
	      double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
	      Wrapper.getPlayer().motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
	      Wrapper.getPlayer().motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
	   }
	   
   public static double getJumpBoostModifier(double baseJumpHeight) {
	      if (Wrapper.getPlayer().isPotionActive(Potion.jump)) {
	         int amplifier = Wrapper.getPlayer().getActivePotionEffect(Potion.jump).getAmplifier();
	         baseJumpHeight += (double)((float)(amplifier + 1) * 0.1F);
	      }

	      return baseJumpHeight;
	   }
}

package xyz.gucciclient.utils;

public class MovementUtils {
   public static boolean isMoving() {
      return Wrapper.getPlayer().moveForward != 0.0F || Wrapper.getPlayer().moveStrafing != 0.0F;
   }

   public static float getSpeed() {
      float speed = (float)Math.sqrt(Wrapper.getPlayer().motionX * Wrapper.getPlayer().motionX + Wrapper.getPlayer().motionZ * Wrapper.getPlayer().motionZ);
      return speed;
   }

   public static void setSpeed(double speed) {
      Wrapper.getPlayer().motionX = -(Math.sin((double)getDirection()) * speed);
      Wrapper.getPlayer().motionZ = Math.cos((double)getDirection()) * speed;
   }

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
}

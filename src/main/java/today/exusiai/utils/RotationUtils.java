package today.exusiai.utils;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;

public class RotationUtils {

   public static float[] getRotations(BlockPos block, EnumFacing face) {
      double x = block.getX() + 0.5 - Minecraft.getMinecraft().thePlayer.posX +  (double) face.getFrontOffsetX()/2;
      double z = block.getZ() + 0.5 - Minecraft.getMinecraft().thePlayer.posZ +  (double) face.getFrontOffsetZ()/2;
      double y = (block.getY() + 0.5);
      double d1 = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - y;
      double d3 = MathHelper.sqrt_double(x * x + z * z);
      float yaw = (float) (Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
      float pitch = (float) (Math.atan2(d1, d3) * 180.0D / Math.PI);
      if (yaw < 0.0F) {
         yaw += 360f;
      }
      return new float[]{yaw, pitch};
   }

   public static void jitter(Random rand) {
      EntityPlayerSP player;
      if (rand.nextBoolean()) {
         if (rand.nextBoolean()) {
            player = Wrapper.getPlayer();
            player.rotationPitch -= (float)((double)rand.nextFloat() * 0.5D);
         } else {
            player = Wrapper.getPlayer();
            player.rotationPitch += (float)((double)rand.nextFloat() * 0.5D);
         }
      } else if (rand.nextBoolean()) {
         player = Wrapper.getPlayer();
         player.rotationYaw -= (float)((double)rand.nextFloat() * 0.5D);
      } else {
         player = Wrapper.getPlayer();
         player.rotationYaw += (float)((double)rand.nextFloat() * 0.5D);
      }

   }

   public static float getYawChange(double posX, double posZ) {
      double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
      double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
      double yawToEntity;
      if ((deltaZ < 0.0D) && (deltaX < 0.0D)) {
         yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else if ((deltaZ < 0.0D) && (deltaX > 0.0D)) {
         yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else {
         yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
      }
      return MathHelper.wrapAngleTo180_float(-(Minecraft.getMinecraft().thePlayer.rotationYaw - (float) yawToEntity));
   }

   public static float getPitchChange(Entity entity, double posY) {
      double deltaX = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
      double deltaZ = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
      double deltaY = posY - 2.2D + entity.getEyeHeight() - Minecraft.getMinecraft().thePlayer.posY;
      double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
      double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
      return -MathHelper.wrapAngleTo180_float(Minecraft.getMinecraft().thePlayer.rotationPitch - (float) pitchToEntity) - 2.5F;
   }
}

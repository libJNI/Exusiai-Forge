package xyz.gucciclient.utils;

import java.util.Random;
import net.minecraft.client.entity.EntityPlayerSP;

public class RotationUtils {
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
}

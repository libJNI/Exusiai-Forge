package today.exusiai.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import today.exusiai.utils.entity.ICheck;

public final class ConstantDistanceCheck implements ICheck {
   private final float distance;

   public ConstantDistanceCheck(float distance) {
      this.distance = distance;
   }

   @Override
   public boolean validate(Entity entity) {
      return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= this.distance;
   }
}

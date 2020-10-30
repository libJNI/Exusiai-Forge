package today.exusiai.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import today.exusiai.utils.entity.ICheck;
import today.exusiai.values.NumberValue;

public final class DistanceCheck implements ICheck {
   private final NumberValue distance;

   public DistanceCheck(NumberValue distance) {
      this.distance = distance;
   }

   @Override
   public boolean validate(Entity entity) {
      return (double)Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity) <= (Double)this.distance.getValue();
   }
}

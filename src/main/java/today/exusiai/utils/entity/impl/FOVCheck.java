package today.exusiai.utils.entity.impl;

import net.minecraft.entity.Entity;
import today.exusiai.utils.RotationUtils;
import today.exusiai.utils.entity.ICheck;
import today.exusiai.values.NumberValue;

public final class FOVCheck implements ICheck {
   private final NumberValue fov;

   public FOVCheck(NumberValue distance) {
      this.fov = distance;
   }

   @Override
   public boolean validate(Entity entity) {
      return RotationUtils.getYawChange(entity.posX, entity.posZ) <= fov.getValue() && RotationUtils.getPitchChange(entity, entity.posY) <= fov.getValue();
   }
}

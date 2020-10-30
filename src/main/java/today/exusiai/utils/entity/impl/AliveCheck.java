package today.exusiai.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import today.exusiai.utils.entity.ICheck;

public final class AliveCheck implements ICheck {

   @Override
   public boolean validate(Entity entity) {
      return entity.isEntityAlive();
   }
}

package today.exusiai.utils.entity.impl;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import today.exusiai.utils.entity.ICheck;
import today.exusiai.values.BooleanValue;

public final class WallCheck implements ICheck {
	
	   private final BooleanValue wall;

	   public WallCheck(BooleanValue wall) {
	      this.wall = wall;
	   }

	   @Override
   public boolean validate(Entity entity) {
      return Minecraft.getMinecraft().thePlayer.canEntityBeSeen(entity) || this.wall.getState();
   }
   
}

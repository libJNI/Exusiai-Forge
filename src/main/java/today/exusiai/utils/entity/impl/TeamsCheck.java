package today.exusiai.utils.entity.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import today.exusiai.utils.PlayerUtils;
import today.exusiai.utils.entity.ICheck;
import today.exusiai.values.BooleanValue;

public final class TeamsCheck implements ICheck {
   private final BooleanValue teams;

   public TeamsCheck(BooleanValue teams) {
      this.teams = teams;
   }

   @Override
   public boolean validate(Entity entity) {
      return !(entity instanceof EntityPlayer) || !PlayerUtils.isOnSameTeam((EntityPlayer)entity) || !this.teams.getState();
   }
}

package today.exusiai.utils.entity.impl;

import java.util.function.Supplier;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import today.exusiai.utils.entity.ICheck;
import today.exusiai.values.BooleanValue;

public final class EntityCheck implements ICheck {
   private final BooleanValue players;
   private final BooleanValue animals;
   private final BooleanValue monsters;
   private final BooleanValue invisibles;

   public EntityCheck(BooleanValue players, BooleanValue animals, BooleanValue monsters, BooleanValue invisibles) {
      this.players = players;
      this.animals = animals;
      this.monsters = monsters;
      this.invisibles = invisibles;
   }

   @Override
   public boolean validate(Entity entity) {
      if (entity instanceof EntityPlayerSP) {
         return false;
      } else if (!this.invisibles.getState() && entity.isInvisible()) {
         return false;
      } else if (this.animals.getState() && entity instanceof EntityAnimal) {
         return true;
      } else if (this.players.getState() && entity instanceof EntityPlayer) {
         return true;
      } else {
         return this.monsters.getState() && (entity instanceof EntityMob || entity instanceof EntitySlime || entity instanceof EntityDragon || entity instanceof EntityGolem);
      }
   }
}

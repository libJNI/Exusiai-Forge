package today.exusiai.modules.collection.combat;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import today.exusiai.event.ActiveEvent;
import today.exusiai.event.events.EventAttack;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.Wrapper;
import today.exusiai.values.BooleanValue;
import today.exusiai.values.NumberValue;

public class Reach extends AbstractModule {
   private NumberValue MIN_RANGE = new NumberValue("Min range", 3.2D, 3.0D, 6.0D);
   private NumberValue MAX_RANGE = new NumberValue("Max range", 3.3D, 3.0D, 6.0D);
   private BooleanValue WEAPON = new BooleanValue("Weapon", false);

   public Reach() {
      super("Reach", 0, AbstractModule.Category.Combat);
   }
}

package today.exusiai.modules.collection.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.values.NumberValue;

public class Step extends AbstractModule {
   private NumberValue speed = new NumberValue("Height", 0.6D, 0.1D, 10.0D);
   public Step() {
      super("Step", 0, Category.Movement);
      this.addValue(this.speed);
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
      this.mc.thePlayer.stepHeight = this.getState() ? (float) speed.getValue() : 0.6f;
   }

   @Override
   public void onEnable() {
      super.onEnable();
   }

   @Override
   public void onDisable() {
      super.onDisable();
   }
}

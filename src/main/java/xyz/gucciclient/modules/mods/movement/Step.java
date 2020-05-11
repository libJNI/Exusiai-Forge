package xyz.gucciclient.modules.mods.movement;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.values.NumberValue;

public class Step extends Module {
   private NumberValue speed = new NumberValue("Height", 0.6D, 0.1D, 10.0D);
   public Step() {
      super("Step", 0, Category.Movement);
      this.addValue(this.speed);
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
      this.mc.thePlayer.stepHeight = this.getState() ? (float) speed.getValue() : 0.6f;
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }
}

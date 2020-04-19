package xyz.gucciclient.modules.mods.movement;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.values.NumberValue;

public class Timer extends Module {
   private long lastRateChange;
   private Field timerField;
   private Field speedField;
   private NumberValue timerspeed = new NumberValue("Timer speed", 0.5D, 0.1D, 2.0D);

   public Timer() {
      super("Timer", 0, Module.Category.Other);
      this.addValue(this.timerspeed);
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
      try {
         this.timerField = Minecraft.class.getDeclaredField("timer");
         this.speedField = net.minecraft.util.Timer.class.getDeclaredField("timerSpeed");
      } catch (NoSuchFieldException var3) {
         var3.printStackTrace();
      }

      if (System.currentTimeMillis() > this.lastRateChange + 1000L) {
         this.setTimerRate((float)this.timerspeed.getValue());
         this.lastRateChange = System.currentTimeMillis();
      }

   }

   public void onDisable() {
      this.setTimerRate(1.0F);
   }

   private void setTimerRate(float rate) {
      try {
         this.timerField.setAccessible(true);
         net.minecraft.util.Timer timer = (net.minecraft.util.Timer)this.timerField.get(this.mc);
         this.timerField.setAccessible(false);
         this.speedField.setAccessible(true);
         this.speedField.set(timer, rate);
         this.speedField.setAccessible(false);
      } catch (Throwable var3) {
         var3.printStackTrace();
      }

   }
}

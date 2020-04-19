package xyz.gucciclient.modules.mods.utility;

import java.awt.Robot;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.NumberValue;

public class AgroPearl extends Module {
   private int stage = 0;
   private Timer timer = new Timer();
   private int currentItem = 0;
   private NumberValue delay;

   public AgroPearl() {
      super(Module.Modules.AgroPearl.name(), 0, Module.Category.Utility);
      this.addValue(this.delay = new NumberValue("Delay", 50.0D, 25.0D, 500.0D));
   }

   public void onEnable() {
      this.currentItem = Wrapper.getPlayer().inventory.currentItem;
      if (this.switchItem()) {
         this.stage = 1;
      } else {
         this.toggle();
      }

   }

   public void onDisable() {
      this.stage = 0;
      Wrapper.getPlayer().inventory.currentItem = this.currentItem;
   }

   private boolean isEnderPearl(ItemStack stack) {
      return stack != null && stack.getItem() == Items.ender_pearl;
   }

   private boolean switchItem() {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = Wrapper.getPlayer().inventory.mainInventory[i];
         if (this.isEnderPearl(stack)) {
            Wrapper.getPlayer().inventory.currentItem = i;
            return true;
         }
      }

      return false;
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) throws Exception {
      if (this.stage == 1 && this.timer.hasReached(this.delay.getValue())) {
         if (!this.isEnderPearl(Wrapper.getPlayer().inventory.getCurrentItem())) {
            this.toggle();
            return;
         }

         Robot bot = new Robot();
         if (Wrapper.getPlayer().getHealth() > 5.0F) {
            bot.mousePress(4);
            bot.mouseRelease(4);
            this.stage = 2;
            this.timer.reset();
         }
      }

      if (this.stage == 2 && this.timer.hasReached(this.delay.getValue())) {
         this.toggle();
         this.timer.reset();
      }

   }
}

package xyz.gucciclient.modules.mods.utility;

import java.awt.Robot;
import java.util.Iterator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.NumberValue;

public class Debuff extends Module {
   private Timer timer = new Timer();
   private int currentItem = 0;
   private int count = 0;
   private int stage = 0;
   private NumberValue delay;

   public Debuff() {
      super(Module.Modules.Debuff.name(), 0, Module.Category.Utility);
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
      this.count = 0;
      Wrapper.getPlayer().inventory.currentItem = this.currentItem;
   }

   private boolean isPot(ItemStack stack) {
      if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
         Iterator var2 = ((ItemPotion)stack.getItem()).getEffects(stack).iterator();

         while(var2.hasNext()) {
            Object o = var2.next();
            PotionEffect e = (PotionEffect)o;
            if (e.getPotionID() == Potion.weakness.id || e.getPotionID() == Potion.poison.id || e.getPotionID() == Potion.moveSlowdown.id || e.getPotionID() == Potion.blindness.id || e.getPotionID() == Potion.wither.id) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean switchItem() {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = Wrapper.getPlayer().inventory.mainInventory[i];
         if (this.isPot(stack)) {
            Wrapper.getPlayer().inventory.currentItem = i;
            return true;
         }
      }

      return false;
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) throws Exception {
      if (this.stage == 1 && this.timer.hasReached(this.delay.getValue())) {
         if (!this.isPot(Wrapper.getPlayer().inventory.getCurrentItem())) {
            this.toggle();
            return;
         }

         Robot bot = new Robot();
         bot.mousePress(4);
         bot.mouseRelease(4);
         this.stage = 2;
         this.timer.reset();
      }

      if (this.stage == 2 && this.timer.hasReached(this.delay.getValue())) {
         if (this.switchItem()) {
            this.stage = 1;
            this.timer.reset();
         } else {
            this.toggle();
         }
      }

   }
}

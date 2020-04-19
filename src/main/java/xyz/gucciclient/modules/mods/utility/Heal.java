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
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Heal extends Module {
   private int currentItem = 0;
   private int vl = 0;
   private int stage = 0;
   private Timer timer = new Timer();
   private BooleanValue pots = new BooleanValue("Pots", true);
   private BooleanValue soup = new BooleanValue("Soup", true);
   private BooleanValue ThrowBowls = new BooleanValue("Throw bowls", true);
   private NumberValue Delay;

   public Heal() {
      super(Module.Modules.Heal.name(), 0, Module.Category.Utility);
      this.addValue(this.Delay = new NumberValue("Delay", 50.0D, 25.0D, 1000.0D));
      this.addBoolean(this.pots);
      this.addBoolean(this.soup);
      this.addBoolean(this.ThrowBowls);
   }

   public void onEnable() {
      this.currentItem = this.mc.thePlayer.inventory.currentItem;
      if (this.mc.thePlayer.getHealth() <= 6.0F) {
         this.vl = 2;
      } else {
         if (this.mc.thePlayer.getHealth() > 12.0F) {
            this.toggle();
            return;
         }

         this.vl = 1;
      }

      if (this.switchItem()) {
         this.stage = 1;
      } else {
         this.toggle();
      }

   }

   public void onDisable() {
      this.stage = 0;
      this.vl = 0;
      this.mc.thePlayer.inventory.currentItem = this.currentItem;
   }

   private boolean isPot(ItemStack stack) {
      if (stack != null && stack.getItem() == Items.potionitem && ItemPotion.isSplash(stack.getItemDamage())) {
         Iterator var2 = ((ItemPotion)stack.getItem()).getEffects(stack).iterator();

         while(var2.hasNext()) {
            Object o = var2.next();
            if (((PotionEffect)o).getPotionID() == Potion.heal.id) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean isItemSoup(ItemStack stack) {
      return stack != null && stack.getItem() == Items.mushroom_stew;
   }

   private boolean isItemBowl(ItemStack stack) {
      return stack != null && stack.getItem() == Items.bowl;
   }

   private boolean isValidItem(ItemStack stack) {
      return this.soup.getState() && this.isItemSoup(stack) || this.pots.getState() && this.isPot(stack);
   }

   private boolean switchItem() {
      for(int i = 0; i < 9; ++i) {
         ItemStack stack = this.mc.thePlayer.inventory.mainInventory[i];
         if (this.isValidItem(stack)) {
            this.mc.thePlayer.inventory.currentItem = i;
            return true;
         }
      }

      return false;
   }

   private void count() {
      --this.vl;
      if (this.vl > 0) {
         if (this.switchItem()) {
            this.stage = 1;
         } else {
            this.toggle();
         }
      } else {
         this.toggle();
      }

   }

   @SubscribeEvent
   public void Tick(ClientTickEvent event) throws Exception {
      if (this.stage == 1 && this.timer.hasReached(this.Delay.getValue())) {
         if (!this.isValidItem(this.mc.thePlayer.inventory.getCurrentItem())) {
            this.toggle();
            return;
         }

         Robot bot = new Robot();
         bot.mousePress(4);
         bot.mouseRelease(4);
         this.stage = 2;
         this.timer.reset();
      }

      if (this.stage == 2 && this.timer.hasReached(this.Delay.getValue())) {
         if (this.ThrowBowls.getState()) {
            if (!this.isItemBowl(this.mc.thePlayer.inventory.getCurrentItem()) && !this.isItemSoup(this.mc.thePlayer.inventory.getCurrentItem())) {
               this.toggle();
               return;
            }

            this.mc.thePlayer.dropOneItem(true);
            this.stage = 3;
            this.timer.reset();
         } else {
            this.count();
            this.timer.reset();
         }
      } else if (this.stage == 3 && this.timer.hasReached(this.Delay.getValue())) {
         this.count();
         this.timer.reset();
      }

   }
}

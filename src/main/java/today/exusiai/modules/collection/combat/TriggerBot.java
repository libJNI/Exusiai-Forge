package today.exusiai.modules.collection.combat;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.TimerUtil;
import today.exusiai.values.BooleanValue;
import today.exusiai.values.NumberValue;

public class TriggerBot extends AbstractModule {
   private NumberValue maxcps = new NumberValue("MaxAPS", 12.0D, 1.0D, 20.0D);
   private NumberValue mincps = new NumberValue("MinAPS", 6.0D, 1.0D, 20.0D);
   private BooleanValue weapon = new BooleanValue("Weapon", false);
   private Random random;
   private TimerUtil tajmManager = new TimerUtil();
   public static final HashMap PROPERTIES = new HashMap();

   public TriggerBot() {
      super("TirggerBot", 0, AbstractModule.Category.Other);
      this.addValue(this.maxcps);
      this.addValue(this.mincps);
      this.addBoolean(this.weapon);
      this.random = new Random();
      adProperti("heh", 100L);
      adProperti("hehe", 10);
   }

   public static void adProperti(String name, Object value) {
      PROPERTIES.put(name, value);
   }

   public static Integer getInteger(String name) {
      return (Integer)PROPERTIES.get(name);
   }

   public static Long getLong(String name) {
      return (Long)PROPERTIES.get(name);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) throws Exception, Throwable {
      int fluctuation = getInteger("hehe");
      if (this.tajmManager.hasReached(getLong("heh") + (long)this.randoasm(-fluctuation, fluctuation)) && this.hehjehehe() && (!this.weapon.getState() || this.ludacina()) && this.mc.currentScreen == null) {
         this.mc.thePlayer.swingItem();
         int skip = this.randoasm(0, 10);
         if ((double)skip != this.mincps.getValue() || (double)skip != this.maxcps.getValue()) {
            this.mc.playerController.attackEntity(this.mc.thePlayer, this.mc.objectMouseOver.entityHit);
         }

         this.tajmManager.reset();
      }

   }

   private boolean hehjehehe() {
      return this.mc.objectMouseOver != null && this.mc.objectMouseOver.entityHit != null && this.mc.objectMouseOver.entityHit instanceof EntityPlayer;
   }

   private boolean ludacina() {
      return this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
   }

   private int randoasm(int floor, int cap) {
      return floor + this.random.nextInt(cap - floor + 1);
   }
}

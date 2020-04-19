package xyz.gucciclient.modules.mods.combat;

import java.lang.reflect.Method;
import java.util.Random;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemSword;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.MouseUtil;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Clicker extends Module {
   private NumberValue maxcps = new NumberValue("MaxCPS", 12.0D, 1.0D, 20.0D);
   private NumberValue mincps = new NumberValue("MinCPS", 6.0D, 1.0D, 20.0D);
   private NumberValue jitterstren = new NumberValue("Jitter Strength", 0.5D, 0.1D, 2.0D);
   private BooleanValue jitter = new BooleanValue("Jitter", false);
   private BooleanValue weapon = new BooleanValue("Weapon", false);
   private BooleanValue inv = new BooleanValue("Inventory fill", false);
   private long nextLeftUp;
   private long nextLeftDown;
   private long nextRightUp;
   private long nextRightDown;
   private long nextDrop;
   private long nextExhaust;
   private Timer timer = new Timer();
   private Random random = new Random();
   private double dr0pR4t3;
   private boolean dr0pp1ng;
   private Method guiScreenMethod;

   public Clicker() {
      super(Module.Modules.Clicker.name(), 0, Module.Category.Combat);
      this.addValue(this.maxcps);
      this.addValue(this.mincps);
      this.addValue(this.jitterstren);
      this.addBoolean(this.jitter);
      this.addBoolean(this.weapon);
      this.addBoolean(this.inv);

      try {
         this.guiScreenMethod = GuiScreen.class.getDeclaredMethod("func_73864_a", Integer.TYPE, Integer.TYPE, Integer.TYPE);
      } catch (NoSuchMethodException var2) {
         var2.printStackTrace();
      }

   }

   public boolean check(EntityPlayerSP playerSP) {
      return !this.weapon.getState() || playerSP.getCurrentEquippedItem() != null && (playerSP.getCurrentEquippedItem().getItem() instanceof ItemSword || playerSP.getCurrentEquippedItem().getItem() instanceof ItemAxe);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent ev3nt) throws Exception {
      long n;
      if (this.mc.currentScreen == null && this.check(this.mc.thePlayer)) {
         Mouse.poll();
         if (Mouse.isButtonDown(0)) {
            if (this.jitter.getState() && this.random.nextDouble() > 0.65D) {
               float jitterstrenght = (float)(this.jitterstren.getValue() * 0.5D);
               EntityPlayerSP var10000;
               if (this.random.nextBoolean()) {
                  var10000 = this.mc.thePlayer;
                  var10000.rotationYaw += this.random.nextFloat() * jitterstrenght;
               } else {
                  var10000 = this.mc.thePlayer;
                  var10000.rotationYaw -= this.random.nextFloat() * jitterstrenght;
               }

               if (this.random.nextBoolean()) {
                  var10000 = this.mc.thePlayer;
                  var10000.rotationPitch += (float)((double)this.random.nextFloat() * (double)jitterstrenght * 0.75D);
               } else {
                  var10000 = this.mc.thePlayer;
                  var10000.rotationPitch -= (float)((double)this.random.nextFloat() * (double)jitterstrenght * 0.75D);
               }
            }

            if (this.nextLeftDown > 0L && this.nextLeftUp > 0L) {
               if (System.currentTimeMillis() > this.nextLeftDown) {
                  KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), true);
                  KeyBinding.onTick(this.mc.gameSettings.keyBindAttack.getKeyCode());
                  MouseUtil.sendClick(0, true);
                  this.generateLeftDelay();
               } else if (System.currentTimeMillis() > this.nextLeftUp) {
                  KeyBinding.setKeyBindState(this.mc.gameSettings.keyBindAttack.getKeyCode(), false);
                  MouseUtil.sendClick(0, false);
               }
            } else {
               this.generateLeftDelay();
            }

            if (!Mouse.isButtonDown(1)) {
               n = 0L;
               this.nextRightUp = 0L;
               this.nextRightDown = 0L;
            }
         } else {
            n = 0L;
            this.nextRightUp = 0L;
            this.nextRightDown = 0L;
            this.nextLeftUp = 0L;
            this.nextLeftDown = 0L;
         }
      } else if (this.mc.currentScreen instanceof GuiInventory) {
         if (Mouse.isButtonDown(0) && (Keyboard.isKeyDown(54) || Keyboard.isKeyDown(42))) {
            if (!this.inv.getState()) {
               return;
            }

            if (this.nextLeftUp == 0L || this.nextLeftDown == 0L) {
               this.generateLeftDelay();
               return;
            }

            if (System.currentTimeMillis() > this.nextLeftDown) {
               this.generateLeftDelay();
               this.clickInventory(this.mc.currentScreen);
            }
         } else {
            n = 0L;
            this.nextRightUp = 0L;
            this.nextRightDown = 0L;
            this.nextLeftUp = 0L;
            this.nextLeftDown = 0L;
         }
      }

   }

   private void generateLeftDelay() {
      if (this.mincps.getValue() <= this.maxcps.getValue()) {
         long d3l4y = (long)((int)Math.round(900.0D / this.mincps.getValue() + this.random.nextDouble() * (this.maxcps.getValue() - this.mincps.getValue())));
         if (System.currentTimeMillis() > this.nextDrop) {
            if (!this.dr0pp1ng && this.random.nextInt(100) >= 85) {
               this.dr0pp1ng = true;
               this.dr0pR4t3 = 1.1D + this.random.nextDouble() * 0.15D;
            } else {
               this.dr0pp1ng = false;
            }

            this.nextDrop = System.currentTimeMillis() + 500L + (long)this.random.nextInt(1500);
         }

         if (this.dr0pp1ng) {
            d3l4y *= (long)this.dr0pR4t3;
         }

         if (System.currentTimeMillis() > this.nextExhaust) {
            if (this.random.nextInt(100) >= 80) {
               d3l4y += 50L + (long)this.random.nextInt(150);
            }

            this.nextExhaust = System.currentTimeMillis() + 500L + (long)this.random.nextInt(1500);
         }

         this.nextLeftDown = System.currentTimeMillis() + d3l4y;
         this.nextLeftUp = System.currentTimeMillis() + d3l4y / 2L - (long)this.random.nextInt(10);
      }
   }

   private void clickInventory(GuiScreen screen) {
      int v4r1 = Mouse.getX() * screen.width / this.mc.displayWidth;
      int v4r2 = screen.height - Mouse.getY() * screen.height / this.mc.displayHeight - 1;
      boolean var4 = false;

      try {
         this.guiScreenMethod.setAccessible(true);
         this.guiScreenMethod.invoke(screen, v4r1, v4r2, 0);
         this.guiScreenMethod.setAccessible(false);
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }
}

package today.exusiai.modules.collection.movement;

import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import today.exusiai.modules.AbstractModule;

import org.lwjgl.input.Keyboard;

public class InvMove extends AbstractModule {

   public InvMove() {
      super("InvMove", 0, Category.Movement);
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
       if (this.getState() && this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiChat)) {
           KeyBinding[] arrkeyBinding;
           for (KeyBinding keyBinding : arrkeyBinding = new KeyBinding[]{this.mc.gameSettings.keyBindForward, this.mc.gameSettings.keyBindBack, this.mc.gameSettings.keyBindLeft, this.mc.gameSettings.keyBindRight, this.mc.gameSettings.keyBindJump}) {
               KeyBinding.setKeyBindState(keyBinding.getKeyCode(), Keyboard.isKeyDown(keyBinding.getKeyCode()));
           }
       }
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

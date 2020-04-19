package xyz.gucciclient.modules.mods.movement;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.MovementUtils;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Sprint extends Module {
	private BooleanValue omni;
	
   public Sprint() {
      super("Sprint", 0, Module.Category.Movement);
      this.addBoolean(this.omni = new BooleanValue("Rage", false));
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
       if (canSprint()) {
           mc.thePlayer.setSprinting(true);
       }
   }

   private boolean canSprint() {
   	if(!(this.omni.getState() &&!mc.gameSettings.keyBindForward.isPressed())) {
   		return false;
   	}
       return MovementUtils.isMoving() && mc.thePlayer.getFoodStats().getFoodLevel() > 6;
   }
}

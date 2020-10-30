package today.exusiai.modules.collection.utility;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.PlayerUtils;

public class AutoTool extends AbstractModule {
   public AutoTool() {
      super("AutoTool", 0, Category.Utility);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
	   if (mc.gameSettings.keyBindAttack.isPressed()) {
	         if (mc.objectMouseOver != null) {
	            BlockPos pos = mc.objectMouseOver.getBlockPos();
	            if (pos != null) {
	            	PlayerUtils.updateTool(pos);
	            }
	         }
	      }
   }
}

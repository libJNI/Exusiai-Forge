package today.exusiai.modules.collection.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import today.exusiai.modules.AbstractModule;

public class Eagle extends AbstractModule {
	
   public Eagle() {
      super("Eagle", 0, AbstractModule.Category.Movement);
   }

   @SubscribeEvent
   public void onTick(PlayerTickEvent ev3nt) throws Exception {
	   if (this.BlockUnder(mc.thePlayer) instanceof BlockAir) {
	         if (mc.thePlayer.onGround) {
	            KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
	         }
	      } else if (mc.thePlayer.onGround) {
	         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
	      }

   }

   @Override
   public void onEnable() {
	   mc.thePlayer.setSneaking(false);
	   super.onEnable();
   }
   @Override
   public void onDisable() {
	   KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
	   super.onDisable();
   }
   
   public Block getBlock(BlockPos pos) {
	      return mc.theWorld.getBlockState(pos).getBlock();
   }

   public Block BlockUnder(EntityPlayer playerIn) {
	      return this.getBlock(new BlockPos(playerIn.posX, playerIn.posY - 1.0D, playerIn.posZ));
   }
}

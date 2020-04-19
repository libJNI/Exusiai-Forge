package xyz.gucciclient.modules.mods.movement;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.MovementUtils;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Eagle extends Module {
	
   public Eagle() {
      super("Eagle", 0, Module.Category.Movement);
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

   public void onEnable() {
	   mc.thePlayer.setSneaking(false);
	   super.onEnable();
   }

   public void onDisable() {
	   KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
	   super.onDisable();

   }
   
   public Block getBlock(BlockPos pos) {
	      return mc.theWorld.getBlockState(pos).getBlock();
   }

   public Block BlockUnder(EntityPlayer player) {
	      return this.getBlock(new BlockPos(player.posX, player.posY - 1.0D, player.posZ));
   }

}

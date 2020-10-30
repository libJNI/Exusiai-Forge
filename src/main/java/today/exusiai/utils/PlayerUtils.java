package today.exusiai.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public final class PlayerUtils {

   public static boolean isHoldingSword() {
      return Wrapper.getPlayer().getCurrentEquippedItem() != null && Wrapper.getPlayer().getCurrentEquippedItem().getItem() instanceof ItemSword;
   }

   /**
    * Ensures that the best tool is used for breaking the block.
    */
   public static void updateTool(BlockPos pos) {
	      Block block = Wrapper.getWorld().getBlockState(pos).getBlock();
	      float strength = 1.0F;
	      int bestItemIndex = -1;

	      for(int i = 0; i < 9; ++i) {
	         ItemStack itemStack = Wrapper.getPlayer().inventory.mainInventory[i];
	         if (itemStack != null && itemStack.getStrVsBlock(block) > strength) {
	            strength = itemStack.getStrVsBlock(block);
	            bestItemIndex = i;
	         }
	      }

	      if (bestItemIndex != -1) {
	         Wrapper.getPlayer().inventory.currentItem = bestItemIndex;
	      }

	   }

   public static boolean isOnSameTeam(Entity entity) {
		if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("\247")) {
           if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2
                   || entity.getDisplayName().getUnformattedText().length() <= 2) {
               return false;
           }
           if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
               return true;
           }
		}
		return false;
	}

   public static boolean isInLiquid() {
      boolean inLiquid = false;
      AxisAlignedBB playerBB = Wrapper.getPlayer().getEntityBoundingBox();
      int y = (int)playerBB.minY;

      for(int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; ++x) {
         for(int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; ++z) {
            Block block = Wrapper.getWorld().getBlockState(new BlockPos(x, y, z)).getBlock();
            if (block != null && !(block instanceof BlockAir)) {
               if (!(block instanceof BlockLiquid)) {
                  return false;
               }

               inLiquid = true;
            }
         }
      }

      return inLiquid;
   }

   public static boolean isOnLiquid() {
      boolean onLiquid = false;
      AxisAlignedBB playerBB = Wrapper.getPlayer().getEntityBoundingBox();
      WorldClient world = Wrapper.getWorld();
      int y = (int)playerBB.offset(0.0D, -0.01D, 0.0D).minY;

      for(int x = MathHelper.floor_double(playerBB.minX); x < MathHelper.floor_double(playerBB.maxX) + 1; ++x) {
         for(int z = MathHelper.floor_double(playerBB.minZ); z < MathHelper.floor_double(playerBB.maxZ) + 1; ++z) {
            Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
            if (block != null && !(block instanceof BlockAir)) {
               if (!(block instanceof BlockLiquid)) {
                  return false;
               }

               onLiquid = true;
            }
         }
      }

      return onLiquid;
   }

   public static boolean isOnHypixel() {
      return !Wrapper.getMinecraft().isSingleplayer() && Wrapper.getMinecraft().getCurrentServerData().serverIP.contains("hypixel");
   }

   public static float getMaxFallDist() {
      PotionEffect potioneffect = Wrapper.getPlayer().getActivePotionEffect(Potion.jump);
      int f = potioneffect != null ? potioneffect.getAmplifier() + 1 : 0;
      return (float)(Wrapper.getPlayer().getMaxFallHeight() + f);
   }

   public static boolean isBlockUnder() {
      EntityPlayerSP player = Wrapper.getPlayer();
      WorldClient world = Wrapper.getWorld();
      AxisAlignedBB pBb = player.getEntityBoundingBox();
      double height = player.posY + (double)player.getEyeHeight();

      for(int offset = 0; (double)offset < height; offset += 2) {
         if (!world.getCollidingBoundingBoxes(player, pBb.offset(0.0D, (double)(-offset), 0.0D)).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   public static boolean isInsideBlock() {
      EntityPlayerSP player = Wrapper.getPlayer();
      WorldClient world = Wrapper.getWorld();
      AxisAlignedBB bb = player.getEntityBoundingBox();

      for(int x = MathHelper.floor_double(bb.minX); x < MathHelper.floor_double(bb.maxX) + 1; ++x) {
         for(int y = MathHelper.floor_double(bb.minY); y < MathHelper.floor_double(bb.maxY) + 1; ++y) {
            for(int z = MathHelper.floor_double(bb.minZ); z < MathHelper.floor_double(bb.maxZ) + 1; ++z) {
               Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
               AxisAlignedBB boundingBox;
               if (block != null && !(block instanceof BlockAir) && (boundingBox = block.getCollisionBoundingBox(world, new BlockPos(x, y, z), world.getBlockState(new BlockPos(x, y, z)))) != null && player.getEntityBoundingBox().intersectsWith(boundingBox)) {
                  return true;
               }
            }
         }
      }

      return false;
   }

	public static boolean isMoving() {
		if ((!Wrapper.getPlayer().isCollidedHorizontally) && (!Wrapper.getPlayer().isSneaking())) {
			return ((Wrapper.getPlayer().movementInput.moveForward != 0.0F || Wrapper.getPlayer().movementInput.moveStrafe != 0.0F));
		}
		return false;
	}

	public static boolean isMoving2() {
		return ((Wrapper.getPlayer().moveForward != 0.0F || Wrapper.getPlayer().moveStrafing != 0.0F));
	}
}

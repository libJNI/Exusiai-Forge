package today.exusiai.utils;

import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BlockData {

	public final BlockPos position;
	public final EnumFacing face;
	
	public BlockData(BlockPos add, EnumFacing up) {
		this.position = add;
		this.face = up;
	}
	
	  public static BlockData getBlockData(BlockPos pos)
	  {
	    if (Wrapper.getWorld().getBlockState(pos.add(0, -1, 0)).getBlock() != Blocks.air) {
	      return new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
	    }
	    if (Wrapper.getWorld().getBlockState(pos.add(-1, 0, 0)).getBlock() != Blocks.air) {
	      return new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST);
	    }
	    if (Wrapper.getWorld().getBlockState(pos.add(1, 0, 0)).getBlock() != Blocks.air) {
	      return new BlockData(pos.add(1, 0, 0), EnumFacing.WEST);
	    }
	    if (Wrapper.getWorld().getBlockState(pos.add(0, 0, -1)).getBlock() != Blocks.air) {
	      return new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH);
	    }
	    if (Wrapper.getWorld().getBlockState(pos.add(0, 0, 1)).getBlock() != Blocks.air) {
	      return new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH);
	    }
	    return null;
	  }
	  
}

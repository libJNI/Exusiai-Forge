package today.exusiai.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.util.Random;

public class Utils {

    public static boolean lookChanged;
    public static float[] rotationsToBlock = null;
    private static final Random RANDOM = new Random();

    public static boolean isBlockMaterial(BlockPos blockPos, Block block) {
        return Wrapper.getWorld().getBlockState(blockPos).getBlock() == Blocks.air;
    }

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float var4 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);
        if (var4 > p_70663_3_) {
            var4 = p_70663_3_;
        }
        if (var4 < -p_70663_3_) {
            var4 = -p_70663_3_;
        }
        return p_70663_1_ + var4;
    }
}

package xyz.gucciclient.modules.mods.render;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.modules.mods.render.xray.XRayBlock;
import xyz.gucciclient.modules.mods.render.xray.XRayData;
import xyz.gucciclient.other.XRayManager;
import xyz.gucciclient.utils.RenderUtils;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.NumberValue;

import java.util.LinkedList;

public class Xray extends Module {
	public static int chestColorRed = 255;
	public static int chestColorGreen = 105;
    public LinkedList<XRayBlock> blocks = new LinkedList<XRayBlock>();
    private NumberValue distance = new NumberValue("Distance", 50.0D, 50.0D, 100.0D);
    private NumberValue delay = new NumberValue("UpdateDelay", 100.0D, 0.0D, 300.0D);
	public static Timer timer = new Timer();
   public Xray() {
          super("Xray", 0, Category.Visuals);
   }
   
   @SubscribeEvent
   public void onRenderWorldLast(RenderWorldLastEvent event) {
       RenderUtils.drawXRayBlocks(blocks, event.partialTicks);
   }
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        int distance = (int) this.distance.getValue();
        if(!timer.hasReached((long) (delay.getValue() * 10))) {
            return;
        }
        blocks.clear();
        for(XRayData data : XRayManager.xrayList) {
            for (BlockPos blockPos : findBlocksNearEntity(Wrapper.getPlayer(), data.getId(), data.getMeta(), distance)) {
                XRayBlock xRayBlock = new XRayBlock(blockPos, data);
                blocks.add(xRayBlock);
            }
        }
        timer.reset();
    }

    public static LinkedList<BlockPos> findBlocksNearEntity(EntityLivingBase entity, int blockId, int blockMeta, int distance) {
        LinkedList<BlockPos> blocks = new LinkedList<BlockPos>();

        for (int x = (int) Wrapper.getPlayer().posX - distance; x <= (int) Wrapper.getPlayer().posX + distance; ++x) {
            for (int z = (int) Wrapper.getPlayer().posZ - distance; z <= (int) Wrapper.getPlayer().posZ + distance; ++z) {

                int height = Wrapper.getWorld().getHeight();
                block: for (int y = 0; y <= height; ++y) {

                    BlockPos blockPos = new BlockPos(x, y, z);
                    IBlockState blockState = Wrapper.getWorld().getBlockState(blockPos);

                    if(blockId == -1 || blockMeta == -1) {
                        blocks.add(blockPos);
                        continue block;
                    }

                    int id = Block.getIdFromBlock(blockState.getBlock());
                    int meta =  blockState.getBlock().getMetaFromState(blockState);

                    if(id == blockId && meta == blockMeta) {

                        blocks.add(blockPos);
                        continue block;
                    }

                }
            }
        }
        return blocks;
    }

}

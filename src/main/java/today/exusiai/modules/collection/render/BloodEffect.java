package today.exusiai.modules.collection.render;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.TimerUtil;
import today.exusiai.utils.Wrapper;
import today.exusiai.values.BooleanValue;

public class BloodEffect extends AbstractModule {
	private BooleanValue player = new BooleanValue("PlayersOnly", false);
	private TimerUtil effecttimer = new TimerUtil();
   public BloodEffect() {
      super("BloodEffect", 0, AbstractModule.Category.Visuals);
      addBoolean(player);
   }
   
   @SubscribeEvent
   public void onLivingDeath(LivingAttackEvent event) {
       this.spawnBlood(event.entity);
   }
   
   private void spawnBlood(final Entity entity) {
       if (entity instanceof EntityLivingBase && (!player.getState() || entity instanceof EntityPlayer)) {
           if(effecttimer.hasReached(1000)) {
        	   final BlockPos pos = new BlockPos(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
               final IBlockState blockState = Blocks.redstone_block.getDefaultState();
               Wrapper.getMinecraft().renderGlobal.playAuxSFX((EntityPlayer)null, 2001, pos, Block.getStateId(blockState));
               effecttimer.reset();
           }
       }
   }
}

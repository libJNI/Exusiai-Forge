package today.exusiai.modules.collection.other;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.Wrapper;

public class FastUse extends AbstractModule {

    public FastUse() {
        super("FastUse", 0, Category.Other);
    }

    @SubscribeEvent
    public void onTick(PlayerTickEvent ev3nt) throws Exception {
        if (mc.thePlayer.isUsingItem())
        {
            Item item = mc.thePlayer.getCurrentEquippedItem().getItem();
            if (item instanceof ItemFood || item instanceof ItemPotion || item instanceof ItemBucketMilk)
            {
                for (int i = 0; i < 20; i++)
                {
                    mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(Wrapper.getPlayer().onGround));
                }
                mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                mc.thePlayer.stopUsingItem();
            }
        }
    }
}

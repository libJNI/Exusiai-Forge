package xyz.gucciclient.modules.mods.utility;

import java.lang.reflect.Field;
import net.minecraft.client.Minecraft;
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
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.MovementUtils;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class FastUse extends Module {

    public FastUse() {
        super("FastUse", 0, Category.Utility);
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

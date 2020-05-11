package xyz.gucciclient.modules.mods.other;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;

public class Regen extends Module {

    public Regen() {
        super("Regen", 0, Category.Other);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if ( Wrapper.isMoving() && mc.thePlayer.getHealth() < mc.thePlayer.getMaxHealth() && mc.thePlayer.getFoodStats().getFoodLevel() > 1) {
            for (int i = 0; i < 200; ++i) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(Wrapper.getPlayer().onGround));
            }
        }
    }
}

package xyz.gucciclient.modules.mods.other;

import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.utils.Timer2;
import xyz.gucciclient.utils.Wrapper;

public class FakePing extends Module {

    public Timer timer = new Timer();
    public FakePing() {
        super("FakePing", 0, Category.Other);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
            if(timer.hasReached(750)) {
                mc.getNetHandler().getNetworkManager().sendPacket(new C00PacketKeepAlive(1338));
                timer.reset();
            }
    }
}

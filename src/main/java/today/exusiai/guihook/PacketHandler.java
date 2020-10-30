package today.exusiai.guihook;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import today.exusiai.event.EventSystem;
import today.exusiai.event.EventType;
import today.exusiai.event.events.EventPacket;

public class PacketHandler extends ChannelDuplexHandler
{
    NetworkManager networkManager;
    
    public PacketHandler(final NetworkManager zhuzhux) {
        this.networkManager = zhuzhux;
    }

    @Override
    public void channelRead(final ChannelHandlerContext a, final Object b) throws Exception {
        if (b instanceof Packet) {
        	final EventPacket fkserverPacket = new EventPacket((Packet)b, EventType.RECIEVE);
            EventSystem.call(fkserverPacket);
            if (fkserverPacket.isCancelled()) {
                return;
            }
        }
        super.channelRead(a, b);
    }

    @Override
    public void write(final ChannelHandlerContext a, final Object b, final ChannelPromise c) throws Exception {
        if (b instanceof Packet) {
        	 final EventPacket badclientPacket = new EventPacket((Packet)b, EventType.SEND);
             EventSystem.call(badclientPacket);
             if (badclientPacket.isCancelled()) {
                 return;
             }
        }
        super.write(a, b, c);
    }
}

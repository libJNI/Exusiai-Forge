package today.exusiai.event.events;

import net.minecraft.network.*;
import today.exusiai.event.AbstractEventCancellable;
import today.exusiai.event.EventType;

public class EventPacket extends AbstractEventCancellable
{
    public Packet packet;
    public EventType type;
    
    public EventPacket(final Packet a, final EventType b) {
        this.packet = a;
        this.type = b;
    }
}

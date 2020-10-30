package today.exusiai.event.events;

import net.minecraft.client.gui.*;
import today.exusiai.event.*;

public class EventRender2D implements Event
{
    public float partialTicks;
    public ScaledResolution res;
    
    public EventRender2D(final float a, final ScaledResolution b) {
        this.partialTicks = a;
        this.res = b;
    }
}

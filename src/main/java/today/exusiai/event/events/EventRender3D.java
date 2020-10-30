package today.exusiai.event.events;

import net.minecraft.entity.EntityLivingBase;
import today.exusiai.event.*;

public class EventRender3D implements Event {
	private float partialTicks;
	
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

	public float getPartialTicks() {
		return this.partialTicks;
	}
}

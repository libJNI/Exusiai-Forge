package today.exusiai.event.events;

import net.minecraft.entity.EntityLivingBase;
import today.exusiai.event.*;

public class EventAttack implements Event {
	private EntityLivingBase target;
	
    public EventAttack(final EntityLivingBase target) {
        this.target = target;
    }
    
    public EntityLivingBase getTarget() {
		return this.target;
	}
}

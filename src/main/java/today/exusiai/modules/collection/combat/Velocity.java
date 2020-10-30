package today.exusiai.modules.collection.combat;

import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import today.exusiai.event.ActiveEvent;
import today.exusiai.event.EventType;
import today.exusiai.event.events.EventPacket;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.Wrapper;
import today.exusiai.values.BooleanValue;
import today.exusiai.values.NumberValue;

public class Velocity extends AbstractModule {
   private NumberValue HorizontalModifier = new NumberValue("Horizontal", 100.0D, 0.0D, 100.0D);
   private NumberValue VerticalModifier = new NumberValue("Vertical", 100.0D, 0.0D, 100.0D);
   private BooleanValue zeroBooleanValue = new BooleanValue("Zero", true);
   private BooleanValue explosionBooleanValue = new BooleanValue("AntiExplosion", true);

   public Velocity() {
      super("AntiVelocity", 0, AbstractModule.Category.Combat);
      this.addValue(this.HorizontalModifier,this.VerticalModifier);
      this.addBoolean(zeroBooleanValue,explosionBooleanValue);
   }

   @SubscribeEvent
   public void onTick(TickEvent event) {
      if (Wrapper.getPlayer() != null) {
         if (Wrapper.getWorld() != null) {
        	 double vertmodifier;
             double horzmodifier;
             if(!zeroBooleanValue.getState()) {
                 vertmodifier = this.VerticalModifier.getValue() / 100.0D;
                 horzmodifier = this.HorizontalModifier.getValue() / 100.0D;
                 if (Wrapper.getPlayer().hurtTime == Wrapper.getPlayer().maxHurtTime && Wrapper.getPlayer().maxHurtTime > 0) {
                	 Wrapper.getPlayer().motionX *= horzmodifier;
                	 Wrapper.getPlayer().motionZ *= horzmodifier;
                	 Wrapper.getPlayer().motionY *= vertmodifier;
                 }
             }
         }
      }
   }
   
   @ActiveEvent
   public void onPacket(EventPacket eventPacket) {
	   if(eventPacket.type == EventType.RECIEVE) {
           if(zeroBooleanValue.getState()) {
        	   if(eventPacket.packet instanceof S12PacketEntityVelocity) {
    			   S12PacketEntityVelocity packet = (S12PacketEntityVelocity) eventPacket.packet;
    	           if (mc.theWorld.getEntityByID(packet.getEntityID()) == mc.thePlayer) {
    	        	   eventPacket.setCancelled(true);
    	           }else if (eventPacket.packet instanceof S27PacketExplosion) {
    	        	   if(explosionBooleanValue.getState()) {
    		        	   eventPacket.setCancelled(true);
    	        	   }
    	           }
    		   }
           }
	   }
   }
}

package today.exusiai.modules.collection.movement;

import net.minecraft.network.play.client.C0BPacketEntityAction;
import today.exusiai.event.ActiveEvent;
import today.exusiai.event.EventType;
import today.exusiai.event.events.EventPacket;
import today.exusiai.event.events.EventUpdate;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.ChatUtil;
import today.exusiai.utils.MovementUtils;
import today.exusiai.values.BooleanValue;

public class Sprint extends AbstractModule {
	private BooleanValue omniBooleanValue = new BooleanValue("Omni", false);
	private BooleanValue nostopsprintBooleanValue = new BooleanValue("KeepSprint", false);
	
   public Sprint() {
      super("Sprint", 0, AbstractModule.Category.Movement);
      this.addBoolean(this.omniBooleanValue,nostopsprintBooleanValue);
   }

   @ActiveEvent
   public void onUpdate(EventUpdate event) {
       if (this.omniBooleanValue.getState()) {
           if (MovementUtils.isMoving() && mc.thePlayer.getFoodStats().getFoodLevel() > 6) {
               mc.thePlayer.setSprinting(true);
           }
       } else {
           if(mc.gameSettings.keyBindForward.isPressed()) {
               mc.thePlayer.setSprinting(true);
           }
       }
   }
   
   @ActiveEvent
   public void onPacket(EventPacket eventPacket) {
	   if(nostopsprintBooleanValue.getState()) {
		   if(eventPacket.type == EventType.RECIEVE && eventPacket.packet instanceof C0BPacketEntityAction) {
	           C0BPacketEntityAction packet = (C0BPacketEntityAction) eventPacket.packet;
	           if (packet.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
	               eventPacket.setCancelled(true);
	               ChatUtil.printChatwithPrefix("STOP_SPRINTING Cancelled");
	           }
		   }
	   }
   }
}

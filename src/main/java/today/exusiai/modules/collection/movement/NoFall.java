package today.exusiai.modules.collection.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import today.exusiai.event.ActiveEvent;
import today.exusiai.event.events.EventUpdate;
import today.exusiai.modules.AbstractModule;

public class NoFall extends AbstractModule {

   public NoFall() {
      super("NoFall", 0, Category.Movement);
   }

   @ActiveEvent
   public void onTick(EventUpdate ev3nt){
       if (this.mc.thePlayer.fallDistance > 3.0f) {
           this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
           this.mc.thePlayer.fallDistance = 0.0f;
       }
   }
}

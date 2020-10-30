package today.exusiai.modules.collection.combat;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.Location;
import today.exusiai.utils.TimerUtil;
import today.exusiai.utils.Wrapper;
import today.exusiai.utils.tpaura.TPCore;
import today.exusiai.utils.tpaura.TPPath;
import today.exusiai.values.NumberValue;

public class TPAura extends AbstractModule {
	private TimerUtil timerUtil = new TimerUtil();
	private NumberValue switchDelay = new NumberValue("CPS", 10.0D, 1.0D, 20D);
	private NumberValue rangeNumberValue = new NumberValue("Range", 10.0D, 1.0D, 100D);
   public TPAura() {
      super("ExploitHit", 0, AbstractModule.Category.Combat);
      this.addValue(switchDelay,rangeNumberValue);
   }

   @SubscribeEvent
   public void onTick(TickEvent event) {
      if (Wrapper.getPlayer() != null && Wrapper.getWorld() != null && this.getState()) {
		  // Every tick
    	  if(timerUtil.hasReached((long) (1000 / switchDelay.getValue()))) {
  			timerUtil.reset();
  			
  			try {
  				for(Object o : mc.theWorld.loadedEntityList) {
  					if(o instanceof EntityLivingBase) {
  						EntityLivingBase e = (EntityLivingBase)o;
  						if(e != null && e != mc.thePlayer && !e.isInvisible() && !e.isEntityInvulnerable(null) && mc.thePlayer.getDistanceToEntity(e) <= rangeNumberValue.getValue() && e.isEntityAlive()) {
  							TPPath path = TPCore.getTPPathToEntity(e);
  							if(path != null) {
  								
  								//Locations
  								Location ppos = new Location(mc.thePlayer);
  								Location epos = new Location(e);
  								
  								//1st TP Wave
  								for(int i = 0; i < path.getTPTimes(); i++) {
  									Location pos = path.getLatestTPPos();
  									Wrapper.getNetManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(pos.getX(), pos.getY(), pos.getZ(), true));
  								}
  								
  								//1st TP Final
  								Wrapper.getNetManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(epos.getX(), epos.getY(), epos.getZ(), true));

  								//Attack
  								this.setSuffix(e.getName());
  								mc.thePlayer.swingItem();
  								Wrapper.getNetManager().sendPacket(new C02PacketUseEntity(e, C02PacketUseEntity.Action.ATTACK));
  								
  								//2nd TP Wave
  								for(int i = 0; i < path.getTPTimes(); i++) {
  									Location pos = path.getLatestBackPos();
  									Wrapper.getNetManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(pos.getX(), pos.getY(), pos.getZ(), true));
  								}
  								
  								//2nd TP Final
									Wrapper.getNetManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(ppos.getX(), ppos.getY(), ppos.getZ(), true));
  							}
  						}
  					}
  				}
  			} catch (Exception e) {}
  		}
      }
   }
}

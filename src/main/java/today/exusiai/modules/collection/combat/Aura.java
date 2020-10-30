package today.exusiai.modules.collection.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.collection.movement.Scaffold;
import today.exusiai.utils.PlayerUtils;
import today.exusiai.utils.TimerUtil;
import today.exusiai.utils.Wrapper;
import today.exusiai.utils.entity.EntityValidator;
import today.exusiai.utils.entity.impl.*;
import today.exusiai.values.BooleanValue;
import today.exusiai.values.NumberValue;

public class Aura extends AbstractModule {
	public static Aura getkillaura = new Aura();
	public static boolean blocking;
	public final List targets = new ArrayList();
	private final TimerUtil attackStopwatch = new TimerUtil();
	private final TimerUtil updateStopwatch = new TimerUtil();
	private final TimerUtil critStopwatch = new TimerUtil();
	private final EntityValidator entityValidator = new EntityValidator();
	private final EntityValidator blockValidator = new EntityValidator();
	private int targetIndex;
	private boolean changeTarget;
	private BooleanValue walls = new BooleanValue("Wall", true);
	private BooleanValue autoblock = new BooleanValue("Autoblock", true);
	private BooleanValue player = new BooleanValue("Players", true);
	private BooleanValue animals = new BooleanValue("Animals", false);
	private BooleanValue mobs = new BooleanValue("Mobs", false);
	private BooleanValue invisibles = new BooleanValue("Invisibles", false);
	private BooleanValue teams = new BooleanValue("Teams", true);
	private BooleanValue autoswitch = new BooleanValue("Switch", true);
	private NumberValue reach = new NumberValue("Reach", 4.20D, 3.0D, 8.0D);
	private NumberValue switchDelay = new NumberValue("SwitchDelay", 3.0D, 1.0D, 10.0D);
	private NumberValue aps = new NumberValue("APS", 9.0D, 1.0D, 20.0D);

   public Aura() {
      super("Aura", 0, AbstractModule.Category.Combat);
      AliveCheck aliveCheck = new AliveCheck();
      EntityCheck entityCheck = new EntityCheck(this.player, this.animals, this.mobs, this.invisibles);
      TeamsCheck teamsCheck = new TeamsCheck(this.teams);
      WallCheck wallCheck = new WallCheck(this.walls);
      this.entityValidator.add(aliveCheck);
      this.entityValidator.add(new DistanceCheck(this.reach));
      this.entityValidator.add(entityCheck);
      this.entityValidator.add(teamsCheck);
      this.entityValidator.add(wallCheck);
      this.blockValidator.add(aliveCheck);
      this.blockValidator.add(new ConstantDistanceCheck(8.0F));
      this.blockValidator.add(entityCheck);
      this.blockValidator.add(teamsCheck);
      this.addValue(this.reach,this.switchDelay,this.aps);
      this.addBoolean(this.animals,this.mobs,this.walls,this.invisibles,this.teams,this.autoswitch,this.autoblock);
   }

   @Override
   public void onEnable() {
	      this.updateStopwatch.reset();
	      this.critStopwatch.reset();
	      this.targetIndex = 0;
	      this.targets.clear();
	      this.changeTarget = false;
   }

   @Override
   public void onDisable() {
	      this.unblock();
   }
   
   @SubscribeEvent
   public void onTick(PlayerTickEvent PlayerTickEvent) {
	   if(!this.getState())return;
	   this.updateTargets();
	      if(this.targets.size() > 2) {
			  this.sortTargets();
		  }
	      if (!PlayerUtils.isHoldingSword()) {
	         blocking = false;
	      }

	      EntityLivingBase target = this.getTarget();
	      if (target == null) {
	         this.unblock();
	      }
	      if (target != null && this.canAttack()) {
	         if (this.attackStopwatch.hasReached((long)(1000 / ((Double)this.aps.getValue()).intValue())) && this.canAttack()) {
	            this.attack(target);
	            this.attackStopwatch.reset();
	         }

	         if ((double)target.hurtTime >= (Double)this.switchDelay.getValue()) {
	            this.changeTarget = true;
	         }
	      }

	      this.block();
   }
   
   private void block() {
	      if (this.autoblock.getState() && PlayerUtils.isHoldingSword() && this.isEntityNearby()) {
	            if (!blocking) {
	                mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
	                blocking = true;
	            }
	      }

   }
   
   private boolean canAttack() {
	      return !AbstractModule.getModule(Scaffold.class).getState();
	   }
   
   public final EntityLivingBase getTarget() {
	      if (this.targets.isEmpty()) {
	         return null;
	      } else if (!autoswitch.getState()) {
	         return (EntityLivingBase)this.targets.get(0);
	      } else {
	         int size = this.targets.size();
	         if (size >= this.targetIndex && this.changeTarget) {
	            ++this.targetIndex;
	            this.changeTarget = false;
	         }

	         if (size <= this.targetIndex) {
	            this.targetIndex = 0;
	         }

	         return (EntityLivingBase)this.targets.get(this.targetIndex);
	      }
	   }

	   private boolean isEntityNearby() {
	      List loadedEntityList = mc.theWorld.loadedEntityList;
	      int i = 0;

	      for(int loadedEntityListSize = loadedEntityList.size(); i < loadedEntityListSize; ++i) {
	         Entity entity = (Entity)loadedEntityList.get(i);
	         if (this.blockValidator.validate(entity)) {
	            return true;
	         }
	      }

	      return false;
	   }
	   
   private void attack(EntityLivingBase entity) {

	      this.unblock();
	      Wrapper.getPlayer().swingItem();
	      Wrapper.getNetManager().sendPacket(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
	      if (this.autoblock.getState()) {
	         mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
	      }

	   }
   
   private void unblock() {
	      if ((this.autoblock.getState() || mc.thePlayer.isBlocking()) && PlayerUtils.isHoldingSword()) {
	            if (blocking) {
	               mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	               blocking = false;
	            }
	      }

   }
   
   private void sortTargets() {
	         this.targets.sort(Comparator.comparingDouble((entity) -> {
	            return (double)mc.thePlayer.getDistanceToEntity((Entity) entity);
	         }));
   }
   
   private void updateTargets() {
	      this.targets.clear();
	      List entities = mc.theWorld.loadedEntityList;
	      int i = 0;

	      for(int entitiesSize = entities.size(); i < entitiesSize; ++i) {
	         Entity entity = (Entity)entities.get(i);
	         if (entity instanceof EntityLivingBase) {
	            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
	            if (this.entityValidator.validate(entityLivingBase)) {
	               this.targets.add(entityLivingBase);
	            }
	         }
	      }
   }  
}

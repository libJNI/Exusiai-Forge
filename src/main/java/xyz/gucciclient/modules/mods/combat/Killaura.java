package xyz.gucciclient.modules.mods.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.modules.mods.other.QYZGSpoof;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Killaura extends Module {
    public static BooleanValue teams = new BooleanValue("Teams", true);
   private BooleanValue player = new BooleanValue("Players", true);
   private BooleanValue mob = new BooleanValue("Mobs", false);
   private BooleanValue animal = new BooleanValue("Animals", false);
    private BooleanValue Lockview = new BooleanValue("Lockview", false);
   private BooleanValue Switch = new BooleanValue("Switch", false);
   private BooleanValue invis = new BooleanValue("Invis", true);
   private NumberValue reach = new NumberValue("Reach", 4.25D, 3.0D, 8.0D);
   private NumberValue ticksexisted = new NumberValue("Ticks existed", 5.0D, 1.0D, 300.0D);
   private NumberValue maxcps = new NumberValue("MaxCPS", 12.0D, 1.0D, 20.0D);
   private NumberValue mincps = new NumberValue("MinCPS", 6.0D, 1.0D, 20.0D);
   private EntityLivingBase entity;
   private ArrayList entities = new ArrayList();
   private Timer timer = new Timer();
   private Random rand = new Random();

   public Killaura() {
      super(Module.Modules.KillAura.name(), 0, Module.Category.Combat);
      this.addValue(this.reach);
      this.addValue(this.maxcps);
      this.addValue(this.mincps);
      this.addValue(this.ticksexisted);
      this.addBoolean(this.teams);
      this.addBoolean(this.invis);
      this.addBoolean(this.player);
      this.addBoolean(this.mob);
      this.addBoolean(this.animal);
      this.addBoolean(this.Switch);
      this.addBoolean(this.Lockview);
   }

   public void onEnable() {
      this.entities.clear();
   }

   @SubscribeEvent
   public void onTick(TickEvent event) {
        if(this.getState()){
            if (Wrapper.getPlayer() != null) {
                if (Wrapper.getWorld() != null) {
                    this.AddValidTargetsToArrayList();
                    this.Attack();
                }
            }
        }
   }

   private long getDelay() {
      return (long)(this.maxcps.getValue() + this.rand.nextDouble() * (this.mincps.getValue() - this.maxcps.getValue()));
   }

   public boolean isTargetValid(Entity entity) {
      if (entity == null) {
         return false;
      } else if (!entity.isEntityAlive()) {
         return false;
      } else if (isOnSameTeam(entity)) {
          return false;
      } else if (!this.mc.thePlayer.canEntityBeSeen(entity)) {
         return false;
      } else {
         float range = (float)this.reach.getValue();
         return this.mc.thePlayer.getDistanceToEntity(entity) <= range;
      }
   }

   public void Attack() {
      for(int i = 0; i < this.entities.size(); ++i) {
         Entity entity = (Entity)this.entities.get(i);
         if (entity.isDead) {
            this.entities.remove(entity);
         }

         if (this.isTargetValid(entity) && this.timer.hasReached((double)(1000L / this.getDelay()))) {
            this.Attack(entity);
            if (this.Switch.getState()) {
               this.entities.remove(i);
            }

            this.getDelay();
            this.timer.reset();
         }
      }

   }

   public boolean isValid(Entity entity) {
      return entity != null && entity.isEntityAlive() && entity != this.mc.thePlayer && (entity instanceof EntityPlayer && this.player.getState() || entity instanceof EntityAnimal && this.animal.getState() || entity instanceof EntityMob && this.mob.getState() || entity instanceof EntityBat || entity instanceof EntityGolem || entity instanceof EntityDragon && this.mob.getState()) && (double)entity.getDistanceToEntity(this.mc.thePlayer) <= this.reach.getValue() && (!entity.isInvisible() || this.invis.getState()) && (double)entity.ticksExisted > this.ticksexisted.getValue();
   }

   private void sortList(final List<EntityLivingBase> fuckkody) {
	   fuckkody.sort((o1, o2) -> (int)(o1.getDistanceToEntity((Entity)mc.thePlayer) - o2.getDistanceToEntity((Entity)mc.thePlayer)));
   }
   
   public void AddValidTargetsToArrayList() {
      for(int entities = 0; entities < Wrapper.getMinecraft().theWorld.loadedEntityList.size(); ++entities) {
         Entity entity = (Entity)Wrapper.getMinecraft().theWorld.getLoadedEntityList().get(entities);
             if (this.isValid(entity)) {
                 if(!this.entities.contains(entity)) {
                     this.entities.add(entity);
                 }
              } else {
                 this.entities.remove(entity);
              }
             //sortList(this.entities);
         }

   }

    public static float[] getRotations(EntityLivingBase ent) {
        double x = ent.posX;
        double z = ent.posZ;
        double y = ent.posY + ent.getEyeHeight() / 2.0F;
        return getRotationFromPosition(x, z, y);
    }

    public static float[] getRotationFromPosition(double x, double z, double y) {
        double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;

        double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
        float pitch = (float) -(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D);
        return new float[]{yaw, pitch};
    }

   public static boolean isOnSameTeam(Entity entity) {
		if(!teams.getState()) return false;
		if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().startsWith("\247")) {
           if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().length() <= 2
                   || entity.getDisplayName().getUnformattedText().length() <= 2) {
               return false;
           }
           if(Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
               return true;
           }
       }
		return false;
	}

    @SubscribeEvent
    public void onTest(EntityViewRenderEvent.CameraSetup event) {
        if (this.getState()) {
            if (event.entity == Wrapper.getPlayer() && entity != null && !Lockview.getState()) {
                if (Wrapper.getWorld() != null) {
                    float[] fakerot = this.getRotations((EntityLivingBase) entity);
                    event.pitch = fakerot[1];
                    event.yaw = fakerot[0];
                }
            }
        }
    }

   public void Attack(Entity entity) {
      if (this.mincps.getValue() > this.maxcps.getValue()) {
         this.mincps.setValue(this.maxcps.getValue());
      }


      if (this.entities.contains(entity)) {
    	 //Wrapper.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(fakerot[0],fakerot[1],Wrapper.getPlayer().onGround));
          Wrapper.getPlayer().swingItem();
          if (Lockview.getState()) {
              float[] fakerot = this.getRotations((EntityLivingBase) entity);
              mc.thePlayer.rotationPitch = fakerot[1];
              mc.thePlayer.rotationYaw = fakerot[0];
          }
          mc.objectMouseOver.entityHit = entity;
         Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), entity);
         mc.getNetHandler().addToSendQueue(new C0BPacketEntityAction(mc.thePlayer,C0BPacketEntityAction.Action.START_SPRINTING));
      }
   }
}

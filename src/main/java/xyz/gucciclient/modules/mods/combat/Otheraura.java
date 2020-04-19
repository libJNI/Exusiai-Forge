package xyz.gucciclient.modules.mods.combat;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Timer;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Otheraura extends Module {
   private BooleanValue player = new BooleanValue("Players", true);
   private BooleanValue mob = new BooleanValue("Mobs", false);
   private BooleanValue animal = new BooleanValue("Animals", false);
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

   public Otheraura() {
      super("Otheraura", 0, Module.Category.Combat);
      this.addValue(this.reach);
      this.addValue(this.maxcps);
      this.addValue(this.mincps);
      this.addValue(this.ticksexisted);
      this.addBoolean(this.invis);
      this.addBoolean(this.player);
      this.addBoolean(this.mob);
      this.addBoolean(this.animal);
      this.addBoolean(this.Switch);
   }

   public void onEnable() {
      this.entities.clear();
   }

   @SubscribeEvent
   public void onTick(TickEvent event) {
      if (Wrapper.getPlayer() != null) {
         if (Wrapper.getWorld() != null) {
            this.AddValidTargetsToArrayList();
            this.Attack();
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
      return entity != null && entity.isEntityAlive() && entity != this.mc.thePlayer && (entity instanceof EntityPlayer && this.player.getState() || entity instanceof EntityAnimal && this.animal.getState() || entity instanceof EntityMob || entity instanceof EntityBat || entity instanceof EntityGolem || entity instanceof EntityDragon && this.mob.getState()) && (double)entity.getDistanceToEntity(this.mc.thePlayer) <= this.reach.getValue() && (!entity.isInvisible() || this.invis.getState()) && (double)entity.ticksExisted > this.ticksexisted.getValue();
   }

   public void AddValidTargetsToArrayList() {
      for(int entities = 0; entities < Wrapper.getMinecraft().theWorld.loadedEntityList.size(); ++entities) {
         Entity entity = (Entity)Wrapper.getMinecraft().theWorld.getLoadedEntityList().get(entities);
         if (this.isValid(entity)) {
            this.entities.add(entity);
         } else {
            this.entities.remove(entity);
         }
      }

   }

   private float[] getAngles(EntityLivingBase entityLiving) {
      double difX = entityLiving.posX - Wrapper.getPlayer().posX;
      double difY = entityLiving.posY - Wrapper.getPlayer().posY + (double)(entityLiving.getEyeHeight() / 1.4F);
      double difZ = entityLiving.posZ - Wrapper.getPlayer().posZ;
      double hypo = (double)Wrapper.getPlayer().getDistanceToEntity(entityLiving);
      float yaw = (float)Math.toDegrees(Math.atan2(difZ, difX)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(difY, hypo)));
      return new float[]{yaw, pitch};
   }

   public float getDistanceBetweenAngles(float angle1, float angle2) {
      return Math.abs(angle1 % 360.0F - angle2 % 360.0F) % 360.0F;
   }

   public void Attack(Entity entity) {
      if (this.mincps.getValue() > this.maxcps.getValue()) {
         this.mincps.setValue(this.maxcps.getValue());
      }

      if (this.entities.contains(entity)) {
         Wrapper.getPlayer().swingItem();
         Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), entity);
      }
   }
}

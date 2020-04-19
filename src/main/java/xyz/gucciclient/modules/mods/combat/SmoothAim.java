package xyz.gucciclient.modules.mods.combat;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class SmoothAim extends Module {
   private NumberValue yawspeed = new NumberValue("Horizontal Speed", 4.0D, 0.0D, 5.0D);
   private NumberValue pitchspeed = new NumberValue("Vertical Speed", 0.0D, 0.0D, 5.0D);
   private NumberValue distance = new NumberValue("Range", 4.2D, 3.0D, 6.0D);
   private NumberValue fov = new NumberValue("FOV", 80.0D, 30.0D, 360.0D);
   private BooleanValue ClickAim = new BooleanValue("ClickAim", true);
   private BooleanValue Invisibles = new BooleanValue("Invisibles", false);
   private BooleanValue increaseonstrafe = new BooleanValue("Increase on strafe", false);
   private Random rand;
   private EntityPlayer Entity;

   public SmoothAim() {
      super(Module.Modules.SmoothAim.name(), 0, Module.Category.Combat);
      this.addValue(this.yawspeed);
      this.addValue(this.pitchspeed);
      this.addValue(this.distance);
      this.addValue(this.fov);
      this.addBoolean(this.ClickAim);
      this.addBoolean(this.Invisibles);
      this.addBoolean(this.increaseonstrafe);
   }

   @SubscribeEvent
   public void clientTick(TickEvent event) {
      if (Wrapper.getPlayer() != null) {
         if (this.Entity != null && (double)Wrapper.getPlayer().getDistanceToEntity(this.Entity) <= this.distance.getValue()) {
            if (this.IsValidEntity(this.Entity)) {
               float hspeed = (float)(this.yawspeed.getValue() * 3.0D);
               float vspeed = (float)(this.pitchspeed.getValue() * 3.0D);
               if (this.Entity != null && this.IsValidEntity(this.Entity)) {
                  hspeed = (float)((double)hspeed * 0.05D);
                  vspeed = (float)((double)vspeed * 0.02D);
               }

               if (this.increaseonstrafe.getState() && Wrapper.getPlayer().moveStrafing != 0.0F && Wrapper.getMinecraft().objectMouseOver == null && Wrapper.getMinecraft().objectMouseOver.entityHit == null) {
                  hspeed = (float)((double)hspeed + 1.5D);
               }

               if (Wrapper.getMinecraft().objectMouseOver != null && Wrapper.getMinecraft().objectMouseOver.entityHit != null) {
                  hspeed = 0.05F;
                  vspeed = 0.0F;
               }

               this.faceTarget(this.Entity, hspeed, vspeed);
            }
         } else {
            this.Entity = this.findEntity();
         }
      }

   }

   private boolean IsValidEntity(EntityPlayer entity) {
      return (!entity.isInvisible() || this.Invisibles.getState()) && Wrapper.getMinecraft().currentScreen == null && Wrapper.getPlayer().isEntityAlive() && entity.isEntityAlive() && (double)Wrapper.getPlayer().getDistanceToEntity(entity) <= this.distance.getValue() && (Wrapper.getMinecraft().gameSettings.keyBindAttack.isKeyDown() || !this.ClickAim.getState()) && (double)this.fov(entity) <= this.fov.getValue();
   }

   protected float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
      float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
      if (deltaAngle > maxIncrement) {
         deltaAngle = maxIncrement;
      }

      if (deltaAngle < -maxIncrement) {
         deltaAngle = -maxIncrement;
      }

      return currentRotation + deltaAngle / 2.0F;
   }

   private void faceTarget(Entity target, float yawspeed, float pitchspeed) {
      EntityPlayer player = Wrapper.getPlayer();
      float yaw = getAngles(target)[1];
      float pitch = getAngles(target)[0];
      player.rotationYaw = this.getRotation(player.rotationYaw, yaw, yawspeed);
      player.rotationPitch = this.getRotation(player.rotationPitch, pitch, pitchspeed);
   }

   private EntityPlayer findEntity() {
      if (Wrapper.getWorld() != null) {
         Iterator var1 = Wrapper.getWorld().playerEntities.iterator();

         while(var1.hasNext()) {
            Object object = var1.next();
            EntityPlayer player = (EntityPlayer)object;
            if (!player.getCommandSenderEntity().equals(Wrapper.getPlayer().getCommandSenderEntity()) && this.IsValidEntity(player)) {
               return player;
            }
         }
      }

      return null;
   }

   private int fov(Entity entity) {
      float[] neededRotations = getAngles(entity);
      if (neededRotations != null) {
         float distanceFromMouse = MathHelper.sqrt_float(Wrapper.getPlayer().rotationYaw - neededRotations[0] * Wrapper.getPlayer().rotationYaw - neededRotations[0] + Wrapper.getPlayer().rotationPitch - neededRotations[1] * Wrapper.getPlayer().rotationPitch - neededRotations[1]);
         return (int)distanceFromMouse;
      } else {
         return -1;
      }
   }

   public static float[] getAngles(Entity entity) {
      double x = entity.posX - Wrapper.getPlayer().posX;
      double z = entity.posZ - Wrapper.getPlayer().posZ;
      double y = entity.posY - 0.2D + (double)entity.getEyeHeight() - 0.4D - Wrapper.getPlayer().posY;
      double helper = (double)MathHelper.sqrt_double(x * x + z * z);
      float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
      float newPitch = (float)(-Math.toDegrees(Math.atan(y / helper)));
      if (z < 0.0D && x < 0.0D) {
         newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
      } else if (z < 0.0D && x > 0.0D) {
         newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
      }

      return new float[]{newPitch, newYaw};
   }
}

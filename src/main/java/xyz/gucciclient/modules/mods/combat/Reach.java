package xyz.gucciclient.modules.mods.combat;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.NumberValue;

public class Reach extends Module {
   private static NumberValue distance = new NumberValue("Range", 3.2D, 3.0D, 6.0D);
   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
   private AxisAlignedBB boundingBox;

   public Reach() {
      super("Reach", 0, Module.Category.Combat);
      this.boundingBox = ZERO_AABB;
      this.addValue(distance);
   }

   @SubscribeEvent
   public void mouse(MouseEvent event) {
      if (Wrapper.getPlayer() != null && event.buttonstate && event.button == 0 && (Wrapper.getMinecraft().objectMouseOver == null || Wrapper.getMinecraft().objectMouseOver.entityHit == null) && this.j(distance.getValue()) != null) {
         Wrapper.getPlayerController().attackEntity(Wrapper.getPlayer(), this.j(distance.getValue()));
      }

   }

   private Entity j(double distance) {
      MovingObjectPosition r = this.mc.thePlayer.rayTrace(distance, 1.0F);
      double distanceTo = distance;
      Vec3 getPosition = this.mc.thePlayer.getPositionEyes(1.0F);
      if (r != null) {
         distanceTo = r.hitVec.distanceTo(getPosition);
      }

      Vec3 ad = this.mc.thePlayer.getLook(1.0F);
      Vec3 addVector = getPosition.addVector(ad.xCoord * distance, ad.yCoord * distance, ad.zCoord * distance);
      Entity entity = null;
      List a = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.thePlayer, this.boundingBox.addCoord(ad.xCoord * distance, ad.yCoord * distance, ad.zCoord * distance).expand(1.0D, 1.0D, 1.0D));
      double d = distanceTo;
      int n3 = 0;

      for(int i = 0; i < a.size(); i = n3) {
         Entity entity2 = (Entity)a.get(n3);
         if (entity2.canBeCollidedWith()) {
            float getCollisionBorderSize = entity2.getCollisionBorderSize();
            AxisAlignedBB expand = this.boundingBox.expand((double)getCollisionBorderSize, (double)getCollisionBorderSize, (double)getCollisionBorderSize);
            MovingObjectPosition calculateIntercept = expand.calculateIntercept(getPosition, addVector);
            if (expand.isVecInside(getPosition)) {
               if (0.0D < d || d == 0.0D) {
                  entity = entity2;
                  d = 0.0D;
               }
            } else {
               double j;
               if (calculateIntercept != null && ((j = getPosition.distanceTo(calculateIntercept.hitVec)) < d || d == 0.0D)) {
                  if (entity2 == this.mc.thePlayer.ridingEntity) {
                     if (d == 0.0D) {
                        entity = entity2;
                     }
                  } else {
                     entity = entity2;
                     d = j;
                  }
               }
            }
         }

         ++n3;
      }

      return entity;
   }

   public static double getDistance() {
      return distance.getValue();
   }
}

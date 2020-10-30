package today.exusiai.modules.collection.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.MovementUtils;
import today.exusiai.utils.PlayerUtils;

public class Speed extends AbstractModule {
    private double moveSpeed;
    private double lastDist;
    private double y;
    private int stage;
    private int hops;

   public Speed() {
      super("Bhop", 0, AbstractModule.Category.Movement);
   }

   @Override
   public void onEnable() {
       this.y = 0.0D;
       this.hops = 1;
       this.moveSpeed = MovementUtils.getBaseMoveSpeed();
       this.lastDist = 0.0D;
       this.stage = 0;
       super.onEnable();
   }
   public static int randomNumber(int max, int min) {
       return Math.round((float)min + (float)Math.random() * (float)(max - min));
   }
   @Override
   public void onDisable() {
       mc.thePlayer.stepHeight = 0.625F;
       super.onDisable();
   }
   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (this.getState()) {
    	  if (PlayerUtils.isInLiquid()) {
              return;
          }
          EntityPlayerSP player = mc.thePlayer;
          if (PlayerUtils.isMoving2()) {
                  if (player.isCollidedVertically) {
                      mc.thePlayer.posY += 7.435E-4D;
                  }
          }

          double xDist = player.posX - player.prevPosX;
          double zDist = player.posZ - player.prevPosZ;
          this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
          
          if (!PlayerUtils.isInLiquid()) {
              if (PlayerUtils.isMoving2()) {
                  double rounded;
                  double difference;
                  switch(this.stage) {
                      case 2:
                          if (player.onGround && player.isCollidedVertically) {
                              player.motionY += MovementUtils.getJumpBoostModifier(0.41999998688697815D);
                              this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 1.5D;
                          }
                          break;
                      case 3:
                          rounded = 0.6200000047683716D * (this.lastDist - MovementUtils.getBaseMoveSpeed());
                          this.moveSpeed = this.lastDist + rounded;
                          break;
                      default:
                          if (mc.theWorld.getCollidingBoundingBoxes(player, player.getEntityBoundingBox().offset(0.0D, player.motionY, 0.0D)).size() > 0 || player.isCollidedVertically && player.onGround) {
                              this.stage = 1;
                              ++this.hops;
                          }

                          this.moveSpeed = this.lastDist - this.lastDist / 149.0D;
                  }

                  this.moveSpeed = Math.max(this.moveSpeed, MovementUtils.getBaseMoveSpeed());
                      MovementUtils.setSpeed(this.moveSpeed);
                  ++this.stage;
              }
          }
      }
   }
}

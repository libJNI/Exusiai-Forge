package xyz.gucciclient.modules.mods.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.MovementUtils;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public class Speed extends Module {
   private NumberValue vanillaspeed = new NumberValue("Vanilla speed", 1.2D, 0.1D, 2.0D);
   private BooleanValue janitor = new BooleanValue("Janitor", false);
   private BooleanValue Guardian = new BooleanValue("Guardian", false);
   private BooleanValue vanilla = new BooleanValue("Vanilla", false);
   private BooleanValue gcheat = new BooleanValue("Gcheat", false);
   private BooleanValue velt = new BooleanValue("Velt", false);

   public Speed() {
      super(Module.Modules.Speed.name(), 0, Module.Category.Movement);
      this.addBoolean(this.vanilla);
      this.addBoolean(this.Guardian);
      this.addBoolean(this.janitor);
      this.addBoolean(this.gcheat);
      this.addBoolean(this.velt);
      this.addValue(this.vanillaspeed);
   }

   public void onDisable() {
      MovementUtils.setSpeed(1.0D);
   }

   @SubscribeEvent
   public void onTick(ClientTickEvent event) {
      if (this.getState()) {
         if (Wrapper.getPlayer() != null) {
            if (Wrapper.getWorld() != null) {
               if (MovementUtils.isMoving()) {
                  if (!Wrapper.getPlayer().isSneaking()) {
                     if (!Wrapper.getPlayer().isInWater()) {
                        if (this.Guardian.getState()) {
                           if (MovementUtils.isMoving() && Wrapper.getPlayer().onGround && !Wrapper.getPlayer().isEating()) {
                              MovementUtils.setSpeed(0.5199999809265137D);
                              Wrapper.getPlayer().motionY = 0.2D;
                           } else {
                              MovementUtils.setSpeed(0.24D);
                              EntityPlayerSP player = Wrapper.getPlayer();
                              --player.motionY;
                           }
                        }

                        if (this.vanilla.getState()) {
                           MovementUtils.setSpeed(this.vanillaspeed.getValue());
                        }

                        if (this.janitor.getState()) {
                           boolean ticks = Wrapper.getPlayer().ticksExisted % 2 == 0;
                           MovementUtils.setSpeed((double)(MovementUtils.getSpeed() * (ticks ? 0.0F : 5.0F)));
                        }

                        if (this.gcheat.getState()) {
                           if (Wrapper.getPlayer().onGround) {
                              MovementUtils.setSpeed(0.499315221D);
                              Wrapper.getPlayer().motionY = 0.39936D;
                           } else {
                              MovementUtils.setSpeed(0.3894613D);
                           }
                        }

                        if (this.velt.getState() && Wrapper.getPlayer().onGround) {
                           MovementUtils.setSpeed(1.2D);
                        }

                     }
                  }
               }
            }
         }
      }
   }
}

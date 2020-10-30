package today.exusiai.modules.collection.render;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.collection.combat.Aura;
import today.exusiai.utils.*;
import today.exusiai.utils.animation.AnimationUtil;

import org.lwjgl.opengl.GL11;

import java.awt.*;

public class TargetHUD extends AbstractModule {
   private static final Color COLOR = new Color(0, 0, 0, 180);
   private final TimerUtil animationStopwatch = new TimerUtil();
   private EntityLivingBase target;
   private double healthBarWidth;
   private double hudHeight;

   public TargetHUD() {
      super("TargetHUD", 0, Category.Visuals);
   }

   @Override
   public void onEnable() {
   }

   @Override
   public void onDisable() {
   }

   @SubscribeEvent
   public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
      float scaledWidth = (float)Wrapper.getScaledResolution().getScaledWidth();
      float scaledHeight = (float)Wrapper.getScaledResolution().getScaledHeight();
       EntityLivingBase target = Aura.getkillaura.getTarget();
      if (target != null && AbstractModule.getModule(Aura.class).getState()) {
            float x = scaledWidth / 2.0F - 70.0F;
            float y = scaledHeight / 2.0F + 80.0F;
            float health = this.target.getHealth();
            double hpPercentage = (double)(health / this.target.getMaxHealth());
            hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0D, 1.0D);
            double hpWidth = 92.0D * hpPercentage;
            int healthColor = ColorUtils.getHealthColor(this.target.getHealth(), this.target.getMaxHealth()).getRGB();
            String healthStr = String.valueOf((float)((int)this.target.getHealth()) / 2.0F);
            if (this.animationStopwatch.hasReached(15L)) {
               this.healthBarWidth = AnimationUtil.animate(hpWidth, this.healthBarWidth, 0.3529999852180481D);
               this.hudHeight = AnimationUtil.animate(45.0D, this.hudHeight, 0.10000000149011612D);
               this.animationStopwatch.reset();
            }

            GL11.glEnable(3089);
            RenderUtils.prepareScissorBox(x, y, x + 140.0F, (float)((double)y + this.hudHeight));
            Gui.drawRect((int)x, (int)y, (int)(x + 140.0F), (int)(y + 40.0F), COLOR.getRGB());
            Gui.drawRect((int)(x + 40.0F), (int)(y + 15.0F), (int) ((int)(x + 40.0F) + this.healthBarWidth), (int)(y + 25.0F), healthColor);
            mc.fontRendererObj.drawStringWithShadow(healthStr, x + 40.0F + 46.0F - (float)mc.fontRendererObj.getStringWidth(healthStr) / 2.0F, y + 16.0F, -1);
            mc.fontRendererObj.drawStringWithShadow(this.target.getName(), x + 40.0F, y + 2.0F, -1);
            GuiInventory.drawEntityOnScreen((int)(x + 13.333333F), (int)(y + 40.0F), 20, this.target.rotationYaw, this.target.rotationPitch, this.target);
            GL11.glDisable(3089);
      } else {
         this.healthBarWidth = 92.0D;
         this.hudHeight = 0.0D;
         this.target = null;
      }

   }
}

package xyz.gucciclient.modules.mods.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.gucciclient.Client;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.ReflectionHelper;
import xyz.gucciclient.utils.RenderUtils;
import xyz.gucciclient.utils.Wrapper;

public class PlayerESP extends Module {
	public static int chestColorRed = 255;
	public static int chestColorGreen = 105;
	public static int chestColorBlue = 180;
   public PlayerESP() {
      super("PlayerESP", 0, Module.Category.Visuals);
   }
   
   @SubscribeEvent
	public void RenderWorld(RenderWorldLastEvent e) {
       for (Object object : Wrapper.getWorld().loadedEntityList) {
           if(object instanceof EntityLivingBase && !(object instanceof EntityArmorStand)) {
               EntityLivingBase entity = (EntityLivingBase)object;
               this.render(entity, e.partialTicks);
           }
       }
    }

    void render(EntityLivingBase entity, float ticks) {
        if(entity == Wrapper.getPlayer()) {
            return;
        }
        if(entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
        }
        if(entity.isInvisible()) {
            RenderUtils.drawESP(entity, 0.0f, 0.0f, 0.0f, 1.0f, ticks);
            return;
        }
        if(entity.hurtTime > 0) {
            RenderUtils.drawESP(entity, 1.0f, 0.0f, 0.0f, 1.0f, ticks);
            return;
        }
        RenderUtils.drawESP(entity, 1.0f, 1.0f, 1.0f, 1.0f, ticks);
    }

}

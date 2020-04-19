package xyz.gucciclient.modules.mods.render;

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
	for (EntityPlayer entityPlayer : Minecraft.getMinecraft().theWorld.playerEntities) {
			if(entityPlayer == Minecraft.getMinecraft().thePlayer) {
				
				renderPlayer(null);
			} else {
				
				renderPlayer(entityPlayer.getPosition());
			}
		}
   }
   
   public static void renderPlayer(BlockPos playerPos)
   {
 	 for(EntityPlayer entityplayer : Minecraft.getMinecraft().theWorld.playerEntities) {
 		 if(entityplayer != Minecraft.getMinecraft().thePlayer) {
 	 playerPos = entityplayer.getPosition();
 	 
 	 double x = Math.floor(entityplayer.posX) - Wrapper.getMinecraft().getRenderManager().viewerPosX;
 	 double y = Math.floor(entityplayer.posY)- Wrapper.getMinecraft().getRenderManager().viewerPosY;
 	 double z = Math.floor(entityplayer.posZ)- Wrapper.getMinecraft().getRenderManager().viewerPosZ;
     
     GL11.glPushMatrix();
     GL11.glEnable(3042);
     GL11.glBlendFunc(770, 771);
     GL11.glLineWidth(3.0F);
     GL11.glDisable(3553);
     GL11.glDisable(2929);
     GL11.glDepthMask(true);
     RenderGlobal.drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1.0D, y + 2.0D, z + 1.0D), chestColorRed, chestColorGreen, chestColorBlue, 255);
     GL11.glEnable(3553);
     GL11.glEnable(2929);
     GL11.glDepthMask(true);
     GL11.glDisable(3042);
     GL11.glPopMatrix();
 	 }
   }
 	 
 }
}

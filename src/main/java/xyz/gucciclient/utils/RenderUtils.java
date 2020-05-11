package xyz.gucciclient.utils;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;
import xyz.gucciclient.modules.mods.render.xray.XRayBlock;
import xyz.gucciclient.modules.mods.render.xray.XRayData;

public class RenderUtils {
   private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
   public static String DF (float value, int maxvalue) {
      DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
      df.setMaximumFractionDigits(maxvalue);
      return df.format(value);
   }

	public static Color effect(long offset, float brightness, int speed) {
		float hue = (float) (System.nanoTime() + (offset * speed)) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, brightness, 1F)).intValue()), 16);
		Color c = new Color((int) color);
		return new Color(c.getRed()/255.0F, c.getGreen()/255.0F, c.getBlue()/255.0F, c.getAlpha()/255.0F);
	}
	
    public static void renderStringWave(String s, float f, float yCount, float bright) {
    	int updateX = (int) f;
    	for(int i = 0; i < s.length(); i++) {
    		String str = s.charAt(i) + "";
    			Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(str, updateX, yCount, effect(i * 3500000L, bright, 100).getRGB());
    			updateX+=Wrapper.getMinecraft().fontRendererObj.getCharWidth(s.charAt(i));
    	}
    }
    
   public static void drawFullCircle(float cx, float cy, double r, int c) {
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      r *= 2.0D;
      cx *= 2.0F;
      cy *= 2.0F;
      float f = (float)(c >> 24 & 255) / 255.0F;
      float f2 = (float)(c >> 16 & 255) / 255.0F;
      float f3 = (float)(c >> 8 & 255) / 255.0F;
      float f4 = (float)(c & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(f2, f3, f4, f);
      GL11.glBegin(6);

      for(int i = 0; i <= 360; ++i) {
         double x = Math.sin((double)i * 3.141592653589793D / 180.0D) * r;
         double y = Math.cos((double)i * 3.141592653589793D / 180.0D) * r;
         GL11.glVertex2d((double)cx + x, (double)cy + y);
      }

      GL11.glEnd();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glScalef(2.0F, 2.0F, 2.0F);
   }

   public static void drawCheckmark(float x, float y, int hexColor) {
      GL11.glPushMatrix();
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      hexColor(hexColor);
      GL11.glLineWidth(2.0F);
      GL11.glBegin(1);
      GL11.glVertex2d((double)(x + 1.0F), (double)(y + 1.0F));
      GL11.glVertex2d((double)(x + 3.0F), (double)(y + 4.0F));
      GL11.glEnd();
      GL11.glBegin(1);
      GL11.glVertex2d((double)(x + 3.0F), (double)(y + 4.0F));
      GL11.glVertex2d((double)(x + 6.0F), (double)(y - 2.0F));
      GL11.glEnd();
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void drawArrow(float x, float y, boolean isOpen, int hexColor) {
      GL11.glPushMatrix();
      GL11.glScaled(1.3D, 1.3D, 1.3D);
      if (isOpen) {
         --y;
         x += 2.0F;
      }

      x = (float)((double)x / 1.3D);
      y = (float)((double)y / 1.3D);
      GL11.glEnable(2848);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      hexColor(hexColor);
      GL11.glLineWidth(2.0F);
      if (isOpen) {
         GL11.glBegin(1);
         GL11.glVertex2d((double)x, (double)y);
         GL11.glVertex2d((double)(x + 4.0F), (double)(y + 3.0F));
         GL11.glEnd();
         GL11.glBegin(1);
         GL11.glVertex2d((double)(x + 4.0F), (double)(y + 3.0F));
         GL11.glVertex2d((double)x, (double)(y + 6.0F));
         GL11.glEnd();
      } else {
         GL11.glBegin(1);
         GL11.glVertex2d((double)x, (double)y);
         GL11.glVertex2d((double)(x + 3.0F), (double)(y + 4.0F));
         GL11.glEnd();
         GL11.glBegin(1);
         GL11.glVertex2d((double)(x + 3.0F), (double)(y + 4.0F));
         GL11.glVertex2d((double)(x + 6.0F), (double)y);
         GL11.glEnd();
      }

      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void hexColor(int hexColor) {
      float red = (float)(hexColor >> 16 & 255) / 255.0F;
      float green = (float)(hexColor >> 8 & 255) / 255.0F;
      float blue = (float)(hexColor & 255) / 255.0F;
      float alpha = (float)(hexColor >> 24 & 255) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void drawBlockESP(BlockPos pos, float red, float green, float blue) {
      GL11.glPushMatrix();
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      GL11.glLineWidth(1);
      GL11.glDisable(GL11.GL_TEXTURE_2D);
      GL11.glEnable(GL11.GL_CULL_FACE);
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      GL11.glDisable(GL11.GL_LIGHTING);
      double renderPosX = Wrapper.getMinecraft().getRenderManager().viewerPosX;
      double renderPosY = Wrapper.getMinecraft().getRenderManager().viewerPosY;
      double renderPosZ = Wrapper.getMinecraft().getRenderManager().viewerPosZ;

      GL11.glTranslated(-renderPosX, -renderPosY, -renderPosZ);
      GL11.glTranslated(pos.getX(), pos.getY(), pos.getZ());

      GL11.glColor4f(red, green, blue, 0.30F);
      drawSolidBox();
      GL11.glColor4f(red, green, blue, 0.7F);
      drawOutlinedBox();

      GL11.glColor4f(1, 1, 1, 1);

      GL11.glEnable(GL11.GL_LIGHTING);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glDisable(GL11.GL_BLEND);
      GL11.glDisable(GL11.GL_LINE_SMOOTH);
      GL11.glPopMatrix();
   }

   public static void drawSolidBox() {

      drawSolidBox(DEFAULT_AABB);
   }

   public static void drawSolidBox(AxisAlignedBB bb) {

      GL11.glBegin(GL11.GL_QUADS);
      {
         GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

         GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

         GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

         GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
         GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

         GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

         GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      }
      GL11.glEnd();
   }

   public static void drawOutlinedBox() {

      drawOutlinedBox(DEFAULT_AABB);
   }

   public static void drawOutlinedBox(AxisAlignedBB bb) {

      GL11.glBegin(GL11.GL_LINES);
      {
         GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);

         GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);

         GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);

         GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);

         GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);

         GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

         GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

         GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

         GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);

         GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
         GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

         GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);

         GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
         GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
      }
      GL11.glEnd();
   }

   public static void drawESP(Entity entity, float colorRed, float colorGreen, float colorBlue, float colorAlpha, float ticks) {
      try {
         double renderPosX = Wrapper.getMinecraft().getRenderManager().viewerPosX;
         double renderPosY = Wrapper.getMinecraft().getRenderManager().viewerPosY;
         double renderPosZ = Wrapper.getMinecraft().getRenderManager().viewerPosZ;
         double xPos = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks) - renderPosX;
         double yPos = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks)  + entity.height / 2.0f - renderPosY;
         double zPos = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks) - renderPosZ;

         float playerViewY = Wrapper.getMinecraft().getRenderManager().playerViewY;
         float playerViewX = Wrapper.getMinecraft().getRenderManager().playerViewX;
         boolean thirdPersonView = Wrapper.getMinecraft().getRenderManager().options.thirdPersonView == 2;

         GL11.glPushMatrix();

         GlStateManager.translate(xPos, yPos, zPos);
         GlStateManager.rotate(-playerViewY, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((float)(thirdPersonView ? -1 : 1) * playerViewX, 1.0F, 0.0F, 0.0F);
         GL11.glEnable(GL11.GL_BLEND);
         GL11.glDisable(GL11.GL_TEXTURE_2D);
         GL11.glDisable(GL11.GL_LIGHTING);
         GL11.glDisable(GL11.GL_DEPTH_TEST);
         GL11.glDepthMask(false);
         GL11.glLineWidth(1.0F);
         GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
         GL11.glEnable(GL11.GL_LINE_SMOOTH);
         GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
         GL11.glBegin((int) 1);

         GL11.glVertex3d((double) 0, (double) 0+1, (double) 0.0);
         GL11.glVertex3d((double) 0-0.5, (double) 0+0.5, (double) 0.0);
         GL11.glVertex3d((double) 0, (double) 0+1, (double) 0.0);
         GL11.glVertex3d((double) 0+0.5, (double) 0+0.5, (double) 0.0);

         GL11.glVertex3d((double) 0, (double) 0, (double) 0.0);
         GL11.glVertex3d((double) 0-0.5, (double) 0+0.5, (double) 0.0);
         GL11.glVertex3d((double) 0, (double) 0, (double) 0.0);
         GL11.glVertex3d((double) 0+0.5, (double) 0+0.5, (double) 0.0);

         GL11.glEnd();
         GL11.glDepthMask(true);
         GL11.glEnable(GL11.GL_DEPTH_TEST);
         GL11.glEnable(GL11.GL_TEXTURE_2D);
         GL11.glEnable(GL11.GL_LIGHTING);
         GL11.glDisable(GL11.GL_LINE_SMOOTH);
         GL11.glDisable(GL11.GL_BLEND);
         GL11.glPopMatrix();
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public static void drawXRayBlocks(LinkedList<XRayBlock> blocks, float ticks) {
      GL11.glPushMatrix();
      GL11.glEnable(GL11.GL_BLEND);
      GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
      GL11.glEnable(GL11.GL_LINE_SMOOTH);
      GL11.glLineWidth(1);
      GL11.glDisable(GL11.GL_TEXTURE_2D);
      GL11.glEnable(GL11.GL_CULL_FACE);
      GL11.glDisable(GL11.GL_DEPTH_TEST);
      GL11.glDisable(GL11.GL_LIGHTING);

      WorldClient world = Wrapper.getWorld();
      EntityPlayerSP player = Wrapper.getPlayer();

      for(XRayBlock block : blocks) {
         BlockPos pos = block.getBlockPos();
         XRayData data = block.getxRayData();

         IBlockState iblockstate = world.getBlockState(pos);

         double x = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)ticks;
         double y = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)ticks;
         double z = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)ticks;

         int color = new Color(data.getRed(), data.getGreen(), data.getBlue(), 255).getRGB();
         //GLUtils.glColor(color);

         //AxisAlignedBB boundingBox = iblockstate.getSelectedBoundingBox(world, pos).grow(0.0020000000949949026D).offset(-x, -y, -z);
         //drawSelectionBoundingBox(boundingBox);
      }

      GL11.glEnable(GL11.GL_LIGHTING);
      GL11.glEnable(GL11.GL_DEPTH_TEST);
      GL11.glEnable(GL11.GL_TEXTURE_2D);
      GL11.glDisable(GL11.GL_BLEND);
      GL11. glDisable(GL11.GL_LINE_SMOOTH);
      GL11.glPopMatrix();
   }
}

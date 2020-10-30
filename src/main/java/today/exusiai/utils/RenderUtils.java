package today.exusiai.utils;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Locale;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import today.exusiai.font.Fonts;

import org.lwjgl.opengl.GL11;

public class RenderUtils {
	private static Frustum frustrum = new Frustum();
   private static final AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
   public static String DF (float value, int maxvalue) {
      DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
      df.setMaximumFractionDigits(maxvalue);
      return df.format(value);
   }

   public static void drawRect(double x, double y, double x2, double y2, final int color) {
       if (x < x2) {
           final float e = (float) x;
           x = x2;
           x2 = e;
       }
       if (y < y2) {
           final float e = (float) y;
           y = y2;
           y2 = e;
       }
       final float a = (color >> 24 & 0xFF) / 255.0f;
       final float b = (color >> 16 & 0xFF) / 255.0f;
       final float c = (color >> 8 & 0xFF) / 255.0f;
       final float d = (color & 0xFF) / 255.0f;
       final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
       GlStateManager.enableBlend();
       GlStateManager.disableTexture2D();
       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
       GlStateManager.color(b, c, d, a);
       worldRenderer.begin(7, DefaultVertexFormats.POSITION);
       worldRenderer.pos((double)x, (double)y2, 0.0).endVertex();
       worldRenderer.pos((double)x2, (double)y2, 0.0).endVertex();
       worldRenderer.pos((double)x2, (double)y, 0.0).endVertex();
       worldRenderer.pos((double)x, (double)y, 0.0).endVertex();
       Tessellator.getInstance().draw();
       GlStateManager.enableTexture2D();
       GlStateManager.disableBlend();
       GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
   }
   
   public static void layeredRect(final float x1, final float y1, final float x2, final float y2, final int outline, final int inline, final int background) {
       drawRect(x1, y1, x2, y2, outline);
       drawRect(x1 + 0.5f, y1 + 0.5f, x2 - 0.5f, y2 - 0.5f, inline);
       drawRect(x1 + 1.0f, y1 + 1.0f, x2 - 1.0f, y2 - 1.0f, background);
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
           Fonts.Roboto18.drawStringWithShadow(str, updateX, yCount, effect(i * 3500000L, bright, 100).getRGB());
    			updateX += Fonts.Roboto18.getStringWidth(String.valueOf(s.charAt(i)));
    	}
    }

    public static int rainbow(int delay) {
        double rainbow = Math.ceil((double)(System.currentTimeMillis() + (long)delay) / 10.0D);
        return Color.getHSBColor((float)((rainbow %= 360.0D) / 360.0D), 0.35F, 1.0F).getRGB();
     }
    
   public static void entityESPBox(Entity entity,float redRGB, int greenRGB, int blueRGB, int alpha) {
      GlStateManager.pushMatrix();
      float red = 0.003921569F * redRGB;
      float green = 0.003921569F * greenRGB;
      float blue = 0.003921569F * blueRGB;
      GL11.glColor4f(red, green, blue, alpha);
      GlStateManager.enableAlpha();
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      try {
         RenderGlobal.drawSelectionBoundingBox(new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05D - entity.posX + entity.posX - ReflectionHelper.getRenderPosX(), entity.getEntityBoundingBox().minY - entity.posY + entity.posY - ReflectionHelper.getRenderPosY(), entity.getEntityBoundingBox().minZ - 0.05D - entity.posZ + entity.posZ - ReflectionHelper.getRenderPosZ(), entity.getEntityBoundingBox().maxX + 0.05D - entity.posX + entity.posX - ReflectionHelper.getRenderPosX(), entity.getEntityBoundingBox().maxY + 0.1D - entity.posY + entity.posY - ReflectionHelper.getRenderPosY(), entity.getEntityBoundingBox().maxZ + 0.05D - entity.posZ + entity.posZ - ReflectionHelper.getRenderPosZ()));
      } catch (Throwable throwable) {
         throwable.printStackTrace();
      }
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GlStateManager.popMatrix();
   }

    public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
        float f = (col1 >> 24 & 0xFF) / 255.0F;
        float f1 = (col1 >> 16 & 0xFF) / 255.0F;
        float f2 = (col1 >> 8 & 0xFF) / 255.0F;
        float f3 = (col1 & 0xFF) / 255.0F;
        
        float f4 = (col2 >> 24 & 0xFF) / 255.0F;
        float f5 = (col2 >> 16 & 0xFF) / 255.0F;
        float f6 = (col2 >> 8 & 0xFF) / 255.0F;
        float f7 = (col2 & 0xFF) / 255.0F;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);

        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);

        GL11.glColor4f(f5, f6, f7, f4);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();

        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
        GL11.glColor4d(255, 255, 255, 255);
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
	   Fonts.Roboto18.drawString(isOpen ? ">" : "+", x, y - 1, hexColor);
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

   public static void prepareScissorBox(float x, float y, float x2, float y2) {
      ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
      int factor = scale.getScaleFactor();
      GL11.glScissor((int)(x * (float)factor), (int)(((float)scale.getScaledHeight() - y2) * (float)factor), (int)((x2 - x) * (float)factor), (int)((y2 - y) * (float)factor));
   }

   public static void rectangle(double left, double top, double right, double bottom, int color) {
       double var5;
       if (left < right) {
           var5 = left;
           left = right;
           right = var5;
       }
       if (top < bottom) {
           var5 = top;
           top = bottom;
           bottom = var5;
       }
       float var11 = (float) (color >> 24 & 255) / 255.0f;
       float var6 = (float) (color >> 16 & 255) / 255.0f;
       float var7 = (float) (color >> 8 & 255) / 255.0f;
       float var8 = (float) (color & 255) / 255.0f;
       Tessellator tessellator = Tessellator.getInstance();
       WorldRenderer worldRenderer = tessellator.getWorldRenderer();
       GlStateManager.enableBlend();
       GlStateManager.disableTexture2D();
       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
       GlStateManager.color(var6, var7, var8, var11);
       worldRenderer.begin(7, DefaultVertexFormats.POSITION);
       worldRenderer.pos(left, bottom, 0.0).endVertex();
       worldRenderer.pos(right, bottom, 0.0).endVertex();
       worldRenderer.pos(right, top, 0.0).endVertex();
       worldRenderer.pos(left, top, 0.0).endVertex();
       tessellator.draw();
       GlStateManager.enableTexture2D();
       GlStateManager.disableBlend();
       GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
   }

    public static int width() {
        return new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth();
    }

    private static void BBDraw1(AxisAlignedBB aa, Tessellator tessellator, WorldRenderer worldRenderer) {
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        tessellator.draw();
    }
    
    private static void BBDraw3(AxisAlignedBB aa, WorldRenderer worldRenderer) {
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
    }

    private static void BBDraw2(AxisAlignedBB aa, WorldRenderer worldRenderer) {
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
    }
    
    private static void BBDrawA(AxisAlignedBB aa, Tessellator tessellator, WorldRenderer worldRenderer) {
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        BBDraw3(aa, worldRenderer);
        tessellator.draw();
        worldRenderer.begin(3, DefaultVertexFormats.POSITION);
        BBDraw2(aa, worldRenderer);
        tessellator.draw();
        worldRenderer.begin(1, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        BBDraw1(aa, tessellator, worldRenderer);
    }
    
    public static void drawBoundingBox(AxisAlignedBB aa) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        BBDraw1(aa, tessellator, worldRenderer);
        worldRenderer.endVertex();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.endVertex();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        BBDraw2(aa, worldRenderer);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.endVertex();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        BBDraw3(aa, worldRenderer);
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.endVertex();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        tessellator.draw();
        worldRenderer.endVertex();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(aa.minX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.minX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.minX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.minZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.maxY, aa.maxZ).endVertex();
        worldRenderer.pos(aa.maxX, aa.minY, aa.maxZ).endVertex();
        tessellator.draw();
        worldRenderer.endVertex();
    }

    public static void drawOutlinedBoundingBox(final AxisAlignedBB aa) {
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        BBDrawA(aa, tessellator, worldRenderer);
    }

    public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor,int borderColor) {
    	rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
    	rectangle(x + width, y, x1 - width, y + width, borderColor);
    	rectangle(x, y, x + width, y1, borderColor);
    	rectangle(x1 - width, y, x1, y1, borderColor);
    	rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
    }

    public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
        drawRect(x + 0.5F, y, x1 - 0.5F, y + 0.5F, insideC);
        drawRect(x + 0.5F, y1 - 0.5F, x1 - 0.5F, y1, insideC);
        drawRect(x, y + 0.5F, x1, y1 - 0.5F, insideC);
    }
    
    public static void drawRoundedRect(int xCoord, int yCoord, int xSize, int ySize, int colour) {
        int width = xCoord + xSize;
        int height = yCoord + ySize;
        drawRect(xCoord + 1, yCoord, width - 1, height, colour);
        drawRect(xCoord, yCoord + 1, width, height - 1, colour);
    }

	public static void setupRender(final boolean start) {
		if (start) {
			GlStateManager.enableBlend();
			GL11.glEnable(2848);
			GlStateManager.disableDepth();
			GlStateManager.disableTexture2D();
			GlStateManager.blendFunc(770, 771);
			GL11.glHint(3154, 4354);
		} else {
			GlStateManager.disableBlend();
			GlStateManager.enableTexture2D();
			GL11.glDisable(2848);
			GlStateManager.enableDepth();
		}
		GlStateManager.depthMask(!start);
	}

	public static Vec3 interpolateRender(EntityPlayer player) {
		float part = ReflectionHelper.getPartialTicks();
		double interpX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) part;
		double interpY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) part;
		double interpZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) part;
		return new Vec3(interpX, interpY, interpZ);
	}

	public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
		      GL11.glPushMatrix();
		      cx *= 2.0F;
		      cy *= 2.0F;
		      float f = (float)(c >> 24 & 255) / 255.0F;
		      float f1 = (float)(c >> 16 & 255) / 255.0F;
		      float f2 = (float)(c >> 8 & 255) / 255.0F;
		      float f3 = (float)(c & 255) / 255.0F;
		      float theta = (float)(6.2831852D / (double)num_segments);
		      float p = (float)Math.cos((double)theta);
		      float s = (float)Math.sin((double)theta);
		      float x = r *= 2.0F;
		      float y = 0.0F;
		      enableGL2D();
		      GL11.glScalef(0.5F, 0.5F, 0.5F);
		      GL11.glColor4f(f1, f2, f3, f);
		      GL11.glBegin(2);

		      for(int ii = 0; ii < num_segments; ++ii) {
		         GL11.glVertex2f(x + cx, y + cy);
		         float t = x;
		         x = p * x - s * y;
		         y = s * t + p * y;
		      }

		      GL11.glEnd();
		      GL11.glScalef(2.0F, 2.0F, 2.0F);
		      disableGL2D();
		      GL11.glPopMatrix();
	}
	   
	public static void enableGL2D() {
		      GL11.glDisable(2929);
		      GL11.glEnable(3042);
		      GL11.glDisable(3553);
		      GL11.glBlendFunc(770, 771);
		      GL11.glDepthMask(true);
		      GL11.glEnable(2848);
		      GL11.glHint(3154, 4354);
		      GL11.glHint(3155, 4354);
	}

	public static void disableGL2D() {
		      GL11.glEnable(3553);
		      GL11.glDisable(3042);
		      GL11.glEnable(2929);
		      GL11.glDisable(2848);
		      GL11.glHint(3154, 4352);
		      GL11.glHint(3155, 4352);
	}

	public static void init3D() {
		GL11.glDisable( GL11.GL_TEXTURE_2D );
		GL11.glDisable( GL11.GL_DEPTH_TEST );
		GL11.glDisable( GL11.GL_CULL_FACE );
		GL11.glDepthMask(false);
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA );
		GL11.glLineWidth( 1f );
	}
	
	public static void reset3D() {
		GL11.glDepthMask(true);
		GL11.glDisable( GL11.GL_BLEND );
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glEnable( GL11.GL_CULL_FACE );
	}
	
	   public static void drawBlockESP(BlockPos pos) {
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
		      GL11.glColor4f(0,1,1,0.5f);
		      //GL11.glColor4f(red, green, blue, 0.30F);
		      drawSolidBox();
		      GL11.glColor4f(0,1,1,0.5f);
		      //GL11.glColor4f(red, green, blue, 0.7F);
		      drawOutlinedBox();

		      GL11.glColor4f(1, 1, 1, 1);

		      GL11.glEnable(GL11.GL_LIGHTING);
		      GL11.glEnable(GL11.GL_DEPTH_TEST);
		      GL11.glEnable(GL11.GL_TEXTURE_2D);
		      GL11.glDisable(GL11.GL_BLEND);
		      GL11.glDisable(GL11.GL_LINE_SMOOTH);
		      GL11.glPopMatrix();
		   }

	    public static void drawBorderedRect(float x2, float y2, float x22, float y22, float l1, int col1, int col2) {
	        RenderUtils.drawRect(x2, y2, x22, y22, col2);
	        float f2 = (float)(col1 >> 24 & 255) / 255.0f;
	        float f22 = (float)(col1 >> 16 & 255) / 255.0f;
	        float f3 = (float)(col1 >> 8 & 255) / 255.0f;
	        float f4 = (float)(col1 & 255) / 255.0f;
	        GL11.glPushMatrix();
	        GL11.glEnable(3042);
	        GL11.glDisable(3553);
	        GL11.glBlendFunc(770, 771);
	        GL11.glEnable(2848);
	        GL11.glColor4f(f22, f3, f4, f2);
	        GL11.glLineWidth(l1);
	        GL11.glBegin(1);
	        GL11.glVertex2d(x2, y2);
	        GL11.glVertex2d(x2, y22);
	        GL11.glVertex2d(x22, y22);
	        GL11.glVertex2d(x22, y2);
	        GL11.glVertex2d(x2, y2);
	        GL11.glVertex2d(x22, y2);
	        GL11.glVertex2d(x2, y22);
	        GL11.glVertex2d(x22, y22);
	        GL11.glEnd();
	        GL11.glEnable(3553);
	        GL11.glDisable(3042);
	        GL11.glDisable(2848);
	        GL11.glPopMatrix();
	    }
}

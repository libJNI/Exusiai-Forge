package xyz.gucciclient.utils;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

public class Render {
	
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
}

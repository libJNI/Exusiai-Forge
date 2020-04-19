package xyz.gucciclient.modules.mods.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.gucciclient.Client;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.RenderLivingEvent.Specials.Pre;


public class Nametag extends Module {
	   private NumberValue scale = new NumberValue("Scale", 1.0D, 0.0D, 2.0D);
	   private BooleanValue distance = new BooleanValue("Distance",false);
	   private BooleanValue health = new BooleanValue("Health", true);
	   private BooleanValue item = new BooleanValue("Item", true);
   public Nametag() {
      super("Nametag", 0, Module.Category.Visuals);
      this.addValue(this.scale);
      this.addBoolean(this.distance);
      this.addBoolean(this.health);
      this.addBoolean(this.item);
   }

   @SubscribeEvent
   public void onPre(Pre d) {
      if (!d.entity.equals(Minecraft.getMinecraft().thePlayer) && d.entity instanceof EntityPlayer) {
         this.x((EntityPlayer)d.entity, d.x, d.y, d.z);
         d.setCanceled(true);
      }
   }

   public static boolean i(float n, float b, float v) {
	      return n >= b && n <= v;
	   }

	   public static String g(int l) {
	      String s = "";

	      for(int i = 0; i < l; ++i) {
	         s = s + " ";
	      }

	      return s;
	   }

	   public static float l(float v, int d) {
	      return (float)(Math.floor((double)v * Math.pow(10.0D, (double)d)) / Math.pow(10.0D, (double)d));
	   }

	   public static void d(float x, float y, float w, float h, int c) {
	      GL11.glEnable(3042);
	      GL11.glDisable(2884);
	      GL11.glDisable(3553);
	      GL11.glEnable(2848);
	      GL11.glBlendFunc(770, 771);
	      GL11.glLineWidth(1.0F);
	      Minecraft.getMinecraft().getResourceManager();
	      float alpha = (float)(c >> 24 & 255) / 255.0F;
	      float red = (float)(c >> 16 & 255) / 255.0F;
	      float green = (float)(c >> 8 & 255) / 255.0F;
	      float blue = (float)(c & 255) / 255.0F;
	      GL11.glColor4f(red, green, blue, alpha);
	      GL11.glBegin(7);
	      GL11.glVertex2d((double)x, (double)y);
	      GL11.glVertex2d((double)(x + w), (double)y);
	      GL11.glVertex2d((double)(x + w), (double)(y + h));
	      GL11.glVertex2d((double)x, (double)(y + h));
	      GL11.glEnd();
	      GL11.glDisable(3042);
	      GL11.glEnable(2884);
	      GL11.glEnable(3553);
	      GL11.glDisable(2848);
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GL11.glShadeModel(7424);
	      GL11.glDisable(3042);
	      GL11.glEnable(3553);
	   }

	   private void x(EntityPlayer e, double x, double y, double z) {
	      y += e.isSneaking() ? 0.5D : 0.7D;
	      String h = EnumChatFormatting.GREEN.toString();
	      double heaelth = Math.ceil((double)(e.getHealth() + e.getAbsorptionAmount())) / 2.0D;
	      if (i((float)heaelth, 5.0F, 6.5F)) {
	         h = EnumChatFormatting.YELLOW.toString();
	      } else if (i((float)heaelth, 0.0F, 5.0F)) {
	         h = EnumChatFormatting.RED.toString();
	      }

	      String d = EnumChatFormatting.GREEN.toString();
	      if (i(Math.max(1.6F, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 30.0F, 50.0F)) {
	         d = EnumChatFormatting.DARK_GREEN.toString();
	      } else if (i(Math.max(1.6F, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 15.0F, 30.0F)) {
	         d = EnumChatFormatting.YELLOW.toString();
	      } else if (Math.max(1.6F, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)) < 15.0F) {
	         d = EnumChatFormatting.RED.toString();
	      }

	      String na = ScorePlayerTeam.formatPlayerName(e.getTeam(), e.getDisplayNameString()).replace(" ", "").trim();
	      String k = (health.getState() ? h + heaelth + " " : "") + g(Minecraft.getMinecraft().fontRendererObj.getStringWidth(na) / 3) + (distance.getState() ? " " + d + l(Math.max(1.6F, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 1) + "m" : " ");
	      int w = Minecraft.getMinecraft().fontRendererObj.getStringWidth((health.getState() ? heaelth + " " : "") + g(Minecraft.getMinecraft().fontRendererObj.getStringWidth(na) / 3) + (distance.getState() ? " " + l(Math.max(1.6F, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)), 1) + "m" : " ")) / 2;
	      float o = (float)((double)(Math.max(1.6F, Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e)) / 125.0F) * scale.getValue());
	      GL11.glPushMatrix();
	      GL11.glTranslated(x, y + 1.4D, z);
	      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
	      RenderManager renderManager = mc.getRenderManager();
	      GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	      GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	      GL11.glScalef(-o, -o, o);
	      GL11.glDisable(2896);
	      GL11.glDisable(2929);
	      GL11.glEnable(3042);
	      GL11.glBlendFunc(770, 771);
	      int f = (new Color(20, 20, 20, 100)).getRGB();
	      d((float)(-w - 4), (float)(-(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1)), (float)(w * 2 + 5), (float)(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 3), f);
	      d(k, -w - 2, -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1), -1);
	      int naa = e.isInvisible() ? 754431 : (e.isSneaking() ? 12257310 : -1);
	      d(na, -w - 2 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(health.getState() ? h + heaelth + " " : ""), -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1), naa);
	      GL11.glEnable(2929);
	      GL11.glEnable(32823);
	      GL11.glPolygonOffset(1.0F, -1.0E7F);
	      if (item.getState()) {
	         int a = 0;

	         int i;
	         for(i = 0; i < e.inventory.armorInventory.length; ++i) {
	            if (e.inventory.armorInventory[i] != null) {
	               a -= 8;
	            }
	         }

	         if (e.getHeldItem() != null) {
	            a -= 8;
	            ItemStack r = e.getHeldItem().copy();
	            if (r.hasEffect() && (r.getItem() instanceof ItemTool || r.getItem() instanceof ItemArmor)) {
	               r.stackSize = 1;
	            }

	            this.r(r, a, -26);
	            a += 16;
	         }

	         for(i = 0; i < e.inventory.armorInventory.length; ++i) {
	            if (e.inventory.armorInventory[i] != null) {
	               ItemStack r2 = e.inventory.armorInventory[i].copy();
	               if (r2.hasEffect() && (r2.getItem() instanceof ItemTool || r2.getItem() instanceof ItemArmor)) {
	                  r2.stackSize = 1;
	               }

	               this.r(r2, a, -26);
	               a += 16;
	            }
	         }
	      }

	      GL11.glPolygonOffset(1.0F, 1000000.0F);
	      GL11.glDisable(32823);
	      GL11.glDisable(3042);
	      GL11.glEnable(2896);
	      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	      GL11.glPopMatrix();
	   }

	   private void r(ItemStack s, int x, int y) {
	      mc.getRenderItem().zLevel = -50.0F;
	      mc.getRenderItem().renderItemAndEffectIntoGUI(s, x, y);
	      mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRendererObj, s, x, y, "");
	      GL11.glDisable(2896);
	      GL11.glColor3f(1.0F, 1.0F, 1.0F);
	      GL11.glScalef(0.5F, 0.5F, 0.5F);
	      GL11.glScalef(2.0F, 2.0F, 2.0F);
	   }

	   public static void d(String str, int x, int y, int c) {
	      if (str != null) {
	         boolean blend = GL11.glIsEnabled(3042);
	         GL11.glDisable(3042);
	         Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(str, (float)x, (float)y, c);
	         if (blend) {
	            GL11.glEnable(3042);
	         }

	      }
	   }
}

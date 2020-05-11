package xyz.gucciclient.modules.mods.render;

import akka.actor.Kill;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.gucciclient.Client;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.modules.mods.combat.Killaura;
import xyz.gucciclient.utils.GLUtil;
import xyz.gucciclient.utils.ReflectionHelper;
import xyz.gucciclient.utils.RotationUtils;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

import java.awt.Color;
import java.text.DecimalFormat;

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
	public boolean drawing = false;
	public Nametag() {
		super("Nametag", 0, Module.Category.Visuals);
	}

	@SubscribeEvent
	public void onPre(Pre d){
	d.setCanceled(drawing);
	}

	@Override
	public void onEnable() {
		drawing = true;
		super.onEnable();
	}

	@Override
	public void onDisable() {
		drawing = false;
		super.onDisable();
	}

	@SubscribeEvent
	public void onRenderWorldLastEvent(RenderWorldLastEvent d) throws Throwable {
		for (Object object : Wrapper.getWorld().loadedEntityList) {
			try {
				if (object instanceof EntityPlayer) {
					EntityPlayer entityplayer = (EntityPlayer) object;
					if (!entityplayer.isDead && entityplayer != Wrapper.getPlayer()) {
						double d0 = entityplayer.lastTickPosX + (entityplayer.posX - entityplayer.lastTickPosX) * (double) d.partialTicks - ReflectionHelper.getRenderPosX();
						double d1 = entityplayer.lastTickPosY + (entityplayer.posY - entityplayer.lastTickPosY) * (double) d.partialTicks - ReflectionHelper.getRenderPosY();
						double d2 = entityplayer.lastTickPosZ + (entityplayer.posZ - entityplayer.lastTickPosZ) * (double) d.partialTicks - ReflectionHelper.getRenderPosZ();
						drawing = true;
						this.renderNameTag(entityplayer, entityplayer.getDisplayName().getFormattedText(), d0, d1, d2);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ) {
		RenderManager rendermanager = this.mc.getRenderManager();
		float f = Wrapper.getPlayer().getDistanceToEntity(entity) / 6.0F;
		if(f < 1.1F) {
			f = 1.1F;
		}

		pY = pY + (entity.isSneaking()?0.5D:0.7D);
		float f1 = f * 2.0F;
		f1 = f1 / 100.0F;
		GL11.glPushMatrix();
		GL11.glTranslatef((float)pX, (float)pY + 1.4F, (float)pZ);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-rendermanager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(rendermanager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f1, -f1, f1);
		GLUtil.setGLCap(2896, false);
		GLUtil.setGLCap(2929, false);
		GLUtil.setGLCap(3042, true);
		GL11.glBlendFunc(770, 771);
		float f2 = entity.getHealth();
		String s = "";
		if(f2 > 6.0F && f2 <= 12.0F) {
			s = EnumChatFormatting.YELLOW + String.valueOf((int)f2);
		} else if(f2 <= 6.0F) {
			s = EnumChatFormatting.RED + String.valueOf((int)f2);
		} else {
			s = EnumChatFormatting.GREEN + String.valueOf((int)f2);
		}

		String s1 = EnumChatFormatting.GRAY + "" + (int)Wrapper.getPlayer().getDistanceToEntity(entity) + "m " + EnumChatFormatting.RESET + tag + " " + s;
		int i = this.mc.fontRendererObj.getStringWidth(s1) / 2;
		GLUtil.setGLCap(3042, true);
		GL11.glBlendFunc(770, 771);
		this.drawBorderedRectNameTag((float)(-i - 1), (float)(-this.mc.fontRendererObj.FONT_HEIGHT), (float)(i + 2), 2.0F, 1.0F, 1426063360, Integer.MIN_VALUE);
		if(Killaura.isOnSameTeam(entity)) {
			int j = this.mc.fontRendererObj.getStringWidth("Team") / 2;
			this.drawBorderedRectNameTag((float)(-j - 1), -20.0F, (float)(j + 2), (float)(-this.mc.fontRendererObj.FONT_HEIGHT - 1), 1.0F, 1426063360, Integer.MIN_VALUE);
			this.mc.fontRendererObj.drawString(EnumChatFormatting.GREEN + "Team", -j + 1, -(this.mc.fontRendererObj.FONT_HEIGHT + 10), -1);
		}

		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		this.mc.fontRendererObj.drawString(s1, -i + 1, -(this.mc.fontRendererObj.FONT_HEIGHT - 2), -1);
		GL11.glPushMatrix();
		int k = 3;

		for(ItemStack itemstack : entity.inventory.armorInventory) {
			if(itemstack != null) {
				k -= 11;
			}
		}

		if(entity.getHeldItem() != null) {
			k = k - 5;
			Object object = entity.getHeldItem().copy();
			if(((ItemStack)object).hasEffect() && (((ItemStack)object).getItem() instanceof ItemTool || ((ItemStack)object).getItem() instanceof ItemArmor)) {
				((ItemStack)object).stackSize = 1;
			}

			this.renderItemStack((ItemStack)object, k, Killaura.isOnSameTeam(entity)?-35:-25);
			k = k + 20;
		}

		for(ItemStack itemstack1 : entity.inventory.armorInventory) {
			if(itemstack1 != null) {
				ItemStack itemstack2 = itemstack1.copy();
				if(itemstack2.hasEffect() && (itemstack2.getItem() instanceof ItemTool || itemstack2.getItem() instanceof ItemArmor)) {
					itemstack2.stackSize = 1;
				}

				this.renderItemStack(itemstack2, k, Killaura.isOnSameTeam(entity)?-35:-25);
				k += 20;
			}
		}

		GL11.glPopMatrix();
		GLUtil.revertAllCaps();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	public void drawBorderedRectNameTag(float x, float y, float x2, float y2, float l1, int col1, int col2) {
		drawRect(x, y, x2, y2, col2);
		float f = (float)(col1 >> 24 & 255) / 255.0F;
		float f1 = (float)(col1 >> 16 & 255) / 255.0F;
		float f2 = (float)(col1 >> 8 & 255) / 255.0F;
		float f3 = (float)(col1 & 255) / 255.0F;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(1);
		GL11.glVertex2d((double)x, (double)y);
		GL11.glVertex2d((double)x, (double)y2);
		GL11.glVertex2d((double)x2, (double)y2);
		GL11.glVertex2d((double)x2, (double)y);
		GL11.glVertex2d((double)x, (double)y);
		GL11.glVertex2d((double)x2, (double)y);
		GL11.glVertex2d((double)x, (double)y2);
		GL11.glVertex2d((double)x2, (double)y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public static void drawRect(float g, float h, float i, float j, int col1) {
		float f = (float)(col1 >> 24 & 255) / 255.0F;
		float f1 = (float)(col1 >> 16 & 255) / 255.0F;
		float f2 = (float)(col1 >> 8 & 255) / 255.0F;
		float f3 = (float)(col1 & 255) / 255.0F;
		GL11.glEnable(3042);
		GL11.glDisable(3553);
		GL11.glBlendFunc(770, 771);
		GL11.glEnable(2848);
		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(7);
		GL11.glVertex2d((double)i, (double)h);
		GL11.glVertex2d((double)g, (double)h);
		GL11.glVertex2d((double)g, (double)j);
		GL11.glVertex2d((double)i, (double)j);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable(3553);
		GL11.glDisable(3042);
		GL11.glDisable(2848);
	}

	public void renderItemStack(ItemStack stack, int x, int y) {
		GL11.glPushMatrix();
		GL11.glDepthMask(true);
		GlStateManager.clear(256);
		RenderHelper.enableStandardItemLighting();
		this.mc.getRenderItem().zLevel = -150.0F;
		this.whatTheFuckOpenGLThisFixesItemGlint();
		this.mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
		this.mc.getRenderItem().renderItemOverlays(this.mc.fontRendererObj, stack, x, y);
		this.mc.getRenderItem().zLevel = 0.0F;
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableCull();
		GlStateManager.enableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.disableLighting();
		GlStateManager.scale(0.5D, 0.5D, 0.5D);
		GlStateManager.disableDepth();
		GlStateManager.enableDepth();
		GlStateManager.scale(2.0F, 2.0F, 2.0F);
		GL11.glPopMatrix();
	}

	public void whatTheFuckOpenGLThisFixesItemGlint() {
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
	}
}

package today.exusiai.modules.collection.render;

import java.awt.Color;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.MathHelper;
import today.exusiai.event.ActiveEvent;
import today.exusiai.event.events.EventRenderGui;
import today.exusiai.font.Fonts;
import today.exusiai.font.TTFFontRenderer;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.ModuleManager;
import today.exusiai.utils.ColorUtils;
import today.exusiai.utils.RenderUtils;
import today.exusiai.utils.Wrapper;
import today.exusiai.utils.animation.Opacity;
import today.exusiai.values.BooleanValue;

import com.mojang.realmsclient.gui.ChatFormatting;

public class Screen extends AbstractModule {
   public static BooleanValue ETBMode = new BooleanValue("ETBMode", false);
   private final BooleanValue userinfo = new BooleanValue("UserInfo", false);
   private final BooleanValue coord = new BooleanValue("Coord", false);
   private final BooleanValue logo = new BooleanValue("Watermark", true);
   private Opacity hue = new Opacity(0);
   private final TTFFontRenderer font = Fonts.nametagsFont;
   public Screen() {
      super("HUD", 0, AbstractModule.Category.Visuals);
      this.addBoolean(this.logo,this.coord, ETBMode,this.userinfo);
       //this.addBoolean(this.skilllogo = new BooleanValue("Skill Logo", false));
   }
   	
	@ActiveEvent
	public void onEvent(EventRenderGui event) {
			ScaledResolution s = new ScaledResolution(mc);
	        	if(logo.getState()){
	        		drawClientWatermark(s);
	        	}
	        	if(ETBMode.getState()) {
	        		drawTabGUI(s);
	        	}
	        	if(userinfo.getState()){
	        		drawUserInfo(s);
	        	}
	        	if(coord.getState()){
	        		drawCoordinates(s);
	        	}
	        	drawArrayList(s);		
	}
    
	private void drawCoordinates(ScaledResolution sr) {
        String text = ChatFormatting.RED + "X" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posX) + " " + ChatFormatting.RED + "Y" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posY) + " " + ChatFormatting.RED + "Z" + ChatFormatting.WHITE + ": " + MathHelper.floor_double(mc.thePlayer.posZ);
        if (ETBMode.getState()) {
        	Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(text, (sr.getScaledWidth() - mc.fontRendererObj.getStringWidth(text)) / 2 , BossStatus.statusBarTime > 0 ? 20 : 2, -788529153);
        }else {
        	Fonts.Tahoma18.drawStringWithShadow(text, (sr.getScaledWidth() - Fonts.Tahoma18.getStringWidth(text)) / 2 , BossStatus.statusBarTime > 0 ? 20 : 2, -788529153);
        	//font.drawStringWithShadow(text, (new ScaledResolution(Minecraft.getMinecraft(), Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight).getScaledWidth() - font.getWidth(text)) / 2, BossStatus.statusBarTime > 0 ? 20 : 2, -788529153);
        }
	}
	
    private void drawTabGUI(ScaledResolution sr) {
    	 RenderUtils.rectangle(2.0D, 12.0D, 62.0D, 72.0D, ColorUtils.getColor(0, 150));
    	 RenderUtils.rectangle(2.5D, 12.5D, 61.5D, 23.5D, ColorUtils.getColor(90, 169, 248));
         mc.fontRendererObj.drawStringWithShadow("Combat", 8.0F, 14.0F, -1);
         mc.fontRendererObj.drawString("\2477Render", 6.0F, 26.0F, -1, false);
         mc.fontRendererObj.drawString("\2477Movement", 6.0F, 38.0F, -1, false);
         mc.fontRendererObj.drawString("\2477Player", 6.0F, 50.0F, -1, false);
         mc.fontRendererObj.drawString("\2477World", 6.0F, 62.0F, -1, false);
         mc.fontRendererObj.drawStringWithShadow("\2477FPS: \247f" + Minecraft.getDebugFPS(), 2.0F, 76.0F, -1);
    }
    
    private void drawClientWatermark(ScaledResolution sr) {
    	if(ETBMode.getState()) {
            mc.fontRendererObj.drawStringWithShadow("ETB \24770.6\247r [" + getfacing() + "]", 2.0F, 2.0F, ColorUtils.getColor(90, 169, 248));
    	}else {
    		 String displayName = "Exusiai V2";
             String firstLetter = displayName.substring(0, 1);
             String restOfName = displayName.substring(1);
             Fonts.Roboto18.drawStringWithShadow(firstLetter, 4, 4,RenderUtils.rainbow(100));
             Fonts.Roboto18.drawStringWithShadow(restOfName, (float) (Fonts.Roboto18.getStringWidth(firstLetter) + 4.5), 4, new Color(200, 200, 200).getRGB());
		}
    }
    
    private void drawUserInfo(ScaledResolution sr) {
    	  String xd = "§7" + "Other Build"+ " - §f§l" + "061120" + " §7- §l" + "Purity#4771";
          Fonts.nametagsFont.drawStringWithShadow(xd, (float)sr.getScaledWidth() - Fonts.nametagsFont.getWidth(xd) - 1.0F, (float)(sr.getScaledHeight() - (this.mc.currentScreen instanceof GuiChat ? 24 : 12)), ColorUtils.getColor(255, 220));
    }
    
    private void drawArrayList(ScaledResolution sr) {
    	int rainbowTick = 0;
        int yCount = 2;
        Iterator ModuleArrayList = ModuleManager.getModulesSorted().iterator();
        while(ModuleArrayList.hasNext()) {
            AbstractModule mod = (AbstractModule)ModuleArrayList.next();
            Color rainbow = new Color(Color.HSBtoRGB((float)((double)this.mc.thePlayer.ticksExisted / 50.0 + Math.sin((double)rainbowTick / 50.0 * 1.6)) % 1.0f, 0.5f, 1.0f));
            if (mod != null && mod.getState()) {
            	if(ETBMode.getState()) {
                    float x = sr.getScaledWidth() - (this.mc.fontRendererObj.getStringWidth(mod.getName())+ 1);
            		Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(mod.getName(), x, yCount, rainbow.getRGB());
                    yCount += 9;
            	}else {
            	    font.drawStringWithShadow(mod.getName(), sr.getScaledWidth() - (font.getWidth(mod.getName()) + 1), (float)yCount,rainbow.getRGB());
                    //RenderUtils.renderStringWave(mod.getName(), sr.getScaledWidth() - (Fonts.Roboto18.getStringWidth(mod.getName()) + 1), (float)yCount,0.6F);
                    yCount += 10;
            	}
                if (++rainbowTick > 100) {
                    rainbowTick = 0;
                }
            }
        }
	}
    
   	private String getfacing() {
   		float yaw = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw + 22.0F) % 360.0F;
        String facing = "?";
        if (yaw > 0.0F && yaw < 45.0F) {
        	return "S";
        }

        if (yaw > 45.0F && yaw < 90.0F) {
        	return "SW";
        }

        if (yaw > 90.0F && yaw < 135.0F) {
        	return "W";
        }

        if (yaw > 135.0F && yaw < 180.0F) {
        	return "NW";
        }

        if (yaw > -180.0F && yaw < -135.0F) {
        	return "N";
        }

        if (yaw > -135.0F && yaw < -90.0F) {
        	return "NE";
        }

        if (yaw > -90.0F && yaw < -45.0F) {
        	return "E";
        }

        if (yaw > -45.0F && yaw < 0.0F) {
        	return "SE";
        }
        return "N";
	}
}

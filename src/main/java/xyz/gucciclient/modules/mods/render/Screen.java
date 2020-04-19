package xyz.gucciclient.modules.mods.render;

import java.awt.Color;
import java.util.Comparator;
import java.util.Iterator;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import com.mojang.realmsclient.gui.ChatFormatting;

import xyz.gucciclient.modules.Module;
import xyz.gucciclient.modules.ModuleManager;
import xyz.gucciclient.utils.Render;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;

public class Screen extends Module {
   private BooleanValue logo;

   public Screen() {
      super(Module.Modules.Hud.name(), 0, Module.Category.Visuals);
      this.addBoolean(this.logo = new BooleanValue("Exusiai Logo", false));
   }

   @SubscribeEvent
   public void onRender(Post e) {
       ScaledResolution s = new ScaledResolution(mc);
      if (!(Wrapper.getMinecraft().currentScreen instanceof GuiMainMenu)) {
         if (e.type == ElementType.TEXT) {
            GL11.glPushMatrix();
            int yCount = 2;
            if (this.logo.getState()) {
               yCount += 25;
            }

            Iterator var3 = ModuleManager.getModulesSorted().iterator();

            while(var3.hasNext()) {
               Module mod = (Module)var3.next();
               if (mod != null && mod != this && mod.getState()) {
                   if (this.logo.getState()) {
                       GL11.glPushMatrix();
                       GL11.glScalef(2, 2, 2);
                       Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow("Exusiai" + ChatFormatting.RED +"!", (s.getScaledWidth() - 75) / 2, 2, 0xffffff);
                       GL11.glPopMatrix();   
                   }
            	   Render.renderStringWave(mod.getName(), s.getScaledWidth() - (Wrapper.getMinecraft().fontRendererObj.getStringWidth(mod.getName()) + 4), (float)yCount,0.6F);
            	   //Render.renderStringWave(mod.getName(), 2.0F, (float)yCount,0.6F);
                  //Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(mod.getName(), 2.0F, (float)yCount, (new Color(0, 220, 0)).getRGB());
                  yCount += 10;
               }
            }

            GL11.glPopMatrix();
         }
      }
   }
}

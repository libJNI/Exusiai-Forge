package today.exusiai.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import today.exusiai.font.Fonts;
import today.exusiai.gui.component.Component;
import today.exusiai.gui.component.Frame;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.MathUtil;
import today.exusiai.utils.Wrapper;

public class ClickGUI extends GuiScreen {
   public static List<Frame> frames = new ArrayList<Frame>();
   public static double opacity = 0.0;
   public ClickGUI() {
      int frameX = 5;
      AbstractModule.Category[] values;
      int length = (values = AbstractModule.Category.values()).length;
      for(int i = 0; i < length; ++i) {
         AbstractModule.Category category = values[i];
         Frame frame = new Frame(category);
         frame.setX(frameX);
         frames.add(frame);
         frame.setOpen(true);
         frameX += frame.getWidth() + 1;
      }
   }
	
   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      //this.drawDefaultBackground();
      //Fonts.Roboto18.drawStringWithShadow("Credits to winxp95qq & enterman", Wrapper.getScaledResolution().getScaledWidth() - Fonts.Roboto18.getStringWidth("Credits to winxp95qq & enterman "), Wrapper.getScaledResolution().getScaledHeight() - 20, 0xFFCCCCCC);
      //Fonts.Roboto18.drawStringWithShadow("ClickGUI still work in progress", Wrapper.getScaledResolution().getScaledWidth() - Fonts.Roboto18.getStringWidth("ClickGUI still work in progress "), Wrapper.getScaledResolution().getScaledHeight() - 10, 0xFFCCCCCC);
      for (Frame frame : frames) {
         frame.renderFrame(this.fontRendererObj);
         frame.updatePosition(mouseX, mouseY);

         for (Object o : frame.getComponents()) {
            Component comp = (Component) o;
            comp.updateComponent(mouseX, mouseY);
         }
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      Iterator var4 = frames.iterator();

      while(true) {
         Frame frame;
         do {
            do {
               if (!var4.hasNext()) {
                  return;
               }

               frame = (Frame)var4.next();
               if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
                  frame.setDrag(true);
                  frame.dragX = mouseX - frame.getX();
                  frame.dragY = mouseY - frame.getY();
               }

               if (frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
                  frame.setOpen(!frame.isOpen());
               }
            } while(!frame.isOpen());
         } while(frame.getComponents().isEmpty());

         Iterator var6 = frame.getComponents().iterator();

         while(var6.hasNext()) {
            Component component = (Component)var6.next();
            component.mouseClicked(mouseX, mouseY, mouseButton);
         }
      }
   }

   protected void func_73869_a(char typedChar, int keyCode) {
      Iterator var3 = frames.iterator();

      while(true) {
         Frame frame;
         do {
            do {
               do {
                  if (!var3.hasNext()) {
                     if (keyCode == 1) {
                        this.mc.displayGuiScreen((GuiScreen)null);
                     }

                     return;
                  }

                  frame = (Frame)var3.next();
               } while(!frame.isOpen());
            } while(keyCode == 1);
         } while(frame.getComponents().isEmpty());

         Iterator var5 = frame.getComponents().iterator();

         while(var5.hasNext()) {
            Component component = (Component)var5.next();
            component.keyTyped(typedChar, keyCode);
         }
      }
   }

   protected void func_146286_b(int mouseX, int mouseY, int state) {
      Iterator var4 = frames.iterator();

      while(var4.hasNext()) {
         Frame frame = (Frame)var4.next();
         frame.setDrag(false);
      }

   }

   public boolean func_73868_f() {
      return false;
   }
   
   @Override
   public void onGuiClosed()
   {
	  Frame.animState = 70f + MathUtil.getRandomInRange(1, 6);
	  super.onGuiClosed();
   }
}

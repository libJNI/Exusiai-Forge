package xyz.gucciclient.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import xyz.gucciclient.gui.component.Component;
import xyz.gucciclient.gui.component.Frame;
import xyz.gucciclient.modules.Module;

public class ClickGUI extends GuiScreen {
   public List<Frame> frames = new ArrayList<>();

   public ClickGUI() {
      int frameX = 5;
      Module.Category[] values;
      int length = (values = Module.Category.values()).length;

      for(int i = 0; i < length; ++i) {
         Module.Category category = values[i];
         Frame frame = new Frame(category);
         frame.setX(frameX);
         this.frames.add(frame);
         frameX += frame.getWidth() + 1;
      }

   }

   public void func_73863_a(int mouseX, int mouseY, float partialTicks) {
      //this.drawDefaultBackground();

      for (Frame frame : this.frames) {
         frame.renderFrame(this.fontRendererObj);
         frame.updatePosition(mouseX, mouseY);

         for (Object o : frame.getComponents()) {
            Component comp = (Component) o;
            comp.updateComponent(mouseX, mouseY);
         }
      }

   }

   protected void func_73864_a(int mouseX, int mouseY, int mouseButton) {
      Iterator var4 = this.frames.iterator();

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
      Iterator var3 = this.frames.iterator();

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
      Iterator var4 = this.frames.iterator();

      while(var4.hasNext()) {
         Frame frame = (Frame)var4.next();
         frame.setDrag(false);
      }

   }

   public boolean func_73868_f() {
      return false;
   }
}

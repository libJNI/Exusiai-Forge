package xyz.gucciclient.gui.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import xyz.gucciclient.gui.parts.ModulesPart;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;

public class Frame {
   public List<Component> components = new ArrayList<>();
   public Module.Category category;
   public boolean open;
   private int width;
   private int y;
   private int x;
   private int barHeight;
   private boolean isDragging;
   public int dragX;
   public int dragY;

   public Frame(Module.Category cat) {
      this.category = cat;
      this.width = 88;
      this.x = 5;
      this.y = 5;
      this.barHeight = 13;
      this.dragX = 0;
      this.open = false;
      this.isDragging = false;
      int tY = this.barHeight;
      Iterator var3 = Module.getCategoryModules(this.category).iterator();

      while(var3.hasNext()) {
         Module mod = (Module)var3.next();
         if (!mod.getName().equalsIgnoreCase("ClickGUI")) {
            ModulesPart modButton = new ModulesPart(mod, this, tY);
            this.components.add(modButton);
            tY += 12;
         }
      }

   }

   public List<Component> getComponents() {
      return this.components;
   }

   public void setX(int newX) {
      this.x = newX;
   }

   public void setY(int newY) {
      this.y = newY;
   }

   public void setDrag(boolean drag) {
      this.isDragging = drag;
   }

   public boolean isOpen() {
      return this.open;
   }

   public void setOpen(boolean open) {
      this.open = open;
   }

   public void renderFrame(FontRenderer fontRenderer) {
      this.width = 80;
      Gui.drawRect(this.x - 1, this.y - 3, this.x + this.width + 1, this.y + this.barHeight - 2, (new Color(255, 0, 0, 150)).getRGB());
      GL11.glPushMatrix();
      Wrapper.getMinecraft().fontRendererObj.drawString(this.category.name(), this.x + 2, this.y, -1);
      if (this.open) {
         fontRenderer.drawString("-", this.x + 70, (int)((double)this.y + 1.5D), -1);
      } else {
         fontRenderer.drawString("+", this.x + 70, this.y + 1, -1);
      }

      GL11.glPushMatrix();
      GL11.glPopMatrix();
      GL11.glPopMatrix();
      if (this.open && !this.components.isEmpty()) {
         Iterator var2 = this.components.iterator();

         while(var2.hasNext()) {
            Component component = (Component)var2.next();
            component.render();
         }
      }

   }

   public void refresh() {
      int off = this.barHeight;

      Component comp;
      for(Iterator var2 = this.components.iterator(); var2.hasNext(); off += comp.getHeight()) {
         comp = (Component)var2.next();
         comp.setOff(off);
      }

   }

   public int getX() {
      return this.x;
   }

   public int getY() {
      return this.y;
   }

   public int getWidth() {
      return this.width;
   }

   public void updatePosition(int mouseX, int mouseY) {
      if (this.isDragging) {
         this.setX(mouseX - this.dragX);
         this.setY(mouseY - this.dragY);
      }

   }

   public boolean isWithinHeader(int x, int y) {
      return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight;
   }
}

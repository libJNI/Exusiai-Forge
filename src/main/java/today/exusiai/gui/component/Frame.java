package today.exusiai.gui.component;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import today.exusiai.font.Fonts;
import today.exusiai.gui.parts.ModulesPart;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.animation.AnimationUtil;

import org.lwjgl.opengl.GL11;

public class Frame {
   public static Frame getFrame;
   public List<Component> components = new ArrayList<>();
   public AbstractModule.Category category;
   public boolean open;
   private int width;
   private int y;
   private int x;
   private int barHeight;
   private boolean isDragging;
   public boolean customBar;
   public String customName;
   public int dragX;
   public int dragY;
   public static float animState = 75f;
	private boolean anim = true;
   public Frame(AbstractModule.Category cat) {
      this.category = cat;
      this.width = 88;
      this.x = 5;
      this.y = 5;
      this.barHeight = 13;
      this.dragX = 0;
      this.open = false;
      this.isDragging = false;
      int tY = this.barHeight;
      Iterator var3 = AbstractModule.getCategoryModules(this.category).iterator();

      while(var3.hasNext()) {
         AbstractModule mod = (AbstractModule)var3.next();
         if (!mod.getName().equalsIgnoreCase("ClickGUI")) {
            ModulesPart modButton = new ModulesPart(mod, this, tY);
            this.components.add(modButton);
            tY += 10;
         }
      }

   }

   public Frame(final String info) {
       this.customBar = false;
       this.customName = "";
       this.components = new ArrayList<Component>();
       this.category = null;
       this.customBar = true;
       this.width = 92;
       this.x = 5;
       this.y = 5;
       this.barHeight = 13;
       this.dragX = 0;
       this.open = false;
       this.isDragging = false;
       this.customName = info;
       final int tY = this.barHeight + 3;
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
	   if (anim) {
			animState = AnimationUtil.moveTowards(animState, 0, 0.2f, 0.5f);
		}

		GlStateManager.translate(0, animState, 0);
      this.width = 80;
      Gui.drawRect(this.x - 1, this.y - 3, this.x + this.width + 1, this.y + this.barHeight - 2, (new Color(94, 178, 232, 255)).getRGB());
      GL11.glPushMatrix();
      Fonts.Roboto18.drawString(this.customBar ? this.customName : this.category.name(), this.x + 2, this.y, -1);
      if (this.open) {
         Fonts.Roboto18.drawString("-", this.x + 70, (int)((double)this.y + 1.5D), -1);
      } else {
         Fonts.Roboto18.drawString("+", this.x + 70, this.y + 1, -1);
      }

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

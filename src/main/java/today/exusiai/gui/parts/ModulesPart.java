package today.exusiai.gui.parts;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.gui.Gui;
import today.exusiai.font.Fonts;
import today.exusiai.gui.component.Component;
import today.exusiai.gui.component.Frame;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.collection.render.G0ui;
import today.exusiai.modules.collection.render.Screen;
import today.exusiai.utils.RenderUtils;
import today.exusiai.values.BooleanValue;
import today.exusiai.values.NumberValue;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModulesPart extends Component {
   private Screen color;
   public AbstractModule mod;
   public Frame parent;
   public int offset;
   private boolean binding;
   private boolean isHovered;
   private ArrayList subcomponents;
   public boolean open;

   public ModulesPart(AbstractModule mod, Frame parent, int offset) {
      this.mod = mod;
      this.parent = parent;
      this.offset = offset;
      this.subcomponents = new ArrayList();
      this.open = false;
      int opY = offset + 14;
      Iterator var5;
      if (!mod.getValues().isEmpty()) {
         for(var5 = mod.getValues().iterator(); var5.hasNext(); opY += 12) {
            NumberValue num = (NumberValue)var5.next();
            SliderPart sliderPart = new SliderPart(num, this, opY);
            this.subcomponents.add(sliderPart);
         }
      }

      if (!mod.getBooleans().isEmpty()) {
         for(var5 = mod.getBooleans().iterator(); var5.hasNext(); opY += 12) {
            BooleanValue bool = (BooleanValue)var5.next();
            CheckboxPart check = new CheckboxPart(bool, this, opY);
            this.subcomponents.add(check);
         }
      }
   }

   @Override
   public void setOff(int newOff) {
      this.offset = newOff;
      int opY = this.offset + 12;

      for(Iterator var3 = this.subcomponents.iterator(); var3.hasNext(); opY += 12) {
         Component comp = (Component)var3.next();
         comp.setOff(opY);
      }

   }

   @Override
   public void render() {
      if (this.isHovered && Mouse.isButtonDown(2)) {
         this.binding = true;
      }

      Gui.drawRect(this.parent.getX() + 1, this.parent.getY() - 2 + this.offset, this.parent.getX() + this.parent.getWidth() - 2, this.parent.getY() + 10 + this.offset, this.isHovered ? (new Color(50, 50, 50, 190)).getRGB() : (new Color(15, 15, 15, 190)).getRGB());
      Fonts.Roboto18.drawCenteredString(this.binding ? "" : this.mod.getName(), this.parent.getX() + 36, this.parent.getY() + 1 + this.offset, this.mod.getState() ? RenderUtils.rainbow(100) : (new Color(150, 150, 150)).getRGB());
      GL11.glPushMatrix();
      Fonts.Roboto13.drawString(this.binding ? "Press a key.. " + Keyboard.getKeyName(this.mod.getKey()) : "", this.parent.getX() + 4, this.parent.getY() + this.offset + 2, (new Color(150, 150, 150)).getRGB());
      //Fonts.Roboto13.drawStringWithShadow(this.binding ? "Press a key.. " + Keyboard.getKeyName(this.mod.getKey()) : "", (float)(this.parent.getX() * 2 + 5), (float)((this.parent.getY() + this.offset) * 2 + 6), (new Color(150, 150, 150)).getRGB());
      GL11.glPopMatrix();
      if (!this.mod.getBooleans().isEmpty()) {
        RenderUtils.drawArrow((float)(this.parent.getX() + 65), (float)(this.parent.getY() + this.offset + 2), this.open, (new Color(150, 150, 150)).getRGB());
      }

      if (!this.mod.getValues().isEmpty()) {
        RenderUtils.drawArrow((float)(this.parent.getX() + 65), (float)(this.parent.getY() + this.offset + 2), this.open, (new Color(150, 150, 150)).getRGB());
      }

      if (this.open && !this.subcomponents.isEmpty()) {
         Iterator var1 = this.subcomponents.iterator();

         while(var1.hasNext()) {
            Component comp = (Component)var1.next();
            comp.render();
         }
      }

   }

   @Override
   public int getHeight() {
      return this.open ? 12 * (this.subcomponents.size() + 1) : 12;
   }

   @Override
   public void updateComponent(int mouseX, int mouseY) {
      this.parent.refresh();
      this.isHovered = this.isMouseOnButton(mouseX, mouseY);
      if (!this.subcomponents.isEmpty()) {
         Iterator var3 = this.subcomponents.iterator();

         while(var3.hasNext()) {
            Component comp = (Component)var3.next();
            comp.updateComponent(mouseX, mouseY);
         }
      }

   }

   @Override
   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 2 && this.parent.open) {
         this.binding = !this.binding;
      }

      if (this.isMouseOnButton(mouseX, mouseY) && button == 0) {
         AbstractModule mod = this.mod;
         if (!AbstractModule.getModule(G0ui.class).getState()) {
            this.mod.setState(!this.mod.getState());
         }
      }

      if (this.isMouseOnButton(mouseX, mouseY) && button == 1) {
         this.open = !this.open;
         this.parent.refresh();
      }

      Iterator var6 = this.subcomponents.iterator();

      while(var6.hasNext()) {
         Component comp = (Component)var6.next();
         comp.mouseClicked(mouseX, mouseY, button);
      }

   }

   @Override
   public void keyTyped(char typedChar, int key) {
      if (this.binding) {
         if (key == 211) {
            this.mod.setKey(0);
            this.binding = false;
         }
         else {
            this.mod.setKey(key);
            this.binding = false;
         }
      }
   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.parent.getX() && x < this.parent.getX() + this.parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset;
   }
}

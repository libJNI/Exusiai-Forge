package xyz.gucciclient.gui.parts;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;
import xyz.gucciclient.gui.component.Component;
import xyz.gucciclient.utils.Render;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;

public class CheckboxPart extends Component {
   private boolean hovered;
   private BooleanValue op;
   private ModulesPart parent;
   private int offset;
   private int x;
   private int y;

   public CheckboxPart(BooleanValue option, ModulesPart modulesPart, int offset) {
      this.op = option;
      this.parent = modulesPart;
      this.x = modulesPart.parent.getX() + modulesPart.parent.getWidth();
      this.y = modulesPart.parent.getY() + modulesPart.offset;
      this.offset = offset;
   }

   public void render() {
      Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() + 12 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset, (new Color(20, 20, 20, 150)).getRGB());
      Gui.drawRect(this.parent.parent.getX() + 3, this.parent.parent.getY() + this.offset + 9, this.parent.parent.getX() - 5 + this.parent.parent.getWidth() - 63, this.parent.parent.getY() + this.offset + 1, (new Color(0, 0, 0, 150)).getRGB());
      if (this.op.getState()) {
         Render.drawCheckmark((float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), (new Color(255, 20, 20)).getRGB());
      }

      GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(this.op.getName(), (float)((this.parent.parent.getX() + 15) * 2), (float)((this.parent.parent.getY() + this.offset + 4) * 2 - 2), -1);
      GL11.glPopMatrix();
   }

   public void setOff(int newOff) {
      this.offset = newOff;
   }

   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButton(mouseX, mouseY);
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
   }

   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
         this.op.toggle();
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 105 && y > this.y && y < this.y + 12;
   }
}

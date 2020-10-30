package today.exusiai.gui.parts;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import today.exusiai.font.Fonts;
import today.exusiai.font.MCFontRenderer;
import today.exusiai.gui.component.Component;
import today.exusiai.utils.RenderUtils;
import today.exusiai.utils.Wrapper;
import today.exusiai.values.BooleanValue;

import org.lwjgl.opengl.GL11;

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

   @Override
   public void render() {
      Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() + 10 + this.offset, this.parent.parent.getX() - 2 + this.parent.parent.getWidth() * 1, this.parent.parent.getY() + this.offset - 2, (new Color(20, 20, 20, 150)).getRGB());
      Gui.drawRect(this.parent.parent.getX() + 3, this.parent.parent.getY() + this.offset + 9, this.parent.parent.getX() - 5 + this.parent.parent.getWidth() - 63, this.parent.parent.getY() + this.offset + 1, (new Color(0, 0, 0, 150)).getRGB());
      if (this.op.getState()) {
         RenderUtils.drawCheckmark((float)(this.parent.parent.getX() + 4), (float)(this.parent.parent.getY() + this.offset + 4), (new Color(255, 20, 20)).getRGB());
      }
      GL11.glPushMatrix();
      Fonts.Roboto13.drawString(this.op.getName(), this.parent.parent.getX() + 15, this.parent.parent.getY() + this.offset + 3, -1);
      GL11.glPopMatrix();
      /*GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      Wrapper.getMinecraft().fontRendererObj.drawStringWithShadow(this.op.getName(), (float)((this.parent.parent.getX() + 15) * 2), (float)((this.parent.parent.getY() + this.offset + 4) * 2 - 2), -1);
      GL11.glPopMatrix();*/
   }

   @Override
   public void setOff(int newOff) {
      this.offset = newOff;
   }

   @Override
   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButton(mouseX, mouseY);
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
   }

   @Override
   public void mouseClicked(int mouseX, int mouseY, int button) {
      if (this.isMouseOnButton(mouseX, mouseY) && button == 0 && this.parent.open) {
         this.op.toggle();
      }

   }

   public boolean isMouseOnButton(int x, int y) {
      return x > this.x && x < this.x + 105 && y > this.y && y < this.y + 12;
   }
}

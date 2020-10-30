package today.exusiai.gui.parts;

import com.ibm.icu.math.BigDecimal;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import today.exusiai.font.Fonts;
import today.exusiai.gui.component.Component;
import today.exusiai.modules.collection.render.Screen;
import today.exusiai.values.NumberValue;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SliderPart extends Component {
   private boolean hovered;
   private NumberValue value;
   private ModulesPart parent;
   private int offset;
   private int x;
   private Screen color;
   private int y;

   public SliderPart(NumberValue value, ModulesPart modulesPart, int offset) {
      this.value = value;
      this.parent = modulesPart;
      this.x = modulesPart.parent.getX() + modulesPart.parent.getWidth();
      this.y = modulesPart.parent.getY() + modulesPart.offset;
      this.offset = offset;
   }

   @Override
   public void render() {
      int drag = (int)(this.value.getValue() / this.value.getMax() * 70.0D);
      Gui.drawRect(this.parent.parent.getX() + 1, this.parent.parent.getY() - 2 +this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 2, this.parent.parent.getY() + this.offset + 10, (new Color(20, 20, 20, 150)).getRGB());
      Gui.drawRect(this.parent.parent.getX() + 5, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + this.parent.parent.getWidth() - 6, this.parent.parent.getY() + this.offset + 8, (new Color(80, 80, 80, 150)).getRGB());
      Gui.drawRect(this.parent.parent.getX() + 4, this.parent.parent.getY() + this.offset, this.parent.parent.getX() + 4 + drag + 2, this.parent.parent.getY() + this.offset + 8, (new Color(255, 0, 150)).getRGB());
      GL11.glPushMatrix();
      Fonts.Roboto11.drawString(String.valueOf(this.value.getName() + ":" + this.value.getValue()), this.parent.parent.getX() + 5, this.parent.parent.getY() + this.offset + 3, -1);
      GL11.glPopMatrix();
      /*GL11.glPushMatrix();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      Wrapper.getMinecraft().fontRendererObj.drawString(String.valueOf(this.value.getName() + " : " + this.value.getValue()), this.parent.parent.getX() * 2 + 12, (this.parent.parent.getY() + this.offset - 1) * 2 + 6, -1);
      GL11.glPopMatrix();*/
   }

   @Override
   public void setOff(int newOff) {
      this.offset = newOff;
   }

   @Override
   public void updateComponent(int mouseX, int mouseY) {
      this.hovered = this.isMouseOnButtonD(mouseX, mouseY) || this.isMouseOnButtonI(mouseX, mouseY);
      this.y = this.parent.parent.getY() + this.offset;
      this.x = this.parent.parent.getX();
      if (this.hovered && this.parent.open && Mouse.isButtonDown(0)) {
         double diff = (double)(mouseX - this.parent.parent.getX());
         double value = this.round(diff / (double)(this.parent.parent.getWidth() - 1) * this.value.getMax(), 1);
         this.value.setValue(value);
      }

   }

   @Override
   public void mouseClicked(int mouseX, int mouseY, int button) {
      NumberValue numberValue;
      double value;
      if (this.isMouseOnButtonD(mouseX, mouseY) && button == 0 && this.parent.open) {
         numberValue = this.value;
         value = numberValue.getValue() - 0.1D;
         numberValue.setValue((double)Math.round(value * 10.0D) / 10.0D);
      }

      if (this.isMouseOnButtonI(mouseX, mouseY) && button == 0 && this.parent.open) {
         numberValue = this.value;
         value = numberValue.getValue() + 0.1D;
         numberValue.setValue((double)Math.round(value * 10.0D) / 10.0D);
      }

   }

   private double round(double doubleValue, int numOfDecimals) {
      BigDecimal bigDecimal = new BigDecimal(doubleValue);
      bigDecimal = bigDecimal.setScale(numOfDecimals, 4);
      return bigDecimal.doubleValue();
   }

   public boolean isMouseOnButtonD(int x, int y) {
      return x > this.x && x < this.x + this.parent.parent.getWidth() / 2 + 1 && y > this.y && y < this.y + 12;
   }

   public boolean isMouseOnButtonI(int x, int y) {
      return x > this.x + this.parent.parent.getWidth() / 2 && x < this.x + this.parent.parent.getWidth() && y > this.y && y < this.y + 12;
   }
}

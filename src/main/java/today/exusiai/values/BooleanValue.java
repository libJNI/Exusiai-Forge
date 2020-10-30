package today.exusiai.values;

public class BooleanValue {
   private String name;
   private boolean value;

   public BooleanValue(String name, boolean value) {
      this.name = name;
      this.value = value;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public boolean getState() {
      return this.value;
   }

   public void toggle() {
      this.value = !this.value;
   }

   public void setState(boolean state) {
      if (state != this.value) {
         this.value = state;
      }
   }
}

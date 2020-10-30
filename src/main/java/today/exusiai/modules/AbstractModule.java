package today.exusiai.modules;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import today.exusiai.event.EventSystem;
import today.exusiai.notifications.Notifications;
import today.exusiai.utils.Wrapper;
import today.exusiai.values.BooleanValue;
import today.exusiai.values.NumberValue;

public abstract class AbstractModule {
   protected Minecraft mc = Wrapper.getMinecraft();
   private String name;
   private String suffix;
   private int key;
   private boolean state;
   private AbstractModule.Category category;
   private List<BooleanValue> booleans = new ArrayList<>();
   private List<NumberValue> values = new ArrayList<>();

   public AbstractModule(String name, int key, AbstractModule.Category category) {
      this.name = name;
      this.key = key;
      this.state = false;
      this.category = category;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSuffix() {
	      return this.suffix;
   }

   public void setSuffix(String name) {
	      this.suffix = String.format("\u00a77- \u00a7f%s\u00a77", new Object[]{EnumChatFormatting.GRAY + suffix});;
   }
   
   public void setKey(int key) {
      this.key = key;
   }

   public boolean setToggled(boolean toggled) {
      return this.state = toggled;
   }

   public boolean getState() {
      return this.state;
   }

   public int getKey() {
      return this.key;
   }

   public AbstractModule.Category getCategory() {
      return this.category;
   }

   public List<BooleanValue> getBooleans() {
      return this.booleans;
   }

   public List<NumberValue> getValues() {
      return this.values;
   }
   
   public void toggle() {
      this.setState(!this.state);
   }

   public void addBoolean(BooleanValue... booleans) {
      BooleanValue[] var5 = booleans;
      int var4 = booleans.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         BooleanValue value = var5[var3];
         this.booleans.add(value);
      }
   }

   public void addValue(NumberValue... values) {
      NumberValue[] var5 = values;
      int var4 = values.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         NumberValue value = var5[var3];
         this.values.add(value);
      }
   }
   
   public static ArrayList getCategoryModules(AbstractModule.Category cat) {
      ArrayList modsInCategory = new ArrayList();
      Iterator var2 = ModuleManager.getModules().iterator();

      while(var2.hasNext()) {
         AbstractModule mod = (AbstractModule)var2.next();
         if (mod.getCategory() == cat) {
            modsInCategory.add(mod);
         }
      }

      return modsInCategory;
   }

   public static AbstractModule getModule(Class clazz) {
      Iterator var1 = ModuleManager.getModules().iterator();

      AbstractModule mod;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         mod = (AbstractModule)var1.next();
      } while(mod.getClass() != clazz);

      return mod;
   }

   public static ArrayList<AbstractModule> getEnabled() {
       final ArrayList<AbstractModule> temp = new ArrayList<AbstractModule>();
       for (final AbstractModule m : ModuleManager.getModules()) {
           if (m.getState()) {
               temp.add(m);
           }
       }
       return temp;
   }
   
   public void setState(boolean enabled) {
      if (this.state == enabled) {
         return;
      }
         this.state = enabled;
         if (enabled) {
            MinecraftForge.EVENT_BUS.register(this);
            FMLCommonHandler.instance().bus().register(this);
            EventSystem.register(this);
            if (Wrapper.getMinecraft().theWorld != null) {
            	Notifications.getManager().post("Module Enable", this.getName(), 1000L, Notifications.Type.INFO);
               this.onEnable();
            }
         } else {
            MinecraftForge.EVENT_BUS.unregister(this);
            FMLCommonHandler.instance().bus().unregister(this);
            EventSystem.unregister(this);
            if (Wrapper.getMinecraft().theWorld != null) {
            	Notifications.getManager().post("Module Disable", this.getName(), 1000L, Notifications.Type.INFO);
               this.onDisable();
            }
         }
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public static enum Category {
      //战斗功能
      Combat,
      //视觉功能
      Visuals,
      //其他功能
      Other,
      //辅助功能
      Utility,
      //移动功能
      Movement;
   }
}

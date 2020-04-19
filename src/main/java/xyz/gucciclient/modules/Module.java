package xyz.gucciclient.modules;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import xyz.gucciclient.utils.Wrapper;
import xyz.gucciclient.values.BooleanValue;
import xyz.gucciclient.values.NumberValue;

public abstract class Module {
   protected Minecraft mc = Wrapper.getMinecraft();
   private String name;
   private int key;
   private boolean state;
   private Module.Category category;
   private ArrayList booleans = new ArrayList();
   private ArrayList values = new ArrayList();

   public Module(String name, int key, Module.Category category) {
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

   public Module.Category getCategory() {
      return this.category;
   }

   public ArrayList getBooleans() {
      return this.booleans;
   }

   public ArrayList getValues() {
      return this.values;
   }

   public void toggle() {
      this.setState(!this.state);
   }

   public void addBoolean(BooleanValue booleans) {
      this.booleans.add(booleans);
   }

   public void addValue(NumberValue values) {
      this.values.add(values);
   }

   public static ArrayList getCategoryModules(Module.Category cat) {
      ArrayList modsInCategory = new ArrayList();
      Iterator var2 = ModuleManager.getModules().iterator();

      while(var2.hasNext()) {
         Module mod = (Module)var2.next();
         if (mod.getCategory() == cat) {
            modsInCategory.add(mod);
         }
      }

      return modsInCategory;
   }

   public static Module getModule(Class clazz) {
      Iterator var1 = ModuleManager.getModules().iterator();

      Module mod;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         mod = (Module)var1.next();
      } while(mod.getClass() != clazz);

      return mod;
   }

   public void setState(boolean enabled) {
      if (this.state != enabled) {
         this.state = enabled;
         if (enabled) {
            MinecraftForge.EVENT_BUS.register(this);
            FMLCommonHandler.instance().bus().register(this);
            this.onEnable();
         } else {
            MinecraftForge.EVENT_BUS.unregister(this);
            FMLCommonHandler.instance().bus().unregister(this);
            this.onDisable();
         }

      }
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public static enum Category {
      Combat,
      Visuals,
      Other,
      Utility,
      Movement;
   }

   public static enum Modules {
      Clicker,
      Heal,
      SmoothAim,
      KillAura,
      Velocity,
      Speed,
      FastPlace,
      Fullbright,
      ClickGUI,
      Hud,
      AgroPearl,
      Debuff,
      SelfDestruct;
   }
}

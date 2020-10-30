package today.exusiai.modules;

import java.util.ArrayList;
import java.util.Comparator;

import scala.reflect.internal.Trees.New;
import today.exusiai.font.Fonts;
import today.exusiai.modules.collection.combat.Antibot;
import today.exusiai.modules.collection.combat.Aura;
import today.exusiai.modules.collection.combat.Clicker;
import today.exusiai.modules.collection.combat.Reach;
import today.exusiai.modules.collection.combat.SmoothAim;
import today.exusiai.modules.collection.combat.TPAura;
import today.exusiai.modules.collection.combat.TriggerBot;
import today.exusiai.modules.collection.combat.Velocity;
import today.exusiai.modules.collection.exploit.C17Debugger;
import today.exusiai.modules.collection.exploit.GodMode;
import today.exusiai.modules.collection.exploit.QYZGSpoof;
import today.exusiai.modules.collection.movement.*;
import today.exusiai.modules.collection.other.*;
import today.exusiai.modules.collection.render.*;
import today.exusiai.modules.collection.utility.*;
import today.exusiai.utils.Wrapper;

public class ModuleManager {
   private static ArrayList modules = new ArrayList();

   public static ArrayList<AbstractModule> getModulesSorted()
   {
       ArrayList<AbstractModule> modulesSorted = new ArrayList<AbstractModule>();
       modulesSorted.addAll(modules);
       modulesSorted.sort(new Comparator<AbstractModule>()
       {
          @Override
           public int compare(AbstractModule m1, AbstractModule m2)
           {
        	   if(Screen.ETBMode.getState()) {
                   return (int) (Wrapper.getMinecraft().fontRendererObj.getStringWidth(m2.getName()) - Wrapper.getMinecraft().fontRendererObj.getStringWidth(m1.getName()));
        	   }else {
                   return (int) (Fonts.Roboto18.getStringWidth(m2.getName()) - Fonts.Roboto18.getStringWidth(m1.getName()));
        	   }
           }
       });
       return modulesSorted;
   }

   public static ArrayList<AbstractModule> getModules() {
      return modules;
   }

   public static void addMod(AbstractModule mod) {
	   modules.add(mod);
   }
   static {
	  addMod(new SmoothAim());
      addMod(new Reach());
      addMod(new Clicker());
      addMod(new G0ui());
      addMod(new Aura());
      addMod(new Screen());
      addMod(new Velocity());
      addMod(new Fly());
      addMod(new Sprint());
      addMod(new Speed());
      addMod(new FastBridge());
      addMod(new Debuff());
      addMod(new AgroPearl());
      addMod(new Regen());
      addMod(new Fullbright());
      addMod(new Heal());
      addMod(new Timer());
      addMod(new Antibot());
      addMod(new FastBreeeak());
      addMod(new TriggerBot());
      addMod(new Eagle());
      addMod(new Nametag());
      addMod(new PlayerESP());
      addMod(new SelfDestruct());
      addMod(new OPKit());
      addMod(new FastUse());
      addMod(new LongJump());
      addMod(new ExternalGui());
      addMod(new QYZGSpoof());
      addMod(new Step());
      addMod(new ChestESP());
      addMod(new NameProtect());
      addMod(new Scaffold());
      addMod(new InvMove());
      addMod(new TargetHUD());
      addMod(new AutoTool());
      addMod(new NoFall());
      addMod(new TPAura());
      addMod(new GodMode());
      addMod(new ItemESP());
      addMod(new BloodEffect());
      addMod(new C17Debugger());
      //addMod(new Xray());
   }
}

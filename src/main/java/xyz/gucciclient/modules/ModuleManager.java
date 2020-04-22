package xyz.gucciclient.modules;

import java.util.ArrayList;
import java.util.Comparator;

import xyz.gucciclient.modules.mods.combat.Clicker;
import xyz.gucciclient.modules.mods.combat.SmoothAim;
import xyz.gucciclient.event.EventManager;
import xyz.gucciclient.modules.mods.combat.Antibot;
import xyz.gucciclient.modules.mods.combat.Killaura;
import xyz.gucciclient.modules.mods.combat.Velocity;
import xyz.gucciclient.modules.mods.combat.Reach;
import xyz.gucciclient.modules.mods.combat.TriggerBot;
import xyz.gucciclient.modules.mods.movement.Eagle;
import xyz.gucciclient.modules.mods.movement.Fly;
import xyz.gucciclient.modules.mods.movement.Speed;
import xyz.gucciclient.modules.mods.movement.Sprint;
import xyz.gucciclient.modules.mods.movement.Timer;
import xyz.gucciclient.modules.mods.other.FastBreeeak;
import xyz.gucciclient.modules.mods.other.FastBridge;
import xyz.gucciclient.modules.mods.other.OPKit;
import xyz.gucciclient.modules.mods.other.SelfDestruct;
import xyz.gucciclient.modules.mods.render.Fullbright;
import xyz.gucciclient.modules.mods.render.G0ui;
import xyz.gucciclient.modules.mods.render.Nametag;
import xyz.gucciclient.modules.mods.render.PlayerESP;
import xyz.gucciclient.modules.mods.render.Screen;
import xyz.gucciclient.modules.mods.utility.AgroPearl;
import xyz.gucciclient.modules.mods.utility.Debuff;
import xyz.gucciclient.modules.mods.utility.Heal;
import xyz.gucciclient.utils.Wrapper;

public class ModuleManager {
   private static ArrayList modules = new ArrayList();

   public static ArrayList<Module> getModulesSorted()
   {
       ArrayList<Module> modulesSorted = new ArrayList<Module>();
       modulesSorted.addAll(modules);
       modulesSorted.sort(new Comparator<Module>()
       {
           public int compare(Module m1, Module m2)
           {
               return Wrapper.getMinecraft().fontRendererObj.getStringWidth(m2.getName()) - Wrapper.getMinecraft().fontRendererObj.getStringWidth(m1.getName());
           }
       });
       return modulesSorted;
   }
   
   public static ArrayList getModules() {
      return modules;
   }

   public static void addMod(Module mod) {
	   modules.add(mod);
       //EventManager.getInstance().register(mod);
   }
   static {
	  addMod(new SmoothAim());
      addMod(new Reach());
      addMod(new Clicker());
      addMod(new G0ui());
      addMod(new Screen());
      addMod(new Velocity());
      addMod(new Fly());
      addMod(new Sprint());
      addMod(new Speed());
      addMod(new FastBridge());
      addMod(new Debuff());
      addMod(new Killaura());
      addMod(new AgroPearl());
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
   }
}

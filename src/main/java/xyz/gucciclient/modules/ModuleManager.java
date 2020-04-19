package xyz.gucciclient.modules;

import java.util.ArrayList;
import java.util.Comparator;

import xyz.gucciclient.modules.mods.combat.Clicker;
import xyz.gucciclient.modules.mods.combat.SmoothAim;
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
   private static ArrayList modules;

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

   static {
      (modules = new ArrayList()).add(new SmoothAim());
      modules.add(new Reach());
      modules.add(new Clicker());
      modules.add(new G0ui());
      modules.add(new Screen());
      modules.add(new Velocity());
      modules.add(new Fly());
      modules.add(new Sprint());
      modules.add(new Speed());
      modules.add(new FastBridge());
      modules.add(new Debuff());
      modules.add(new Killaura());
      modules.add(new AgroPearl());
      modules.add(new Fullbright());
      modules.add(new Heal());
      modules.add(new Timer());
      modules.add(new Antibot());
      modules.add(new FastBreeeak());
      modules.add(new TriggerBot());
      modules.add(new Eagle());
      modules.add(new Nametag());
      modules.add(new PlayerESP());
      modules.add(new SelfDestruct());
   }
}

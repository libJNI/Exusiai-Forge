package xyz.gucciclient.modules.mods.render;

import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;

public class Fullbright extends Module {
   private float gamma;

   public Fullbright() {
      super(Module.Modules.Fullbright.name(), 0, Module.Category.Visuals);
   }

   public void onEnable() {
      this.gamma = Wrapper.getMinecraft().gameSettings.gammaSetting;
      Wrapper.getMinecraft().gameSettings.gammaSetting = 1000.0F;
   }

   public void onDisable() {
      Wrapper.getMinecraft().gameSettings.gammaSetting = this.gamma;
   }
}

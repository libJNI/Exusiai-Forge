package today.exusiai.modules.collection.render;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.Wrapper;

public class Fullbright extends AbstractModule {
   private float gamma;

   public Fullbright() {
      super("FullBright", 0, AbstractModule.Category.Visuals);
   }

    @Override
   public void onEnable() {
		Wrapper.getPlayer().addPotionEffect(new PotionEffect(Potion.nightVision.id, 860, 10));
   }

    @Override
   public void onDisable() {
	   Wrapper.getPlayer().removePotionEffectClient(Potion.nightVision.id);
   }
}

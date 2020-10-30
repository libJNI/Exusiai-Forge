package today.exusiai.modules.collection.other;

import today.exusiai.Client;
import today.exusiai.modules.AbstractModule;

public class SelfDestruct extends AbstractModule {

   public SelfDestruct() {
      super("SelfDestruct", 0, AbstractModule.Category.Other);
   }

   @Override
   public void onEnable() {
	   Client.INSTANCE.selfDestruct();
   }
}

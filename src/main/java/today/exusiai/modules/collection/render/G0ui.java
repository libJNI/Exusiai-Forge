package today.exusiai.modules.collection.render;

import today.exusiai.Client;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.Wrapper;

public class G0ui extends AbstractModule {
	public G0ui() {
		super("ClickGui", 54, AbstractModule.Category.Visuals);
	}

	@Override
	public void onEnable() {
		if (Wrapper.getPlayer() != null && Wrapper.getMinecraft().currentScreen == null) {
			Wrapper.getMinecraft().displayGuiScreen(Client.getClickGUI());
			this.toggle();
		}

	}
}

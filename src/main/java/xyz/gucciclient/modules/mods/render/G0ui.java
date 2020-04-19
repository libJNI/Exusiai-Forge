package xyz.gucciclient.modules.mods.render;

import xyz.gucciclient.Client;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;

public class G0ui extends Module {
	public G0ui() {
		super(Module.Modules.ClickGUI.name(), 54, Module.Category.Visuals);
	}

	public void onEnable() {
		if (Wrapper.getPlayer() != null && Wrapper.getMinecraft().currentScreen == null) {
			Wrapper.getMinecraft().displayGuiScreen(Client.getClickGUI());
			this.toggle();
		}

	}
}

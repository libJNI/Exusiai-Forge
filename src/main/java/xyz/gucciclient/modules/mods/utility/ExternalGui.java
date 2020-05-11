package xyz.gucciclient.modules.mods.utility;

import xyz.gucciclient.Client;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.Wrapper;

public class ExternalGui extends Module {
	public ExternalGui() {
		super("ExternalGui", 0, Category.Visuals);
	}

	public void onEnable() {
			xyz.gucciclient.ExternalGui gui = new xyz.gucciclient.ExternalGui();
			gui.setVisible(true);
			this.toggle();
	}
}

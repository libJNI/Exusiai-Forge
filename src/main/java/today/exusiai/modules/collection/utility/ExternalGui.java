package today.exusiai.modules.collection.utility;


import today.exusiai.modules.AbstractModule;

public class ExternalGui extends AbstractModule {
	public ExternalGui() {
		super("ExternalGui", 0, Category.Visuals);
	}

	@Override
	public void onEnable() {
			today.exusiai.ExternalGui gui = new today.exusiai.ExternalGui();
			gui.setVisible(true);
			this.toggle();
	}
}

package xyz.gucciclient.gamehook;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.GuiIngameForge;
import xyz.gucciclient.event.EventManager;
import xyz.gucciclient.event.impl.EventRenderGui;

public class GuiIngameHook extends GuiIngameForge {

    public GuiIngameHook(Minecraft wd4j) {
		super(wd4j);
	}
    protected void renderSleepFade(int width, int height) {
        EventManager.getInstance().postAll(new EventRenderGui());
        super.renderSleepFade(width, height);
    }
}

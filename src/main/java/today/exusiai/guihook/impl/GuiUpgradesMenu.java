package today.exusiai.guihook.impl;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

/**
 * Created by Arithmo on 9/17/2017 at 11:42 AM.
 */
public class GuiUpgradesMenu extends GuiScreen {

    protected GuiScreen parentScreen;

    public GuiUpgradesMenu(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 50, height / 2 + 50, 100, 20, "Back"));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        mc.fontRendererObj.drawStringWithShadow("Forge Mod Manager", (width - mc.fontRendererObj.getStringWidth("Forge Mod Manager")) / 2, height / 2 - 150, -1);
        mc.fontRendererObj.drawStringWithShadow("forgot to finish this", (width - mc.fontRendererObj.getStringWidth("forgot to finish this")) / 2, height / 2 - 138, -1);
    }

}

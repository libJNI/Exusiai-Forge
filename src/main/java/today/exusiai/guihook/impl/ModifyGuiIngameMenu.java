package today.exusiai.guihook.impl;

import java.awt.Color;
import java.io.IOException;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import today.exusiai.Client;
import today.exusiai.font.Fonts;
import today.exusiai.font.TTFFontRenderer;
import today.exusiai.utils.ColorUtils;
import today.exusiai.utils.RenderUtils;
import today.exusiai.utils.Wrapper;

@SideOnly(Side.CLIENT)
public class ModifyGuiIngameMenu extends GuiScreen
{
    private int field_146445_a;
    private int field_146444_f;

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui()
    {
        this.field_146445_a = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));

        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }

        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(12, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("fml.menu.modoptions")));
        GuiButton guibutton;
        this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 - 100, this.height / 4 + 72 + i, 200, 20, I18n.format("menu.shareToLan", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    @Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 1:
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.func_181540_al();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);

                if (flag)
                {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                }

            case 2:
            case 3:
            default:
                break;
            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;
            case 5:
                if (this.mc.thePlayer != null) {
                    this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                }
                break;
            case 6:
                if (this.mc.thePlayer != null) {
                    this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                }
                break;
            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 12:
                Wrapper.getMinecraft().displayGuiScreen(new GuiUpgradesMenu(this));
                break;
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    @Override
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

	private float animState = 75f;
	private boolean anim = true;
	
    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        if(!Client.clientDestruct) {
            this.renderMusicPlayer(width - 200, 5);
        }
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    public void renderMusicPlayer(int x, int y) {
    	RenderUtils.drawRoundedRect(x, y, x + 195, 60 + y - 5, ColorUtils.getColor(232, 220), ColorUtils.getColor(232, 220));
        
    	TTFFontRenderer font = Fonts.Verdana18;

    	RenderUtils.rectangleBordered(x + 5, y + 5, x + 200 - 195 + 45, 10 + 45 + y - 5, 1.0f, ColorUtils.getColor(225, 50), ColorUtils.getColor(160, 150));

            font.drawString("Music", x + 200 - 145, 15 + y - 5, 0xff000000);

        int readedSeconds = 0;
        int totalSeconds = 0;

        font.drawString("0:00", x + 200 - 145, 25 + y - 5, 0xff000000);
        font.drawString("0:00", x + 200 - 37, 25 + y - 5, 0xff000000);


            drawRect(x + 200 - 115, 30 + y - 5, x + 200 - 40, 32 + y - 5, 0xff000000);
            drawRect(x + 200 - 115, 30 + y - 5, (int) (x + 200 - 115 + (115 - 40) * ((float) readedSeconds / Math.max(1, totalSeconds))), 32 + y - 5, 0xff777777);

        RenderUtils.drawRoundedRect(x + 200 - 145, 40 + y - 5, x + 200 - 145 + 29, 40 + 15 + y - 5, ColorUtils.getColor(150, 220), ColorUtils.getColor(150, 220));
        font.drawString("Play", x + 200 - 143, 42 + y - 5, ColorUtils.getColor(45));

        RenderUtils.drawRoundedRect(x + 200 - 115, 40 + y - 5, x + 200 - 115 + 28, 40 + 15 + y - 5, ColorUtils.getColor(150, 220), ColorUtils.getColor(150, 220));
        font.drawString("Next", x + 200 - 113, 42 + y - 5, ColorUtils.getColor(45));

        font.drawString("Volume", x + 200 - 60, 42 + y - 5, ColorUtils.getColor(45));

        RenderUtils.drawRoundedRect(x + 200 - 75, 40 + y - 5, x + 200 - 75 + 13, 40 + 15 + y - 5, ColorUtils.getColor(150, 220), ColorUtils.getColor(150, 220));
        font.drawString("-", x + 200 - 71, 42 + y - 5, ColorUtils.getColor(45));
        RenderUtils.drawRoundedRect(x + 200 - 23, 40 + y - 5, x + 200 - 23 + 13, 40 + 15 + y - 5, ColorUtils.getColor(150, 220), ColorUtils.getColor(150, 220));
        font.drawString("+", x + 200 - 21, 42 + y - 5, ColorUtils.getColor(45));
    }
}
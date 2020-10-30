package today.exusiai.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.collection.other.NameProtect;

public class MargeleRenderer extends FontRenderer {
    public MargeleRenderer(GameSettings gameSettingsIn, ResourceLocation location, TextureManager textureManagerIn, boolean unicode) {
        super(gameSettingsIn, location, textureManagerIn, unicode);
    }
    
    public int drawString(String text, int x, int y, int color, boolean dropShadow)
    {
    	return super.drawString(text, x, y, color, dropShadow);
    }
}

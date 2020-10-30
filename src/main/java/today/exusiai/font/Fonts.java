package today.exusiai.font;

import java.awt.Font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Fonts {
    //public static final MCFontRenderer verdana12 = new MCFontRenderer(new Font("Verdana", Font.PLAIN, 12), true, true);
	public static final TTFFontRenderer Verdana18 = new TTFFontRenderer(new Font("Verdana", 0, 18), true);
	public static final TTFFontRenderer nametagsFont = new TTFFontRenderer(new Font("Tahoma", 0, 18), true);
	public static final TTFFontRenderer Tahoma20 = new TTFFontRenderer(new Font("Tahoma", 0, 20), true);
	public static final TTFFontRenderer Tahoma16 = new TTFFontRenderer(new Font("Tahoma", 0, 16), true);
	public static final MCFontRenderer Tahoma18 = new MCFontRenderer(new Font("Tahoma", Font.PLAIN, 18), true, true);
    public static final MCFontRenderer Roboto13 = new MCFontRenderer(new Font("Roboto", Font.PLAIN, 13), true, true);
    public static final MCFontRenderer Roboto11 = new MCFontRenderer(new Font("Roboto", Font.PLAIN, 11), true, true);
    public static final MCFontRenderer Roboto18 = new MCFontRenderer(new Font("Roboto", Font.PLAIN, 18), true, true);


    public static Font fontFromTTF(ResourceLocation fontLocation, float fontSize, int fontType) {
        Font output = null;
        try {
            output = Font.createFont(fontType, Minecraft.getMinecraft().getResourceManager().getResource(fontLocation).getInputStream());
            output = output.deriveFont(fontSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

}

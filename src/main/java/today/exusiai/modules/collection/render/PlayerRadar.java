package today.exusiai.modules.collection.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.ColorUtils;
import today.exusiai.utils.RenderUtils;
import today.exusiai.utils.Wrapper;

public class PlayerRadar extends AbstractModule {
    private float gamma;

    public PlayerRadar() {
        super("PlayerRadar", 0, Category.Visuals);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        int y = 2;
        ScaledResolution sr = new ScaledResolution(Wrapper.getMinecraft());

        for(Object o : Wrapper.getWorld().loadedEntityList) {
            if(o instanceof EntityPlayer) {

                EntityPlayer e = (EntityPlayer) o;
                float range = (float) Wrapper.getPlayer().getDistanceToEntity(e);
                float health = ((EntityPlayer) e).getHealth();

                String heal = " \u00a72[" + RenderUtils.DF(health, 0) + "] ";
                if (health >= 12.0) {
                    heal = " \u00a72[" + RenderUtils.DF(health, 0) + "] ";
                }
                else if (health >= 4.0) {
                    heal = " \u00a76[" + RenderUtils.DF(health, 0) + "] ";
                }
                else {
                    heal = " \u00a74[" + RenderUtils.DF(health, 0) + "] ";
                }

                String name = e.getGameProfile().getName();
                String str = name + heal + "\u00a77" + "[" + RenderUtils.DF(range, 0) + "]";

                int color;
                if(e.isInvisible()) {
                    color = ColorUtils.color(155, 155, 155, 255);
                } else {
                    color = ColorUtils.color(255, 255, 255, 255);
                }

                Wrapper.getMinecraft().fontRendererObj.drawString(str, sr.getScaledWidth() - Wrapper.getMinecraft().fontRendererObj.getStringWidth(str), y, color);

                y += 12;
            }
        }
    }
}

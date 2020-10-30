package today.exusiai.notifications;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import today.exusiai.font.Fonts;
import today.exusiai.utils.ColorUtils;
import today.exusiai.utils.RenderUtils;

import org.lwjgl.opengl.GL11;

public class NotificationRenderer implements INotificationRenderer {

	public void draw(List notifications) {
		Minecraft mc = Minecraft.getMinecraft();
		ScaledResolution scaledRes = new ScaledResolution(mc);
		float y = (float) scaledRes.getScaledHeight() - (float) (notifications.size() * 22 + 12
				+ (mc.currentScreen != null && mc.currentScreen instanceof GuiChat ? 12 : 0));

		for (Iterator var5 = notifications.iterator(); var5.hasNext(); y += 22.0F) {
			INotification notification = (INotification) var5.next();
			Notification not = (Notification) notification;
			not.translate.interpolate(not.getTarX(), y, 0.3F);
			int s = scaledRes.getScaleFactor();
			float subHeaderWidth = Fonts.Tahoma16.getWidth(not.getSubtext());
			float headerWidth = Fonts.Tahoma20.getWidth(not.getHeader());
			float x = (float) (scaledRes.getScaledWidth() - 20)
					- (headerWidth > subHeaderWidth ? headerWidth : subHeaderWidth);
			GL11.glPushMatrix();
			GL11.glEnable(3089);
			GL11.glScissor((int) not.translate.getX() * s,
					(int) ((float) scaledRes.getScaledWidth() - not.translate.getY() * (float) s),
					scaledRes.getScaledWidth() * s, (int) ((not.translate.getY() + 50.0F) * (float) s));
			RenderUtils.rectangle((double) x, (double) not.translate.getY(), (double) scaledRes.getScaledWidth(),
					(double) (not.translate.getY() + 22.0F - 1.0F), ColorUtils.getColor(0, 200));
			GlStateManager.pushMatrix();
			GlStateManager.translate(x + 10.0F, not.translate.getY() + 13.0F, 0.0F);
			GlStateManager.rotate(270.0F, 0.0F, 0.0F, 90.0F);

			for (int i = 0; i < 11; ++i) {
				RenderUtils.drawCircle(0.0F, 0.0F, (float) (11 - i), 3, this.getColor(not.getType()));
			}

			RenderUtils.drawCircle(0.0F, 0.0F, 11.0F, 3, ColorUtils.getColor(0));
			GlStateManager.popMatrix();
			RenderUtils.rectangle((double) x + 9.6D, (double) (not.translate.getY() + 5.0F), (double) x + 10.3D,
					(double) (not.translate.getY() + 13.0F), ColorUtils.getColor(0));
			RenderUtils.rectangle((double) x + 9.6D, (double) (not.translate.getY() + 15.0F), (double) x + 10.3D,
					(double) (not.translate.getY() + 17.0F), ColorUtils.getColor(0));

			Fonts.Tahoma20.drawStringWithShadow(not.getHeader(), x + 20.0F, not.translate.getY() + 1.0F, -1);
			Fonts.Tahoma16.drawStringWithShadow(not.getSubtext(), x + 20.0F, not.translate.getY() + 12.0F, -1);
			GL11.glDisable(3089);
			GL11.glPopMatrix();
			if (not.checkTime() >= not.getDisplayTime() + not.getStart()) {
				not.setTarX(scaledRes.getScaledWidth());
				if (not.translate.getX() >= (float) scaledRes.getScaledWidth()) {
					notifications.remove(notification);
				}
			}
		}

	}

	private int getColor(Notifications.Type type) {
		int color = 0;
		switch (type) {
		case INFO:
			color = ColorUtils.getColor(64, 131, 214);
			break;
		case NOTIFY:
			color = ColorUtils.getColor(242, 206, 87);
			break;
		case WARNING:
			color = ColorUtils.getColor(226, 74, 74);
			break;
		case SPOTIFY:
			color = ColorUtils.getColor(30, 215, 96);
		}

		return color;
	}
}

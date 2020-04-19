package xyz.gucciclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;

public class Wrapper {
   public static Minecraft getMinecraft() {
      return Minecraft.getMinecraft();
   }

   public static EntityPlayerSP getPlayer() {
      return getMinecraft().thePlayer;
   }

   public static WorldClient getWorld() {
      return getMinecraft().theWorld;
   }

   public static void drawCenteredString(String text, int x, int y, int color) {
      getMinecraft().fontRendererObj.drawString(text, x - getMinecraft().fontRendererObj.getStringWidth(text) / 2, y, color);
   }

   public static PlayerControllerMP getPlayerController() {
      return getMinecraft().playerController;
   }
}

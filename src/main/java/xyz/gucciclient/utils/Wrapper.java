package xyz.gucciclient.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

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

   public static boolean isMoving(final Entity e) {
      return e.motionX != 0.0 && e.motionZ != 0.0 && (e.motionY != 0.0 || e.motionY > 0.0);
   }

   public static boolean isMoving() {
      return isMoving(getPlayer());
   }

   public static void printChat(String text) {
      getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(text));
   }

   public static void sendChat_NoFilter(String text) {
      getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(text));
   }

   public static void sendChat(String text) {
      getMinecraft().thePlayer.sendChatMessage(text);
   }
}

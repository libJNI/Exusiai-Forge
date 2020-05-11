package xyz.gucciclient;

import net.minecraft.client.Minecraft;
import net.minecraft.profiler.Profiler;

public class ProfilerHook
        extends Profiler {

    @Override
    public void startSection(String string) {
        if (string.equals("destroyProgress")) {

        }
        if (string.equals("gui")) {
            Minecraft.getMinecraft().entityRenderer.setupOverlayRendering();

        }
        super.startSection(string);
    }
}

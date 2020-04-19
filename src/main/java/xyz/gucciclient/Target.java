package xyz.gucciclient;

import java.lang.instrument.Instrumentation;

import javax.swing.JOptionPane;

import net.minecraft.launchwrapper.LaunchClassLoader;

public class Target {
	//PRE START CODE OF MINECRAFT just instance a Squad.class pre start

	public static void agentmain(final String args, final Instrumentation instrumentation) {
		try {
			for (final Class<?> classes : instrumentation.getAllLoadedClasses()) {
				if (classes.getName().equals("net.minecraft.client.Minecraft")) {
					LaunchClassLoader classLoader = (LaunchClassLoader)classes.getClassLoader();
					classLoader.addURL(Client.class.getProtectionDomain().getCodeSource().getLocation());
					final Class<?> instance = classLoader.loadClass(Client.class.getName());
					instance.newInstance();
					return;
				}
			}
		}
		catch (Exception e) {}
	}
}

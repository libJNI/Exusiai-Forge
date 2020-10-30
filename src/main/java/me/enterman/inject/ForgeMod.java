package me.enterman.inject;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import today.exusiai.Client;

@Mod(modid="exusiai", name="exusiai", version="null", acceptedMinecraftVersions="[1.8.9]")
public class ForgeMod {
	
	@EventHandler
	public void Mod(FMLPreInitializationEvent event) {
		new Client();
	}
}

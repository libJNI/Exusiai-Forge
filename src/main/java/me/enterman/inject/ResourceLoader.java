package me.enterman.inject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.*;

public class ResourceLoader implements IResourcePack {
	public static void init(){
		Field field;
		try {
			field = Minecraft.class.getDeclaredField("defaultResourcePacks");
			field.setAccessible(true);
			((List<IResourcePack>) field.get(Minecraft.getMinecraft())).add(new ResourceLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
		Minecraft.getMinecraft().refreshResources();
	}
	Map<String,InputStream> cache = new HashMap<>();
	@Override
	public InputStream getInputStream(ResourceLocation location) throws IOException {
		if(!location.getResourceDomain().equals("exusiai")) return null;
		if(!cache.containsKey(location.getResourcePath())) {
			InputStream stream = this.getClass().getResourceAsStream(location.getResourcePath());
			if(stream == null) return null;
			cache.put(location.getResourcePath(),stream);
			return stream;
		}
		return cache.get(location.getResourcePath());
	}

	@Override
	public boolean resourceExists(ResourceLocation location) {
		if(!location.getResourceDomain().equals("exusiai")) return false;
		if(!cache.containsKey(location.getResourcePath())) {
			InputStream stream = this.getClass().getResourceAsStream(location.getResourcePath());
			if(stream == null) return false;
			cache.put(location.getResourcePath(),stream);
		}
		return true;
	}

	@Override
	public Set<String> getResourceDomains() {
		HashSet<String> set = new HashSet<>();
		set.add("exusiai");
		return set;
	}

	@Override
	public <T extends IMetadataSection> T getPackMetadata(IMetadataSerializer p_135058_1_, String p_135058_2_) throws IOException {
		return null;
	}

	@Override
	public BufferedImage getPackImage() throws IOException {
		return null;
	}

	@Override
	public String getPackName() {
		return "ocD pack";
	}
}

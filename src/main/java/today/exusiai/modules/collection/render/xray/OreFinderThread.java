package today.exusiai.modules.collection.render.xray;

import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.collection.render.Xray;
import today.exusiai.utils.Location;
import today.exusiai.utils.Wrapper;

public class OreFinderThread implements Runnable {

	private HashMap<Location, Integer> ores;
	
	public int count;
	
	public boolean finished, started;
	
	public OreFinderThread() {
		ores = new HashMap<Location, Integer>();
		count = 0;
		finished = started = false;
	}
	
	@Override
	public void run() {
		if(!started) {
			started = true;
			scanOres();
		}
	}
	
	public void scanOres() {
		Minecraft mc = Minecraft.getMinecraft();
		
		int radius = (int)Xray.RADIUS.getValue();
		
		HashMap<Location, Integer> xres = new HashMap<Location, Integer>();
		
		count = 0;
		for(int x = 0; x < (radius * 2); x++) {
			for(int y = 0; y < (radius * 2); y++) {
				for(int z = 0; z < (radius * 2); z++) {
					int px = (int) mc.thePlayer.posX;
					int py = (int) mc.thePlayer.posY;
					int pz = (int) mc.thePlayer.posZ;
					
					Location loc = new Location(((px - radius) + x), ((py - radius) + y), ((pz - radius) + z));
					if(Blocks.diamond_ore.equals(Wrapper.getWorld().getBlockState(new BlockPos(((px - radius) + x), ((py - radius) + y), ((pz - radius) + z))).getBlock())) { // tile.paladium_ore
						xres.put(loc, 0xFFF68F43);
						count++;
					}
				}	
			}	
		}
		ores.clear();
		for(Location l : xres.keySet()) {
			ores.put(l, xres.get(l));
		}
//		ores = xres;
		finished = true;
	}
	
	public HashMap<Location, Integer> getOres(){
		if(!finished || !started)
			return null;
		return ores;
	}
	
	public void reset() {
		finished = started = false;
	}

}

package today.exusiai.utils.tpaura;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import today.exusiai.utils.Location;
import today.exusiai.utils.Wrapper;

public class TPCore {
	
	private static Minecraft mc = Minecraft.getMinecraft();
	
	public static int tpBuffer = 3;
	
	public static int getXDiff(Entity e) {
		return (int)Math.round(Wrapper.getPlayer().posX - e.posX);
	}
	
	public static int getYDiff(Entity e) {
		return (int)Math.round(Wrapper.getPlayer().posY - e.posY);
	}
	
	public static int getZDiff(Entity e) {
		return (int)Math.round(Wrapper.getPlayer().posZ - e.posZ);
	}
	
	public static int getTPTimesToEntity(Entity e) {
		return (int)Math.round(mc.thePlayer.getDistanceToEntity(e) / tpBuffer);
	}

	public static TPPath getTPPathToEntity(Entity e) {
		ArrayList<Location> path = new ArrayList<Location>();
		
		ArrayList<Integer> Xpath = new ArrayList<Integer>();
		ArrayList<Integer> Ypath = new ArrayList<Integer>();
		ArrayList<Integer> Zpath = new ArrayList<Integer>();
		
		double x = Math.round(mc.thePlayer.posX), y = Math.round(mc.thePlayer.posY), z = Math.round(mc.thePlayer.posZ);

		// kinda useless
		int yTPTimes = (int)Math.round(getYDiff(e) / tpBuffer);
		int xTPTimes = Math.abs((int)Math.round(getXDiff(e)) / tpBuffer);
		int zTPTimes = Math.abs((int)Math.round(getZDiff(e)) / tpBuffer);
		
		for(int i = 0; i < xTPTimes; i++) {
			if(getXDiff(e) < 0) {
				x = x + tpBuffer;
			}else {
				x = x - tpBuffer;
			}
			Xpath.add((int)x);
		}
		for(int i = 0; i < zTPTimes; i++) {
			if(getZDiff(e) < 0) {
				z = z + tpBuffer;
			}else {
				z = z - tpBuffer;
			}
			Zpath.add((int)z);
		}
		for(int i = 0; i < yTPTimes; i++) {
			if(getYDiff(e) < 0) {
				y = y + tpBuffer;
			}else {
				y = y - tpBuffer;
			}
			Zpath.add((int)z);
		}
		
		double xPos = mc.thePlayer.posX;
		double zPos = mc.thePlayer.posZ;
		for(int i2 = 0; i2 < getTPTimesToEntity(e); i2++) {
			if(Xpath.size() > i2)
				xPos = Xpath.get(i2);
			if(Zpath.size() > i2)
				zPos = Zpath.get(i2);
			
			path.add(new Location(xPos,	y + 1, zPos));
		}
		
		return new TPPath(e, path);
	}

}

package today.exusiai.utils.tpaura;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import today.exusiai.utils.Location;

public class TPPath {
	
	private ArrayList<Location> path, alreadyUsed;
	private Entity entity;
	private int tptimes;
	
	public TPPath(Entity entity, ArrayList<Location> path) {
		this.path = path;
		this.alreadyUsed = new ArrayList<Location>();
		this.tptimes = path.size();
		this.entity = entity;
	}

	public ArrayList<Location> getPath() {
		return path;
	}
	
	public Entity getEntity() {
		return entity;
	}
	
	public Location getLatestTPPos() {
		Location bp = this.path.get(0);
		alreadyUsed.add(bp);
		this.path.remove(0);
		return bp;
	}

	public Location getLatestBackPos() {
		Location bp = this.alreadyUsed.get(0);
		this.alreadyUsed.remove(0);
		tptimes--;
		return bp;
	}
	
	public int getTPTimes() {
		return tptimes;
	}

}

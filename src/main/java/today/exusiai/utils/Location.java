package today.exusiai.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;

public class Location {
	
	private double x, y, z;
	
	public Location(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Location(Entity e) { this(e.posX, e.posY, e.posZ); }
	public Location(Location loc) { this(loc.getX(), loc.getY(), loc.getZ()); }
	public Location(AxisAlignedBB aabb) { this(aabb.minX + ((aabb.maxX - aabb.minX) / 2), aabb.minY + ((aabb.maxY - aabb.minY) / 2), aabb.minZ + ((aabb.maxZ - aabb.minZ) / 2)); }
	
	public Location offset(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}
	
	public Location offset(Entity e) { return offset(e.posX, e.posY, e.posZ); }
	public Location offset(Location loc) { return offset(loc.getX(), loc.getY(), loc.getZ()); }
	public Location offset(AxisAlignedBB aabb) { return offset(aabb.minX + ((aabb.maxX - aabb.minX) / 2), aabb.minY + ((aabb.maxY - aabb.minY) / 2), aabb.minZ + ((aabb.maxZ - aabb.minZ) / 2)); }
	
	public float getDistanceToEntity(Entity p_70032_1_) {
	    return MathHelper.sqrt_float((float)(this.x - p_70032_1_.posX) * (float)(this.x - p_70032_1_.posX) + (float)(this.y - p_70032_1_.posY) * (float)(this.y - p_70032_1_.posY) + (float)(this.z - p_70032_1_.posZ) * (float)(this.z - p_70032_1_.posZ));
	}
	
	public double getX() { return x; }
	public double getY() { return y; }
	public double getZ() { return z; }
	
	public String getXYZ() { return x + "," + y + "," + z; }

	@Override
	public Location clone() { return new Location(this); }
	
	public AxisAlignedBB genAABB() {
		return AxisAlignedBB.fromBounds(x, y, z, x + 1, y + 1, z + 1);
	}
	
	@Override
	public String toString() { return "me.xtrm.PalaClient.utils.Location()[" + getXYZ() + "];"; }

}

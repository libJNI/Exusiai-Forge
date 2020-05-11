package xyz.gucciclient.other;

import java.util.LinkedList;


import net.minecraft.block.Block;
import xyz.gucciclient.modules.mods.render.xray.XRayData;

public class XRayManager {
	
	public static LinkedList<XRayData> xrayList = new LinkedList<XRayData>();
	
	public static void clear() {
		if(!xrayList.isEmpty()) {
			xrayList.clear();
		}
	}
	
	public static LinkedList<XRayData> getDataById(int id) {
		LinkedList<XRayData> list = new LinkedList<XRayData>();
		for(XRayData x : xrayList) {
        	if(x.getId() == id) {
        		list.add(x);
        	}
        }
		return list;
	}
	
	public static XRayData getDataByMeta(int meta) {
		XRayData data = null;
		for(XRayData x : xrayList) {
        	if(x.getMeta() == meta) {
        		data = x;
        	}
        }
		return data;
	}
	
	public static void add(XRayData data) {
		if(Block.getBlockById(data.getId()) == null) {
			return;
		}
		LinkedList<XRayData> list = getDataById(data.getId());
		if(list.isEmpty()) {
			addData(data);
			return;
		}
		boolean isId = false;
		boolean isMeta = false;
		for(XRayData currentData : list) {
			if(currentData.getId() == data.getId()) {
				isId = true;
			}
			if(currentData.getMeta() == data.getMeta()) {
				isMeta = true;
			}
		}
		if(isId && isMeta) {
			return;
		}
		addData(data);
	}
	
	public static void addData(XRayData data) {
		xrayList.add(data);
	}
	
	public static void removeData(int id) {
		for(XRayData data : getDataById(id)) {
			if(xrayList.contains(data)) {
				xrayList.remove(data);

			}
		}
	}
}

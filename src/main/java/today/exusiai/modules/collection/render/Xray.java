package today.exusiai.modules.collection.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import today.exusiai.event.ActiveEvent;
import today.exusiai.event.events.EventRender3D;
import today.exusiai.modules.AbstractModule;
import today.exusiai.modules.collection.render.xray.OreFinderThread;
import today.exusiai.utils.ColorUtils;
import today.exusiai.utils.Location;
import today.exusiai.utils.RenderUtils;
import today.exusiai.utils.Wrapper;
import today.exusiai.values.BooleanValue;
import today.exusiai.values.NumberValue;

public class Xray extends AbstractModule {
	private ScheduledExecutorService exec;
	private OreFinderThread thread;
	private boolean utc = true;
	private HashMap<Location, Integer> ores = new HashMap<Location, Integer>();
	
   public final static NumberValue RADIUS = new NumberValue("Radius", 3.3D, 3.0D, 100.0D);
   public final static BooleanValue USE_THREAD_CALC = new BooleanValue("UseThreadCalc", false);

   public Xray() {
      super("Xray", 0, AbstractModule.Category.Combat);
      addBoolean(USE_THREAD_CALC);
      addValue(RADIUS);
   }
   
	@Override
	public void onEnable() {
		if(USE_THREAD_CALC.getState()) {
			exec = Executors.newSingleThreadScheduledExecutor();
			exec.scheduleAtFixedRate(thread = new OreFinderThread(), 0, 100, TimeUnit.MILLISECONDS);
		}	
		super.onEnable();
	}
	
	@Override
	public void onDisable() {
		if(exec != null) {
			exec.shutdown();
			exec = null;
		}		
		
		super.onDisable();
	}
	
   @ActiveEvent
   public void onUpdate() {
		if(Wrapper.getPlayer().hurtTime == 5) {
			thread.reset();
		}
   }
   
   @ActiveEvent
   public void onRender3D(EventRender3D event) {
	   boolean utcx = USE_THREAD_CALC.getState();
		
		if(utc != utcx) {
			utc = utcx;
			toggle();
			//ChatUtils.sendMessage("RÃ©activez le module");
			return;
		}
		
		if(utc) {
			float fx = event.getPartialTicks();
			
			if(thread == null)
				return;
			
			if(thread.started && thread.finished) {
				ores = (HashMap<Location, Integer>) thread.getOres().clone();
			}
			ArrayList<Location> locs = new ArrayList<Location>();
			locs.addAll(ores.keySet());

			RenderUtils.init3D();
			for(Location l : locs) {
				int color = ores.get(l);
				RenderUtils.drawBlockESP(new BlockPos(l.getX(), l.getY(), l.getZ()));
			}
			RenderUtils.reset3D();
			
		}else {
			int radius = (int)RADIUS.getValue();
			for(int x = -radius; x<radius; x++) {
				for(int y = -radius; y<radius; y++) {
					for(int z = -radius; z<radius; z++) {
						Location loc = new Location(mc.thePlayer).offset(x, y, z);
						HashMap<Location, Integer> xres = new HashMap<Location, Integer>();
						Block block = mc.theWorld.getBlockState(new BlockPos((int)loc.getX(), (int)loc.getY(), (int)loc.getZ())).getBlock();
						
						if(Blocks.diamond_ore.equals(block)) {
							xres.put(loc, 0xFF11DBCD);
						}
						
						RenderUtils.init3D();
						for(Location l : xres.keySet()) {
							int color = xres.get(l);
							RenderUtils.drawBlockESP(new BlockPos(l.getX(), l.getY(), l.getZ()));
						}
						RenderUtils.reset3D();
					}
				}
			}
		}
   }
}

package today.exusiai.modules.collection.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.ReflectionHelper;
import today.exusiai.utils.RenderUtils;
import today.exusiai.values.BooleanValue;

public class ItemESP extends AbstractModule {
	private BooleanValue player = new BooleanValue("Outlined", true);
	
   public ItemESP() {
      super("ItemESP", 0, AbstractModule.Category.Visuals);
      this.addBoolean(this.player);
   }
   
   @SubscribeEvent
	public void RenderWorld(RenderWorldLastEvent event) {
	   for (Object o : mc.theWorld.loadedEntityList) {
   		if (!(o instanceof EntityItem)) {
			continue;
		}
   		EntityItem item = (EntityItem)o;
		   	double itemposX = item.posX;
		   	double x = 0;
		   	double y = 0;
		   	double z = 0;
			try {
				x = itemposX - ReflectionHelper.getRenderPosX();
		   itemposX = item.posY + 0.5D;
				y = itemposX - ReflectionHelper.getRenderPosY();
		   itemposX = item.posZ;
				z = itemposX - ReflectionHelper.getRenderPosZ();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   	GL11.glEnable(3042);
		   	GL11.glLineWidth(2.0F);
		   	GL11.glColor4f(1, 1, 1, .75F);
		   	GL11.glDisable(3553);
		   	GL11.glDisable(2929);
		   	GL11.glDepthMask(false);
           if((Boolean) this.player.getState()) {
	   			RenderUtils.drawOutlinedBoundingBox(new AxisAlignedBB(x - .2D, y-0.05, z - .2D, x + .2D, y - 0.45d, z + .2D));
	   		}else {
	   			GL11.glColor4f(1, 1, 1, 0.15f);
	 	   		RenderUtils.drawBoundingBox(new AxisAlignedBB(x - .2D, y-0.05, z - .2D, x + .2D, y - 0.45d, z + .2D));
	   		}
	   		GL11.glEnable(3553);
	   		GL11.glEnable(2929);
	   		GL11.glDepthMask(true);
	   		GL11.glDisable(3042);
   	}
    }
}

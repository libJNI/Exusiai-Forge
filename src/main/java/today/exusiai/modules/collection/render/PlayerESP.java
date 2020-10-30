package today.exusiai.modules.collection.render;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;

import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.ColorUtils;
import today.exusiai.utils.RenderUtils;
import today.exusiai.utils.Wrapper;
import today.exusiai.utils.entity.EntityValidator;
import today.exusiai.utils.entity.impl.EntityCheck;
import today.exusiai.values.BooleanValue;

public class PlayerESP extends AbstractModule {
	private final EntityValidator entityValidator = new EntityValidator();
	private BooleanValue player = new BooleanValue("Players", true);
	private BooleanValue animals = new BooleanValue("Animals", false);
	private BooleanValue mobs = new BooleanValue("Mobs", false);
	private BooleanValue invisibles = new BooleanValue("Invisibles", false);
   public PlayerESP() {
      super("EntityESP", 0, AbstractModule.Category.Visuals);
      EntityCheck entityCheck = new EntityCheck(this.player, this.animals, this.mobs, this.invisibles);
      this.entityValidator.add(entityCheck);
      this.addBoolean(this.player,this.animals,this.mobs,this.invisibles);
   }
   
   @SubscribeEvent
	public void RenderWorld(RenderWorldLastEvent e) {
       for (Object object : Wrapper.getWorld().loadedEntityList) {
           if(object instanceof EntityLivingBase && !(object instanceof EntityArmorStand)) {
               EntityLivingBase entity = (EntityLivingBase)object;
               this.render(entity, e.partialTicks);
           }
       }
    }
   public int getcolor(EntityLivingBase entity) {
	String text = entity.getDisplayName().getFormattedText();
   	if(Character.toLowerCase(text.charAt(0)) == '§'){
   		
       	char oneMore = Character.toLowerCase(text.charAt(1));
       	int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
       	
       	if (colorCode < 16) {
               try {
                   int newColor = mc.fontRendererObj.getColorCode(oneMore);   
                    return ColorUtils.getColor((newColor >> 16), (newColor >> 8 & 0xFF), (newColor & 0xFF), 255);
               } catch (ArrayIndexOutOfBoundsException ignored) {
               }
           }
   		}
   		return ColorUtils.getColor(255, 255, 255, 255);
   }
   
    void render(EntityLivingBase entity, float ticks) {
        if(entity == Wrapper.getPlayer()) {
            return;
        }
        if (this.entityValidator.validate(entity)) {
        if(entity.isInvisible()) {
            RenderUtils.entityESPBox(entity, 0, 0, 0, 1);
            return;
        }
        if(entity.hurtTime > 0) {
            RenderUtils.entityESPBox(entity,getcolor(entity), getcolor(entity), getcolor(entity), 1);
            return;
        }
        RenderUtils.entityESPBox(entity,getcolor(entity), getcolor(entity), getcolor(entity), 1);
        }
    }
}

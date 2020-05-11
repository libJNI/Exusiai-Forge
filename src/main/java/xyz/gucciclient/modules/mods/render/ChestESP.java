package xyz.gucciclient.modules.mods.render;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.gucciclient.modules.Module;
import xyz.gucciclient.utils.RenderUtils;
import xyz.gucciclient.utils.Wrapper;

import java.util.ArrayDeque;

public class ChestESP extends Module {
    private int maxChests = 1000;
    public boolean shouldInform = true;
    private TileEntityChest openChest;
    private ArrayDeque<TileEntityChest> emptyChests = new ArrayDeque<TileEntityChest>();
    private ArrayDeque<TileEntityChest> nonEmptyChests = new ArrayDeque<TileEntityChest>();
    public ChestESP() {
        super("ChestESP", 0, Module.Category.Visuals);
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        int chests = 0;
        for(int i = 0; i < Wrapper.getWorld().loadedTileEntityList.size(); i++)
        {
            TileEntity tileEntity = Wrapper.getWorld().loadedTileEntityList.get(i);
            if(chests >= maxChests) {
                break;
            }

            if(tileEntity instanceof TileEntityChest){
                chests++;
                TileEntityChest chest = (TileEntityChest)tileEntity;
                boolean trapped = ((TileEntityChest)chest).getChestType() == 0;
                if(emptyChests.contains(tileEntity)) {
                    RenderUtils.drawBlockESP(chest.getPos(), 0.25F, 0.25F, 0.25F);
                } else if(nonEmptyChests.contains(tileEntity)) {

                    if(trapped) {
                        RenderUtils.drawBlockESP(chest.getPos(), 0, 1, 0);
                    } else {
                        RenderUtils.drawBlockESP(chest.getPos(), 1, 0F, 0);
                    }

                } else if(trapped) {
                    RenderUtils.drawBlockESP(chest.getPos(), 0, 1F, 0);
                } else {
                    RenderUtils.drawBlockESP(chest.getPos(), 1, 0f, 0);
                }

                if(trapped) {
                    RenderUtils.drawBlockESP(chest.getPos(), 0, 1F, 0);
                } else {
                    RenderUtils.drawBlockESP(chest.getPos(), 1, 0F, 0);
                }

            } else if(tileEntity instanceof TileEntityEnderChest) {
                chests++;
                RenderUtils.drawBlockESP(((TileEntityEnderChest)tileEntity).getPos(), 1, 0, 1);
            }
        }

        for(int i = 0; i < Wrapper.getWorld().loadedEntityList.size(); i++){
            Entity entity = Wrapper.getWorld().loadedEntityList.get(i);
            if(chests >= maxChests) {
                break;
            }
            if(entity instanceof EntityMinecartChest){
                chests++;
                RenderUtils.drawBlockESP(((EntityMinecartChest)entity).getPosition(), 1, 1, 1);
            }
        }

        if(chests >= maxChests && shouldInform){
            Wrapper.printChat("To prevent lag, it will only show the first " + maxChests + " chests.");
            shouldInform = false;
        } else if(chests < maxChests) {
            shouldInform = true;
        }
    }

}

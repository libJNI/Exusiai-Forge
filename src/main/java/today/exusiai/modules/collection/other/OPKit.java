package today.exusiai.modules.collection.other;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import today.exusiai.modules.AbstractModule;
import today.exusiai.utils.ItemTemplate;

public class OPKit extends AbstractModule {

    private ItemTemplate[] templates = new ItemTemplate[]{new ItemTemplate(
            "M1CEsHandy", Items.iron_sword, "{AttributeModifiers:[{AttributeName:generic.attackDamage,Name:generic.attackDamage, Amount:2147483647,Operation:0, UUIDMost:246216, UUIDLeast:24636}], display:{Name:\u00a79\u00a7l我的世界Exusiai客户端 -> https://Arithmo.tk/}, Unbreakable:1,HideFlags:63,ench:[{id:16,lvl:32767}]}"), new ItemTemplate(
                    "Super Bow", Items.bow, "{ench:[{id:48, lvl:32767}, {id:49, lvl:5}, {id:50, lvl:1},{id:51, lvl:1}], display:{Name:\u00a79M1CE}, HideFlags:63}"), new ItemTemplate(
                            "M1CEHelm", Items.golden_helmet, "{ench:[{id:7, lvl:32767},{id:0, lvl:32767}], AttributeModifiers:[{AttributeName:generic.maxHealth, Name:generic.maxHealth,Amount:200, Operation:0, UUIDMost:43631, UUIDLeast:2641}], display:{Name:\u00a79M1CESquad}, HideFlags:63,Unbreakable:1}"), new ItemTemplate(
                                    "M1CEPlate", Items.golden_chestplate, "{ench:[{id:7, lvl:32767},{id:0, lvl:32767}], AttributeModifiers:[{AttributeName:generic.maxHealth, Name:generic.maxHealth,Amount:200, Operation:0, UUIDMost:43631, UUIDLeast:2641}], display:{Name:\u00a79M1CESquad}, HideFlags:63,Unbreakable:1}"), new ItemTemplate(
                                            "M1CEPants", Items.golden_leggings, "{ench:[{id:7, lvl:32767},{id:0, lvl:32767}], AttributeModifiers:[{AttributeName:generic.maxHealth, Name:generic.maxHealth,Amount:200, Operation:0, UUIDMost:43631, UUIDLeast:2641}], display:{Name:\u00a79M1CESquad}, HideFlags:63,Unbreakable:1}"), new ItemTemplate(
                                                    "M1CEBoots", Items.golden_boots, "{ench:[{id:7, lvl:32767},{id:0, lvl:32767}], AttributeModifiers:[{AttributeName:generic.maxHealth, Name:generic.maxHealth,Amount:200, Operation:0, UUIDMost:43631, UUIDLeast:2641}], display:{Name:\u00a79M1CESquad}, HideFlags:63,Unbreakable:1}"), new ItemTemplate(
                                                            "M1CEPot", Items.potionitem, "{CustomPotionEffects: [{Id:11, Amplifier:127, Duration:2147483647},{Id:10, Amplifier:127, Duration:2147483647},{Id:23, Amplifier:127, Duration:2147483647},{Id:1, Amplifier:5, Duration:2147483647},{Id:5, Amplifier:127, Duration:2147483647}],display:{Name:\u00a79M1CE Vitamins}, HideFlags:63, CustomPotionColor:65489}")};;

    public OPKit() {
        super("OPKit", 0, AbstractModule.Category.Other);
    }

    @Override
    public void onEnable() {
        for(int i = 0; i < this.templates.length; ++i) {
            Item item = null;
            int amount = 1;
            int metadata = 0;
            String nbt = null;
            ItemTemplate template = this.templates[i];
            item = template.item;
            nbt = template.tag;
            ItemStack stack = new ItemStack(item, amount, metadata);
            if (nbt != null) {
                try {
                    stack.setTagCompound(JsonToNBT.getTagFromJson(nbt));
                } catch (NBTException var9) {
                    var9.printStackTrace();
                }
            }

            mc.getNetHandler().addToSendQueue(new C10PacketCreativeInventoryAction(36 + i, stack));
        }

    }
}

package xyz.gucciclient.utils;

import net.minecraft.item.Item;

public class ItemTemplate {
    public Item item;
    public String name;
    public String tag;

    public ItemTemplate(String name, Item item, String tag) {
        this.name = name;
        this.item = item;
        this.tag = tag;
    }
}

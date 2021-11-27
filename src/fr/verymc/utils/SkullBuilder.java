package fr.verymc.utils;

import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;
import java.util.Arrays;
import org.bukkit.SkullType;
import org.bukkit.Material;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.ItemStack;

public class SkullBuilder
{
    private ItemStack skull;
    private SkullMeta skullMeta;
    
    public SkullBuilder(final String name) {
        this.skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        (this.skullMeta = (SkullMeta)this.skull.getItemMeta()).setOwner(name);
    }
    
    public SkullBuilder setDisplayName(final String name) {
        this.skullMeta.setDisplayName(name);
        return this;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public SkullBuilder setLore(final String... lore) {
        this.skullMeta.setLore((List)Arrays.asList(lore));
        return this;
    }
    
    public ItemStack getItemStack() {
        this.skull.setItemMeta((ItemMeta)this.skullMeta);
        return this.skull;
    }
}

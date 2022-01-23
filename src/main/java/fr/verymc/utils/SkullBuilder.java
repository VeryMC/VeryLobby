package fr.verymc.utils;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;

public class SkullBuilder
{
    private final ItemStack skull;
    private final SkullMeta skullMeta;
    
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
        this.skullMeta.setLore(Arrays.asList(lore));
        return this;
    }
    
    public ItemStack getItemStack() {
        this.skull.setItemMeta(this.skullMeta);
        return this.skull;
    }
}

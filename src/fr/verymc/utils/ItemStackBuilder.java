package fr.verymc.utils;

import java.util.List;
import java.util.Arrays;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;

public class ItemStackBuilder
{
    private ItemStack currentItemStack;
    private ItemMeta currentItemMeta;
    
    public ItemStackBuilder(final Material materialType) {
        this.currentItemStack = new ItemStack(materialType);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }
    
    public ItemStackBuilder(final Material materialType, final byte data) {
        this.currentItemStack = new ItemStack(materialType, 1, (short)data);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }
    
    public ItemStackBuilder(final Material materialType, final int amount) {
        this.currentItemStack = new ItemStack(materialType, amount);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }
    
    public ItemStackBuilder(final Material materialType, final int amount, final byte data) {
        this.currentItemStack = new ItemStack(materialType, amount, (short)data);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }
    
    public ItemStackBuilder(final Material materialType, final int amount, final short damage) {
        this.currentItemStack = new ItemStack(materialType, amount, damage);
        this.currentItemMeta = this.currentItemStack.getItemMeta();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public ItemStackBuilder setLore(final String... lore) {
        this.currentItemMeta.setLore((List)Arrays.asList(lore));
        return this;
    }
    
    public ItemStackBuilder setDurability(final short durability) {
        this.currentItemStack.setDurability(durability);
        return this;
    }
    
    public ItemStackBuilder setName(final String name) {
        this.currentItemMeta.setDisplayName(name);
        return this;
    }
    
    public ItemStack getItemStack() {
        this.currentItemStack.setItemMeta(this.currentItemMeta);
        return this.currentItemStack;
    }
    
    public List<String> getLore() {
        return (List<String>)this.currentItemMeta.getLore();
    }
    
    public int getAmount() {
        return this.currentItemStack.getAmount();
    }
}

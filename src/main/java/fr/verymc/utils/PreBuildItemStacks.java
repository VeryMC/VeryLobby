package fr.verymc.utils;

import fr.verymc.main;
import net.luckperms.api.model.user.User;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class PreBuildItemStacks {
	
	public static ItemStack GetHead(Player player) {
		
		String Grade = "§7N/A";
		
		User user = main.api.getUserManager().getUser(player.getUniqueId());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		}
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§6Profil");
        ((SkullMeta) meta).setOwner(player.getName());
        if(RankExpiry.GetTimeLeft(player) >= 1) {
            long aaa = RankExpiry.GetTimeLeft(player);
            long dayleft = TimeUnit.MILLISECONDS.toDays(aaa);
            long hourleft = TimeUnit.MILLISECONDS.toHours(aaa);
            long minleft = TimeUnit.MILLISECONDS.toMinutes(aaa);
            long secleft = TimeUnit.MILLISECONDS.toSeconds(aaa);
            long realsecleft = secleft - 60*minleft;
            long realminleft = minleft - 60*hourleft;
            long realhourleft = hourleft - 24*dayleft;
            meta.setLore(Arrays.asList("§7Grade: " + Grade,"§7Status du Premium: §a§lTemporaire","§6"+dayleft+" §7jour(s) §6"+realhourleft+" §7heure(s) §6"+realminleft+" §7minute(s) §6"
            +realsecleft+" §7seconde(s) restantes"));
            meta.addEnchant(Enchantment.SILK_TOUCH, 0, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else if(player.hasPermission("group.premium")){
            	meta.setLore(Arrays.asList("§7Grade: " + Grade,"§7Status du Premium: §2§lPermanent"));
            	meta.addEnchant(Enchantment.SILK_TOUCH, 0, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	    } else {
    	    	meta.setLore(Arrays.asList("§7Grade: " + Grade,"§7Status du Premium: §c§lInactif"));
    	    }
        head.setItemMeta(meta);
		
		return head;
	}
}

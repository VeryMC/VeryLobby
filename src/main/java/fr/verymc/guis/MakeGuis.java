package fr.verymc.guis;

import fr.verymc.main;
import fr.verymc.utils.PreBuildItemStacks;
import fr.verymc.utils.RankExpiry;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class MakeGuis {

    public static MakeGuis instance;
    static String Grade = "§7N/A";
    static String Suffix = "";
    public String playerCount = "§7N/A";

    public MakeGuis() {
        instance = this;
    }

    public void MakeMainGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "§6Very§fMc §f➔ §6Mini-jeux");

        User user = main.api.getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        }

        ItemStack stack4 = new ItemStack(Material.GRASS, 1);
        ItemMeta meta4 = stack4.getItemMeta();
        meta4.setDisplayName("§6Skyblock");
        meta4.setLore(Arrays.asList("§7En SkyBlock, vous commencez avec une petite île",
                "§7où vous devez survivre, agrandir votre terrain pour forger", "§7votre empire !", "§7",
                "§d§lInformations", "§7Connectés: §a" + playerCount, "§7", "§7Version: §e1.16.5 et supérieur", "§7Développeur: Farmeurimmo"));
        stack4.setItemMeta(meta4);
        inv.setItem(22, stack4);

        player.openInventory(inv);

    }

    public void MakeProfil(Player player) {
        User user = main.api.getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        }

        Inventory profil = Bukkit.createInventory(null, 45, "§6Very§fMc §f➔ Votre profil");

        profil.setItem(22, PreBuildItemStacks.GetHead(player));

        player.openInventory(profil);
    }

    public void MakeBoutiquesGui(Player player) {
        final Inventory sous = Bukkit.createInventory(null, 27, "§6Very§fMc §f➔ §6Boutiques");

        ItemStack stackm = new ItemStack(Material.BLAZE_POWDER, 1);
        ItemMeta metam = stackm.getItemMeta();
        metam.setDisplayName("§6Boutique des particules");
        metam.setLore(Arrays.asList("§cSoon"));
        stackm.setItemMeta(metam);

        User user = main.api.getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        }

        ItemStack stacku = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta metau = stacku.getItemMeta();
        metau.setDisplayName("§6Boutique des grades");
        metau.setLore(Arrays.asList("§cAchats bientôt disponibles !"));
        stacku.setItemMeta(metau);

        sous.setItem(13, PreBuildItemStacks.GetHead(player));
        sous.setItem(15, stackm);
        sous.setItem(11, stacku);

        player.openInventory(sous);
    }

    public void MakeBoutiqueRank(Player player) {
        User user = main.api.getUserManager().getUser(player.getUniqueId());
        if (user.getCachedData().getMetaData().getPrefix() != null) {
            Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
        }

        final Inventory inv = Bukkit.createInventory(null, 27, "§6Very§fMc §f➔ §6Boutique des grades");

        ItemStack stacke = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta metae = stacke.getItemMeta();
        if(RankExpiry.GetTimeLeft(player) >= 1) {
        long aaa = RankExpiry.GetTimeLeft(player);
        long dayleft = TimeUnit.MILLISECONDS.toDays(aaa);
        long hourleft = TimeUnit.MILLISECONDS.toHours(aaa);
        long minleft = TimeUnit.MILLISECONDS.toMinutes(aaa);
        long secleft = TimeUnit.MILLISECONDS.toSeconds(aaa);
        long realsecleft = secleft - 60*minleft;
        long realminleft = minleft - 60*hourleft;
        long realhourleft = hourleft - 24*dayleft;
        metae.setDisplayName("§eStatus du Premium §a(§lACTIF§a)");
        metae.setLore(Arrays.asList("§7Type: §a§lTemporaire","§6"+dayleft+" §7jour(s) §6"+realhourleft+" §7heure(s) §6"+realminleft+" §7minute(s) §6"
        +realsecleft+" §7seconde(s) restantes"));
        metae.addEnchant(Enchantment.SILK_TOUCH, 0, true);
        metae.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else if(player.hasPermission("group.premium")){
        	metae.setDisplayName("§eStatus du Premium §a(§lACTIF§a)");
        	metae.setLore(Arrays.asList("§7Type: §2§lPermanant"));
        	metae.addEnchant(Enchantment.SILK_TOUCH, 0, true);
            metae.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    } else {
        	metae.setDisplayName("§eStatus du Premium §c(§lINACTIF§c)");
        }
        stacke.setItemMeta(metae);

        ItemStack stack1 = new ItemStack(Material.ARROW, 1);
        ItemMeta meta1 = stack1.getItemMeta();
        meta1.setDisplayName("§6Retourner en arrière");
        meta1.setLore(Arrays.asList("§c<<---"));
        stack1.setItemMeta(meta1);

        inv.setItem(13, stacke);
        inv.setItem(26, stack1);

        player.openInventory(inv);
    }

}

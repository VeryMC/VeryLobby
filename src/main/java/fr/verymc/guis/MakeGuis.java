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
    public String playerCountSkyblock = "§7N/A";
    public String playerCountComboFFA = "§7N/A";

    public MakeGuis() {
        instance = this;
    }

    public void MakeMainGui(Player player) {
        Inventory inv = Bukkit.createInventory(null, 45, "§6Very§fMc §f➔ §6Mini-jeux");

        ItemStack stack4 = new ItemStack(Material.GRASS, 1);
        ItemMeta meta4 = stack4.getItemMeta();
        meta4.setDisplayName("§6Skyblock §7| §e1.0.0");
        meta4.setLore(Arrays.asList("§7En SkyBlock, vous commencez avec",
                "§7une petite île où vous devez", "§7survivre, agrandir votre terrain", "§7pour forger votre empire !", "§7",
                "§d§lInformations", "§7Connectés: §a" + playerCountSkyblock, "§7", "§7Version du jeu: §e1.16.5 et supérieur"));
        stack4.setItemMeta(meta4);
        inv.setItem(21, stack4);

        ItemStack stack5 = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta meta5 = stack5.getItemMeta();
        meta5.setDisplayName("§6ComboFFA §7| §eBêta");
        meta5.setLore(Arrays.asList("§7En ComboFFA, vous devez vous",
                "§7battre contre les autres joueurs", "§7avec un knockback modifié.",
                "§7Un seul objectif:", "§7-> Être le meilleur","§7",
                "§d§lInformations", "§7Connectés: §a" + playerCountComboFFA, "§7", "§7Version du jeu: §e1.8.9 et supérieur"));
        stack5.setItemMeta(meta5);
        inv.setItem(23, stack5);

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
        if (RankExpiry.GetTimeLeft(player) >= 1) {
            long aaa = RankExpiry.GetTimeLeft(player);
            long dayleft = TimeUnit.MILLISECONDS.toDays(aaa);
            long hourleft = TimeUnit.MILLISECONDS.toHours(aaa);
            long minleft = TimeUnit.MILLISECONDS.toMinutes(aaa);
            long secleft = TimeUnit.MILLISECONDS.toSeconds(aaa);
            long realsecleft = secleft - 60 * minleft;
            long realminleft = minleft - 60 * hourleft;
            long realhourleft = hourleft - 24 * dayleft;
            metae.setDisplayName("§eStatus du Premium §a(§lACTIF§a)");
            metae.setLore(Arrays.asList("§7Type: §a§lTemporaire", "§6" + dayleft + " §7jour(s) §6" + realhourleft + " §7heure(s) §6" + realminleft + " §7minute(s) §6"
                    + realsecleft + " §7seconde(s) restantes"));
            metae.addEnchant(Enchantment.SILK_TOUCH, 0, true);
            metae.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else if (player.hasPermission("group.premium")) {
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

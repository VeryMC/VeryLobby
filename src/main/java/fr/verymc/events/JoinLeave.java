package fr.verymc.events;

import fr.verymc.ScoreBoardNMS;
import fr.verymc.jump.InteractJump;
import fr.verymc.main;
import fr.verymc.serverqueue.ServerQueueComboFFAManager;
import fr.verymc.serverqueue.ServerQueueSkyblockManager;
import fr.verymc.utils.ChooseEffect;
import fr.verymc.utils.ItemStackBuilder;
import fr.verymc.utils.SkullBuilder;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

public class JoinLeave implements Listener {
    public static ItemStack effecttrue;
    public static ItemStack gamesNether;

    public static Location spawn = new Location(Bukkit.getWorld("world"), 120.5, 44.5, 173.5, 180, 0);

    public static void GiveItem(Player player) {
        final PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        player.setGameMode(GameMode.ADVENTURE);
        if (InteractJump.haseffect.contains(player.getName())) {
            playerInventory.setItem(8, JoinLeave.effecttrue = new ItemStackBuilder(Material.SLIME_BALL).setName("§a§lEffets Activés §8| §7(clic-droit) ").getItemStack());
        } else {
            playerInventory.setItem(8, JoinLeave.effecttrue = new ItemStackBuilder(Material.FIREWORK_CHARGE).setName("§c§lEffets désactivés §8| §7(clic droit) ").getItemStack());
        }
        playerInventory.setItem(0, JoinLeave.gamesNether = new ItemStackBuilder(Material.NETHER_STAR).setName("§a§lMenu §8| §7(clic-droit) ").getItemStack());
        final ItemStack skullProfile = new SkullBuilder(player.getName()).setDisplayName("§d§lProfil §8| §7(clic-droit)").getItemStack();
        playerInventory.setItem(4, skullProfile);
        final ItemStack boutique = new ItemStackBuilder(Material.GOLD_INGOT).setName("§e§lBoutique §8| §7(clic-droit)").getItemStack();
        playerInventory.setItem(2, boutique);
        final ItemStack FeatherFly = new ItemStackBuilder(Material.FEATHER).setName("§e§lFly §8| §7(clic-droit)").getItemStack();
        if (player.hasPermission("fly")) {
            playerInventory.setItem(6, FeatherFly);
        }
        player.teleport(spawn);
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        /*if (!player.hasPermission("maintenance")) {
            player.kickPlayer("§cMaintenance en cours...\n §chttps://discord.gg/ZRsd85u9Yf");
        }*/
        final PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        if (!ChooseEffect.permchangeeffect.containsKey(player)) {
            ChooseEffect.permchangeeffect.put(player, true);
        }
        player.teleport(spawn);
        player.sendMessage("\n ");
        player.sendMessage("§6§lVery§f§lMc §f| Version §aStable §d1.8x1.18.1 \n  \n§aBon jeu sur VeryMc ! \n ");
        player.setHealth(2.0);
        player.setMaxHealth(2.0);
        player.setFoodLevel(20);
        player.removePotionEffect(PotionEffectType.SPEED);
        player.removePotionEffect(PotionEffectType.JUMP);
        if (InteractJump.haseffect.contains(player.getName())) {
            InteractJump.haseffect.remove(player.getName());
        }
        player.setGameMode(GameMode.ADVENTURE);
        GiveItem(player);

        ScoreBoardNMS.instance.MakeScoreBoardForPlayer(player);
        if (!player.hasPermission("broadcast.join")) {
            event.setJoinMessage(null);
        } else {
            String Grade = "§7N/A";
            String Suffix = "";

            User user = main.api.getUserManager().getUser(player.getUniqueId());
            if (user.getCachedData().getMetaData().getPrefix() != null) {
                Grade = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
            }

            if (user.getCachedData().getMetaData().getSuffix() != null) {
                Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§");
            }
            if (!Suffix.equalsIgnoreCase("§d✯")) {
                event.setJoinMessage("§l§o" + Grade + " " + player.getName() + " §6§ovient de rejoindre le serveur.");
            } else {
                event.setJoinMessage("§l§o" + Grade + " " + player.getName() + " " + Suffix + " §6§ovient de rejoindre le serveur.");
            }
        }
    }

    @EventHandler
    public void OnLeavebroadcast(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("broadcast.join")) {
            event.setQuitMessage(null);
        } else {
            String Grade = "§7N/A";
            String Suffix = "";

            User user = main.api.getUserManager().getUser(player.getUniqueId());
            if (user.getCachedData().getMetaData().getPrefix() != null) {
                Grade = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
            }
            if (user.getCachedData().getMetaData().getSuffix() != null) {
                Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§");
            }
            if (!Suffix.equalsIgnoreCase("§d✯")) {
                event.setQuitMessage("§l§o" + Grade + " " + player.getName() + " §6§ovient de quitter le serveur.");
            } else {
                event.setQuitMessage("§l§o" + Grade + " " + player.getName() + " " + Suffix + " §6§ovient de quitter le serveur.");
            }
        }
        if (ServerQueueSkyblockManager.instance.position.containsKey(player.getName())) {
            ServerQueueSkyblockManager.instance.position.remove(player.getName());
        }
        if (ServerQueueComboFFAManager.instance.position.containsKey(player.getName())) {
            ServerQueueComboFFAManager.instance.position.remove(player.getName());
        }
    }
}

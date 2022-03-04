package fr.verymc.guis;

import fr.verymc.commands.CommandBuilder;
import fr.verymc.main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class GuisManager implements Listener {

    @EventHandler
    public void OnInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand().getType() == Material.NETHER_STAR) {
                MakeGuis.MakeMainGui(player);
            }
            if (player.getItemInHand().getType() == Material.SKULL_ITEM) {
                MakeGuis.MakeProfil(player);
            }
            if (player.getItemInHand().getType() == Material.GOLD_INGOT) {
                MakeGuis.MakeBoutiquesGui(player);
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (player.getItemInHand().getType() == Material.NETHER_STAR) {
                MakeGuis.MakeMainGui(player);
            }
            if (player.getItemInHand().getType() == Material.SKULL_ITEM) {
                MakeGuis.MakeProfil(player);
            }
            if (player.getItemInHand().getType() == Material.GOLD_INGOT) {
                MakeGuis.MakeBoutiquesGui(player);
            }
        }
    }

    @EventHandler
    public void Onclick(InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack current = event.getCurrentItem();
        if (CommandBuilder.statutbuilder.containsKey(player) && CommandBuilder.statutbuilder.get(player) == true) {
            event.setCancelled(false);
            return;
        } else {
            event.setCancelled(true);

        }
        if (current == null) {
            return;
        }
        if (current.getType() == null) {
            return;
        }


        if (event.getInventory().getName() == "§6Very§fMc §f➔ Votre profil") {
            if (current.getType() == Material.ARROW) {
                MakeGuis.MakeMainGui(player);
            }
            if (current.getType() == Material.SKULL_ITEM) {
                MakeGuis.MakeProfil(player);
            }
        }
        if (event.getInventory().getName() == "§6Very§fMc §f➔ §6Boutiques") {
            if (current.getType() == Material.SKULL_ITEM) {
                MakeGuis.MakeProfil(player);
            }
            if (current.getType() == Material.GOLD_INGOT) {
                MakeGuis.MakeBoutiqueRank(player);
            }
            if (current.getType() == Material.BLAZE_POWDER) {

            }
        }
        if (event.getInventory().getName() == "§6Very§fMc §f➔ §6Boutique des grades") {
            if (current.getType() == Material.ARROW) {
                MakeGuis.MakeBoutiquesGui(player);
            }
            if (current.getType() == Material.SKULL_ITEM) {
                MakeGuis.MakeProfil(player);
            }
            if (current.getType() == Material.GOLD_INGOT) {
                if (player.hasPermission("group.vip")) {
                    player.sendMessage("§cErreur, vous possédez déjà ce grade !");
                    current.setType(Material.BARRIER);
                    Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
                        public void run() {
                            current.setType(Material.GOLD_INGOT);
                        }
                    }, 50);
                } else {
                    player.sendMessage("§6Vérification de la disponibilité du grade...");
                    current.setType(Material.BARRIER);
                    Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
                        public void run() {
                            player.sendMessage("§cErreur, achat indisponible !");
                            current.setType(Material.GOLD_INGOT);
                        }
                    }, 50);
                }
            }
        }
        if (event.getInventory().getName() == "§6Very§fMc §f➔ §6Mini-jeux") {

            if (current.getType() == Material.GRASS) {
                player.chat("/skyblock");
            }
        }
    }

}

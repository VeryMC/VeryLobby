package fr.farmeurimmo.premsi.guis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.farmeurimmo.premsi.commands.CommandBuilder;

public class GuisManager implements Listener {
	
	@EventHandler
	public void OnInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getItemInHand().getType() == Material.NETHER_STAR) {
				MakeGuis.MakeMainGui(player);
			}
			if(player.getItemInHand().getType() == Material.SKULL_ITEM) {
				MakeGuis.MakeProfil(player);
			}
			if(player.getItemInHand().getType() == Material.GOLD_INGOT) {
				MakeGuis.MakeBoutiquesGui(player);
			}
		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(player.getItemInHand().getType() == Material.NETHER_STAR) {
				MakeGuis.MakeMainGui(player);
			}
			if(player.getItemInHand().getType() == Material.SKULL_ITEM) {
				MakeGuis.MakeProfil(player);
			}
			if(player.getItemInHand().getType() == Material.GOLD_INGOT) {
				MakeGuis.MakeBoutiquesGui(player);
			}
		}
	}
	@EventHandler
	public void Onclick(InventoryClickEvent event) {
		final Player player = (Player) event.getWhoClicked();
		final ItemStack current = event.getCurrentItem();
		if(CommandBuilder.statutbuilder.containsKey(player) && CommandBuilder.statutbuilder.get(player) == true) {
			event.setCancelled(false);
			return;
		}
		else {
		event.setCancelled(true);
		
		}
		if(current.getType() == null) {
			return;
		}
        
        
        if(event.getInventory().getName() == "§fPremsi§6Serv §f➔ Votre profil") {
			if(current.getType() == Material.ARROW) {
				MakeGuis.MakeMainGui(player);
			}
			if(current.getType() == Material.SKULL_ITEM) {
				MakeGuis.MakeProfil(player);
			}
        }
        if(event.getInventory().getName() == "§fPremsi§6Serv §f➔ §6Boutiques") {
			if(current.getType() == Material.ARROW) {
				MakeGuis.MakeMainGui(player);
			}
			if(current.getType() == Material.SKULL_ITEM) {
				MakeGuis.MakeProfil(player);
			}
			if(current.getType() == Material.GOLD_INGOT) {
				MakeGuis.MakeBoutiqueRank(player);
            }
            if(current.getType() == Material.BLAZE_POWDER) {
            player.sendMessage("§cPas encore disponnible !");
            }
        }
		if(event.getInventory().getName() == "§fPremsi§6Serv §f➔ §6Boutique des grades") {
			if(current.getType() == Material.ARROW) {
				MakeGuis.MakeBoutiquesGui(player);
			}
			if(current.getType() == Material.WOOL) {
				MakeGuis.MakeBoutiqueRank(player);
			}
			if(current.getType() == Material.SKULL_ITEM) {
				MakeGuis.MakeProfil(player);
			}
			if(current.getType() == Material.IRON_SWORD) {
				if(player.hasPermission("rusher")){
					player.sendMessage("§cErreur, vous possédez déjà ce grade !");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 current.setType(Material.IRON_SWORD);
					     }
					}, 80);
				}
				else {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 current.setType(Material.IRON_SWORD);
					     }
					}, 40);
				}
			}
			if(current.getType() == Material.DIAMOND_SWORD) {
				if(player.hasPermission("mania")){
					player.sendMessage("§cErreur, vous possédez déjà ce grade !");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 current.setType(Material.DIAMOND_SWORD);
					     }
					}, 80);
				}
				else {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 current.setType(Material.DIAMOND_SWORD);
					     }
					}, 40);
				}
			}
			if(current.getType() == Material.GOLD_BLOCK) {
				if(player.hasPermission("vip")){
					player.sendMessage("§cErreur, vous possédez déjà ce grade !");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 current.setType(Material.GOLD_BLOCK);
					     }
					}, 80);
				}
				else {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 current.setType(Material.GOLD_BLOCK);
					     }
					}, 40);
				}
			}
			if(current.getType() == Material.DIAMOND_BLOCK) {
				if(player.hasPermission("premium")){
					player.sendMessage("§cErreur, vous possédez déjà ce grade !");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 current.setType(Material.DIAMOND_BLOCK);
					     }
					}, 80);
				}
				else {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 current.setType(Material.DIAMOND_BLOCK);
					     }
					}, 40);
				}
			}
			if(current.getType() == Material.EMERALD_BLOCK) {
				if(player.hasPermission("elite")){
					player.sendMessage("§cErreur, vous possédez déjà ce grade !");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 current.setType(Material.EMERALD_BLOCK);
					     }
					}, 80);
				}
				else {
					player.sendMessage("§6Vérification de la disponibilité du grade...");
					current.setType(Material.BARRIER);
					Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
					     public void run() {
					    	 player.sendMessage("§cErreur, achat indisponible !");
					    	 current.setType(Material.EMERALD_BLOCK);
					     }
					}, 40);
				}
			}
		}
		if(event.getInventory().getName() == "§fPremsi§6Serv §f➔ §6Mini-jeux") {
			
			if(current.getType() == Material.GOLD_INGOT) {
				MakeGuis.MakeBoutiquesGui(player);
				}
			if(current.getType() == Material.SKULL_ITEM) {
				MakeGuis.MakeProfil(player);
				}
			
			if(current.getType() == Material.GRASS) {
				player.chat("/skyblock");
			}
		}
	}

}

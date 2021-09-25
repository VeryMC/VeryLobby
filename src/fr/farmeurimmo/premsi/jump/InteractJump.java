package fr.farmeurimmo.premsi.jump;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.farmeurimmo.premsi.commands.CommandBuilder;
import fr.farmeurimmo.premsi.core.JoinLeaveHub;
import fr.farmeurimmo.premsi.utils.ChooseEffect;

public class InteractJump implements Listener {
	
	public static Map<Player, Boolean> statutbuilder = CommandBuilder.statutbuilder;
	
	public static ArrayList<UUID> Jump = new ArrayList<UUID>();
	public static ArrayList<UUID> Checkpoint1 = new ArrayList<UUID>();
	public static ArrayList<UUID> Checkpoint2 = new ArrayList<UUID>();
	public static ArrayList<UUID> Reset = new ArrayList<UUID>();
	public static ArrayList<String> haseffect = new ArrayList<String>();
	public static Map<UUID, Integer> Timer = new HashMap < > ();
	public static Map<String, Integer> Valeurs = new HashMap < > ();
	public static Map<String, Integer> SecondMap = new HashMap < > ();
	public static Map<Player, Boolean> permchangeeffect = ChooseEffect.permchangeeffect;
	
	
	public static void PrepareJump(Player player) {
		PlayerInventory aa = player.getInventory();
		aa.clear();
		
		if(player.hasPotionEffect(PotionEffectType.JUMP)) {
			haseffect.add(player.getName());
			player.removePotionEffect(PotionEffectType.JUMP);
			player.removePotionEffect(PotionEffectType.SPEED);
		}
		
		ItemStack stack1 = new ItemStack(Material.INK_SACK, 1, (byte) 10);
        ItemMeta meta1 = stack1.getItemMeta();
        meta1.setDisplayName("§aDernier Checkpoint §8| §7(clic droit)");
        stack1.setItemMeta(meta1);
        aa.setItem(0, stack1);
        
        ItemStack stack2 = new ItemStack(Material.INK_SACK, 1, (byte) 1);
        ItemMeta meta2 = stack2.getItemMeta();
        meta2.setDisplayName("§aArrêter le jump §8| §7(clic droit)");
        stack2.setItemMeta(meta2);
        aa.setItem(2, stack2);
	}
	@EventHandler
	public void Leave(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if(Jump.contains(player.getUniqueId())) {
		Jump.remove(player.getUniqueId());
		}
		if(Checkpoint1.contains(player.getUniqueId())) {
			Checkpoint1.remove(player.getUniqueId());
			}
		if(Checkpoint2.contains(player.getUniqueId())) {
			Checkpoint2.remove(player.getUniqueId());
			}
	}
	
	@EventHandler
	public void OnInteract(PlayerInteractEvent e) {
		Integer timeintotal = 0;
		int min = 0;
		Player player = e.getPlayer();
		Block bb = e.getClickedBlock();
		Location start = new Location(Bukkit.getServer().getWorld("Lobby-01"), 260.5, 66, 279.5, -22, 14);
		Location checkpointnum1 = new Location(Bukkit.getServer().getWorld("Lobby-01"), 281.5, 78, 257.5, 136, 16);
		Location checkpointnum2 = new Location(Bukkit.getServer().getWorld("Lobby-01"), 253.5, 82, 252.5, 45, 12);
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getItemInHand() == null) {
				return;
			}
			if(player.getItemInHand().getItemMeta() == null) {
				return;
			}
			if(player.getItemInHand().getItemMeta().getDisplayName() != null) {
			if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aArrêter le jump §8| §7(clic droit)")) {
			JoinLeaveHub.GiveItem(player);
			player.sendMessage("§a§lJump §7» §aVous avez décidé d'arrêter le jump !");
			if(haseffect.contains(player.getName())) {
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 3));
			}
			if(Jump.contains(player.getUniqueId())) {
			Jump.remove(player.getUniqueId());
			}
			if(Checkpoint1.contains(player.getUniqueId())) {
				Checkpoint1.remove(player.getUniqueId());
			}
			if(Checkpoint2.contains(player.getUniqueId())) {
				Checkpoint2.remove(player.getUniqueId());
			}
			}
			}
			if(player.getItemInHand() == null) {
				return;
			}
			if(player.getItemInHand().getItemMeta() == null) {
				return;
			}
			if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aDernier Checkpoint §8| §7(clic droit)")) {
			if(Jump.contains(player.getUniqueId()) && !Checkpoint1.contains(player.getUniqueId())) {
				player.teleport(start);
			}
			else if(Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && !Checkpoint2.contains(player.getUniqueId())) {
				player.teleport(checkpointnum1);
			}
			else if(Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && Checkpoint2.contains(player.getUniqueId())) {
				player.teleport(checkpointnum2);
			}
			else {
				player.sendMessage("§a§lJump §7» §aImpossible de trouver le dernier check point enregistré !");
				return;
			}
			player.sendMessage("§a§lJump §7» §aTéléportation au dernier checkpoint !");
		}
		}
		if(e.getAction().equals(Action.PHYSICAL)) {
			if(e.getClickedBlock().getType() == Material.GOLD_PLATE) {
			if(bb.getX() == 260 && bb.getZ() == 279 && bb.getY() == 66) {
				if(!Jump.contains(player.getUniqueId())) {
				if(statutbuilder.get(player) != null) {
					if(statutbuilder.get(player) == true) {
					statutbuilder.put(player, false);
					player.sendMessage("§a§lMode Builder §8>> §fTu es désormais en mode Joueur !");
					}
				}
				if(player.getAllowFlight() == true) {
					player.setAllowFlight(false);
					player.setFlying(false);
				}
				player.setGameMode(GameMode.ADVENTURE);
				PrepareJump(player);
                Jump.add(player.getUniqueId());
				Integer ddd = 0;
				Timer.put(player.getUniqueId(), ddd);
				Timer(player);
				player.sendMessage("§a§lJump §7» §aVous avez commencé le jump !");
				}
				else if(Jump.contains(player.getUniqueId())) {
					player.sendMessage("§a§lJump §7» §aChronomètre réinitialisé !");
					Integer ddd = 0;
					Timer.put(player.getUniqueId(), ddd);	
				}
			}
			if(bb.getX() == 281 && bb.getZ() == 257 && bb.getY() == 78) {
				if(Jump.contains(player.getUniqueId()) && !Checkpoint1.contains(player.getUniqueId())) {
					Checkpoint1.add(player.getUniqueId());
					player.sendMessage("§a§lJump §7» §aTu viens de passer le checkpoint 1 !");
				}
				}
			if(bb.getX() == 253 && bb.getZ() == 252 && bb.getY() == 82) {
				if(Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && !Checkpoint2.contains(player.getUniqueId())) {
					Checkpoint2.add(player.getUniqueId());
					player.sendMessage("§a§lJump §7» §aTu viens de passer le checkpoint 2 !");
				}
				}
			if(bb.getX() == 241 && bb.getY() == 88 && bb.getZ() == 285) {
					if(Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && Checkpoint2.contains(player.getUniqueId())) {
						timeintotal = Timer.get(player.getUniqueId());
						
						if(Valeurs.get(player.getName()) != null) {
							if(timeintotal < Valeurs.get(player.getName())) {
								SecondMap.put(player.getName(), timeintotal);
					    Valeurs.put(player.getName(), timeintotal);
							}
						}
						else {
							Valeurs.put(player.getName(), timeintotal);
							SecondMap.put(player.getName(), timeintotal);
						}
						if(timeintotal < 239) {
						min = 0;
						if(timeintotal - 60 >= 0) {
							min = min + 1;
							timeintotal = timeintotal - 60;
						}
						if(timeintotal - 60 >= 0) {
							min = min + 1;
							timeintotal = timeintotal - 60;
						}
						if(timeintotal - 60 >= 0) {
							min = min + 1;
							timeintotal = timeintotal - 60;
						}
						}
						else {
							player.sendMessage("§a§lJump §7» §aDélai maximum atteint pour le chronomètre !");
							if(Jump.contains(player.getUniqueId())) {
								Jump.remove(player.getUniqueId());
								}
								if(Checkpoint1.contains(player.getUniqueId())) {
									Checkpoint1.remove(player.getUniqueId());
									}
								if(Checkpoint2.contains(player.getUniqueId())) {
									Checkpoint2.remove(player.getUniqueId());
									}
							JoinLeaveHub.GiveItem(player);
							if(haseffect.contains(player.getName())) {
								player.sendMessage("aa");
								player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
				                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 3));
							}
						}
						
				Bukkit.broadcastMessage("§a§lJump §7» §aGG à " + player.getName() + " qui vient de finir le jump en " + min + " minutes et " + timeintotal + " secondes !");
				player.setGameMode(GameMode.ADVENTURE);
				Jump.remove(player.getUniqueId());
				JoinLeaveHub.GiveItem(player);
				if(haseffect.contains(player.getName())) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
	                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 3));
				}
				Checkpoint1.remove(player.getUniqueId());
				Checkpoint2.remove(player.getUniqueId());
				}
				else {
					player.sendMessage("§a§lJump §7» §aErreur, vous n'avez pas commencé le jump !");
				}
			}
			}
		}
	}
	
	public void Timer(final Player player) {
		if(Jump.contains(player.getUniqueId())) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
		     public void run() {
		    	 if(Jump.contains(player.getUniqueId())) {
		    	 Integer time = Timer.get(player.getUniqueId()) + 1;
		    	 Timer.put(player.getUniqueId(), time);
		    	 Timer(player);
		    	 }
		     }
		}, 20);
		}
	}
}

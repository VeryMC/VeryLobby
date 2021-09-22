package fr.farmeurimmo.premsi.core;

import org.bukkit.Location;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;
import org.bukkit.Material;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.farmeurimmo.premsi.commands.CommandBuilder;
import fr.farmeurimmo.premsi.jump.InteractJump;
import fr.farmeurimmo.premsi.utils.*;

import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Listener;

public class JoinHub implements Listener
{
    public static ItemStack effecttrue;
    public static ItemStack gamesNether;
    
    static Location spawn = new Location(Bukkit.getWorld("Lobby-01"), 260.5, 67.5, 268.5, 0, 10);
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final PlayerInventory playerInventory = player.getInventory();
        playerInventory.clear();
        if (!ChooseEffect.permchangeeffect.containsKey(player)) {
            ChooseEffect.permchangeeffect.put(player, true);
        }
        CommandBuilder.statutbuilder.put(player, false);
        player.teleport(spawn);
        player.sendMessage("§6§lPremsiServ §f| Version §cBêta §d1.8x1.16.5 \n \n§aBon jeu sur PremsiServ !");
        if(InteractJump.haseffect.contains(player.getName())) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 3));
        }
        player.setHealth(2.0);
        player.setMaxHealth(2.0);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.ADVENTURE);
        if(InteractJump.haseffect.contains(player.getName())) {
        playerInventory.setItem(8, JoinHub.effecttrue = new ItemStackBuilder(Material.SLIME_BALL).setName("§a§lEffets Activés §8| §7(clic-droit) ").getItemStack());
        } else {
        	playerInventory.setItem(8, JoinHub.effecttrue = new ItemStackBuilder(Material.FIREWORK_CHARGE).setName("§c§lEffets désactivés §8| §7(clic droit) ").getItemStack());
        }
        playerInventory.setItem(4, JoinHub.gamesNether = new ItemStackBuilder(Material.NETHER_STAR).setName("§a§lMenu §8| §7(clic-droit) ").getItemStack());
        final ItemStack skullProfile = new SkullBuilder(player.getName()).setDisplayName("§d§lProfil §8| §7(clic-droit)").getItemStack();
        playerInventory.setItem(0, skullProfile);
        final ItemStack boutique = new ItemStackBuilder(Material.GOLD_INGOT).setName("§e§lBoutique §8| §7(clic-droit)").getItemStack();
        playerInventory.setItem(5, boutique);
        final ItemStack FeatherFly = new ItemStackBuilder(Material.FEATHER).setName("§e§lFly §8| §7(clic-droit)").getItemStack();
        if (player.hasPermission("fly")) {
            playerInventory.setItem(3, FeatherFly);
        }
        //main.setScoreBoard(player);
    }
    
    public static void GiveItem(Player player) {
    	final PlayerInventory playerInventory = player.getInventory();
    	playerInventory.clear();
    	player.setGameMode(GameMode.ADVENTURE);
    	if(InteractJump.haseffect.contains(player.getName())) {
            playerInventory.setItem(8, JoinHub.effecttrue = new ItemStackBuilder(Material.SLIME_BALL).setName("§a§lEffets Activés §8| §7(clic-droit) ").getItemStack());
            } else {
            	playerInventory.setItem(8, JoinHub.effecttrue = new ItemStackBuilder(Material.FIREWORK_CHARGE).setName("§c§lEffets désactivés §8| §7(clic droit) ").getItemStack());
            }
        playerInventory.setItem(4, JoinHub.gamesNether = new ItemStackBuilder(Material.NETHER_STAR).setName("§a§lMenu §8| §7(clic-droit) ").getItemStack());
        final ItemStack skullProfile = new SkullBuilder(player.getName()).setDisplayName("§d§lProfil §8| §7(clic-droit)").getItemStack();
        playerInventory.setItem(0, skullProfile);
        final ItemStack boutique = new ItemStackBuilder(Material.GOLD_INGOT).setName("§e§lBoutique §8| §7(clic-droit)").getItemStack();
        playerInventory.setItem(5, boutique);
        final ItemStack FeatherFly = new ItemStackBuilder(Material.FEATHER).setName("§e§lFly §8| §7(clic-droit)").getItemStack();
        if (player.hasPermission("fly")) {
            playerInventory.setItem(3, FeatherFly);
        }
        player.teleport(spawn);
    }
    
    @EventHandler
    public void OnPlayerDrop(PlayerDropItemEvent event) {
    	event.setCancelled(true);
    }
    
    @EventHandler
    public void onDamage(final EntityDamageEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onPlayerHunger(final FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        if (CommandBuilder.statutbuilder.get(event.getPlayer())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPlace(final BlockPlaceEvent event) {
        if (CommandBuilder.statutbuilder.get(event.getPlayer())) {
            return;
        }
        event.setCancelled(true);
    }
    
    @EventHandler
    public void onBlockPlaceOnPlayer(final BlockPlaceEvent event) {
        if (CommandBuilder.statutbuilder.get(event.getPlayer())) {
            return;
        }
        final Location playerlocation = event.getPlayer().getLocation();
        final Location blocklocation = event.getBlock().getLocation();
        if (playerlocation == blocklocation) {
            event.setCancelled(true);
        }
    }
}

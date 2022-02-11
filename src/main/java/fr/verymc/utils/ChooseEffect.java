package fr.verymc.utils;

import fr.verymc.jump.InteractJump;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChooseEffect implements Listener {

    private static final HashMap<UUID, Integer> cooldowns = new HashMap<>();
    private static final HashMap<UUID, Integer> cooldowns2 = new HashMap<>();
    public static Map<Player, Boolean> permchangeeffect;

    static {
        ChooseEffect.permchangeeffect = new HashMap<Player, Boolean>();
    }

    public static void setCooldown(UUID player, Integer time) {
        if (time == null)
            cooldowns.remove(player);
        else
            cooldowns.put(player, time);
    }

    public static int getCooldown(UUID player) {
        return (cooldowns.get(player) == null ? 0 : cooldowns.get(player));
    }

    public static void setCooldown2(UUID player1, Integer time2) {
        if (time2 == null)
            cooldowns2.remove(player1);
        else
            cooldowns2.put(player1, time2);
    }

    public static int getCooldown2(UUID player1) {
        return (cooldowns2.get(player1) == null ? 0 : cooldowns2.get(player1));
    }

    @EventHandler
    public void onPlayerChooseEffect(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final ItemStack it = event.getItem();
        if (it == null) {
            return;
        }
        if (player.getInventory().getItemInHand().getType() == Material.SLIME_BALL) {
            if (ChooseEffect.permchangeeffect.containsKey(player) && ChooseEffect.permchangeeffect.get(player) != null
                    && ChooseEffect.permchangeeffect.get(player) == true) {

                player.removePotionEffect(PotionEffectType.SPEED);
                player.removePotionEffect(PotionEffectType.JUMP);
                player.sendMessage("§cVous venez de désactiver vos effets !");

                ItemStack stack1 = new ItemStack(Material.FIREWORK_CHARGE, 1);
                ItemMeta meta1 = stack1.getItemMeta();
                meta1.setDisplayName("§c§lEffets désactivés §8| §7(5 secondes restantes)");
                stack1.setItemMeta(meta1);

                ChooseEffect.permchangeeffect.put(player, false);
                if (!InteractJump.Jump.contains(player.getUniqueId())) {

                    int timeLeft = ChooseEffect.getCooldown2(player.getUniqueId());
                    if (timeLeft == 0) {
                        ChooseEffect.setCooldown2(player.getUniqueId(), 5);
                        player.getInventory().setItem(8, stack1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!InteractJump.Jump.contains(player.getUniqueId())) {
                                    int timeLeft = ChooseEffect.getCooldown2(player.getUniqueId());
                                    if (timeLeft == 1) {
                                        ItemStack stack1 = new ItemStack(Material.FIREWORK_CHARGE, 1);
                                        ItemMeta meta1 = stack1.getItemMeta();
                                        meta1.setDisplayName("§c§lEffets désactivés §8| §7(clic droit)");
                                        stack1.setItemMeta(meta1);
                                        player.getInventory().setItem(8, stack1);
                                        ChooseEffect.setCooldown2(player.getUniqueId(), null);
                                        ChooseEffect.permchangeeffect.put(player, true);
                                        this.cancel();
                                        return;
                                    }
                                    ChooseEffect.setCooldown2(player.getUniqueId(), timeLeft - 1);
                                    int time = timeLeft - 1;
                                    if (time >= 2) {
                                        ItemStack stack1 = new ItemStack(Material.FIREWORK_CHARGE, 1);
                                        ItemMeta meta1 = stack1.getItemMeta();
                                        meta1.setDisplayName("§c§lEffets désactivés §8| §7(" + time + " secondes restantes)");
                                        stack1.setItemMeta(meta1);
                                        if (!InteractJump.Jump.contains(player.getUniqueId())) {
                                            player.getInventory().setItem(8, stack1);
                                        }
                                    } else {
                                        ItemStack stack1 = new ItemStack(Material.FIREWORK_CHARGE, 1);
                                        ItemMeta meta1 = stack1.getItemMeta();
                                        meta1.setDisplayName("§c§lEffets désactivés §8| §7(" + time + " seconde restante)");
                                        stack1.setItemMeta(meta1);
                                        if (!InteractJump.Jump.contains(player.getUniqueId())) {
                                            player.getInventory().setItem(8, stack1);
                                        }
                                    }
                                }
                            }
                        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("VeryLobby"), 20, 20);
                    }
                }
            }
        } else if (player.getInventory().getItemInHand().getType() == Material.FIREWORK_CHARGE) {
            if (ChooseEffect.permchangeeffect.get(player) == true) {

                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 3));
                player.sendMessage("§aVous venez d'activer vos effets !");

                ItemStack stack1 = new ItemStack(Material.SLIME_BALL, 1);
                ItemMeta meta1 = stack1.getItemMeta();
                meta1.setDisplayName("§a§lEffets activés §8| §7(5 secondes restantes)");
                stack1.setItemMeta(meta1);

                ChooseEffect.permchangeeffect.put(player, false);
                if (!InteractJump.Jump.contains(player.getUniqueId())) {

                    int timeLeft = ChooseEffect.getCooldown(player.getUniqueId());
                    if (timeLeft == 0) {
                        ChooseEffect.setCooldown(player.getUniqueId(), 5);
                        player.getInventory().setItem(8, stack1);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if (!InteractJump.Jump.contains(player.getUniqueId())) {
                                    int timeLeft = ChooseEffect.getCooldown(player.getUniqueId());
                                    if (timeLeft == 1) {
                                        ItemStack stack1 = new ItemStack(Material.SLIME_BALL, 1);
                                        ItemMeta meta1 = stack1.getItemMeta();
                                        meta1.setDisplayName("§a§lEffets activés §8| §7(clic droit)");
                                        stack1.setItemMeta(meta1);
                                        player.getInventory().setItem(8, stack1);
                                        ChooseEffect.setCooldown(player.getUniqueId(), null);
                                        ChooseEffect.permchangeeffect.put(player, true);
                                        this.cancel();
                                        return;
                                    }
                                    ChooseEffect.setCooldown(player.getUniqueId(), timeLeft - 1);
                                    int time = timeLeft - 1;
                                    if (time >= 2) {
                                        ItemStack stack1 = new ItemStack(Material.SLIME_BALL, 1);
                                        ItemMeta meta1 = stack1.getItemMeta();
                                        meta1.setDisplayName("§a§lEffets activés §8| §7(" + time + " secondes restantes)");
                                        stack1.setItemMeta(meta1);
                                        if (!InteractJump.Jump.contains(player.getUniqueId())) {
                                            player.getInventory().setItem(8, stack1);
                                        }
                                    } else {
                                        ItemStack stack1 = new ItemStack(Material.SLIME_BALL, 1);
                                        ItemMeta meta1 = stack1.getItemMeta();
                                        meta1.setDisplayName("§a§lEffets activés §8| §7(" + time + " seconde restante)");
                                        stack1.setItemMeta(meta1);
                                        if (!InteractJump.Jump.contains(player.getUniqueId())) {
                                            player.getInventory().setItem(8, stack1);
                                        }
                                    }
                                }
                            }
                        }.runTaskTimer(Bukkit.getPluginManager().getPlugin("VeryLobby"), 20, 20);
                    }
                }
            }
        } else if (player.getInventory().getItemInHand().getType() == Material.FEATHER && player.hasPermission("fly")) {
            if (!player.getAllowFlight()) {
                player.setAllowFlight(true);
                player.sendMessage("§3§lFly §8» §7Vous pouvez maintenant voler !");
            } else {
                player.setAllowFlight(false);
                player.sendMessage("§3§lFly §8» §7Vous ne pouvez plus voler !");
            }
        }
    }
}

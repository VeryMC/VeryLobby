package fr.verymc.jump;

import fr.verymc.commands.CommandBuilder;
import fr.verymc.events.JoinLeave;
import fr.verymc.main;
import fr.verymc.utils.ChooseEffect;
import fr.verymc.utils.PlayerNMS;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class InteractJump implements Listener {

    public static ArrayList<UUID> statutbuilder = CommandBuilder.statutbuilder;

    public static ArrayList<UUID> Jump = new ArrayList<UUID>();
    public static ArrayList<UUID> Checkpoint1 = new ArrayList<UUID>();
    public static ArrayList<UUID> Checkpoint2 = new ArrayList<UUID>();
    public static ArrayList<UUID> Checkpoint3 = new ArrayList<UUID>();
    public static ArrayList<UUID> Checkpoint4 = new ArrayList<UUID>();
    public static ArrayList<UUID> Checkpoint5 = new ArrayList<UUID>();
    public static ArrayList<UUID> Reset = new ArrayList<UUID>();
    public static ArrayList<String> haseffect = new ArrayList<String>();
    public static Map<String, Double> Valeurs = new HashMap<>();
    public static Map<Player, Boolean> permchangeeffect = ChooseEffect.permchangeeffect;

    public static Location start = new Location(Bukkit.getServer().getWorld("world"), 110.5, 44, 169.5, 180, 0);
    public static Location end = new Location(Bukkit.getServer().getWorld("world"), 68.5, 95, 149.5, 180, 0);
    public static Location checkpointnum1 = new Location(Bukkit.getServer().getWorld("world"), 93.5, 40, 131.5, 110, 10);
    public static Location checkpointnum2 = new Location(Bukkit.getServer().getWorld("world"), 75.5, 51, 127.5, 0, 0);
    public static Location checkpointnum3 = new Location(Bukkit.getServer().getWorld("world"), 71.5, 60, 149.5, 125, 10);
    public static Location checkpointnum4 = new Location(Bukkit.getServer().getWorld("world"), 67.5, 69, 147.5, -160, 10);
    public static Location checkpointnum5 = new Location(Bukkit.getServer().getWorld("world"), 62.5, 81, 137.5, -30, 0);


    public static void PrepareJump(Player player) {
        PlayerInventory aa = player.getInventory();
        aa.clear();

        if (player.hasPotionEffect(PotionEffectType.JUMP)) {
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

    public static void ShowChronoForEvery() {
        for (Entry<String, Double> a : Valeurs.entrySet()) {
            Player player = Bukkit.getPlayer(a.getKey());
            if (player != null) {
                if (player.isOnline()) {
                    double time = System.currentTimeMillis() - Valeurs.get(a.getKey());
                    int x = (int) player.getLocation().getX();
                    int y = (int) player.getLocation().getY();
                    int z = (int) player.getLocation().getZ();
                    if (x >= 109 && x <= 111 && z >= 168 && z <= 170 && y >= 43 && y <= 45) {
                        continue;
                    } else {
                        if (time / 1000 >= 2) {
                            PlayerNMS.sendActionBar(player, "§a" + time / 1000 + " secondes");
                        } else {
                            PlayerNMS.sendActionBar(player, "§a" + time / 1000 + " seconde");
                        }
                    }
                }
            }
        }
        Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(main.instance, new Runnable() {
            public void run() {
                ShowChronoForEvery();
            }
        }, 3);
    }

    @EventHandler
    public void Leave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Jump.remove(player.getUniqueId());
        Checkpoint1.remove(player.getUniqueId());
        Checkpoint2.remove(player.getUniqueId());
    }

    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent e) {
        if (e.getTo().getBlockY() < -2) {
            e.getPlayer().teleport(JoinLeave.spawn);
            return;
        }
        Location bb = e.getTo();
        Player player = e.getPlayer();
        int x = (int) bb.getX();
        int y = (int) bb.getY();
        int z = (int) bb.getZ();
        if (x == 93 && z == 131 && y >= 40 && y <= 41) {
            if (InteractJump.Jump.contains(player.getUniqueId()) && !InteractJump.Checkpoint1.contains(player.getUniqueId())) {
                InteractJump.Checkpoint1.add(player.getUniqueId());
                player.sendMessage("§a§lJump §7» §aTu viens de passer le checkpoint 1 !");
            }
        }
        if (x == 75 && z == 127 && y >= 51 && y <= 52) {
            if (InteractJump.Jump.contains(player.getUniqueId()) && InteractJump.Checkpoint1.contains(player.getUniqueId()) && !InteractJump.Checkpoint2.contains(player.getUniqueId())) {
                InteractJump.Checkpoint2.add(player.getUniqueId());
                player.sendMessage("§a§lJump §7» §aTu viens de passer le checkpoint 2 !");
            }
        }
        if (x == 71 && z == 149 && y >= 60 && y <= 61) {
            if (InteractJump.Jump.contains(player.getUniqueId()) && InteractJump.Checkpoint1.contains(player.getUniqueId()) && InteractJump.Checkpoint2.contains(player.getUniqueId())
                    && !InteractJump.Checkpoint3.contains(player.getUniqueId())) {
                InteractJump.Checkpoint3.add(player.getUniqueId());
                player.sendMessage("§a§lJump §7» §aTu viens de passer le checkpoint 3 !");
            }
        }
        if (x == 67 && z == 147 && y >= 69 && y <= 70) {
            if (InteractJump.Jump.contains(player.getUniqueId()) && InteractJump.Checkpoint1.contains(player.getUniqueId()) && !InteractJump.Checkpoint4.contains(player.getUniqueId())
                    && InteractJump.Checkpoint2.contains(player.getUniqueId()) && InteractJump.Checkpoint3.contains(player.getUniqueId())) {
                InteractJump.Checkpoint4.add(player.getUniqueId());
                player.sendMessage("§a§lJump §7» §aTu viens de passer le checkpoint 4 !");
            }
        }
        if (x == 62 && z == 137 && y >= 81 && y <= 82) {
            if (InteractJump.Jump.contains(player.getUniqueId()) && InteractJump.Checkpoint1.contains(player.getUniqueId()) && !InteractJump.Checkpoint5.contains(player.getUniqueId())
                    && InteractJump.Checkpoint2.contains(player.getUniqueId()) && InteractJump.Checkpoint3.contains(player.getUniqueId()) && InteractJump.Checkpoint4.contains(player.getUniqueId())) {
                InteractJump.Checkpoint5.add(player.getUniqueId());
                player.sendMessage("§a§lJump §7» §aTu viens de passer le checkpoint 5 !");
            }
        }
        if (x == 68 && y >= 95 && y <= 96 && z == 149) {
            if (InteractJump.Jump.contains(player.getUniqueId()) && InteractJump.Checkpoint1.contains(player.getUniqueId()) && InteractJump.Checkpoint5.contains(player.getUniqueId())
                    && InteractJump.Checkpoint2.contains(player.getUniqueId()) && InteractJump.Checkpoint3.contains(player.getUniqueId()) &&
                    InteractJump.Checkpoint4.contains(player.getUniqueId())) {
                double timeintotal = System.currentTimeMillis() - InteractJump.Valeurs.get(player.getName());

                if (MakeTop.toclass.containsKey(player.getName())) {
                    if (MakeTop.toclass.get(player.getName()) > timeintotal) {
                        MakeTop.toclass.put(player.getName(), timeintotal);
                    }
                } else {
                    MakeTop.toclass.put(player.getName(), timeintotal);
                }

                InteractJump.Valeurs.remove(player.getName());

                Bukkit.broadcastMessage("§a§lJump §7» §aGG à " + player.getName() + " qui vient de finir le jump en " + timeintotal / 1000 + " secondes !");
                player.setGameMode(GameMode.ADVENTURE);
                InteractJump.Jump.remove(player.getUniqueId());
                JoinLeave.GiveItem(player);
                if (InteractJump.haseffect.contains(player.getName())) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 3));
                }
                InteractJump.Checkpoint1.remove(player.getUniqueId());
                InteractJump.Checkpoint2.remove(player.getUniqueId());
                InteractJump.Checkpoint3.remove(player.getUniqueId());
                InteractJump.Checkpoint4.remove(player.getUniqueId());
                InteractJump.Checkpoint5.remove(player.getUniqueId());
            }
        }
        if (x >= 109 && x <= 111 && z >= 168 && z <= 170 && y >= 43 && y <= 45) {
            if (!InteractJump.Jump.contains(player.getUniqueId())) {
                if (statutbuilder.contains(player.getUniqueId())) {
                    statutbuilder.remove(player.getUniqueId());
                    player.sendMessage("§a§lMode Builder §8>> §fTu es désormais en mode Joueur !");
                }
                if (player.getAllowFlight() == true) {
                    player.setAllowFlight(false);
                    player.setFlying(false);
                }
                player.setGameMode(GameMode.ADVENTURE);
                InteractJump.PrepareJump(player);
                InteractJump.Jump.add(player.getUniqueId());
                InteractJump.Valeurs.put(player.getName(), (double) System.currentTimeMillis());
                player.sendMessage("§a§lJump §7» §aVous avez commencé le jump !");
            } else if (InteractJump.Jump.contains(player.getUniqueId())) {
                PlayerNMS.sendActionBar(player, "§aChronomètre réinitialisé !");
                InteractJump.Valeurs.put(player.getName(), (double) System.currentTimeMillis());
            }
        }
    }

    @EventHandler
    public void OnInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInHand() == null) {
                return;
            }
            if (player.getItemInHand().getItemMeta() == null) {
                return;
            }
            if (player.getItemInHand().getItemMeta().getDisplayName() == null) {
                return;
            }
            if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aArrêter le jump §8| §7(clic droit)")) {
                    JoinLeave.GiveItem(player);
                    player.sendMessage("§a§lJump §7» §aVous avez décidé d'arrêter le jump !");
                    PlayerNMS.sendActionBar(player, "§aChronomètre arrêté !");
                    Valeurs.remove(player.getName());
                    if (haseffect.contains(player.getName())) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999999, 1));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 999999999, 3));
                    }
                    Jump.remove(player.getUniqueId());
                    Checkpoint1.remove(player.getUniqueId());
                    Checkpoint2.remove(player.getUniqueId());
                    Checkpoint3.remove(player.getUniqueId());
                    Checkpoint4.remove(player.getUniqueId());
                    Checkpoint5.remove(player.getUniqueId());
                }
            }
            if (player.getItemInHand() == null) {
                return;
            }
            if (player.getItemInHand().getItemMeta() == null) {
                return;
            }
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§aDernier Checkpoint §8| §7(clic droit)")) {
                if (Jump.contains(player.getUniqueId()) && !Checkpoint1.contains(player.getUniqueId())) {
                    player.teleport(start);
                } else if (Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && Checkpoint2.contains(player.getUniqueId())
                        && Checkpoint3.contains(player.getUniqueId()) && Checkpoint4.contains(player.getUniqueId()) && Checkpoint5.contains(player.getUniqueId())) {
                    player.teleport(checkpointnum5);
                } else if (Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && Checkpoint2.contains(player.getUniqueId())
                        && Checkpoint3.contains(player.getUniqueId()) && Checkpoint4.contains(player.getUniqueId())) {
                    player.teleport(checkpointnum4);
                } else if (Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && Checkpoint2.contains(player.getUniqueId())
                        && Checkpoint3.contains(player.getUniqueId())) {
                    player.teleport(checkpointnum3);
                } else if (Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && Checkpoint2.contains(player.getUniqueId())) {
                    player.teleport(checkpointnum2);
                } else if (Jump.contains(player.getUniqueId()) && Checkpoint1.contains(player.getUniqueId()) && !Checkpoint2.contains(player.getUniqueId())) {
                    player.teleport(checkpointnum1);
                } else {
                    player.sendMessage("§a§lJump §7» §aImpossible de trouver le dernier check point enregistré !");
                    return;
                }
                player.sendMessage("§a§lJump §7» §aTéléportation au dernier checkpoint !");
            }
        }
    }
}

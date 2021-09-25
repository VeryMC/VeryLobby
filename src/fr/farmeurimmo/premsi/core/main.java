package fr.farmeurimmo.premsi.core;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.farmeurimmo.premsi.commands.CommandBoutique;
import fr.farmeurimmo.premsi.commands.CommandBuilder;
import fr.farmeurimmo.premsi.commands.CommandDiscord;
import fr.farmeurimmo.premsi.commands.CommandServeur;
import fr.farmeurimmo.premsi.commands.CommandSite;
import fr.farmeurimmo.premsi.commands.CommandSkyblock;
import fr.farmeurimmo.premsi.jump.InteractJump;
import fr.farmeurimmo.premsi.jump.MakeTop;
import fr.farmeurimmo.premsi.serverqueue.ServerQueueManager;
import fr.farmeurimmo.premsi.utils.ChooseEffect;
import fr.farmeurimmo.premsi.utils.RankExpiry;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
public class main extends JavaPlugin implements Listener {
	
	public static main instance;
	public static LuckPerms api;
	static String Grade = "N/A";
	static Location spawnLocation = new Location(Bukkit.getWorld("Lobby-01"), 69.0, 165.0, -243.0);
	
	public static HashMap <UUID, Integer > cooldowns = new HashMap < > ();

	public static void setCooldown(UUID player, Integer time) {
		if (time == null)
			cooldowns.remove(player);
		else
			cooldowns.put(player, time);
	}
    public static int getCooldown(UUID player) {
        return (cooldowns.get(player) == null ? 0 : cooldowns.get(player));
    }
    
    //✯
	//✔
    
	@Override
	public void onEnable() {
		instance = this;
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this, this);
		getServer().getPluginManager().registerEvents(new JoinHub(), this);
		getServer().getPluginManager().registerEvents(new ChooseEffect(), this);
		getServer().getPluginManager().registerEvents(new InteractJump(), this);
		this.getCommand("builder").setExecutor(new CommandBuilder());
		this.getCommand("serveur").setExecutor(new CommandServeur());
		this.getCommand("discord").setExecutor(new CommandDiscord());
		this.getCommand("site").setExecutor(new CommandSite());
		this.getCommand("boutique").setExecutor(new CommandBoutique());
		this.getCommand("skyblock").setExecutor(new CommandSkyblock());
		Bukkit.getPluginManager().isPluginEnabled("LuckPerms");
		RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
		if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
			System.out.println("Le plugin LuckPerms a été trouvé !");
		} else {
			getLogger().warning("Le plugin LuckPerms est manquant.");
			Bukkit.getPluginManager().disablePlugin(this);
		}
		System.out.println("-----------------------------------------------------------------------------------------------------");
		System.out.println("Initialisation de l'api LuckPerms en cours...");
		if (provider != null) {
			api = provider.getProvider();
			System.out.println("API initialisée !");
			System.out.println("-----------------------------------------------------------------------------------------------------");
		}
		MakeTop.CreateHolo();
		MakeTop.SecondMap.clear();
		MakeTop.Valeurs.clear();
		MakeTop.Classement.clear();
		ServerQueueManager.Every5sec();
		for (Player player : Bukkit.getServer().getOnlinePlayers()) {
			InteractJump.permchangeeffect.put(player, true);
			ScoreBoardNMS.MakeScoreBoardForPlayer(player);

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        float red = 0;
                        float green = 255;
                        float blue = 255;
                        Location location = player.getLocation();

                        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.NOTE, true, (float) location.getX(), (float) location.getY() + 2, (float) location.getZ(), red, green, blue, (float)255, 0, 10);
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
                    }
                }.runTaskTimerAsynchronously(this, 0, 0);
        }
		ScoreBoardNMS.UpdateScoreBoard();
	}
	@Override
	public void onDisable() {
		MakeTop.RemoveHolo();
		ScoreBoardNMS.DeleteScoreBoard();
	}
	@EventHandler
	public void OnJOINbroadcast(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		//setScoreBoard(player);
		if(!player.hasPermission("broadcast.join")) {
	        event.setJoinMessage((String)null);
		}
		else {
			String Grade = "§7N/A";
			String Suffix = "";
			
			User user = main.api.getUserManager().getUser(player.getUniqueId());
			if (user.getCachedData().getMetaData().getPrefix() != null) {
				Grade = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
			}
					
			if (user.getCachedData().getMetaData().getSuffix() != null) {
				Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§");
			}
			if(!Suffix.equalsIgnoreCase("§d✯")) {
			event.setJoinMessage("§l§o" + Grade + " " + player.getName() +" §6§ovient de rejoindre le serveur.");
			} else {
				event.setJoinMessage("§l§o" + Grade + " " + player.getName() + " " + Suffix +" §6§ovient de rejoindre le serveur.");
			}
			Grade = "§fN/A";
			Suffix = "";
		}
	}
	@EventHandler
	public void OnLeavebroadcast(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if(!player.hasPermission("broadcast.join")) {
	        event.setQuitMessage((String)null);
		}
		else {
			String Grade = "§7N/A";
			String Suffix = "";
			
			User user = main.api.getUserManager().getUser(player.getUniqueId());
			if (user.getCachedData().getMetaData().getPrefix() != null) {
				Grade = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
			}
			if (user.getCachedData().getMetaData().getSuffix() != null) {
				Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§");
			}
			if(!Suffix.equalsIgnoreCase("§d✯")) {
			event.setQuitMessage("§l§o" + Grade + " " + player.getName() +" §6§ovient de quitter le serveur.");
			} else {
				event.setQuitMessage("§l§o" + Grade + " " + player.getName() + " " + Suffix +" §6§ovient de quitter le serveur.");
			}
			Grade = "§fN/A";
			Suffix = "";
		}
	}
	public static void MakeMainGui(Player player) {
		Inventory inv = Bukkit.createInventory(null, 54, "§fPremsi§6Serv §f➔ §6Mini-jeux");
		
		User user = api.getUserManager().getUser(player.getUniqueId());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		}
		
		ItemStack stack8 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta meta8 = stack8.getItemMeta();
        meta8.setDisplayName("§6");
        stack8.setItemMeta(meta8);
		
        ItemStack stack23 = new ItemStack(Material.ARROW, 1);
        ItemMeta meta23 = stack23.getItemMeta();
        meta23.setDisplayName("§6Retourner en arrière");
        meta23.setLore(Arrays.asList("§c<<---"));
        stack23.setItemMeta(meta23);
		
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§6Profil");
        ((SkullMeta) meta).setOwner(player.getName());
        meta.setLore(Arrays.asList("§7Grade: " + Grade));
        head.setItemMeta(meta);
        inv.setItem(0, head);
        
        ItemStack stack1 = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta meta1 = stack1.getItemMeta();
        meta1.setDisplayName("§6Boutique");
        meta1.setLore(Arrays.asList("§cAchat des grades bientôt disponible !", "§cLes particules ne sont pas encore disponibles !"));
        stack1.setItemMeta(meta1);
        inv.setItem(8, stack1);
        
        ItemStack stack2 = new ItemStack(Material.BOOK, 1);
        ItemMeta meta2 = stack2.getItemMeta();
        meta2.setDisplayName("§6Informations");
        meta2.setLore(Arrays.asList("§7Site §8» §awww.premsiserv.com", "§7Discord §8» §9https://discord.gg/DJgzEAdG28"));
        stack2.setItemMeta(meta2);
        inv.setItem(45, stack2);
        
        ItemStack stack3 = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta meta3 = stack3.getItemMeta();
        meta3.setDisplayName("§6Amis");
        meta3.setLore(Arrays.asList("§cSoon"));
        stack3.setItemMeta(meta3);
        inv.setItem(53, stack3);
        
        ItemStack stack4 = new ItemStack(Material.GRASS, 1);
        ItemMeta meta4 = stack4.getItemMeta();
        meta4.setDisplayName("§6Skyblock");
        meta4.setLore(Arrays.asList("§7SkyBlock, vous commencez avec une petite île", "§7où vous devez survivre, agrandir votre terrain pour forger", "§7votre empire !", "§7", "§d§lInformations","§7","§7Version: §e1.16.5 et supérieur","§7Développeur: Farmeurimmo"));
        stack4.setItemMeta(meta4);
        inv.setItem(22, stack4);
        
        inv.setItem(1, stack8);
        inv.setItem(2, stack8);
        inv.setItem(3, stack8);
        inv.setItem(4, stack8);
        inv.setItem(5, stack8);
        inv.setItem(6, stack8);
        inv.setItem(7, stack8);
        inv.setItem(9, stack8);
        inv.setItem(18, stack8);
        inv.setItem(17, stack8);
        inv.setItem(27, stack8);
        inv.setItem(26, stack8);
        inv.setItem(36, stack8);
        inv.setItem(35, stack8);
        inv.setItem(44, stack8);
        inv.setItem(46, stack8);
        inv.setItem(47, stack8);
        inv.setItem(48, stack8);
        inv.setItem(49, stack8);
        inv.setItem(50, stack8);
        inv.setItem(51, stack8);
        inv.setItem(52, stack8);
        
        player.openInventory(inv);
		
	}
	public static void MakeProfil(Player player) {
		User user = api.getUserManager().getUser(player.getUniqueId());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		}
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§6Profil");
        ((SkullMeta) meta).setOwner(player.getName());
        meta.setLore(Arrays.asList("§7Grade: " + Grade));
        head.setItemMeta(meta);
		
		ItemStack stack8 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta meta8 = stack8.getItemMeta();
        meta8.setDisplayName("§6");
        stack8.setItemMeta(meta8);
		
        ItemStack stack23 = new ItemStack(Material.ARROW, 1);
        ItemMeta meta23 = stack23.getItemMeta();
        meta23.setDisplayName("§6Retourner en arrière");
        meta23.setLore(Arrays.asList("§c<<---"));
        stack23.setItemMeta(meta23);
		
		Inventory profil = Bukkit.createInventory(null, 45, "§fPremsi§6Serv §f➔ Votre profil");
		
		ItemStack head11 = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta meta11 = head11.getItemMeta();
        meta11.setDisplayName("§6Profil");
        ((SkullMeta) meta11).setOwner(player.getName());
        meta11.setLore(Arrays.asList("§7Grade: " + Grade));
        head11.setItemMeta(meta11);
        
        profil.setItem(22, head11);
        profil.setItem(0, stack8);
        profil.setItem(1, stack8);
        profil.setItem(2, stack8);
        profil.setItem(3, stack8);
        profil.setItem(4, stack8);
        profil.setItem(5, stack8);
        profil.setItem(6, stack8);
        profil.setItem(7, stack8);
        profil.setItem(8, stack8);
        profil.setItem(9, stack8);
        profil.setItem(17, stack8);
        profil.setItem(18, stack8);
        profil.setItem(26, stack8);
        profil.setItem(27, stack8);
        profil.setItem(35, stack8);
        profil.setItem(36, stack8);
        profil.setItem(37, stack8);
        profil.setItem(38, stack8);
        profil.setItem(39, stack8);
        profil.setItem(40, stack8);
        profil.setItem(41, stack8);
        profil.setItem(42, stack8);
        profil.setItem(43, stack8);
        profil.setItem(44, stack23);
        
        player.openInventory(profil);
	}
	public static void MakeBoutiquesGui(Player player) {
        final Inventory sous = Bukkit.createInventory(null, 27, "§fPremsi§6Serv §f➔ §6Boutiques");
        
        ItemStack stackm = new ItemStack(Material.BLAZE_POWDER, 1);
        ItemMeta metam = stackm.getItemMeta();
        metam.setDisplayName("§6Boutique des particules");
        metam.setLore(Arrays.asList("§cSoon"));
        stackm.setItemMeta(metam);
        
        ItemStack stack8 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta meta8 = stack8.getItemMeta();
        meta8.setDisplayName("§6");
        stack8.setItemMeta(meta8);
        
        User user = api.getUserManager().getUser(player.getUniqueId());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		}
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§6Profil");
        ((SkullMeta) meta).setOwner(player.getName());
        meta.setLore(Arrays.asList("§7Grade: " + Grade));
        head.setItemMeta(meta);
        
        ItemStack stacku = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta metau = stacku.getItemMeta();
        metau.setDisplayName("§6Boutique des grades");
        metau.setLore(Arrays.asList("§cAchats bientôt disponibles !"));
        stacku.setItemMeta(metau);
        
        ItemStack stack30 = new ItemStack(Material.ARROW, 1);
        ItemMeta meta30 = stack30.getItemMeta();
        meta30.setDisplayName("§6Retourner en arrière");
        meta30.setLore(Arrays.asList("§c<<---"));
        stack30.setItemMeta(meta30);
        
        sous.setItem(13, head);
        sous.setItem(1, stack8);
        sous.setItem(2, stack8);
        sous.setItem(3, stack8);
        sous.setItem(4, stack8);
        sous.setItem(5, stack8);
        sous.setItem(6, stack8);
        sous.setItem(7, stack8);
        sous.setItem(8, stack8);
        sous.setItem(9, stack8);
        sous.setItem(0, stack8);
        sous.setItem(15, stackm);
        sous.setItem(17, stack8);
        sous.setItem(18, stack8);
        sous.setItem(19, stack8);
        sous.setItem(20, stack8);
        sous.setItem(21, stack8);
        sous.setItem(22, stack8);
        sous.setItem(23, stack8);
        sous.setItem(24, stack8);
        sous.setItem(11, stacku);
        sous.setItem(25, stack8);
        sous.setItem(26, stack30);
        
        player.openInventory(sous);
	}
	public static void MakeBoutiqueRank(Player player) {
		User user = api.getUserManager().getUser(player.getUniqueId());
		if (user.getCachedData().getMetaData().getPrefix() != null) {
			Grade = user.getCachedData().getMetaData().getPrefix().replace("&", "§");
		}
		
		final Inventory inv = Bukkit.createInventory(null, 54, "§fPremsi§6Serv §f➔ §6Boutique des grades");
		
		
		ItemStack stacku = new ItemStack(Material.BEACON, 1);
        ItemMeta metau = stacku.getItemMeta();
        metau.setDisplayName("§d7 Jours d'abonnements");
        metau.setLore(Arrays.asList("§c..."));
        stacku.setItemMeta(metau);
        
        ItemStack stacka = new ItemStack(Material.BEACON, 1);
        ItemMeta metaa = stacka.getItemMeta();
        metaa.setDisplayName("§d14 Jours d'abonnements");
        metaa.setLore(Arrays.asList("§c..."));
        stacka.setItemMeta(metaa);
        
        ItemStack stackb= new ItemStack(Material.BEACON, 1);
        ItemMeta metab = stackb.getItemMeta();
        metab.setDisplayName("§d30 Jours d'abonnements");
        metab.setLore(Arrays.asList("§c..."));
        stackb.setItemMeta(metab);
        
        ItemStack stackc = new ItemStack(Material.BEACON, 1);
        ItemMeta metac = stackc.getItemMeta();
        metac.setDisplayName("§d60 Jours d'abonnements");
        metac.setLore(Arrays.asList("§c..."));
        stackc.setItemMeta(metac);
        
        ItemStack stackd = new ItemStack(Material.BEACON, 1);
        ItemMeta metad = stackd.getItemMeta();
        metad.setDisplayName("§d90 Jours d'abonnements");
        metad.setLore(Arrays.asList("§c..."));
        stackd.setItemMeta(metad);
        
        ItemStack stacke = new ItemStack(Material.PAPER, 1);
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
        metae.setDisplayName("§eStatus de l'abonnement §a(§lACTIF§a)");
        metae.setLore(Arrays.asList("§6"+dayleft+" §7jour(s) §6"+realhourleft+" §7heure(s) §6"+realminleft+" §7minute(s) §6"
        +realsecleft+" §7seconde(s) restants"));
        metae.addEnchant(Enchantment.SILK_TOUCH, 0, true);
        metae.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        } else {
        	metae.setDisplayName("§eStatus de l'abonnement §c(§lINACTIF§c)");
        }
        stacke.setItemMeta(metae);
        
        inv.setItem(20, stacku);
        inv.setItem(22, stacka);
        inv.setItem(24, stackb);
        inv.setItem(30, stackc);
        inv.setItem(31, stacke);
        inv.setItem(32, stackd);
		
		
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName("§6Profil");
        ((SkullMeta) meta).setOwner(player.getName());
        meta.setLore(Arrays.asList("§7Grade: " + Grade));
        head.setItemMeta(meta);
        
        ItemStack stack7 = new ItemStack(Material.BOOK, 1);
        ItemMeta meta7 = stack7.getItemMeta();
        meta7.setDisplayName("§6Informations");
        meta7.setLore(Arrays.asList("§7Site §8» §awww.premsiserv.com", "§7Discord §8» §9https://discord.gg/DJgzEAdG28"));
        stack7.setItemMeta(meta7);
        
        ItemStack stack1 = new ItemStack(Material.ARROW, 1);
        ItemMeta meta1 = stack1.getItemMeta();
        meta1.setDisplayName("§6Retourner en arrière");
        meta1.setLore(Arrays.asList("§c<<---"));
        stack1.setItemMeta(meta1);
		
		ItemStack stack8 = new ItemStack(Material.STAINED_GLASS_PANE, 1);
        ItemMeta meta8 = stack8.getItemMeta();
        meta8.setDisplayName("§6");
        stack8.setItemMeta(meta8);
        inv.setItem(0, head);
        inv.setItem(1, stack8);
        inv.setItem(2, stack8);
        inv.setItem(3, stack8);
        inv.setItem(4, stack8);
        inv.setItem(5, stack8);
        inv.setItem(6, stack8);
        inv.setItem(7, stack8);
        inv.setItem(8, stack8);
        inv.setItem(9, stack8);
        inv.setItem(18, stack8);
        inv.setItem(17, stack8);
        inv.setItem(27, stack8);
        inv.setItem(26, stack8);
        inv.setItem(36, stack8);
        inv.setItem(35, stack8);
        inv.setItem(44, stack8);
        inv.setItem(45, stack7);
        inv.setItem(46, stack8);
        inv.setItem(47, stack8);
        inv.setItem(48, stack8);
        inv.setItem(49, stack8);
        inv.setItem(50, stack8);
        inv.setItem(51, stack8);
        inv.setItem(52, stack8);
        inv.setItem(53, stack1);
        
        player.openInventory(inv);
	}
	@EventHandler
	public void OnInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(player.getItemInHand().getType() == Material.NETHER_STAR) {
				MakeMainGui(player);
			}
			if(player.getItemInHand().getType() == Material.SKULL_ITEM) {
				MakeProfil(player);
			}
			if(player.getItemInHand().getType() == Material.GOLD_INGOT) {
				MakeBoutiquesGui(player);
			}
		}
		if(event.getAction() == Action.RIGHT_CLICK_AIR) {
			if(player.getItemInHand().getType() == Material.NETHER_STAR) {
				MakeMainGui(player);
			}
			if(player.getItemInHand().getType() == Material.SKULL_ITEM) {
				MakeProfil(player);
			}
			if(player.getItemInHand().getType() == Material.GOLD_INGOT) {
				MakeBoutiquesGui(player);
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
				MakeMainGui(player);
			}
        }
        if(event.getInventory().getName() == "§fPremsi§6Serv §f➔ §6Boutiques") {
			if(current.getType() == Material.ARROW) {
				MakeMainGui(player);
			}
			if(current.getType() == Material.GOLD_INGOT) {
			MakeBoutiqueRank(player);
            }
            if(current.getType() == Material.BLAZE_POWDER) {
            player.sendMessage("§cPas encore disponnible !");
            }
        }
		if(event.getInventory().getName() == "§fPremsi§6Serv §f➔ §6Boutique des grades") {
			if(current.getType() == Material.ARROW) {
				MakeBoutiquesGui(player);
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
	        	  MakeBoutiquesGui(player);
				}
			if(current.getType() == Material.SKULL_ITEM) {
	        	  MakeProfil(player);
				}
			
			if(current.getType() == Material.GRASS) {
				player.chat("/skyblock");
			}
		}
	}
}

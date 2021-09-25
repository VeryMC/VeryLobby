package fr.farmeurimmo.premsi.core;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
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
import fr.farmeurimmo.premsi.guis.GuisManager;
import fr.farmeurimmo.premsi.jump.InteractJump;
import fr.farmeurimmo.premsi.jump.MakeTop;
import fr.farmeurimmo.premsi.serverqueue.ServerQueueManager;
import fr.farmeurimmo.premsi.utils.ChooseEffect;
import net.luckperms.api.LuckPerms;
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
		getServer().getPluginManager().registerEvents(new JoinLeaveHub(), this);
		getServer().getPluginManager().registerEvents(new ChooseEffect(), this);
		getServer().getPluginManager().registerEvents(new InteractJump(), this);
		getServer().getPluginManager().registerEvents(new GuisManager(), this);
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
}

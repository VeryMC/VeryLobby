package fr.verymc;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import fr.verymc.commands.CommandBoutique;
import fr.verymc.commands.CommandBuilder;
import fr.verymc.commands.CommandDiscord;
import fr.verymc.commands.CommandServeur;
import fr.verymc.commands.CommandSite;
import fr.verymc.commands.CommandSkyblock;
import fr.verymc.events.JoinLeaveHub;
import fr.verymc.events.WeatherEvent;
import fr.verymc.guis.GuisManager;
import fr.verymc.jump.InteractJump;
import fr.verymc.jump.MakeTop;
import fr.verymc.serverqueue.ServerQueueManager;
import fr.verymc.utils.ChooseEffect;
import fr.verymc.utils.ScoreBoardNMS;
import net.luckperms.api.LuckPerms;
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
		getServer().getPluginManager().registerEvents(new WeatherEvent(), this);
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
		ScoreBoardNMS.UpdateScorePings();
		ScoreBoardNMSRanksJump.AutoUpdate();
		ScoreBoardNMS.UpdateScoreBoard();
	}
	@Override
	public void onDisable() {
		MakeTop.RemoveHolo();
		ScoreBoardNMS.DeleteScoreBoard();
	}
}
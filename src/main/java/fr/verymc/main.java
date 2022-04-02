package fr.verymc;

import fr.verymc.commands.*;
import fr.verymc.events.DamageManager;
import fr.verymc.events.JoinLeave;
import fr.verymc.events.ProtectExplo;
import fr.verymc.events.TchatManager;
import fr.verymc.guis.GuisManager;
import fr.verymc.jump.InteractJump;
import fr.verymc.jump.JumpParticleManager;
import fr.verymc.jump.MakeTop;
import fr.verymc.serverqueue.ServerQueueManager;
import fr.verymc.utils.ChooseEffect;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.JedisPool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class main extends JavaPlugin implements Listener {

    public static main instance;
    public static LuckPerms api;
    public static JedisPool pool;
    public static HashMap<UUID, Integer> cooldowns = new HashMap<>();
    static String Grade = "N/A";
    static Location spawnLocation = new Location(Bukkit.getWorld("Lobby-01"), 69.0, 165.0, -243.0);

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
        getServer().getPluginManager().registerEvents(new ChooseEffect(), this);
        getServer().getPluginManager().registerEvents(new JoinLeave(), this);
        getServer().getPluginManager().registerEvents(new InteractJump(), this);
        getServer().getPluginManager().registerEvents(new GuisManager(), this);
        getServer().getPluginManager().registerEvents(new ProtectExplo(), this);
        getServer().getPluginManager().registerEvents(new TchatManager(), this);
        getServer().getPluginManager().registerEvents(new DamageManager(), this);

        this.getCommand("builder").setExecutor(new CommandBuilder());
        this.getCommand("serveur").setExecutor(new CommandServeur());
        this.getCommand("discord").setExecutor(new CommandDiscord());
        this.getCommand("site").setExecutor(new CommandSite());
        this.getCommand("boutique").setExecutor(new CommandBoutique());
        this.getCommand("skyblock").setExecutor(new CommandSkyblock());
        this.getCommand("leavequeues").setExecutor(new CommandLeaveQueues());

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

        MakeTop.GenClassement();
        MakeTop.Valeurs.clear();
        MakeTop.Classement.clear();

        ServerQueueManager.Every5sec();
        ServerQueueManager.DisplayActionBarForA();

        pool = new JedisPool(System.getenv("REDIS_HOST"), 6379);
        new ScoreBoardNMS();
        ScoreBoardNMSRanksJump.AutoUpdate();

        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreBoardNMS.instance.MakeScoreBoardForPlayer(player);
        }

        new TABManager();

        InteractJump.ShowChronoForEvery();

        JumpParticleManager.locs.addAll(Arrays.asList(InteractJump.checkpointnum1, InteractJump.checkpointnum2, InteractJump.checkpointnum3,
                InteractJump.checkpointnum4, InteractJump.checkpointnum5, InteractJump.start, InteractJump.end));
        JumpParticleManager.CheckForParticuleApply();
    }

    @Override
    public void onDisable() {
        MakeTop.RemoveHolo();
        ScoreBoardNMS.instance.DeleteScoreBoard();
        pool.close();
    }
}

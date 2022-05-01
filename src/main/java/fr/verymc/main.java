package fr.verymc;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.verymc.commands.*;
import fr.verymc.events.DamageManager;
import fr.verymc.events.JoinLeave;
import fr.verymc.events.ProtectExplo;
import fr.verymc.events.TchatManager;
import fr.verymc.guis.GuisManager;
import fr.verymc.guis.MakeGuis;
import fr.verymc.jump.InteractJump;
import fr.verymc.jump.JumpParticleManager;
import fr.verymc.jump.MakeTop;
import fr.verymc.serverqueue.ServerQueueComboFFAManager;
import fr.verymc.serverqueue.ServerQueueSkyblockManager;
import fr.verymc.utils.ChooseEffect;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;

public class main extends JavaPlugin implements Listener, PluginMessageListener {

    public static main instance;
    public static LuckPerms api;

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
        this.getCommand("discord").setExecutor(new CommandDiscord());
        this.getCommand("site").setExecutor(new CommandSite());
        this.getCommand("boutique").setExecutor(new CommandBoutique());
        this.getCommand("skyblock").setExecutor(new CommandSkyblock());
        this.getCommand("comboffa").setExecutor(new CommandComboFFA());
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

        new ServerQueueSkyblockManager();
        new ServerQueueComboFFAManager();

        new ScoreBoardNMS();
        ScoreBoardNMSRanksJump.AutoUpdate();

        for (Player player : Bukkit.getOnlinePlayers()) {
            ScoreBoardNMS.instance.MakeScoreBoardForPlayer(player);
        }

        new TABManager();

        new MakeGuis();

        InteractJump.ShowChronoForEvery();

        JumpParticleManager.locs.addAll(Arrays.asList(InteractJump.checkpointnum1, InteractJump.checkpointnum2, InteractJump.checkpointnum3,
                InteractJump.checkpointnum4, InteractJump.checkpointnum5, InteractJump.start, InteractJump.end));
        JumpParticleManager.CheckForParticuleApply();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        playerCounts();
    }

    @Override
    public void onDisable() {
        MakeTop.RemoveHolo();
        ScoreBoardNMS.instance.DeleteScoreBoard();
        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public void playerCounts() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(main.instance, new Runnable() {
            @Override
            public void run() {
                Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(main.instance, "BungeeCord");
                ByteArrayDataOutput out = ByteStreams.newDataOutput();

                out.writeUTF("PlayerCount");
                out.writeUTF("skyblock");

                Bukkit.getServer().sendPluginMessage(main.instance, "BungeeCord", out.toByteArray());

                ByteArrayDataOutput out2 = ByteStreams.newDataOutput();

                out2.writeUTF("PlayerCount");
                out2.writeUTF("comboffa");

                Bukkit.getServer().sendPluginMessage(main.instance, "BungeeCord", out2.toByteArray());
            }
        }, 0L, ServerQueueSkyblockManager.delayInSec * 20L);
    }


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        String server = in.readUTF();
        if (subchannel.equals("PlayerCount")) {
            if (server.equalsIgnoreCase("skyblock")) {
                MakeGuis.instance.playerCountSkyblock = String.valueOf(message[message.length - 1]);
            } else if (server.equalsIgnoreCase("comboffa")) {
                MakeGuis.instance.playerCountComboFFA = String.valueOf(message[message.length - 1]);
            }
        }
    }
}

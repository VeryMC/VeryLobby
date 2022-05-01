package fr.verymc.commands;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import fr.verymc.main;
import fr.verymc.serverqueue.ServerQueueComboFFAManager;
import fr.verymc.serverqueue.ServerQueueSkyblockManager;
import fr.verymc.utils.PlayerNMS;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CommandSkyblock implements CommandExecutor {

    public static HashMap<UUID, Integer> cooldowns = main.cooldowns;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (ServerQueueComboFFAManager.instance.position.containsKey(player.getName())) {
                ServerQueueComboFFAManager.instance.position.containsKey(player.getName());
                player.sendMessage("\n§an§a§lFile d'attente §7» §cVous avez été retiré de la file d'attente du ComboFFA !");
                return true;
            }
            if (ServerQueueSkyblockManager.instance.position.containsKey(player.getName())) {
                player.sendMessage("\n§a§lFile d'attente §7» §cVous êtes déjà dans une file d'attente !");
                return true;
            }
            ViaAPI api = Via.getAPI();
            int playerversion = api.getPlayerVersion(player);
            int pos = ServerQueueSkyblockManager.instance.position.size();
            if (playerversion >= 754) {
                if (player.hasPermission("fastjoin")) {
                    ServerQueueSkyblockManager.instance.setPosition(player.getName(), 0);
                    pos = 0;
                } else {
                    ServerQueueSkyblockManager.instance.setPosition(player.getName(), pos);
                }
                player.closeInventory();
                int tmp = ServerQueueSkyblockManager.instance.position.size() - 1;
                PlayerNMS.sendActionBar(player, "§7Position §6" +
                        pos + "§7 sur §6" + tmp + "§7 dans la file d'attente du §6Skyblock");
                TextComponent message = new TextComponent("\n §c§l» §c§lCliquez ici pour quitter la file d'attente §c§l« \n");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/leavequeues"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("§c§lCliquez sur ce message pour quitter la file d'attente").create()));
                player.sendMessage(message);
            } else {
                player.sendMessage("\n§a§lFile d'attente §7» §cVous devez posséder la version 1.16.5 ou supérieure pour rejoindre le skyblock.");
            }
        }
        return true;
    }

}
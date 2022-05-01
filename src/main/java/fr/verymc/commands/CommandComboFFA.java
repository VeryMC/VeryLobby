package fr.verymc.commands;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
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

public class CommandComboFFA implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            final Player player = (Player) sender;
            if (ServerQueueSkyblockManager.instance.position.containsKey(player.getName())) {
                ServerQueueSkyblockManager.instance.position.remove(player.getName());
                player.sendMessage("\n§a§lFile d'attente §7» §cVous avez été retiré de la file d'attente du skyblock !");
                return true;
            }
            if (ServerQueueComboFFAManager.instance.position.containsKey(player.getName())) {
                player.sendMessage("\n§a§lFile d'attente §7» §cVous êtes déjà dans la file d'attente !");
                return true;
            }
            ViaAPI api = Via.getAPI();
            int playerversion = api.getPlayerVersion(player);
            int pos = ServerQueueComboFFAManager.instance.position.size();
            if (playerversion >= 47) {
                if (player.hasPermission("fastjoin")) {
                    ServerQueueComboFFAManager.instance.setPosition(player.getName(), 0);
                    pos = 0;
                } else {
                    ServerQueueComboFFAManager.instance.setPosition(player.getName(), pos);
                }
                player.closeInventory();
                int tmp = ServerQueueComboFFAManager.instance.position.size() - 1;
                PlayerNMS.sendActionBar(player, "§7Position §6" +
                        pos + "§7 sur §6" + tmp + "§7 dans la file d'attente du §6ComboFFA");
                TextComponent message = new TextComponent("\n §c§l» §c§lCliquez ici pour quitter la file d'attente §c§l« \n");
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/leavequeues"));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("§c§lCliquez sur ce message pour quitter la file d'attente").create()));
                player.sendMessage(message);
            } else {
                player.sendMessage("\n§a§lFile d'attente §7» §cVous devez posséder la version 1.8.9 ou supérieure pour rejoindre le comboFFA.");
            }
        }
        return true;
    }
}

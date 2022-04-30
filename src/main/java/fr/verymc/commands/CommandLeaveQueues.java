package fr.verymc.commands;

import fr.verymc.serverqueue.ServerQueueComboFFAManager;
import fr.verymc.serverqueue.ServerQueueSkyblockManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLeaveQueues implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command label, String arg, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (ServerQueueSkyblockManager.instance.position.containsKey(player.getName())) {
                ServerQueueSkyblockManager.instance.position.remove(player.getName());
                player.sendMessage("§a§lFile d'attente §7» §aVous avez quitté la file d'attente du skyblock avec succès !");
            }
            if (ServerQueueComboFFAManager.instance.position.containsKey(player.getName())) {
                ServerQueueComboFFAManager.instance.position.remove(player.getName());
                player.sendMessage("§a§lFile d'attente §7» §aVous avez quitté la file d'attente du ComboFFA avec succès !");
            }
        }

        return false;
    }

}

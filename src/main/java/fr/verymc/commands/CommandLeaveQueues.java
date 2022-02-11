package fr.verymc.commands;

import fr.verymc.serverqueue.ServerQueueManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandLeaveQueues implements CommandExecutor {

    public static void removefromqueues(Player player) {
        ServerQueueManager.position.remove(player.getName());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command label, String arg, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (ServerQueueManager.position.containsKey(player.getName())) {
                removefromqueues(player);
                player.sendMessage("§a§lFile d'attente §7» §aVous avez quitté la file d'attente du skyblock avec succès !");
            }
        }

        return false;
    }

}

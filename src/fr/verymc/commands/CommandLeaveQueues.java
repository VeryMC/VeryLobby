package fr.verymc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.verymc.serverqueue.ServerQueueManager;

public class CommandLeaveQueues implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command label, String arg, String[] args) {
		
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(ServerQueueManager.position.containsKey(player.getName())) {
				removefromqueues(player);
				player.sendMessage("§a§lFile d'attente §7» §aVous avez quitté la file d'attente du skyblock avec succès !");
			}
		}
		
		return false;
	}
	
	public static void removefromqueues(Player player) {
		ServerQueueManager.position.remove(player.getName());
	}

}

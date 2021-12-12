package fr.verymc.commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;

import fr.verymc.main;
import fr.verymc.serverqueue.ServerQueueManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandSkyblock implements CommandExecutor {
	
	public static HashMap <UUID, Integer > cooldowns = main.cooldowns;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(sender instanceof Player) {
			final Player player = (Player) sender;
			ViaAPI api = Via.getAPI();
			if(api.getPlayerVersion(player) >= 754) {
				int pos = ServerQueueManager.position.size();
			if(player.hasPermission("fastjoin")) {
		        ServerQueueManager.setPosition(player.getName(), 0);
			} else {
				ServerQueueManager.setPosition(player.getName(), pos);
			}
			int tmp = ServerQueueManager.position.size()-1;
		player.sendMessage("\n§a§lFile d'attente §7» §aVous rejoignez la file d'attente du skyblock en position §6" + 
					pos + "§a sur §6" + tmp + "§a.");
		TextComponent message = new TextComponent("\n §c§l» §c§lCliquez ici pour quitter la file d'attente §c§l« \n");
			message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/leavequeues"));
			message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
					new ComponentBuilder("§c§lCliquez sur ce message pour quitter la file d'attente").create()));
			player.sendMessage(message);
			} else {
				player.sendMessage("\n§a§lFile d'attente §7» §cVous devez utilisez la version 1.16.5 ou supérieure pour jouer au skyblock !");
			}
		}
		return true;	
		}

}
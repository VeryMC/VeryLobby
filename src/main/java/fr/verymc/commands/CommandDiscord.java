package fr.verymc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDiscord implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player) sender;
            p.sendMessage("§e§lDiscord » §fhttps://discord.gg/FnePjz6rAh");
            return true;
        }
        System.out.println("Merci d'éxecuter cette comande en jeu");
        return false;
    }
}

package fr.verymc.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandSite implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            p.sendMessage("§e§lSite » §fhttps://verymc.fr/");
            return true;
        }
        System.out.println("Merci d'éxecuter cette commande en jeu !");
        return true;
    }
}

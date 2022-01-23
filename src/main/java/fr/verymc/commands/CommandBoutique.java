package fr.verymc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBoutique implements CommandExecutor {

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            p.sendMessage("§e§lBoutique » §fhttps://verymcfr/boutique");
            return true;
        }
        System.out.println("Merci d'éxecuter cette commande en jeu !");
        return false;
    }

}

package fr.farmeurimmo.premsi.commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class CommandDiscord implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player p = (Player)sender;
            p.sendMessage("§e§lDiscord » §fhttps://discord.gg/B93dApbDAa");
            return true;
        }
        System.out.println("Merci d'éxecuter cette comande en jeu");
        return false;
    }
}

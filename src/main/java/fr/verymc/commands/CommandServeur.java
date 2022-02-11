package fr.verymc.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandServeur implements CommandExecutor {
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player) sender;
            player.sendMessage("§6§m------------------------------------ \n\n§6Information serveur: \n§bServeur: §f" + Bukkit.getServer().getName() + "\n§bVersion: §f" + Bukkit.getVersion() + "\n§bConnectés: §f " + Bukkit.getOnlinePlayers().size() + "\n§bPlugins: §fVeryLobby \n§bEtat: §aAllumé \n \n§6§m------------------------------------ \n");
            return true;
        }
        System.out.println("Merci d'utiliser les commandes en jeu !");
        return true;
    }
}

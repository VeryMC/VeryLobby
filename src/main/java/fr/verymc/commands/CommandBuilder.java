package fr.verymc.commands;

import fr.verymc.events.JoinLeave;
import fr.verymc.jump.InteractJump;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CommandBuilder implements CommandExecutor {
    public static Map<Player, Boolean> statutbuilder;

    static {
        CommandBuilder.statutbuilder = new HashMap<Player, Boolean>();
    }

    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("Merci d'éxecuter cette commande en jeu !");
            return false;
        }
        final Player player = (Player) sender;
        if (!player.hasPermission("builder")) {
            player.sendMessage("§cVous n'avez pas la permission requise pour effectuer cette commande !");
            return true;
        }
        if (InteractJump.Jump.contains(player.getUniqueId())) {
            player.sendMessage("§a§lMode Builder §8>> §fVous ne pouvez pas entrer en mode buildeur pendant le jump !");
            return true;
        }
        if (CommandBuilder.statutbuilder.containsKey(player)) {
            if (CommandBuilder.statutbuilder.get(player) == true) {
                CommandBuilder.statutbuilder.put(player, false);
                player.sendMessage("§a§lMode Builder §8>> §fTu es maintenant en mode Joueur !");
                JoinLeave.GiveItem(player);
                player.setGameMode(GameMode.ADVENTURE);
                return true;
            } else {
                CommandBuilder.statutbuilder.put(player, true);
                player.sendMessage("§a§lMode Builder §8>> §fTu es maintenant en mode Builder !");
                player.getInventory().clear();
                player.setGameMode(GameMode.CREATIVE);
                return true;
            }
        } else {
            CommandBuilder.statutbuilder.put(player, true);
            player.sendMessage("§a§lMode Builder §8>> §fTu es maintenant en mode Builder !");
            player.getInventory().clear();
            player.setGameMode(GameMode.CREATIVE);
            return true;
        }
    }
}

package fr.farmeurimmo.premsi.core;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.farmeurimmo.premsi.utils.ScoreboardSign;
import net.luckperms.api.model.user.User;

public class ScoreBoardNMS {
	
	public static Map<Player, ScoreboardSign> boards = new HashMap<>();
	
	public static void MakeScoreBoardForPlayer(Player player) {
	ScoreboardSign sb = new ScoreboardSign(player, "§6§lPremsiServ");
	
	String Grade = "§7N/A";
	
	User user = main.api.getUserManager().getUser(player.getUniqueId());
	if (user.getCachedData().getMetaData().getPrefix() != null) {
		Grade = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§");
	}
	
	int ping = 0;
	String pingcolor = "§2";
	try {
		  Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
		  ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
		  ((Throwable) e).printStackTrace();
		}
	if(ping >= 0 && ping <= 35) {
		pingcolor = "§2";
	}
	if(ping >= 36 && ping <= 65) {
		pingcolor = "§a";
	}
	if(ping >= 66 && ping <= 95) {
		pingcolor = "§e";
	}
	if(ping >= 96 && ping <= 125) {
		pingcolor = "§c";
	}
	if(ping >= 126) {
		pingcolor = "§4";
	}
	
	sb.create();
	sb.setLine(14, "  §eplay.premsiserv.com");
	sb.setLine(13, "§1");
	sb.setLine(12, "§fServeur §8» §c"+Bukkit.getServerName());
	sb.setLine(11, "§fEn ligne §8» §c" + Bukkit.getServer().getOnlinePlayers().size());
	sb.setLine(10, "§6§lServeur");
	sb.setLine(9, "§6");
	sb.setLine(8, "§fCrédits §8» §csoon");
	sb.setLine(7, "§fCoins §8» §csoon");
	sb.setLine(6, "§fGrade §8» §f"+Grade);
	sb.setLine(5, "§fPing §8» §f" + pingcolor + ping);
	sb.setLine(4, "§6§l"+player.getName());
	sb.setLine(3, "§f");

	//...//

	boards.put(player, sb); //-> Important to update after//

	/**
	* Until line 15 !
	* Limitation of the game.
	*/
	}
	public static void UpdateScoreBoard() {
		for (Entry<Player, ScoreboardSign> board : boards.entrySet()) {
			int ping = 0;
			String pingcolor = "§2";
			try {
				  Object entityPlayer = board.getKey().getClass().getMethod("getHandle").invoke(board.getKey());
				  ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
				  ((Throwable) e).printStackTrace();
				}
			if(ping >= 0 && ping <= 35) {
				pingcolor = "§2";
			}
			if(ping >= 36 && ping <= 65) {
				pingcolor = "§a";
			}
			if(ping >= 66 && ping <= 95) {
				pingcolor = "§e";
			}
			if(ping >= 96 && ping <= 125) {
				pingcolor = "§c";
			}
			if(ping >= 126) {
				pingcolor = "§4";
			}
			
			String Grade = "§7N/A";
			
			User user = main.api.getUserManager().getUser(board.getKey().getUniqueId());
			if (user.getCachedData().getMetaData().getPrefix() != null) {
				Grade = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§");
			}
			
			board.getValue().setLine(5, "§fPing §8» §f" + pingcolor + ping);
			board.getValue().setLine(11, "§fEn ligne §8» §c" + Bukkit.getServer().getOnlinePlayers().size());
			board.getValue().setLine(6, "§fGrade §8» §f"+Grade);
		    
		}
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("PremsiLobby"), new Runnable() {
			public void run() {
				UpdateScoreBoard();
			}
		}, 20);
	}
	public static void DeleteScoreBoard() {
		for (Entry<Player, ScoreboardSign> board : boards.entrySet()) {
			board.getValue().destroy();
		}
	}
}

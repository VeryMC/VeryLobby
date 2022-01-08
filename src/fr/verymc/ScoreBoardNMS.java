package fr.verymc;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.verymc.utils.ScoreboardSign;

public class ScoreBoardNMS {
	
	public static Map<Player, ScoreboardSign> boards = new HashMap<>();
	public static Map<Player, String> preffixes = new HashMap<>();
	public static Map<Player, String> suffixes = new HashMap<>();
	
	public static void MakeScoreBoardForPlayer(Player player) {
	ScoreboardSign sb = new ScoreboardSign(player, "\u00A76\u00A7l✯ Very\u00A7f\u00A7lMc ✯");
	
	String Preffix = ScoreBoardNMSRanksJump.GetPrefix(player);
	String Suffix = ScoreBoardNMSRanksJump.GetSuffix(player);
	
	int online = Bukkit.getServer().getOnlinePlayers().size();
	String serveurname = Bukkit.getServerName();
	sb.create();
	sb.setLine(14, "\u00A7f\u00A7lwww.\u00A76\u00A7lvery\u00A7f\u00A7lmc\u00A7f\u00A7l.fr");
	
	sb.setLine(13, "\u00A7f");
	
	sb.setLine(12, "\u00A77Lobby \u00A77▸ \u00A7a"+online);
	sb.setLine(11, "\u00A77Boutique \u00A77▸\u00A7e /boutique");
	sb.setLine(10, "\u00A77Discord \u00A77▸\u00A7d /discord");
	sb.setLine(9, "\u00A7c\u00A7l➔ Informations");
	
	sb.setLine(8, "\u00A71");
	
	sb.setLine(7, "\u00A77Hub \u00A77▸ \u00A76"+serveurname);
	sb.setLine(6, "\u00A77Verycoins \u00A77▸ \u00A7csoon");
	if(Suffix.contains("✰")) {
		sb.setLine(5, "\u00A77Premium \u00A77▸ \u00A7a\u00A7l✔");
		} else {
			sb.setLine(5, "\u00A77Premium \u00A77▸ \u00A7c\u00A7lx");
		}
	sb.setLine(4, "\u00A77Grade \u00A77▸ "+Preffix);
	sb.setLine(3, "\u00A77Pseudo \u00A77▸ \u00A7f"+player.getName());
	
	sb.setLine(2, "\u00A7a\u00A7l➔ Profil");
	
	sb.setLine(1, "\u00A73");

	boards.put(player, sb);

	}
	public static void UpdateScoreBoard() {
		for (Entry<Player, ScoreboardSign> board : boards.entrySet()) {
			
			String Preffix = ScoreBoardNMSRanksJump.GetPrefix(board.getKey());
			String Suffix = ScoreBoardNMSRanksJump.GetSuffix(board.getKey());
			int online = Bukkit.getServer().getOnlinePlayers().size();
			board.getValue().setLine(12, "\u00A77Lobby \u00A77▸ \u00A7a" + online);
			board.getValue().setLine(4, "\u00A77Grade \u00A77▸ "+Preffix);
			
			if(Suffix.contains("✰")) {
				board.getValue().setLine(5, "\u00A77Premium \u00A77▸ \u00A7a\u00A7l✔");
				} else {
					board.getValue().setLine(5, "\u00A77Premium \u00A77▸ \u00A7c\u00A7lx");
				}
		    
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
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

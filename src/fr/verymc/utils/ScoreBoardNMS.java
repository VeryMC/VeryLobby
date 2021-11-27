package fr.verymc.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.verymc.ScoreBoardNMSRanksJump;

public class ScoreBoardNMS {
	
	public static Map<Player, ScoreboardSign> boards = new HashMap<>();
	public static Map<Player, Integer> pings = new HashMap<>();
	public static Map<Player, String> preffixes = new HashMap<>();
	public static Map<Player, String> suffixes = new HashMap<>();
	
	public static void MakeScoreBoardForPlayer(Player player) {
	ScoreboardSign sb = new ScoreboardSign(player, "§6§lVery§f§lMc");
	
	String Preffix = ScoreBoardNMSRanksJump.GetPrefix(player);
	String Suffix = ScoreBoardNMSRanksJump.GetSuffix(player);
	
	int online = Bukkit.getServer().getOnlinePlayers().size();
	String serveurname = Bukkit.getServerName();
	sb.create();
	sb.setLine(13, "  §eplay.verymc.fr");
	sb.setLine(12, "§1");
	sb.setLine(11, "§fServeur §8» §c"+serveurname);
	sb.setLine(10, "§fEn ligne §8»§f §c" + online);
	sb.setLine(9, "§6§lServeur");
	sb.setLine(8, "§6");
	sb.setLine(7, "§fCroins §8» §csoon");
	if(Suffix.equalsIgnoreCase("§d✯")) {
	sb.setLine(6, "§fAbonnement §8» §a✔");
	} else {
		sb.setLine(6, "§fAbonnement §8» §cx");
	}
	sb.setLine(5, "§fGrade §8» §f"+Preffix);
	sb.setLine(4, "§fPing §8» §f" + GetPlayerPingFormatted(player));
	sb.setLine(3, "§6§l"+player.getName());
	sb.setLine(2, "§f");

	boards.put(player, sb);

	}
	@SuppressWarnings("deprecation")
	public static void UpdateScoreBoard() {
		for (Entry<Player, ScoreboardSign> board : boards.entrySet()) {
			
			String Preffix = ScoreBoardNMSRanksJump.GetPrefix(board.getKey());
			String Suffix = ScoreBoardNMSRanksJump.GetSuffix(board.getKey());
			int online = Bukkit.getServer().getOnlinePlayers().size();
			board.getValue().setLine(4, "§fPing §8» §f" + GetPlayerPingFormatted(board.getKey()));
			board.getValue().setLine(10, "§fEn ligne §8»§f §c" + online);
			board.getValue().setLine(5, "§fGrade §8» §f"+Preffix);
			
			if(Suffix.equalsIgnoreCase("§d✯")) {
				board.getValue().setLine(6, "§fAbonnement §8» §a✔");
				} else {
					board.getValue().setLine(6, "§fAbonnement §8» §cx");
				}
		    
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
			public void run() {
				UpdateScoreBoard();
			}
		}, 60);
	}
	@SuppressWarnings("deprecation")
	public static void UpdateScorePings() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			int ping = 0;
			try {
				  Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
				  ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
				  ((Throwable) e).printStackTrace();
				}
			pings.put(player, ping);
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
			public void run() {
				UpdateScorePings();
			}
		}, 40);
	}
	public static String GetPlayerPingFormatted(Player player) {
		String pingcolor = "§2";
		int ping = 0;
		if(!pings.containsKey(player)) {
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
			String formatteda = pingcolor+ping;
			return formatteda;
		}
		ping = pings.get(player);
		
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
		
		String formatted = pingcolor + ping;
		return formatted;
	}
	public static void DeleteScoreBoard() {
		for (Entry<Player, ScoreboardSign> board : boards.entrySet()) {
			board.getValue().destroy();
		}
	}
}

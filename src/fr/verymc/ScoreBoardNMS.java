package fr.verymc;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.verymc.utils.ScoreboardSign;

public class ScoreBoardNMS {
	
	public static Map<Player, ScoreboardSign> boards = new HashMap<>();
	public static Map<Player, Integer> pings = new HashMap<>();
	public static Map<Player, String> preffixes = new HashMap<>();
	public static Map<Player, String> suffixes = new HashMap<>();
	
	public static void MakeScoreBoardForPlayer(Player player) {
	ScoreboardSign sb = new ScoreboardSign(player, "§6§l✯ Very§f§lMc ✯");
	
	String Preffix = ScoreBoardNMSRanksJump.GetPrefix(player);
	String Suffix = ScoreBoardNMSRanksJump.GetSuffix(player);
	
	int online = Bukkit.getServer().getOnlinePlayers().size();
	String serveurname = Bukkit.getServerName();
	sb.create();
	sb.setLine(14, "§f§lwww.§6§lvery§f§lmc§f§l.fr");
	
	sb.setLine(13, "§f");
	
	sb.setLine(12, "§7Lobby §7▸ §a"+online);
	sb.setLine(11, "§7Boutique §7▸ §§e/boutique");
	sb.setLine(10, "§7Discord §7▸ §d/discord");
	sb.setLine(9, "§c§l➔ Informations");
	
	sb.setLine(8, "§1");
	
	sb.setLine(7, "§7Hub §7▸ §6"+serveurname);
	sb.setLine(6, "§7Verycoins §7▸ §csoon");
	if(Suffix.contains("✯")) {
		sb.setLine(5, "§7Premium §7▸ §a§l✔");
		} else {
			sb.setLine(5, "§7Premium §7▸ §c§lx");
		}
	sb.setLine(4, "§7Grade §7▸ "+Preffix);
	sb.setLine(3, "§7Pseudo §7▸ §f"+player.getName());
	
	sb.setLine(2, "§a§l➔ Profil");
	
	sb.setLine(1, "§3");

	boards.put(player, sb);

	}
	@SuppressWarnings("deprecation")
	public static void UpdateScoreBoard() {
		for (Entry<Player, ScoreboardSign> board : boards.entrySet()) {
			
			if(board.getKey().isOnline() == false) {
				board.getKey().remove();
				pings.remove(board.getKey());
			}
			
			String Preffix = ScoreBoardNMSRanksJump.GetPrefix(board.getKey());
			String Suffix = ScoreBoardNMSRanksJump.GetSuffix(board.getKey());
			int online = Bukkit.getServer().getOnlinePlayers().size();
			board.getValue().setLine(12, "§7Lobby §7▸ §a" + online);
			board.getValue().setLine(4, "§7Grade §7▸ "+Preffix);
			
			if(Suffix.contains("✯")) {
				board.getValue().setLine(5, "§7Premium §8» §a§l✔");
				} else {
					board.getValue().setLine(5, "§7Premium §8» §c§lx");
				}
		    
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
			public void run() {
				UpdateScoreBoard();
			}
		}, 40);
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
	public static void GetPlayerPing(Player player) {
			int ping = 0;
			try {
				  Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
				  ping = (int) entityPlayer.getClass().getField("ping").get(entityPlayer);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
				  ((Throwable) e).printStackTrace();
				}
			pings.put(player, ping);
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

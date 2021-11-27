package fr.verymc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.verymc.utils.ScoreBoardNMS;
import net.luckperms.api.model.user.User;

public class ScoreBoardNMSRanksJump {
	
	@SuppressWarnings("deprecation")
	public static void AutoUpdate() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			String Preffix = "§7N/A";
			String Suffix = "x";
			
			User user = main.api.getUserManager().getUser(player.getUniqueId());
			if (user.getCachedData().getMetaData().getPrefix() != null) {
				Preffix = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
			}
			if (user.getCachedData().getMetaData().getSuffix() != null) {
				Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§");
			}
			ScoreBoardNMS.preffixes.put(player, Preffix);
			ScoreBoardNMS.suffixes.put(player, Suffix);
		}
		Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
			public void run() {
				AutoUpdate();
			}
		}, 40);
	}
	public static String GetPrefix(Player player) {
		
		String Prefix = "§7N/A";
		
		if(!ScoreBoardNMS.preffixes.containsKey(player)) {		
			User user = main.api.getUserManager().getUser(player.getUniqueId());
			if (user.getCachedData().getMetaData().getPrefix() != null) {
				Prefix = user.getCachedData().getMetaData().getPrefix().replace("&l", "").replace("&", "§").replace("&d✯", "");
			}
		} else {
			Prefix = ScoreBoardNMS.preffixes.get(player);
		}
		
		return Prefix;
	}
	
    public static String GetSuffix(Player player) {
		
		String Suffix = "§7N/A";
		
		if(!ScoreBoardNMS.suffixes.containsKey(player)) {		
			User user = main.api.getUserManager().getUser(player.getUniqueId());
			if (user.getCachedData().getMetaData().getSuffix() != null) {
				Suffix = user.getCachedData().getMetaData().getSuffix().replace("&l", "").replace("&", "§").replace("&d✯", "");
			}
		} else {
			Suffix = ScoreBoardNMS.suffixes.get(player);
		}
		
		return Suffix;
	}

}

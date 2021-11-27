package fr.verymc.jump;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

public class MakeTop {
	
	public static Map<String, Double> Classement = new HashMap < > ();
	public static Map<String, Double> Valeurs = InteractJump.Valeurs;
	static String NumberOne = null;
	static Integer NumberToReachForSecond = null;
	static Integer NumberToReachForTrois = null;
	static String NumberTwo = null;
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), 257, 70, 285);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("VeryLobby");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
	public static void GenClassement() {
		Classement.clear();
		String un = "",deux = "",trois = "",quatre = "",cinq = "";
		int current = 0;
		int maxi = 5;
		while (current <= maxi) {
			Double bestvalue = (double) 0;
			String playerlayer = "N/A";
		for(Entry<String, Double> aa : Valeurs.entrySet()) {
			if(Classement.containsKey(aa.getKey())) {
				continue;
			}
			if(aa.getValue() > bestvalue) {
				bestvalue = aa.getValue();
				if(Classement.containsKey(playerlayer)) {
					Classement.remove(playerlayer);
				}
				playerlayer = aa.getKey();
				Classement.put(aa.getKey(), aa.getValue());
			}
		}
		if (current == 1) {
			un = playerlayer;
		}
		if (current == 2) {
			deux = playerlayer;
		}
		if (current == 3) {
			trois = playerlayer;
		}
		if (current == 4) {
			quatre = playerlayer;
		}
		if (current == 5) {
			cinq = playerlayer;
		}
		
		current += 1;
		}
		String sc1 = Classement.get(un)+"";
		String sc2 = Classement.get(deux)+"";
		String sc3 = Classement.get(trois)+"";
		String sc4 = Classement.get(quatre)+"";
		String sc5 = Classement.get(cinq)+"";
		hologram.clearLines();
		hologram = HologramsAPI.createHologram(plugin, holo);
		hologram.appendTextLine("§7▸ §6Classement §7◂ ");
		hologram.appendTextLine("§7");
		hologram.appendTextLine("1.§7 " + un + " en §e" + sc1.replace("null", "N/A") + " §7secondes");
		hologram.appendTextLine("2.§7 " + deux + " en §e" + sc2.replace("null", "N/A") + " §7secondes");	
		hologram.appendTextLine("3.§7 " + trois + " en §e" + sc3.replace("null", "N/A") + " §7secondes");
		hologram.appendTextLine("4.§7 " + quatre + " en §e" + sc4.replace("null", "N/A") + " §7secondes");
		hologram.appendTextLine("5.§7 " + cinq + " en §e" + sc5.replace("null", "N/A") + " §7secondes");
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
			public void run() {
				GenClassement();
			}
		}, 100);
	}
	public static void RemoveHolo() {
		hologram.delete();
	}

}
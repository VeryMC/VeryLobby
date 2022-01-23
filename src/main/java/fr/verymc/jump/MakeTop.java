package fr.verymc.jump;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class MakeTop {
	
	public static Map<String, Double> Classement = new HashMap < > ();
	public static Map<String, Double> toclass = new HashMap < > ();
	public static Map<String, Double> Valeurs = InteractJump.Valeurs;
	public static ArrayList<String> alreadyin = new ArrayList<String>();
	static String NumberOne = null;
	static Integer NumberToReachForSecond = null;
	static Integer NumberToReachForTrois = null;
	static String NumberTwo = null;
	
	static Location holo = new Location(Bukkit.getServer().getWorld("world"), 111.5, 47, 160.5);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("VeryLobby");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);
	
	public static void GenClassement() {
		Classement.clear();
		alreadyin.clear();
		int current = 0;
		int maxi = 5;
		String un = "",deux = "",trois = "",quatre = "",cinq = "";
		while (current <= maxi) {
			Double bestvalue = (double) 0;
			String playerlayer = "N/A";
		for(Entry<String, Double> aa : toclass.entrySet()) {
			if(alreadyin.contains(aa.getKey())) {
				continue;
			}
			if(bestvalue == 0) {
				if(!alreadyin.contains(playerlayer)) {
					bestvalue = aa.getValue();
					playerlayer = aa.getKey();
					alreadyin.add(aa.getKey());
					continue;
				}
			}
			if(aa.getValue() < bestvalue) {
				alreadyin.remove(playerlayer);
				bestvalue = aa.getValue();
				playerlayer = aa.getKey();
				alreadyin.add(aa.getKey());
				continue;
			}
		}
		if (current == 0) {
			un = playerlayer;
			if(playerlayer != "N/A") {
			  Classement.put(playerlayer, bestvalue/1000);
			}
		}
		if (current == 1) {
			deux = playerlayer;
			if(playerlayer != "N/A") {
				  Classement.put(playerlayer, bestvalue/1000);
			}
		}
		if (current == 2) {
			trois = playerlayer;
			if(playerlayer != "N/A") {
				  Classement.put(playerlayer, bestvalue/1000);
			}
		}
		if (current == 3) {
			quatre = playerlayer;
			if(playerlayer != "N/A") {
				  Classement.put(playerlayer, bestvalue/1000);
			}
		}
		if (current == 4) {
			cinq = playerlayer;
			if(playerlayer != "N/A") {
				  Classement.put(playerlayer, bestvalue/1000);
			}
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
		hologram.appendTextLine("§7▸ §6Classement Jump §7◂ ");
		hologram.appendTextLine("§7");
		hologram.appendTextLine("1.§7 §e" + un + "§7 en §e" + sc1.replace("null", "N/A") + " §7secondes");
		hologram.appendTextLine("2.§7 §e" + deux + "§7 en §e" + sc2.replace("null", "N/A") + " §7secondes");	
		hologram.appendTextLine("3.§7 §e" + trois + "§7 en §e" + sc3.replace("null", "N/A") + " §7secondes");
		hologram.appendTextLine("4.§7 §e" + quatre + "§7 en §e" + sc4.replace("null", "N/A") + " §7secondes");
		hologram.appendTextLine("5.§7 §e" + cinq + "§7 en §e" + sc5.replace("null", "N/A") + " §7secondes");
		
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
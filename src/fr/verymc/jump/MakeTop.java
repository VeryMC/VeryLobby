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
	
	public static Map<String, Integer> Classement = new HashMap < > ();
	public static Map<String, Integer> Valeurs = InteractJump.Valeurs;
	public static Map<String, Integer> SecondMap = InteractJump.SecondMap;
	static String NumberOne = null;
	static Integer NumberToReachForSecond = null;
	static Integer NumberToReachForTrois = null;
	static String NumberTwo = null;
	
	static String p1 = "N/A";
	static Integer score1 = 0;
	static String p2 = "N/A";
	static Integer score2 = 0;
	static String p3 = "N/A";
	static Integer score3 = 0;
	
	static Location holo = new Location(Bukkit.getServer().getWorld("Lobby-01"), 257, 70, 285);
	static Plugin plugin = Bukkit.getPluginManager().getPlugin("VeryLobby");
	static Hologram hologram = HologramsAPI.createHologram(plugin, holo);

	
	public static void GetTheFirst() {
		int NumberOneTime = 0;
		for(Entry<String, Integer> aaa : Valeurs.entrySet()) {
			if(NumberOneTime == 0) {
			NumberOneTime = aaa.getValue();
			p1 = aaa.getKey();
			score1 = aaa.getValue();
			}
			else if(NumberOneTime > aaa.getValue()) {
				NumberOneTime = aaa.getValue();
				p1 = aaa.getKey();
				score1 = aaa.getValue();
			}
		}
		NumberOne = p1;
		NumberToReachForSecond = score1;
		Classement.put(p1, score1);
		SecondMap.remove(p1);
	}
	public static void GetTheSecond() {
		int NumberOneTime = 0;
		for(Entry<String, Integer> aaa : SecondMap.entrySet()) {
			if(p1 != aaa.getKey() && p3 != aaa.getKey()) {
				if(NumberOneTime == 0) {
					NumberOneTime = aaa.getValue();
					p2 = aaa.getKey();
					score2 = aaa.getValue();
					}
					else if(NumberOneTime > aaa.getValue()) {
						if(NumberTwo != aaa.getKey()) {
						NumberOneTime = aaa.getValue();
						p2 = aaa.getKey();
						score2 = aaa.getValue();
						}
			}
		}
		}
			NumberTwo = p2;
			NumberToReachForTrois = score2;
			Classement.put(p2, score2);	
			SecondMap.remove(p2);
	}
	public static void GetTheThird() {
		int NumberOneTime = 0;
		for(Entry<String, Integer> aaa : SecondMap.entrySet()) {
				if(p1 != aaa.getKey() && p2 != aaa.getKey()) {
				if(NumberOneTime == 0) {
					NumberOneTime = aaa.getValue();
					p3 = aaa.getKey();
					score3 = aaa.getValue();
					}
					else if(NumberOneTime > aaa.getValue()) {
						NumberOneTime = aaa.getValue();
						p3 = aaa.getKey();
						score3 = aaa.getValue();
				}
			}
		}
			NumberOne = p3;
			NumberToReachForTrois = score3;
			Classement.put(p3, score3);	
			SecondMap.remove(p3);
	}
	public static void CreateHolo() {
		hologram.appendTextLine("§7▸ §6Classement §7◂ ");
		hologram.appendTextLine("§7");
		hologram.appendTextLine("1.§7 " + p1 + " en §e" + score1 + " §7secondes");
		hologram.appendTextLine("2.§7 " + p2 + " en §e" + score2 + " §7secondes");	
		hologram.appendTextLine("3.§7 " + p3 + " en §e" + score3 + " §7secondes");
		UpdateHolos();
	}
	public static void UpdateHolos() {
		GetTheFirst();
		GetTheSecond();
		GetTheThird();
		
		SecondMap.put(p1, score1);
		SecondMap.put(p2, score2);
		SecondMap.put(p3, score3);
		
		hologram.clearLines();
		hologram = HologramsAPI.createHologram(plugin, holo);
		hologram.appendTextLine("§7▸ §6Classement §7◂ ");
		hologram.appendTextLine("§7");
		hologram.appendTextLine("1.§7 " + p1 + " en §e" + score1 + " §7secondes");
		hologram.appendTextLine("2.§7 " + p2 + " en §e" + score2 + " §7secondes");	
		hologram.appendTextLine("3.§7 " + p3 + " en §e" + score3 + " §7secondes");
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
			public void run() {
				UpdateHolos();
			}
		}, 40);
	}
	public static void RemoveHolo() {
		hologram.delete();
	}

}
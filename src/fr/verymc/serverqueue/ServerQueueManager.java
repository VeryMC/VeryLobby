package fr.verymc.serverqueue;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.verymc.main;

public class ServerQueueManager {
	
	public static HashMap <String, Integer > position = new HashMap < > ();

	public static void setPosition(String playername, Integer time) {
		if (time == null)
			position.remove(playername);
		else
			position.put(playername, time);
	}
    public static int getPosition(String playername) {
        return (position.get(playername) == null ? 0 : position.get(playername));
    }
	
	public static void Every5sec() {	
		Player player = null;
		for (Map.Entry<String, Integer> entry : position.entrySet()){
			  if(entry.getValue() == 0) {
				  if(Bukkit.getPlayer(entry.getKey()) != null) {
					  if(Bukkit.getPlayer(entry.getKey()).isOnline() == true) {
				  player = Bukkit.getPlayer(entry.getKey());
					  } else {
						  position.remove(entry.getKey());
					  }
				  } else {
					  position.remove(entry.getKey());
				  }
			  }
			}
		
		if(player != null) {
			if(player.isOnline() == true) {
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Bukkit.getPluginManager().getPlugin("VeryMc"), "BungeeCord");
		player.closeInventory();
		out.writeUTF("Connect");
	    out.writeUTF("skyblock");
		player.sendPluginMessage(main.instance, "BungeeCord", out.toByteArray());
		}
		}
		final Player p = player;
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryMc"), new Runnable() {
		     public void run() {
		    	 if(p != null) {
		    		 if(!p.isOnline()) {
		    			 int aaa = 0;
		    			 for (Map.Entry<String, Integer> entry : position.entrySet()){
		    				 if(aaa <= entry.getValue()) {
		    					 aaa = entry.getValue();
		    				 }
		    			 }
		    			 for (Map.Entry<String, Integer> entry : position.entrySet()){
		    				 if(aaa >= 1) {
	    						entry.setValue(entry.getValue() - 1);
		    				 }
	    				}
		    			 aaa = 0;
		    		 }
		    	 }
		    	 for (Map.Entry<String, Integer> entry : position.entrySet()){
		 			if(Bukkit.getPlayer(entry.getKey()) != null) {
		 				if(Bukkit.getPlayer(entry.getKey()).isOnline() == true) {
		 			Bukkit.getPlayer(entry.getKey()).sendMessage("§a§lFile d'attente §7» §aVous êtes en position §6" + entry.getValue() + 
		 					" §adans la file d'attente du skyblock");
		 				}
		 			} else {
		 				position.remove(entry.getKey());
		 			}
		 		}
		    	 Every5sec();
		     }
		}, 100);
   }
}

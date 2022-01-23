package fr.verymc.serverqueue;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.verymc.main;
import fr.verymc.utils.PlayerNMS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ServerQueueManager {
	
	public static HashMap <String, Integer> position = new HashMap < > ();

	public static void setPosition(String playername, Integer time) {
		if (time == null)
			position.remove(playername);
		else
			position.put(playername, time);
	}
    public static int getPosition(String playername) {
        return (position.get(playername) == null ? 0 : position.get(playername));
    }
	
	public static void DisplayActionBarForA() {
    	Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
		     public void run() {
		    	 for (Map.Entry<String, Integer> entry : position.entrySet()){
		 			if(Bukkit.getPlayer(entry.getKey()) != null) {
		 				if(Bukkit.getPlayer(entry.getKey()).isOnline() == true) {
		 					int tmp = position.size()-1;
		 					PlayerNMS.sendActionBar(Bukkit.getPlayer(entry.getKey()), "§7Position §6" + 
				 					entry.getValue() + "§7 sur §6" + tmp + "§7 dans la file d'attente du §6Skyblock");
		 				} else {
			 				position.remove(entry.getKey());
			 			}
		 			} else {
		 				position.remove(entry.getKey());
		 			}
		 		}
		    	 DisplayActionBarForA();
		     }
		}, 20);
    }
    
	public static void Every5sec() {	
		Player player = null;
		if(position.size() > 0) {
			int value = 0;
			while (player == null) {
				if(position.containsValue(value)){
		      for (Map.Entry<String, Integer> entry : position.entrySet()){
			    	if(entry.getValue() == value) {
						  if(Bukkit.getPlayer(entry.getKey()) != null) {
							  if(Bukkit.getPlayer(entry.getKey()).isOnline() == true) {
						        player = Bukkit.getPlayer(entry.getKey());
						        break;
							  } else {
								  position.remove(entry.getKey());
							  }
						  } else {
							  position.remove(entry.getKey());
						  }
			    	} else {
			    		continue;
			    	}
			    }
			    } else {
					  value+=1;
					  continue;
		}		
			}
		if(player != null) {
			if(player.isOnline() == true) {
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(Bukkit.getPluginManager().getPlugin("VeryLobby"), "BungeeCord");
		out.writeUTF("Connect");
	    out.writeUTF("skyblock");
		player.sendPluginMessage(main.instance, "BungeeCord", out.toByteArray());
		}
		}
		}
		final Player p = player;
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("VeryLobby"), new Runnable() {
		     public void run() {
		    	 if(p != null) {
		    		 if(!p.isOnline()) {
		    			 int bestvalue = position.size();
		    			 while (bestvalue != 0) {
		    			   for (Map.Entry<String, Integer> entry : position.entrySet()){
		    				   if(entry.getValue()<bestvalue) {
		    					   bestvalue=entry.getValue();
		    				   }
		    			    }
		    			   if(bestvalue >= 1) {
		    				   for (Map.Entry<String, Integer> entry : position.entrySet()){
			    				   if(entry.getValue()>=1) {
			    					   if(!position.containsValue(entry.getValue()-1)) {
			    					     setPosition(entry.getKey(), entry.getValue()-1);
			    					     if(entry.getValue()<bestvalue) {
					    					   bestvalue=entry.getValue();
					    				   }
			    					   }
			    				   }
			    			    }
		    			    }
		    			 }
		    		 }
		    	 }
		    	 Every5sec();
		     }
		}, 100);
   }
}

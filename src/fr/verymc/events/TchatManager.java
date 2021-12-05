package fr.verymc.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

@SuppressWarnings("deprecation")
public class TchatManager implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onAsyncChat(PlayerChatEvent e) {
		Player player = e.getPlayer();
		String Prefix = "§7N/A";
		String Suffix = "";
		
		e.setCancelled(true);
		
		User user = LuckPermsProvider.get().getUserManager().getUser(player.getName());
		if(user.getCachedData().getMetaData().getPrefix() != null) {
		Prefix = user.getCachedData().getMetaData().getPrefix();
		}
		if(user.getCachedData().getMetaData().getSuffix() != null) {
			Suffix = " " + user.getCachedData().getMetaData().getSuffix();
		}
		
		TextComponent message = new TextComponent();
        TextComponent symbole = new TextComponent();
        
        String temp = e.getMessage();
        String end = "";
        
        for(char a : temp.toCharArray()) {
        	end = end + "§7" + a;
        }
        
        message.setText(Prefix + " " + player.getName() + Suffix + "§7: "+end);
        symbole.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§cCliquez ici pour report le message de " +
                player.getName()).create()));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("").create()));
        symbole.setText("§cx ");
        symbole.addExtra(message);
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(symbole);
        }
	}

}

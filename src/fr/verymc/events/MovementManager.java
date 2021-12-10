package fr.verymc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MovementManager implements Listener {
	
	@EventHandler
	public void OnPlayerMove(PlayerMoveEvent e) {
		if(e.getTo().getBlockY() < -2) {
			e.getPlayer().teleport(JoinLeaveHub.spawn);
		} else {
			return;
		}
	}

}

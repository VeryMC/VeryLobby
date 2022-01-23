package fr.verymc.events;

import fr.verymc.jump.InteractJump;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ProtectExplo implements Listener {
	
	@EventHandler
	public void OnWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void Explo(ExplosionPrimeEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void Explo1(EntityExplodeEvent e) {
		e.setCancelled(true);
	}
	@EventHandler
	public void ToggleFlight(PlayerToggleFlightEvent e) {
		if(InteractJump.Jump.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
	@EventHandler
	public void GamemodeChange(PlayerGameModeChangeEvent e) {
		if(InteractJump.Jump.contains(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}
}

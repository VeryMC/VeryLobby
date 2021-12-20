package fr.verymc.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
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
}

package fr.farmeurimmo.premsi.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherEvent implements Listener {
	
	@EventHandler
	public void OnWeatherChange(WeatherChangeEvent e) {
		e.setCancelled(true);
	}
}

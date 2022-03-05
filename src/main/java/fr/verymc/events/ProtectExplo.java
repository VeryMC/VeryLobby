package fr.verymc.events;

import fr.verymc.commands.CommandBuilder;
import fr.verymc.jump.InteractJump;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
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
        if (InteractJump.Jump.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void GamemodeChange(PlayerGameModeChangeEvent e) {
        if (InteractJump.Jump.contains(e.getPlayer().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void OnPlayerDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHunger(final FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {
        if (CommandBuilder.statutbuilder.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (CommandBuilder.statutbuilder.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlaceOnPlayer(final BlockPlaceEvent event) {
        if (CommandBuilder.statutbuilder.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        final Location playerlocation = event.getPlayer().getLocation();
        final Location blocklocation = event.getBlock().getLocation();
        if (playerlocation == blocklocation) {
            event.setCancelled(true);
        }
    }
}

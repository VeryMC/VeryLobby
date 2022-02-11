package fr.verymc.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageManager implements Listener {

    @EventHandler
    public void OnDamageByBlock(EntityDamageByBlockEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void OnDamageEvent(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void OnDamageByEntity(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Entity damager = e.getDamager();
        if (!(damager instanceof Player)) {
            e.setCancelled(true);
            return;
        }

        if (!Bukkit.getPlayer(e.getDamager().getName()).getMetadata("mod").isEmpty()) {
            e.setDamage(0);
            e.setCancelled(false);
            return;
        }

        e.setCancelled(true);
    }

}

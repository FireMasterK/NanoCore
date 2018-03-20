package me.idarkyy.nanocore.listeners;

import org.bukkit.entity.Enderman;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTeleportEvent;

public class EntityTeleportListener implements Listener {
    @EventHandler
    public void execute(EntityTeleportEvent event) {
        if (event.getEntity() instanceof Enderman) {
            event.setCancelled(true);
        }
    }
}

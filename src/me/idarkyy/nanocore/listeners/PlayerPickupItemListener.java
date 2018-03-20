package me.idarkyy.nanocore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItemListener implements Listener {
    @EventHandler
    public void execute(PlayerPickupItemEvent event) {
        if (event.getItem().getType().equals(Material.COBBLESTONE)) {
            event.setCancelled(true);
        }
    }
}

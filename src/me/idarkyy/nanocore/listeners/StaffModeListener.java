package me.idarkyy.nanocore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class StaffModeListener implements Listener {

    @EventHandler
    public void execute(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

    }
}

package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.events.PlayerMoveByBlockEvent;
import me.idarkyy.nanocore.managers.CoreManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    CoreManager core = CoreManager.getManager();

    @EventHandler
    public void execute(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ActionPlayer ap = ActionPlayer.of(player);

        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockY() != event.getTo().getBlockY() || event.getFrom().getBlockZ() != event.getTo().getBlockZ()) {
            PlayerMoveByBlockEvent moveByBlockEvent = new PlayerMoveByBlockEvent(event.getPlayer(), event.getTo(), event.getFrom());
            Bukkit.getPluginManager().callEvent(moveByBlockEvent);

            if(moveByBlockEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }
}

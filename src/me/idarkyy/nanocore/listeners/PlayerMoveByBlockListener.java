package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.events.PlayerMoveByBlockEvent;
import me.idarkyy.nanocore.managers.CoreManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerMoveByBlockListener implements Listener {
    CoreManager core = CoreManager.getManager();

    public void execute(PlayerMoveByBlockEvent event) {

        Player player = event.getPlayer();
        ActionPlayer ap = ActionPlayer.of(player);

        boolean shouldCancel = false;
        if (core.getPanicMode(player) || core.isFrozen(player)) {
            shouldCancel = true;
        }

        if (shouldCancel && (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ())) {
            player.teleport(event.getFrom());
        }
    }
}

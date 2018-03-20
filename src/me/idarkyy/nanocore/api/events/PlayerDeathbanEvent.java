package me.idarkyy.nanocore.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerDeathbanEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled = false;

    private Player player;

    public PlayerDeathbanEvent(Player player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public String getEventName() {
        return super.getEventName();
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}

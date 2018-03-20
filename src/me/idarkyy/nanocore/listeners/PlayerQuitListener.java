package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerQuitListener implements Listener {
    ConfigurationManager config = ConfigurationManager.getManager();

    @EventHandler
    public void execute(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ActionPlayer ap = new ActionPlayer(player);

        if (player.hasPermission(config.getPermissions().getString("STAFF", "nanocore.staff")) || player.isOp()) {
            Bukkit.broadcast(config.getLanguage().getString("STAFF_QUIT_MESSAGE").replace("%player%", player.getName()),
                    config.getPermissions().getString("STAFF", "nanocore.staff"));
        }
    }
}

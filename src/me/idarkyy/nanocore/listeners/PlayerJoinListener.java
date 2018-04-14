package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.CoreManager;
import me.idarkyy.nanocore.managers.DataManager;
import me.idarkyy.nanocore.managers.PrivateMessageManager;
import me.idarkyy.nanocore.utils.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();

    @EventHandler
    public void execute(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ActionPlayer ap = new ActionPlayer(player);

        CoreManager.getManager().setPanicMode(player, false);

        PrivateMessageManager.getManager().setSounds(player, true);

        data.setKey(player, "UUID", player.getUniqueId().toString());

        if (player.hasPermission(config.getPermissions().getString("STAFF", "nanocore.staff")) || player.isOp()) {
            Bukkit.broadcast(config.getLanguage().getString("STAFF_QUIT_MESSAGE").replace("%player%", player.getName()),
                    config.getPermissions().getString("STAFF", "nanocore.staff"));
        }

        Sidebar.createScore(player);
        Sidebar.getByPlayer(player).setTitle(config.getScoreboard().getString("TITLE"));
    }
}

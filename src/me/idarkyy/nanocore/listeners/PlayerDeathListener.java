package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.api.events.PlayerDeathbanEvent;
import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.DataManager;
import me.idarkyy.nanocore.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.time.ZonedDateTime;

public class PlayerDeathListener implements Listener {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();

    @EventHandler
    public void execute(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ActionPlayer ap = new ActionPlayer(player);

        YamlConfiguration pd = data.getPlayerData(player);

        String inventory = InventoryUtils.itemStackArrayToBase64(player.getInventory().getContents());
        String armor = InventoryUtils.itemStackArrayToBase64(player.getInventory().getArmorContents());

        data.setKey(player, "LASTDEATH.INVENTORY", inventory);
        data.setKey(player, "LASTDEATH.ARMOR", armor);

        PlayerDeathbanEvent dbEvent = new PlayerDeathbanEvent(player);

        Bukkit.getPluginManager().callEvent(dbEvent);

        if (dbEvent.isCancelled()) {
            return;
        }

        if (!player.hasPermission(config.getPermissions().getString("DEATHBAN.BYPASS")) && !player.isOp()) {
            data.setKey(player, "deathban.state", true);
            ZonedDateTime until = ZonedDateTime.now().plusMinutes(config.getConfig().getLong("deathban-time"));
            data.setKey(player, "deathban.year", until.getYear());
            data.setKey(player, "deathban.month", until.getMonthValue());
            data.setKey(player, "deathban.day", until.getDayOfMonth());
            data.setKey(player, "deathban.hour", until.getHour());
            data.setKey(player, "deathban.minute", until.getMinute());
            data.setKey(player, "deathban.second", until.getSecond());

            player.kickPlayer(
                    ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getManager().getMessages().getString("DEATHBAN_KICKED"))
            );
        }
    }
}

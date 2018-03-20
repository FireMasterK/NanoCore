package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.DataManager;
import me.idarkyy.nanocore.managers.DeathbanManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class PlayerPreLoginListener implements Listener {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();
    DeathbanManager dbManager = DeathbanManager.getManager();

    HashMap<OfflinePlayer, Boolean> joinAgain = new HashMap<>();

    @EventHandler
    public void execute(AsyncPlayerPreLoginEvent event) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(event.getUniqueId());
        YamlConfiguration pd = data.getPlayerData(player);

        if (pd.get("lives") == null) {
            data.setKey(player, "lives", 0);
        }

        if (pd.get("deathban.state") == null) {
            data.setKey(player, "deathban.state", false);
            data.setKey(player, "deathban.year", null);
            data.setKey(player, "deathban.month", null);
            data.setKey(player, "deathban.day", null);
            data.setKey(player, "deathban.hour", null);
            data.setKey(player, "deathban.minute", null);
            data.setKey(player, "deathban.second", null);
        } else {
            if (pd.getBoolean("deathban.state")) {
                ZonedDateTime time = ZonedDateTime.now();
                ZonedDateTime deathbanExpiration = ZonedDateTime.of(
                        pd.getInt("deathban.year"),
                        pd.getInt("deathban.month"),
                        pd.getInt("deathban.day"),
                        pd.getInt("deathban.hour"),
                        pd.getInt("deathban.minute"),
                        pd.getInt("deathban.second"),
                        0,
                        ZoneId.systemDefault()
                );
                Duration duration = Duration.between(time, deathbanExpiration);

                if (time.isBefore(deathbanExpiration)) {
                    if (data.getPlayerData(player).getInt("lives") < 0) {
                        long hours = duration.minusDays(duration.toHours()).toHours();
                        long minutes = duration.minusHours(duration.toHours()).toMinutes();
                        long seconds = duration.minusMinutes(duration.toMinutes()).getSeconds();
                        event.setResult(PlayerPreLoginEvent.Result.KICK_OTHER);
                        event.setKickMessage(color(config.getMessages().getString("DEATHBAN_DENY")
                                .replace("%hours%", String.valueOf(hours))
                                .replace("%minutes%", String.valueOf(minutes))
                                .replace("%seconds%", String.valueOf(seconds)
                                        .replace("%player%", player.getName()))));
                    } else {
                        if (joinAgain.get(player)) {
                            event.setResult(PlayerPreLoginEvent.Result.ALLOWED);

                            data.setKey(player, "lives", data.getPlayerData(player).getInt("lives") - 1);
                        } else {
                            long hours = duration.minusDays(duration.toHours()).toHours();
                            long minutes = duration.minusHours(duration.toHours()).toMinutes();
                            long seconds = duration.minusMinutes(duration.toMinutes()).getSeconds();
                            event.setResult(PlayerPreLoginEvent.Result.KICK_OTHER);
                            event.setKickMessage(color(config.getMessages().getString("DEATHBAN_DENY_LIVES")
                                    .replace("%hours%", String.valueOf(hours))
                                    .replace("%minutes%", String.valueOf(minutes))
                                    .replace("%seconds%", String.valueOf(seconds)
                                            .replace("%lives%", String.valueOf(data.getPlayerData(player).getInt("lives")))
                                            .replace("%player%", player.getName()))));
                        }
                    }
                } else {
                    pd.set("deathban.state", false);
                }

            }
        }
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

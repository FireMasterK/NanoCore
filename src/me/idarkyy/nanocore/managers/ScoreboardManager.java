package me.idarkyy.nanocore.managers;

import me.idarkyy.nanocore.utils.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ScoreboardManager {
    private static ScoreboardManager manager;
    public static void setManager(ScoreboardManager manager) {
        ScoreboardManager.manager = manager;
    }
    public static ScoreboardManager getManager() {
        return manager;
    }

    ConfigurationManager config = ConfigurationManager.getManager();

    public void update(Player player) {
        if(Sidebar.hasScore(player)) {
            String line = config.getScoreboard().getString("LINE");

            Sidebar scoreboard = Sidebar.getByPlayer(player);

            ArrayList<String> entries = new ArrayList<>();

            entries.add(config.getScoreboard().getString("LINE"));

            if(player.hasPermission(config.getPermissions().getString("STAFF_PERMISSION"))) {
                entries.add(line);
                entries.add(config.getScoreboard().getString("ENTRIES.STAFF.STAFF"));
                entries.add(config.getScoreboard().getString("ENTRIES.STAFF.VANISHED"));
                entries.add(config.getScoreboard().getString("ENTRIES.STAFF.CHAT_STATE"));
                entries.add(config.getScoreboard().getString("ENTRIES.STAFF.ONLINE") + getOnline());
                if(entries.get(entries.size()).equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', line)));
            }

        }
    }

    private String getOnline() {
        return config.getScoreboard().getString("COLORS.ONLINE.COLOR") + String.valueOf(Bukkit.getServer().getOnlinePlayers().size());
    }

    private String vanishState(Player player) {
        if(CoreManager.getManager().isVanished(player)) {
            return config.getScoreboard().getString("COLORS.VANISHED.TRUE");
        }

        return config.getScoreboard().getString("COLORS.VANISHED.TRUE");
    }
}

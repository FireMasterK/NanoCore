package me.idarkyy.nanocore.managers;

import me.idarkyy.nanocore.NanoCore;
import me.idarkyy.nanocore.utils.Sidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
                entries.add(config.getScoreboard().getString("ENTRIES.STAFF.ONLINE") + config.getScoreboard().getString("COLORS.ONLINE.COLOR") + getOnline());
            }
            
            for (int i = 0; i < entries.size(); i++) {
				scoreboard.setSlot(i, entries.get(i));
			}
        } else { // Server Has been reloaded
        	new BukkitRunnable() {
				@Override
				public void run() {
					Sidebar.createScore(player);
					Sidebar.getByPlayer(player).setTitle(config.getScoreboard().getString("TITLE"));
				}
			}.runTask(NanoCore.getInstance());
        }
    }

    private int getOnline() {
        return Bukkit.getServer().getOnlinePlayers().length;
    }

    private String vanishState(Player player) {
        if(CoreManager.getManager().isVanished(player)) {
            return config.getScoreboard().getString("COLORS.VANISHED.TRUE");
        }

        return config.getScoreboard().getString("COLORS.VANISHED.TRUE");
    }
}

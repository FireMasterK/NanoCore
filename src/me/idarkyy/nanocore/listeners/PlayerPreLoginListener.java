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
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import java.util.HashMap;
import java.util.UUID;

public class PlayerPreLoginListener implements Listener {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();
    DeathbanManager dbManager = DeathbanManager.getManager();

    HashMap<UUID, Long> joinAgain = new HashMap<>();

    @EventHandler
    public void execute(AsyncPlayerPreLoginEvent event) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(event.getUniqueId());
        YamlConfiguration pd = data.getPlayerData(player);

        if (pd.get("lives") == null) {
            data.setKey(player, "lives", 0);
        }

        if (pd.get("deathban.state") == null) {
            data.setKey(player, "deathban.state", false);
            data.setKey(player, "deathban.time", null);
        } else if (pd.getBoolean("deathban.state")) {
        	
        	long diff = pd.getLong("deathban.time") - System.currentTimeMillis();
        	long hours = (((diff / 1000) / 60) / 60) % 24;
        	long minutes = ((diff / 1000) / 60) % 60;
        	long seconds = (diff / 1000) % 60;
        	
            if (pd.getBoolean("deathban.state")) {
                if(System.currentTimeMillis() - pd.getLong("deathban.time") > 0) {
                	event.setLoginResult(Result.ALLOWED);
                	pd.set("deathban.state", false);
                } else if (pd.getInt("lives") > 0) {
                	if(joinAgain.containsKey(player.getUniqueId())) {
                		if (System.currentTimeMillis() - joinAgain.get(player.getUniqueId()) <= 5000) {
                			event.setLoginResult(Result.ALLOWED);
                			pd.set("lives", pd.getInt("lives") - 1);
                			pd.set("deathban.state", false);
                		}
                	} else {
            			event.setLoginResult(Result.KICK_OTHER);
                    	event.setKickMessage(color(config.getMessages().getString("DEATHBAN_DENY_LIVES")
                                .replace("%hours%", String.valueOf(hours))
                                .replace("%minutes%", String.valueOf(minutes))
                                .replace("%seconds%", String.valueOf(seconds)
                                .replace("%player%", player.getName()
                                .replace("%lives%", String.valueOf(pd.getInt("lives")))))));
                    	joinAgain.put(player.getUniqueId(), System.currentTimeMillis());
            		}
                } else {
                	event.setLoginResult(Result.KICK_OTHER);
                	event.setKickMessage(color(config.getMessages().getString("DEATHBAN_DENY")
                            .replace("%hours%", String.valueOf(hours))
                            .replace("%minutes%", String.valueOf(minutes))
                            .replace("%seconds%", String.valueOf(seconds)
                            .replace("%player%", player.getName()))));
                }
            }
        }
    }

    private String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

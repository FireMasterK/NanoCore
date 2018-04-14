package me.idarkyy.nanocore.managers;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PrivateMessageManager {
    private static PrivateMessageManager manager;
    HashMap<Player, Player> replies = new HashMap<>();
    private ConfigurationManager config = ConfigurationManager.getManager();

    private HashMap<Player, Boolean> sounds = new HashMap<>();
    private HashMap<Player, Boolean> toggledPms = new HashMap<>();

    public static PrivateMessageManager getManager() {
        return manager;
    }

    public static void setManager(PrivateMessageManager manager) {
        PrivateMessageManager.manager = manager;
    }

    public void privateMessage(Player from, Player to, String message) {
        replies.put(from, to);
        replies.put(to, from);

        String toMessage = ChatColor.translateAlternateColorCodes('&', config.getLanguage().getString("PRIVATE_MESSAGE_TO"));
        String fromMessage = ChatColor.translateAlternateColorCodes('&', config.getLanguage().getString("PRIVATE_MESSAGE_FROM"));

        to.sendMessage(fromMessage
                .replace("%player%", from.getName())
                .replace("%message%", message));

        from.sendMessage(toMessage
                .replace("%player%", to.getName())
                .replace("%message%", message));

        if (getSounds(to)) {
            to.playSound(to.getLocation(), Sound.NOTE_PLING, 1, 1);
        }
    }

    public Player getLastReply(Player player) {
        return replies.get(player);
    }

    public void setSounds(Player player, boolean state) {
        sounds.put(player, state);
    }

    public boolean getSounds(Player player) {
        return sounds.containsKey(player) ? sounds.get(player) : true;
    }

    public boolean getPmState(Player player) {
        return true;
    }
}

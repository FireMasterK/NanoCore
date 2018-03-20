package me.idarkyy.nanocore.managers;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.time.ZonedDateTime;

@SuppressWarnings("all")
public class DeathbanManager {
    private static DeathbanManager manager;
    DataManager data = DataManager.getManager();

    public static DeathbanManager getManager() {
        return manager;
    }

    public static void setManager(DeathbanManager manager) {
        DeathbanManager.manager = manager;
    }

    public boolean isDeathbanned(Player player) {
        DataManager data = DataManager.getManager();
        if (data.getPlayerData(player).getBoolean("deathban.state")) {
            return true;
        }

        return false;
    }

    @SuppressWarnings("all")
    public void deathBan(Player player, long timeInMinutes) {
        DataManager data = DataManager.getManager();
        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', ConfigurationManager.getManager().getMessages().getString("DEATHBAN.ACTION")));

        data.setKey(player, "deathban.state", true);
        ZonedDateTime until = ZonedDateTime.now().plusMinutes(timeInMinutes);
        data.setKey(player, "deathban.year", until.getYear());
        data.setKey(player, "deathban.month", until.getMonthValue());
        data.setKey(player, "deathban.day", until.getDayOfMonth());
        data.setKey(player, "deathban.hour", until.getHour());
        data.setKey(player, "deathban.minute", until.getMinute());
        data.setKey(player, "deathban.second", until.getSecond());
    }

    public void setDeathbanState(Player player, boolean state) {
        DataManager.getManager().setKey(player, "deathban.state", state);
    }

    public void setDeathbanState(OfflinePlayer player, boolean state) {
        DataManager.getManager().setKey(player, "deathban.state", state);
    }

    public void clearDeathban(Player player) {
        DataManager data = DataManager.getManager();
        data.setKey(player, "deathban.state", false);
        ZonedDateTime until = ZonedDateTime.now();
        data.setKey(player, "deathban.year", (until.getYear() - 1));
        data.setKey(player, "deathban.month", (until.getMonthValue() - 1));
        data.setKey(player, "deathban.day", (until.getDayOfMonth() - 1));
        data.setKey(player, "deathban.hour", (until.getHour() - 1));
        data.setKey(player, "deathban.minute", (until.getMinute() - 1));
        data.setKey(player, "deathban.second", (until.getSecond() - 1));
    }

    public void clearDeathban(OfflinePlayer player) {
        DataManager data = DataManager.getManager();
        data.setKey(player, "deathban.state", false);
        ZonedDateTime until = ZonedDateTime.now();
        data.setKey(player, "deathban.year", (until.getYear() - 1));
        data.setKey(player, "deathban.month", (until.getMonthValue() - 1));
        data.setKey(player, "deathban.day", (until.getDayOfMonth() - 1));
        data.setKey(player, "deathban.hour", (until.getHour() - 1));
        data.setKey(player, "deathban.minute", (until.getMinute() - 1));
        data.setKey(player, "deathban.second", (until.getSecond() - 1));
    }


    public int getLives(Player player) {
        return DataManager.getManager().getPlayerData(player).getInt("lives");
    }

    public void setLives(Player player, int amount) {
        data.setKey(player, "lives", amount);
    }

    public void addLives(Player player, int amount) {
        setLives(player, (data.getPlayerData(player).getInt("lives") + amount));
    }

    public void removeLives(Player player, int amount) {
        setLives(player, (data.getPlayerData(player).getInt("lives") - amount));
    }

}

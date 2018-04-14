package me.idarkyy.nanocore.constructors;

import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.DataManager;
import me.idarkyy.nanocore.managers.DeathbanManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.time.ZonedDateTime;

public class NanoPlayer {
    protected ConfigurationManager config = ConfigurationManager.getManager();
    protected DataManager data = DataManager.getManager();

    private Player player;

    public NanoPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void saveData() {
        // TODO
    }

    public void setDeathbanState(boolean b) {
        data.getPlayerData(player).set("deathban.state", b);
    }

    public void clearDeathban() {
        DeathbanManager.getManager().clearDeathban(player);
    }

    public void deathban(int timeInMinutes) {
        if (!player.hasPermission(config.getPermissions().getString("DEATHBAN.BYPASS")) && !player.isOp()) {
            data.setKey(player, "deathban.state", true);
            ZonedDateTime until = ZonedDateTime.now().plusMinutes(timeInMinutes); //config.getConfig().getLong("deathban-time")
            data.setKey(player, "deathban.year", until.getYear());
            data.setKey(player, "deathban.month", until.getMonthValue());
            data.setKey(player, "deathban.day", until.getDayOfMonth());
            data.setKey(player, "deathban.hour", until.getHour());
            data.setKey(player, "deathban.minute", until.getMinute());
            data.setKey(player, "deathban.second", until.getSecond());

            player.kickPlayer(
                    ChatColor.translateAlternateColorCodes('&', config.getMessages().getString("DEATHBAN_KICKED"))
            );
        }
    }

    public void deathban() {
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
                    ChatColor.translateAlternateColorCodes('&', config.getMessages().getString("DEATHBAN_KICKED"))
            );
        }
    }

    public InventoryContainer getInventory() {
        return new InventoryContainer(player.getInventory());
    }

    public void setInventory(InventoryContainer container) {
        player.getInventory().setContents(container.getInventory());
        player.getInventory().setArmorContents(container.getArmor());
    }
}

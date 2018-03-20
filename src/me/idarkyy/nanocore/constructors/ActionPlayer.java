package me.idarkyy.nanocore.constructors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionPlayer {
    private CommandSender player;

    public ActionPlayer(CommandSender player) {
        this.player = player;
    }

    public static ActionPlayer of(CommandSender commandSender) {
        return new ActionPlayer(commandSender);
    }

    public void sendMessage(String... s) {
        for (String string : s) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
    }

    public void sendMessage(List<String> s) {
        for (String string : s) {
            string = string.replace("%player%", player.getName());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
    }

    public void sendLogMessage(String... s) {
        for (String string : s) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&3NanoCore&8) &7" + string));
        }
    }

    public void sendUncoloredMessage(String... s) {
        for (String string : s) {
            player.sendMessage(string);
        }
    }

    public boolean isConsole() {
        if (player instanceof ConsoleCommandSender) {
            return true;
        }

        return false;
    }

    public boolean isPlayer() {
        if (player instanceof Player) {
            return true;
        }

        return false;
    }
}

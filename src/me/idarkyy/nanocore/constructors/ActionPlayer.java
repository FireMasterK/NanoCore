package me.idarkyy.nanocore.constructors;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ActionPlayer {
    private CommandSender sender;

    public ActionPlayer(CommandSender player) {
        this.sender = player;
    }

    public static ActionPlayer of(CommandSender commandSender) {
        return new ActionPlayer(commandSender);
    }

    public void sendMessage(String... s) {
        for (String string : s) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
    }

    public void sendMessage(List<String> s) {
        for (String string : s) {
            string = string.replace("%player%", sender.getName());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
    }

    public void sendLogMessage(String... s) {
        for (String string : s) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8(&3NanoCore&8) &7" + string));
        }
    }

    public void sendUncoloredMessage(String... s) {
        for (String string : s) {
            sender.sendMessage(string);
        }
    }

    public boolean isConsole() {
        if (sender instanceof ConsoleCommandSender) {
            return true;
        }

        return false;
    }

    public boolean isPlayer() {
        if (sender instanceof Player) {
            return true;
        }

        return false;
    }
    
    public Player getPlayer () throws IllegalAccessException {
    	if (sender instanceof Player)
    		return (Player) sender;
    	else
    		throw new IllegalAccessException("called getPlayer() when sender is not a Player");
    }
}

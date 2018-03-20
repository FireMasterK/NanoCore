package me.idarkyy.nanocore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        // TODO - REMOVE THIS COMMAND

        if (commandSender instanceof Player) {
            ((Player) commandSender).setHealth(0);
        }
        return false;
    }
}

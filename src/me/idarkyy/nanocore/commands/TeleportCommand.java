package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.DataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        ActionPlayer ap = new ActionPlayer(commandSender);

        if (commandSender instanceof Player) {

        }
        return false;
    }
}

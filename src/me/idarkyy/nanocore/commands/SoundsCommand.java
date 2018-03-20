package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.PrivateMessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoundsCommand implements CommandExecutor {
    PrivateMessageManager privateMessageManager = PrivateMessageManager.getManager();
    ConfigurationManager config = ConfigurationManager.getManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ActionPlayer ap = new ActionPlayer(commandSender);
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (privateMessageManager.getSounds(player)) {
                privateMessageManager.setSounds(player, false);
                ap.sendMessage(config.getLanguage().getString("SOUNDS_DISABLED"));
            } else {
                privateMessageManager.setSounds(player, true);
                ap.sendMessage(config.getLanguage().getString("SOUNDS_ENABLED"));
            }
        }
        return false;
    }
}

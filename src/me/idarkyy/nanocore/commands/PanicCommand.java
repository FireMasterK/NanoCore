package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.CoreManager;
import me.idarkyy.nanocore.managers.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PanicCommand implements CommandExecutor {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();
    CoreManager core = CoreManager.getManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        ActionPlayer ap = new ActionPlayer(commandSender);

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (player.hasPermission(config.getPermissions().getString("COMMANDS.PANIC"))) {
                if (core.getPanicMode(player)) {
                    core.setPanicMode(player, false);
                    ap.sendMessage(config.getMessages().getString("PANIC.DISABLED")
                            .replace("%player%", player.getName()));

                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        if (p.hasPermission(config.getPermissions().getString("PANIC_NOTIFICATION"))) {
                            new ActionPlayer(p).sendMessage(config.getMessages().getStringList("PANIC.NOTIFICATION_DISABLED"));
                        }
                    }
                } else {
                    core.setPanicMode(player, true);
                    ap.sendMessage(config.getMessages().getString("PANIC.ENABLED")
                            .replace("%player%", player.getName()));

                    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                        if (p.hasPermission(config.getPermissions().getString("PANIC_NOTIFICATION"))) {
                            new ActionPlayer(p).sendMessage(config.getMessages().getStringList("PANIC.NOTIFICATION_ENABLED"));
                        }
                    }
                }
            } else {
                ap.sendMessage(config.getLanguage().getString("NO_PERMISSION"));
            }
        }
        return false;
    }
}

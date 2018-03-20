package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.PrivateMessageManager;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {
    ConfigurationManager config = ConfigurationManager.getManager();
    PrivateMessageManager pmManager = PrivateMessageManager.getManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ActionPlayer ap = new ActionPlayer(player);

            if (args.length == 0 || args.length == 1) {
                ap.sendMessage(config.getLanguage().getString("PRIVATE_MESSAGE_CORRECT_USAGE")
                        .replace("%command%", command.getName())
                        .replace("%player%", player.getName()));
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null) {
                    pmManager.privateMessage(player, target, StringUtils.join(args, " ", 1, args.length));
                } else {
                    ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                            .replace("%player%", args[0]));
                }
            }
        }
        return false;
    }
}

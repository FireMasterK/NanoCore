package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.PrivateMessageManager;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {
    ConfigurationManager config = ConfigurationManager.getManager();
    PrivateMessageManager pmManager = PrivateMessageManager.getManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ActionPlayer ap = new ActionPlayer(player);

            if (args.length == 0) {
                ap.sendMessage(config.getLanguage().getString("PRIVATE_MESSAGE_REPLY_CORRECT_USAGE")
                        .replace("%command%", command.getName())
                        .replace("%player%", player.getName()));
            } else {
                if (pmManager.getLastReply(player) != null) {
                    Player target = pmManager.getLastReply(player);
                    if (((OfflinePlayer) target).isOnline()) {
                        pmManager.privateMessage(player, target, StringUtils.join(args, " ", 0, args.length));
                    } else {
                        ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                .replace("%player%", ((OfflinePlayer) target).getName()));
                    }
                } else {
                    ap.sendMessage(config.getLanguage().getString("PRIVATE_MESSAGE_REPLY_DID_NOT_MESSAGE"
                            .replace("%player%", player.getName())));
                }
            }
        }
        return false;
    }
}

package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.CoreManager;
import me.idarkyy.nanocore.managers.DataManager;
import net.minecraft.util.org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();
    CoreManager core = CoreManager.getManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        ActionPlayer ap = ActionPlayer.of(commandSender);

        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(player.hasPermission(config.getPermissions().getString("COMMANDS.STAFFCHAT"))) {
                if(args.length == 0) {
                     if(core.getChatState(player).equals(CoreManager.ChatState.GLOBAL)) {
                         core.setChatState(player, CoreManager.ChatState.STAFFCHAT);
                         ap.sendMessage(config.getMessages().getString("CHAT.STAFF.ENABLED"));
                     } else {
                         core.setChatState(player, CoreManager.ChatState.GLOBAL);
                         ap.sendMessage(config.getMessages().getString("CHAT.STAFF.DISABLED"));
                     }
                } else {

                    for(Player target : Bukkit.getServer().getOnlinePlayers()) {
                        if (target.hasPermission(config.getPermissions().getString("COMMANDS.STAFFCHAT"))) {
                            ActionPlayer.of(target).sendMessage(config.getMessages().getString("CHAT.STAFF_CHAT")
                                    .replace("%player%", player.getName())
                                    .replace("%message%", StringUtils.join(args, " ")));
                        }
                    }
                }
            }
        }
        return false;
    }
}

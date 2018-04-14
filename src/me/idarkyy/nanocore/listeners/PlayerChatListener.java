package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.CoreManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {
    CoreManager core = CoreManager.getManager();
    ConfigurationManager config =  ConfigurationManager.getManager();

    public void execute(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ActionPlayer ap = ActionPlayer.of(player);

        if(core.getChatState(player).equals(CoreManager.ChatState.STAFFCHAT)) {
            event.setCancelled(true);

            if(!player.hasPermission(config.getPermissions().getString("COMMANDS.STAFFCHAT"))) {
                core.setChatState(player, CoreManager.ChatState.GLOBAL);

                return;
            }

            for(Player target : Bukkit.getServer().getOnlinePlayers()) {
                if (target.hasPermission(config.getPermissions().getString("COMMANDS.STAFFCHAT"))) {
                    ActionPlayer.of(target).sendMessage(config.getMessages().getString("CHAT.STAFF_CHAT")
                            .replace("%player%", player.getName())
                            .replace("%message%", event.getMessage()));
                }
            }
        }
    }
}

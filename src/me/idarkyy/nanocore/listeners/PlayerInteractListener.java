package me.idarkyy.nanocore.listeners;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.CooldownManager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;

public class PlayerInteractListener implements Listener {
    CooldownManager cooldownManager = CooldownManager.getManager();
    ConfigurationManager config = ConfigurationManager.getManager();

    @EventHandler
    public void execute(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ActionPlayer ap = ActionPlayer.of(player);

        Action action = event.getAction();

        /*
         * Enderpearl Cooldown
         */

        if (action == Action.LEFT_CLICK_AIR
                || event.getAction() == Action.LEFT_CLICK_BLOCK
                || event.getItem() == null
                || event.getItem().getType() != Material.ENDER_PEARL) {
            return;
        }
        
        if(cooldownManager.hasCooldown(player, CooldownManager.CooldownType.ENDERPEARL)) {
            event.setCancelled(true);
            ap.sendMessage(config.getMessages().getString("ENDERPEARL_COOLDOWN.CANCEL_MESSAGE")
                    .replace("%time%", cooldownManager.getCooldown(player, CooldownManager.CooldownType.ENDERPEARL)));
        } else {
            cooldownManager.putCooldown(player, CooldownManager.CooldownType.ENDERPEARL, System.currentTimeMillis());
        }
    }
}

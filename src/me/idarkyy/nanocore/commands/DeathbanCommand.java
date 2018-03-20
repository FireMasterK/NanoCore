package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.DataManager;
import me.idarkyy.nanocore.managers.DeathbanManager;
import me.idarkyy.nanocore.utils.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeathbanCommand implements CommandExecutor {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        ActionPlayer ap = new ActionPlayer(commandSender);
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.hasPermission(config.getPermissions().getString("COMMANDS.DEATHBAN"))) {
                if (args.length == 0) {
                    ap.sendMessage(config.getMessages().getStringList("DEATHBAN.HELP"));
                } else {
                    if (args[0].equalsIgnoreCase("revive")) {
                        if (args.length >= 2) {
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                            if (data.getPlayerData(target).getBoolean("deathban.state")) {
                                DeathbanManager.getManager().clearDeathban(target);
                                ap.sendMessage(config.getMessages().getString("DEATHBAN.SUBCOMMAND.REVIVE_REVIVED")
                                        .replace("%player%", target.getName()));
                            } else {
                                ap.sendMessage(config.getMessages().getString("DEATHBAN.SUBCOMMAND.REVIVE_NOT_DEATHBANNED")
                                        .replace("%player%", target.getName()));
                            }
                        } else {
                            ap.sendMessage(config.getMessages().getString("DEATHBAN.SUBCOMMAND.REVIVE_CORRECT_USAGE"));
                        }
                    } else if (args[0].equalsIgnoreCase("rollback")) {
                        if (args.length >= 2) {
                            Player target = Bukkit.getPlayer(args[1]);

                            if (target != null) {
                                if (data.getPlayerData(target).getString("LASTDEATH.ARMOR") != null
                                        && data.getPlayerData(target).getString("LASTDEATH.INVENTORY") != null) {
                                    try {
                                        target.getInventory().setContents(InventoryUtils.itemStackArrayFromBase64(data.getPlayerData(target).getString("LASTDEATH.INVETNORY")));
                                        target.getInventory().setArmorContents(InventoryUtils.itemStackArrayFromBase64(data.getPlayerData(target).getString("LASTDEATH.ARMOR")));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        ap.sendMessage("&cAn error occured, please check the console.");
                                        return false;
                                    }
                                    ap.sendMessage(config.getMessages().getString("DEATHBAN.SUBCOMMAND.ROLLBACK_SUCCESSFUL")
                                            .replace("%player%", target.getName()));
                                    new ActionPlayer(target).sendMessage(config.getMessages().getString("DEATHBAN.SUBCOMMAND.ROLLBACK_SUCCESSFUL_TARGET")
                                            .replace("%player%", player.getName()));
                                    data.setKey(target, "LASTDEATH.ARMOR", null);
                                    data.setKey(target, "LASTDEATH.INVENTORY", null);
                                } else {
                                    ap.sendMessage(config.getMessages().getString("DEATHBAN.SUBCOMMAND.ROLLBACK_NO_INVENTORY")
                                            .replace("%player%", target.getName()));
                                }
                            } else {
                                ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                        .replace("%player%", args[1]));
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}

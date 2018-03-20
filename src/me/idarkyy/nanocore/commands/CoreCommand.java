package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.NanoCore;
import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoreCommand implements CommandExecutor {
    //Updater updater = new Updater("https://pastebin.com/w8aj2tnH", new NanoCore());
    ConfigurationManager config = ConfigurationManager.getManager();

    @Override
    @SuppressWarnings("all")
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        ActionPlayer ap = new ActionPlayer(commandSender);
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!commandSender.hasPermission(config.getPermissions().getString("COMMANDS.CORE", "nanocore.command.core")) || player.isOp()) {
                if (args.length == 0) {
                    ap.sendMessage(
                            "&8&m-----------------------------------------",
                            "&3&lNano Core:",
                            "  &7/&3" + command.getName() + " reload &7Reload the config, language and permissions",
                            "  &7/&3" + command.getName() + " version &7Get the plugin version",
                            "  &7/&3" + command.getName() + " gc &7Force run the garbage collector",
                            "&8&m-----------------------------------------"
                    );
                } else {
                    if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                        long now = System.currentTimeMillis();
                        config.initialize();
                        ap.sendMessage("&8(&3NanoCore&8) &7Reloading all configurations...");
                        config.reloadAll();
                        ap.sendMessage("&8(&3NanoCore&8) &7Reloaded! &8(&7Took &3" + (System.currentTimeMillis() - now) + "ms&8)");
                    } else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")) {
                        ap.sendMessage("&8(&3NanoCore&8) &7Current version: &3" + NanoCore.getInstance().getDescription().getVersion());
                    } else if (args[0].equalsIgnoreCase("gc") || args[0].equalsIgnoreCase("garbagecleaner")) {
                        long now = System.currentTimeMillis();
                        ap.sendMessage("&8(&3NanoCore&8) &7Force running the garbage collector...");
                        System.gc();
                        ap.sendMessage("&8(&3NanoCore&8) &7Done! &8(&7Took &3" + (System.currentTimeMillis() - now) + "ms&8)");
                    } else {
                        ap.sendMessage("&8(&3NanoCore&8) &7Unknown sub-command &3" + args[0] + "&7!");
                    }
                }
            } else {
                ap.sendMessage(config.getLanguage().getString("NO_PERMISSION", "&cYou do not have permission."));
            }
        } else {
            if (args.length == 0) {
                ap.sendMessage(
                        "&8&m-----------------------------------------",
                        "&3&lNano Core:",
                        "  &7/&3" + command.getName() + " reload &7Reload the config, language and permissions",
                        "  &7/&3" + command.getName() + " version &7Get the plugin version",
                        "  &7/&3" + command.getName() + " gc &7Force run the garbage collector",
                        "&8&m-----------------------------------------"
                );
            } else {
                if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                    long now = System.currentTimeMillis();
                    ap.sendMessage("&8(&3NanoCore&8) &7Reloading all configurations...");
                    config.reloadAll();
                    ap.sendMessage("&8(&3NanoCore&8) &7Reloaded! &8(&7Took &3" + (System.currentTimeMillis() - now) + "ms&8)");
                } else if (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")) {
                    ap.sendMessage("&8(&3NanoCore&8) &7Current version: &3" + NanoCore.getInstance().getDescription().getVersion());
                } else if (args[0].equalsIgnoreCase("gc") || args[0].equalsIgnoreCase("garbagecleaner")) {
                    long now = System.currentTimeMillis();
                    ap.sendMessage("&8(&3NanoCore&8) &7Force running the garbage collector...");
                    System.gc();
                    ap.sendMessage("&8(&3NanoCore&8) &7Done! &8(&7Took &3" + (System.currentTimeMillis() - now) + "ms&8)");
                } else {
                    ap.sendMessage("&8(&3NanoCore&8) &7Unknown sub-command &3" + args[0] + "&7!");
                }
            }
        }
        return false;
    }
}

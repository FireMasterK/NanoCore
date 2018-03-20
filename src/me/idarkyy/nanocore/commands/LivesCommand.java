package me.idarkyy.nanocore.commands;

import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.DataManager;
import me.idarkyy.nanocore.managers.DeathbanManager;
import me.idarkyy.nanocore.utils.ParseUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LivesCommand implements CommandExecutor {
    ConfigurationManager config = ConfigurationManager.getManager();
    DataManager data = DataManager.getManager();

    @Override
    @SuppressWarnings("all")
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        ActionPlayer ap = new ActionPlayer(commandSender);

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if (args.length == 0) {
                ap.sendMessage(config.getMessages().getStringList("LIVES.HELP"));
            } else {
                if (args[0].equalsIgnoreCase("check")) {
                    if (args.length <= 1) {
                        ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.CHECK")
                                .replace("%lives%", String.valueOf(data.getPlayerData(player).getInt("lives"))));
                    } else {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target != null) {
                            ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.CHECK_OTHER")
                                    .replace("%player%", target.getName())
                                    .replace("%lives%", String.valueOf(data.getPlayerData(target).getInt("lives"))));
                        } else {
                            ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                    .replace("%player%", args[1]));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("send")) {
                    if (args.length >= 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        Integer amount = ParseUtils.parseInteger(args[2]);

                        if (target != null) {
                            if (amount != null) {
                                if (data.getPlayerData(player).getInt("lives") >= amount) {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SEND_SUCCESSFUL"));
                                    new ActionPlayer(target).sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SEND_RECEIVED")
                                            .replace("%lives%", String.valueOf(amount)));
                                    DeathbanManager.getManager().removeLives(player, amount);
                                    DeathbanManager.getManager().addLives(target, amount);
                                } else {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_ENOUGH"));
                                }
                            } else {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_A_NUMBER")
                                        .replace("%arg%", args[2]));
                            }
                        } else {
                            ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                    .replace("%player%", args[1]));
                        }
                    } else {
                        ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SEND_CORRECT_USAGE"));
                    }
                } else if (args[0].equalsIgnoreCase("revive")) {
                    if (args.length >= 2) {
                        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                        if (data.getPlayerData(target).getBoolean("deathban.state")) {
                            if (data.getPlayerData(player).getInt("lives") >= 1) {
                                DeathbanManager.getManager().removeLives(player, 1);
                                DeathbanManager.getManager().clearDeathban(target);
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REVIVE_REVIVED")
                                        .replace("%player%", target.getName()));
                            } else {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_ENOUGH"));
                            }
                        } else {
                            ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REVIVE_NOT_DEATHBANNED")
                                    .replace("%player%", target.getName()));
                        }
                    }
                } else if (args[0].equalsIgnoreCase("set")) {
                    if (player.hasPermission(config.getPermissions().getString("LIVES_SET"))) {
                        if (args.length >= 3) {
                            Player target = Bukkit.getPlayer(args[1]);
                            Integer amount = ParseUtils.parseInteger(args[2]);

                            if (target != null) {
                                if (amount != null) {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SET_SUCCESSFUL")
                                            .replace("%player%", target.getName())
                                            .replace("%lives%", String.valueOf(amount)));
                                    new ActionPlayer(target).sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SET_RECEIVED")
                                            .replace("%lives%", String.valueOf(amount)));
                                    data.setKey(target, "lives", amount);
                                } else {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_A_NUMBER")
                                            .replace("%arg%", args[2]));
                                }
                            } else {
                                ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                        .replace("%player%", args[1]));
                            }
                        } else {
                            ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SET_CORRECT_USAGE"));
                        }
                    } else {
                        ap.sendMessage(config.getLanguage().getString("NO_PERMISSION"));
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (player.hasPermission(config.getPermissions().getString("LIVES_SET"))) {
                        if (args.length >= 3) {
                            Player target = Bukkit.getPlayer(args[1]);
                            Integer amount = ParseUtils.parseInteger(args[2]);

                            if (target != null) {
                                if (amount != null) {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.ADD_SUCCESSFUL")
                                            .replace("%player%", target.getName())
                                            .replace("%lives%", String.valueOf(amount)));
                                    new ActionPlayer(target).sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.ADD_RECEIVED")
                                            .replace("%lives%", String.valueOf(amount)));
                                    DeathbanManager.getManager().addLives(target, amount);
                                } else {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_A_NUMBER")
                                            .replace("%arg%", args[2]));
                                }
                            } else {
                                ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                        .replace("%player%", args[1]));
                            }
                        } else {
                            ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.ADD_CORRECT_USAGE"));
                        }
                    } else {
                        ap.sendMessage(config.getLanguage().getString("NO_PERMISSION"));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (player.hasPermission(config.getPermissions().getString("LIVES_SET"))) {
                        if (args.length >= 3) {
                            Player target = Bukkit.getPlayer(args[1]);
                            Integer amount = ParseUtils.parseInteger(args[2]);

                            if (target != null) {
                                if (amount != null) {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REMOVE_SUCCESSFUL")
                                            .replace("%player%", target.getName())
                                            .replace("%lives%", String.valueOf(amount)));
                                    new ActionPlayer(target).sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REMOVE_RECEIVED")
                                            .replace("%lives%", String.valueOf(amount)));
                                    DeathbanManager.getManager().removeLives(target, amount);
                                } else {
                                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_A_NUMBER")
                                            .replace("%arg%", args[2]));
                                }
                            } else {
                                ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                        .replace("%player%", args[1]));
                            }
                        } else {
                            ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REMOVE_CORRECT_USAGE"));
                        }
                    } else {
                        ap.sendMessage(config.getLanguage().getString("NO_PERMISSION"));
                    }
                } else {
                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.UNKNOWN_SUBCOMMAND"));
                }
            }
        } else {
            if (args.length == 0) {
                ap.sendMessage(config.getMessages().getStringList("LIVES.HELP"));
            } else {
                if (args[0].equalsIgnoreCase("set")) {
                    if (args.length >= 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        Integer amount = ParseUtils.parseInteger(args[2]);

                        if (target != null) {
                            if (amount != null) {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SET_SUCCESSFUL")
                                        .replace("%player%", target.getName())
                                        .replace("%lives%", String.valueOf(amount)));
                                new ActionPlayer(target).sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SET_RECEIVED")
                                        .replace("%lives%", String.valueOf(amount)));
                                data.setKey(target, "lives", amount);
                            } else {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_A_NUMBER")
                                        .replace("%arg%", args[2]));
                            }
                        } else {
                            ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                    .replace("%player%", args[1]));
                        }
                    } else {
                        ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.SET_CORRECT_USAGE"));
                    }
                } else if (args[0].equalsIgnoreCase("add")) {
                    if (args.length >= 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        Integer amount = ParseUtils.parseInteger(args[2]);

                        if (target != null) {
                            if (amount != null) {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.ADD_SUCCESSFUL")
                                        .replace("%player%", target.getName())
                                        .replace("%lives%", String.valueOf(amount)));
                                new ActionPlayer(target).sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.ADD_RECEIVED")
                                        .replace("%lives%", String.valueOf(amount)));
                                DeathbanManager.getManager().addLives(target, amount);
                            } else {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_A_NUMBER")
                                        .replace("%arg%", args[2]));
                            }
                        } else {
                            ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                    .replace("%player%", args[1]));
                        }
                    } else {
                        ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.ADD_CORRECT_USAGE"));
                    }
                } else if (args[0].equalsIgnoreCase("remove")) {
                    if (args.length >= 3) {
                        Player target = Bukkit.getPlayer(args[1]);
                        Integer amount = ParseUtils.parseInteger(args[2]);

                        if (target != null) {
                            if (amount != null) {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REMOVE_SUCCESSFUL")
                                        .replace("%player%", target.getName())
                                        .replace("%lives%", String.valueOf(amount)));
                                new ActionPlayer(target).sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REMOVE_RECEIVED")
                                        .replace("%lives%", String.valueOf(amount)));
                                DeathbanManager.getManager().removeLives(target, amount);
                            } else {
                                ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.NOT_A_NUMBER")
                                        .replace("%arg%", args[2]));
                            }
                        } else {
                            ap.sendMessage(config.getLanguage().getString("NOT_ONLINE")
                                    .replace("%player%", args[1]));
                        }
                    } else {
                        ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.REMOVE_CORRECT_USAGE"));
                    }
                } else {
                    ap.sendMessage(config.getMessages().getString("LIVES.SUBCOMMAND.UNKNOWN_SUBCOMMAND"));
                }
            }
        }
        return false;
    }
}

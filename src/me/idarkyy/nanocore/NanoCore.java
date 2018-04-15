package me.idarkyy.nanocore;

import me.idarkyy.nanocore.commands.*;
import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.listeners.*;
import me.idarkyy.nanocore.managers.*;
import me.idarkyy.nanocore.utils.Updater;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class NanoCore extends JavaPlugin {
    private static NanoCore instance;
    Updater updater = new Updater("https://pastebin.com/raw/w8aj2tnH");
    ActionPlayer console = new ActionPlayer(Bukkit.getConsoleSender());
    private ArrayList<Plugin> pluginHooks = new ArrayList<>();
    private FactionPlugin factionPlugin = FactionPlugin.NONE;

    public static NanoCore getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        
        DeathbanManager.setManager(new DeathbanManager());
        ConfigurationManager.setManager(new ConfigurationManager());
        DataManager.setManager(new DataManager());
        PrivateMessageManager.setManager(new PrivateMessageManager());
        CoreManager.setManager(new CoreManager());
        ScoreboardManager.setManager(new ScoreboardManager());
        FactionsManager.setInstance(new FactionsManager());
        CooldownManager.setManager(new CooldownManager());
        ClassManager.setManager(new ClassManager());

        ConfigurationManager.getManager().initialize();
        DataManager.getManager().initialize();

        registerListener(new PlayerJoinListener());
        registerListener(new PlayerQuitListener());
        registerListener(new PlayerDeathListener());
        registerListener(new PlayerPreLoginListener());
        registerListener(new EntityTargetListener());
        registerListener(new EntityTeleportListener());
        registerListener(new PlayerMoveListener());
        registerListener(new PlayerChatListener());
        registerListener(new StaffModeListener());
        registerListener(new PlayerInteractListener());
        registerListener(new PlayerPickupItemListener());
        registerListener(new PlayerMoveByBlockListener());
//        registerListener(new ClassListener());
        
        registerCommand("core", new CoreCommand());
        registerCommand("deathban", new DeathbanCommand());
        registerCommand("message", new MessageCommand());
        registerCommand("reply", new ReplyCommand());
        registerCommand("suicide", new SuicideCommand());
        registerCommand("lives", new LivesCommand());
        registerCommand("sounds", new SoundsCommand());
        registerCommand("panic", new PanicCommand());
        registerCommand("staffchat", new StaffChatCommand());

        String currentPlugin = "NONE";

        if (Bukkit.getPluginManager().getPlugin("Mango") != null) {
            currentPlugin = "MANGO";
        }

        if (Bukkit.getPluginManager().getPlugin("Blueberry") != null) {
            currentPlugin = "BLUEBERRY";
        }

        factionPlugin = FactionPlugin.valueOf(currentPlugin);

        if (factionPlugin.equals(FactionPlugin.NONE)) {
            console.sendLogMessage("Couldn't find any faction plugin (Blueberry and Mango available at the moment)");
        }


        console.sendMessage(
                "&8&m-------------------------------",
                "&3&lNano Core &7created by &3iDarkyy",
                " &7Version " + getDescription().getVersion(),
                "&8&m-------------------------------"
        );
        
        new BukkitRunnable() {
			@Override
			public void run() {
				if (!updater.getLatestVersion().equals(getDescription().getVersion())) {
		            console.sendMessage(
		                    "&7New version of &3NanoCore &7is available!",
		                    "&7Current version: " + getDescription().getVersion(),
		                    "&7Latest version: " + updater.getLatestVersion(),
		                    "&8&m-------------------------------"
		            );
		        }
			}
		}.runTaskAsynchronously(this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                    ScoreboardManager.getManager().update(player);
                }
            }
        }.runTaskTimerAsynchronously(this,
                0L,
                /* Refresh Rate */ ConfigurationManager.getManager().getScoreboard().getLong("REFRESH_RATE"));
    }

    @Override
    public void onDisable() {

    }

    private void registerListener(Listener listener) {
        try {
            Bukkit.getPluginManager().registerEvents(listener, this);
            console.sendLogMessage("&7Listener &3" + listener.getClass().getSimpleName() + " &7has been registered!");
        } catch (Exception e) {
            e.printStackTrace();
            console.sendLogMessage("&7An error occured while registering listener &3" + listener.getClass().getSimpleName() + "&7!");
        }
    }

    private void registerCommand(String command, CommandExecutor commandExecutor) {
        try {
            getCommand(command).setExecutor(commandExecutor);
            console.sendLogMessage("&7Command &3" + commandExecutor.getClass().getSimpleName() + " &7has been registered!");
        } catch (Exception e) {
            e.printStackTrace();
            console.sendLogMessage("&7An error occured while registering command &3" + commandExecutor.getClass().getSimpleName() + "&7!");
        }
    }

    @Deprecated
    public void hook(Plugin plugin) {
        if (pluginHooks.contains(plugin)) {
            console.sendLogMessage("&3" + plugin.getName() + " &7tried to initialize itself twice!");
        } else {
            pluginHooks.add(plugin);
            console.sendLogMessage("&3" + plugin.getName() + "&7has been initialized successfully");
        }
    }

    @Deprecated
    public ArrayList<Plugin> getHooks() {
        return pluginHooks;
    }

    public FactionPlugin getFactionPlugin() {
        return factionPlugin;
    }

    public enum FactionPlugin {
        BLUEBERRY, MANGO, NONE
    }
}

package me.idarkyy.nanocore.api;

import me.idarkyy.nanocore.NanoCore;
import me.idarkyy.nanocore.constructors.ActionPlayer;
import me.idarkyy.nanocore.managers.ConfigurationManager;
import me.idarkyy.nanocore.managers.DataManager;
import me.idarkyy.nanocore.managers.DeathbanManager;
import me.idarkyy.nanocore.managers.PrivateMessageManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class NanoCoreApi {
    protected ConfigurationManager config = ConfigurationManager.getManager();
    protected DataManager data = DataManager.getManager();
    protected DeathbanManager deathbanManager = DeathbanManager.getManager();
    protected PrivateMessageManager privateMessageManager = PrivateMessageManager.getManager();
    protected ActionPlayer console = new ActionPlayer(Bukkit.getConsoleSender());
    private Plugin plugin;

    public NanoCoreApi(Plugin plugin) {
        this.plugin = plugin;
        console.sendLogMessage("Created an API instance for the plugin &3" + plugin.getName() + "&7!");
    }

    public DeathbanManager getDeathbanManager() {
        return deathbanManager;
    }

    public PrivateMessageManager getPrivateMessageManager() {
        return privateMessageManager;
    }

    public DataManager getData() {
        return data;
    }

    public ConfigurationManager getConfig() {
        return config;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public NanoCore getMainInstance() {
        return NanoCore.getInstance();
    }

    @SuppressWarnings("deprecation")
    public void addHook() {
        getMainInstance().hook(plugin);
    }

    @SuppressWarnings("deprecation")
    public boolean isHooked() {
        if (getMainInstance().getHooks().contains(plugin)) {
            return true;
        }

        return false;
    }
}

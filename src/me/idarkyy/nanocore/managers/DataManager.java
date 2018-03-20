package me.idarkyy.nanocore.managers;

import me.idarkyy.nanocore.NanoCore;
import me.idarkyy.nanocore.constructors.ActionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

@SuppressWarnings("all")
public class DataManager {
    private static DataManager manager;
    ActionPlayer console = new ActionPlayer(Bukkit.getConsoleSender());
    private File dataFolder;

    public static DataManager getManager() {
        return manager;
    }

    public static void setManager(DataManager dataManager) {
        manager = dataManager;
    }

    public void initialize() {
        console.sendLogMessage("&7Initializing data...");
        dataFolder = new File(NanoCore.getInstance().getDataFolder(), "data");
        if (!dataFolder.exists()) {
            console.sendLogMessage("&7Directory &3NanoCore/data &7does not exist, Creating...");
            dataFolder.mkdir();
        }
    }

    public File getDataFolder() {
        return dataFolder;
    }

    public File getPlayerDataFile(Player player) {
        return new File(getDataFolder(), player.getUniqueId() + ".yml");
    }

    public File getPlayerDataFile(OfflinePlayer player) {
        return new File(getDataFolder(), player.getUniqueId() + ".yml");
    }


    public YamlConfiguration getPlayerData(Player player) {
        return YamlConfiguration.loadConfiguration(getPlayerDataFile(player));
    }

    public YamlConfiguration getPlayerData(OfflinePlayer player) {
        return YamlConfiguration.loadConfiguration(getPlayerDataFile(player));
    }


    public void setKey(Player player, String key, Object value) {
        File dataFile = getPlayerDataFile(player);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                console.sendLogMessage("Data file of &3" + player.getName() + " &7does not exist, Creating...");
            } catch (Exception e) {
                e.printStackTrace();
                console.sendLogMessage("&7Unable to create data file for &3" + player.getName() + "&7!");
            }
        }
        try {
            if (key != null) {
                YamlConfiguration data = getPlayerData(player);
                data.set(key, value);
                data.options().copyDefaults(true);
                data.save(getPlayerDataFile(player));
            }
        } catch (Exception e) {
            e.printStackTrace();
            console.sendLogMessage("&7Unable to save data for &3" + player.getName() + "&7! &8(&3KEY: &7" + key + "&7, &3VALUE: &7" + value.toString() + "&8)");
        }
    }

    public void setKey(OfflinePlayer player, String key, Object value) {
        File dataFile = getPlayerDataFile(player);
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
                console.sendLogMessage("Data file of &3" + player.getName() + " &7does not exist, Creating...");
            } catch (Exception e) {
                e.printStackTrace();
                console.sendLogMessage("&7Unable to create data file for &3" + player.getName() + "&7!");
            }
        }
        try {
            if (key != null) {
                YamlConfiguration data = getPlayerData(player);
                data.set(key, value);
                data.options().copyDefaults(true);
                data.save(getPlayerDataFile(player));
            }
        } catch (Exception e) {
            e.printStackTrace();
            console.sendLogMessage("&7Unable to save data for &3" + player.getName() + "&7! &8(&3KEY: &7" + key + "&7, &3VALUE: &7" + value.toString() + "&8)");
        }
    }
}

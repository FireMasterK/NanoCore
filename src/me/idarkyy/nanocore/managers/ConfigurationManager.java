package me.idarkyy.nanocore.managers;

import me.idarkyy.nanocore.NanoCore;
import me.idarkyy.nanocore.constructors.ActionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

@SuppressWarnings("unused")
public class ConfigurationManager {
    private static ConfigurationManager manager;
    ActionPlayer console = new ActionPlayer(Bukkit.getConsoleSender());
    private File configFile, languageFile, messagesFile, permissionsFile, scoreboardFile;
    private YamlConfiguration config, language, messages, permissions, scoreboard;
    private File pluginFolder = NanoCore.getInstance().getDataFolder();

    public static ConfigurationManager getManager() {
        return manager;
    }

    public static void setManager(ConfigurationManager manager) {
        ConfigurationManager.manager = manager;
    }

    public void initialize() {
        console.sendLogMessage("&7Initializing configurations...");

        configFile = new File(pluginFolder, "config.yml");
        languageFile = new File(pluginFolder, "language.yml");
        messagesFile = new File(pluginFolder, "messages.yml");
        permissionsFile = new File(pluginFolder, "permissions.yml");
        scoreboardFile = new File(pluginFolder, "scoreboard.yml");

        if (!configFile.exists()) {
            console.sendLogMessage("&7File &3config.yml &7does not exist, Creating...");
            NanoCore.getInstance().saveResource("config.yml", false);
        }
        if (!languageFile.exists()) {
            console.sendLogMessage("&7File &3language.yml &7does not exist, Creating...");
            NanoCore.getInstance().saveResource("language.yml", false);
        }
        if (!messagesFile.exists()) {
            console.sendLogMessage("&7File &3messages.yml &7does not exist, Creating...");
            NanoCore.getInstance().saveResource("messages.yml", false);
        }
        if (!permissionsFile.exists()) {
            console.sendLogMessage("&7File &3permissions.yml &7does not exist, Creating...");
            NanoCore.getInstance().saveResource("permissions.yml", false);
        }

        if (!scoreboardFile.exists()) {
            console.sendLogMessage("&7File &3scoreboard.yml &7does not exist, Creating...");
            NanoCore.getInstance().saveResource("scoreboard.yml", false);
        }


        config = YamlConfiguration.loadConfiguration(configFile);
        language = YamlConfiguration.loadConfiguration(languageFile);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        permissions = YamlConfiguration.loadConfiguration(permissionsFile);
        scoreboard = YamlConfiguration.loadConfiguration(scoreboardFile);
    }

    public void reloadAll() {
        try {
            config.load(configFile);
            language.load(languageFile);
            permissions.load(permissionsFile);
        } catch (Exception e) {
            e.printStackTrace();
            console.sendLogMessage("An error occurred while loading the configurations!");
        }
    }

    public File getConfigFile() {
        return configFile;
    }

    public File getLanguageFile() {
        return languageFile;
    }

    public File getMessagesFile() {
        return messagesFile;
    }

    public File getPermissionsFile() {
        return permissionsFile;
    }

    public File getScoreboardFile() {
        return scoreboardFile;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public YamlConfiguration getLanguage() {
        return language;
    }

    public YamlConfiguration getMessages() {
        return messages;
    }

    public YamlConfiguration getPermissions() {
        return permissions;
    }

    public YamlConfiguration getScoreboard() {
        return scoreboard;
    }
}

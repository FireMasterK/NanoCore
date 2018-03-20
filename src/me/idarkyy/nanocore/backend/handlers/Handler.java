package me.idarkyy.nanocore.backend.handlers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public interface Handler {

    void init();
    void connect(ConfigurationSection cs);
    void saveStats(UUID u);
    void stopDB();
}

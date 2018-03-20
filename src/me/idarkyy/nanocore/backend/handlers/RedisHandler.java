package me.idarkyy.nanocore.backend.handlers;

import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public class RedisHandler implements Handler {


    // PROBABLY WILL BE REPLACED WITH MYSQL, AND REDIS WOULD BE IN A SEPARATE PACKAGE.

    @Override
    public void init() {
        // initialize - make stuff in redis.
    }

    @Override
    public void connect(ConfigurationSection cs) {
        // connect to redis.
    }

    @Override
    public void saveStats(UUID u) {
        // save stats.
    }

    @Override
    public void stopDB() {
        // stop the db. called in onDisable async probably.
    }
}

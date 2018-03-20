package me.idarkyy.nanocore.utils;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.bukkit.BukkitUtil;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class WorldGuardUtils {
    private static WorldGuardPlugin worldGuardPlugin = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");

    public static boolean isPvPEnabled(Location location) {
        World world = location.getWorld();
        Vector vector = BukkitUtil.toVector(location);
        RegionManager regionManager = worldGuardPlugin.getRegionManager(world);
        ApplicableRegionSet regionSet = regionManager.getApplicableRegions(vector);

        return regionSet.allows(DefaultFlag.PVP) || regionSet.getFlag(DefaultFlag.PVP) == null;
    }
}

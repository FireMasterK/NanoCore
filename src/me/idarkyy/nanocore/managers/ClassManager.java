package me.idarkyy.nanocore.managers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ClassManager {

	private static ClassManager manager;

	public static ClassManager getManager() {
        return manager;
    }

    public static void setManager(ClassManager manager) {
        ClassManager.manager = manager;
    }

    public ClassType getClass(Player player) {
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        if (helmet.getType().equals(Material.GOLD_HELMET)
                && chestplate.getType().equals(Material.GOLD_CHESTPLATE)
                && leggings.getType().equals(Material.GOLD_LEGGINGS)
                && boots.getType().equals(Material.GOLD_BOOTS)) {
            return ClassType.BARD;
        } else if (helmet.getType().equals(Material.DIAMOND_HELMET)
                && chestplate.getType().equals(Material.DIAMOND_CHESTPLATE)
                && leggings.getType().equals(Material.DIAMOND_LEGGINGS)
                && boots.getType().equals(Material.DIAMOND_BOOTS)) {
            return ClassType.DIAMOND;
        } else if (helmet.getType().equals(Material.CHAINMAIL_HELMET)
                && chestplate.getType().equals(Material.CHAINMAIL_CHESTPLATE)
                && leggings.getType().equals(Material.CHAINMAIL_LEGGINGS)
                && boots.getType().equals(Material.CHAINMAIL_BOOTS)) {
            return ClassType.ROGUE;
        } else if (helmet.getType().equals(Material.LEATHER_HELMET)
                && chestplate.getType().equals(Material.LEATHER_CHESTPLATE)
                && leggings.getType().equals(Material.LEATHER_LEGGINGS)
                && boots.getType().equals(Material.LEATHER_BOOTS)) {
            return ClassType.ARCHER;
        } else if (helmet.getType().equals(Material.IRON_HELMET)
                && chestplate.getType().equals(Material.IRON_CHESTPLATE)
                && leggings.getType().equals(Material.IRON_LEGGINGS)
                && boots.getType().equals(Material.IRON_BOOTS)) {
            return ClassType.MINER;
        }

        return ClassType.NONE;
    }

    public enum ClassType {
        DIAMOND, BARD, ROGUE, ARCHER, MINER, NONE
    }
}

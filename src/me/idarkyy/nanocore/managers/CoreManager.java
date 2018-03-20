package me.idarkyy.nanocore.managers;

import me.idarkyy.nanocore.constructors.InventoryContainer;
import me.idarkyy.nanocore.constructors.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;

public class CoreManager {
    private static CoreManager manager;
    private HashMap<Player, Boolean> panicMode = new HashMap<>();
    private HashMap<Player, Boolean> freeze = new HashMap<>();
    private HashMap<Player, InventoryContainer> inventories = new HashMap<>();
    private ArrayList<Player> vanished = new ArrayList<>();
    private ArrayList<Player> staffChat = new ArrayList<>();

    private ArrayList<Player> staffMode = new ArrayList<>();
    private ConfigurationManager config = ConfigurationManager.getManager();
    private DataManager data = DataManager.getManager();

    public static CoreManager getManager() {
        return manager;
    }

    public static void setManager(CoreManager manager) {
        CoreManager.manager = manager;
    }

    public void setPanicMode(Player player, boolean state) {
        panicMode.put(player, state);
    }

    public boolean getPanicMode(Player player) {
        if (panicMode.get(player) != null) {
            if (panicMode.get(player)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public boolean isFrozen(Player player) {
        if (freeze.get(player) != null) {
            if (freeze.get(player)) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public void setFrozen(Player player, boolean state) {
        freeze.put(player, state);
    }

    public void enableStaffMode(Player player) {
        if (!staffMode.contains(player)) {
            staffMode.add(player);
            inventories.put(player, new InventoryContainer(player.getInventory()));

            player.getInventory().clear();

            player.getActivePotionEffects().clear();
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999, 100));

            player.getInventory().setContents(getStaffInventory(player).getContents());
            player.getInventory().setArmorContents(getStaffInventory(player).getArmorContents());

        } else {
            throw new IllegalStateException("Failed to put a player into StaffMode because he is already in it!");
        }
    }

    public void disableStaffMode(Player player) {
        if (staffMode.contains(player)) {
            staffMode.remove(player);

            player.getActivePotionEffects().clear();

            player.getInventory().setContents(inventories.get(player).getInventory());
            player.getInventory().setArmorContents(inventories.get(player).getArmor());
        } else {
            throw new IllegalStateException("Failed to put a player into StaffMode because he is not in it!");
        }
    }

    public void disableAllStaffModes() {

    }

    public PlayerInventory getStaffInventory(Player player) {
        PlayerInventory i = player.getInventory();

        ItemStack air = new ItemStack(Material.AIR);

        int slot = 8;
        while (slot > -1) {
            i.setItem(slot, getItem(slot));
            slot--;
        }

        return i;
    }

    public ItemStack getItem(int slot) {
        ItemStack slot0 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.0.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.0.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.0.LORE")).build();
        ItemStack slot1 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.1.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.1.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.1.LORE")).build();
        ItemStack slot2 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.2.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.2.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.2.LORE")).build();
        ItemStack slot3 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.3.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.3.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.3.LORE")).build();
        ItemStack slot4 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.4.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.4.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.4.LORE")).build();
        ItemStack slot5 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.5.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.5.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.5.LORE")).build();
        ItemStack slot6 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.6.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.6.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.6.LORE")).build();
        ItemStack slot7 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.7.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.7.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.7.LORE")).build();
        ItemStack slot8 = new ItemBuilder(
                Material.valueOf(config.getConfig().getString("STAFF_ITEMS.8.TYPE")),
                config.getConfig().getString("STAFF_ITEMS.8.NAME"))
                .setLore(config.getConfig().getStringList("STAFF_ITEMS.8.LORE")).build();

        switch (slot) {
            default:
                return null;
            case 0:
                return slot0;
            case 1:
                return slot1;
            case 2:
                return slot2;
            case 3:
                return slot3;
            case 4:
                return slot4;
            case 5:
                return slot5;
            case 6:
                return slot6;
            case 7:
                return slot7;
            case 8:
                return slot8;
        }
    }

    public boolean isInStaffMode(Player player) {
        if (staffMode.contains(player)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isVanished(Player player) {
        if(vanished.contains(player)) {
            return true;
        }

        return false;
    }

    public void setVanishState(Player player, boolean state) {
        if(state) {
            if(!vanished.contains(player)) {
                vanished.add(player);
            }
        } else {
            if(vanished.contains(player)) {
                vanished.remove(player);
            }
        }
    }

    public ChatState getChatState(Player player) {
        if(staffChat.contains(player)) {
            return ChatState.STAFFCHAT;
        }

        return ChatState.GLOBAL;
    }

    public void setChatState(Player player, ChatState state) {
        if(state.equals(ChatState.GLOBAL)) {
            if(staffChat.contains(player)) {
                staffChat.remove(player);
            }
        } else if(state.equals(ChatState.STAFFCHAT)) {
            if(!staffChat.contains(player)) {
                staffChat.add(player);
            }
        }
    }


    public enum ChatState {
        GLOBAL, STAFFCHAT
    }

    private String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

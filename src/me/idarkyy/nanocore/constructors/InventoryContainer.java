package me.idarkyy.nanocore.constructors;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class InventoryContainer {
    private ItemStack[] inventory;
    private ItemStack[] armor;

    public InventoryContainer() {
    }

    public InventoryContainer(ItemStack[] inventoryContents, ItemStack[] armorContents) {
        this.inventory = inventoryContents;
        this.armor = armorContents;
    }

    public InventoryContainer(PlayerInventory playerInventory) {
        if (playerInventory.getContents().length > 0) {
            inventory = playerInventory.getContents();
        }
        if (playerInventory.getArmorContents().length > 0) {
            armor = playerInventory.getArmorContents();
        }
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack[] getInventory() {
        return inventory;
    }

    public void setInventory(ItemStack[] inventory) {
        this.inventory = inventory;
    }
}

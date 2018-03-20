package me.idarkyy.nanocore.constructors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {
    private ItemStack itemstack;
    private ItemMeta meta;

    public ItemBuilder(Material material, String name, String... lore) {
        this.itemstack = new ItemStack(material);
        this.meta = itemstack.getItemMeta();

        meta.setDisplayName(c(name));

        List<String> fixedLore = new ArrayList<>();

        for (String s : lore) {
            fixedLore.add(c(s));
        }

        meta.setLore(fixedLore);
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);

        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        meta.removeEnchant(enchantment);

        return this;
    }

    public ItemBuilder setName(String name) {
        meta.setDisplayName(c(name));

        return this;
    }

    public ItemBuilder setLore(String... lore) {
        List<String> fixedLore = new ArrayList<>();

        for (String s : lore) {
            fixedLore.add(c(s));
        }

        meta.setLore(fixedLore);

        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> fixedLore = new ArrayList<>();

        for (String s : lore) {
            fixedLore.add(c(s));
        }

        meta.setLore(fixedLore);

        return this;
    }


    public ItemStack build() {
        ItemStack i = itemstack;
        i.setItemMeta(meta);

        return i;
    }

    protected String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}

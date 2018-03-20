package me.idarkyy.nanocore.crowbar;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Crowbar {
    public static final int MAX_SPAWNER_USES = 1;
    public static final int MAX_END_DRAGON_USES = 1;
    public static final int MAX_END_FRAME_USES = 5;
    public static final Material CROWBAR_TYPE = Material.DIAMOND_HOE;
    private static final String CROWBAR_NAME = ChatColor.GOLD.toString() + "Crowbar";
    private static final String END_DRAGON_USE_TAG = "End Dragon Uses";
    private static final String SPAWNER_USE_TAG = "Spawner Uses";
    private static final String END_FRAME_USE_TAG = "End Frame Uses";
    private static final String LORE_FORMAT = ChatColor.GRAY + "%1$s: " + ChatColor.YELLOW + "%2$s/%3$s";
    private final ItemStack stack = new ItemStack(CROWBAR_TYPE, 1);
    private int endFrameUses;
    private int endDragonUses;
    private int spawnerUses;
    private boolean needsMetaUpdate;

    public Crowbar() {
        this(1, 5, 1);
    }

    public Crowbar(int spawnerUses, int endFrameUses, int endDragonUses) {
        Preconditions.checkArgument((spawnerUses > 0) || (endFrameUses > 0), "Cannot create a crowbar with empty uses");
        setSpawnerUses(Math.min(1, spawnerUses));
        setEndDragonUses(Math.min(1, endDragonUses));
        setEndFrameUses(Math.min(5, endFrameUses));
    }

    public static Optional<Crowbar> fromStack(ItemStack stack) {
        if ((stack == null) || (!stack.hasItemMeta())) {
            return Optional.absent();
        }
        ItemMeta meta = stack.getItemMeta();
        if ((!meta.hasDisplayName()) || (!meta.hasLore()) || (!meta.getDisplayName().equals(CROWBAR_NAME))) {
            return Optional.absent();
        }
        Crowbar crowbar = new Crowbar();
        List loreList = meta.getLore();
        Iterator iterator = loreList.iterator();
        while (iterator.hasNext()) {
            String lore = (String) iterator.next();
            lore = ChatColor.stripColor(lore);
            int length = lore.length();
            for (int i = 0; i < length; i++) {
                char character = lore.charAt(i);
                if (Character.isDigit(character)) {
                    int amount = Integer.parseInt(String.valueOf(character));
                    if (lore.startsWith("End Dragon Uses")) {
                        crowbar.setEndDragonUses(amount);
                    }
                    if (lore.startsWith("End Frame Uses")) {
                        crowbar.setEndFrameUses(amount);
                        break;
                    }
                    if (lore.startsWith("Spawner Uses")) {
                        crowbar.setSpawnerUses(amount);
                        break;
                    }
                }
            }
        }
        return Optional.of(crowbar);
    }

    public int getEndDragonUses() {
        return endDragonUses;
    }

    public void setEndDragonUses(int uses) {
        if (endDragonUses != uses) {
            endDragonUses = Math.min(1, uses);
            needsMetaUpdate = true;
        }
    }

    public int getEndFrameUses() {
        return endFrameUses;
    }

    public void setEndFrameUses(int uses) {
        if (endFrameUses != uses) {
            endFrameUses = Math.min(5, uses);
            needsMetaUpdate = true;
        }
    }

    public int getSpawnerUses() {
        return spawnerUses;
    }

    public void setSpawnerUses(int uses) {
        if (spawnerUses != uses) {
            spawnerUses = Math.min(1, uses);
            needsMetaUpdate = true;
        }
    }

    public ItemStack getItemIfPresent() {
        Optional<ItemStack> optional = toItemStack();
        return optional.isPresent() ? (ItemStack) optional.get() : new ItemStack(Material.AIR, 1);
    }

    public Optional<ItemStack> toItemStack() {
        if (needsMetaUpdate) {
            double curDurability;
            double maxDurability = curDurability = CROWBAR_TYPE.getMaxDurability();
            double increment;
            if (Math.abs(curDurability -= (increment = curDurability / 6.0D) * (spawnerUses + endFrameUses + endDragonUses) - maxDurability) == 0.0D) {
                return Optional.absent();
            }
            ItemMeta meta = stack.getItemMeta();
            meta.setDisplayName(CROWBAR_NAME);
            meta.setLore(Arrays.asList(new String[]{String.format(LORE_FORMAT, new Object[]{"Spawner Uses", Integer.valueOf(spawnerUses), Integer.valueOf(1)}), String.format(LORE_FORMAT, new Object[]{"End Frame Uses", Integer.valueOf(endFrameUses), Integer.valueOf(5)}), String.format(LORE_FORMAT, new Object[]{"End Dragon Uses", Integer.valueOf(endDragonUses), Integer.valueOf(1)})}));
            stack.setItemMeta(meta);
            stack.setDurability((short) (int) curDurability);
            needsMetaUpdate = false;
        }
        return Optional.of(stack);
    }
}

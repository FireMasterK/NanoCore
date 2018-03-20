package me.idarkyy.nanocore.listeners;

import com.google.common.base.Optional;
import me.idarkyy.nanocore.NanoCore;
import me.idarkyy.nanocore.crowbar.Crowbar;
import org.bukkit.*;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class CrowbarListener implements Listener {
    private final NanoCore plugin;

    public CrowbarListener(NanoCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Optional<Crowbar> crowbarOptional;
        if ((event.getAction() == org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) && (event.hasItem()) && ((crowbarOptional = Crowbar.fromStack(event.getItem())).isPresent())) {
            event.setCancelled(true);
            Player player = event.getPlayer();
            World world = player.getWorld();
            if (world.getEnvironment() != Environment.NORMAL) {
                player.sendMessage(ChatColor.RED + "Crowbars may only be used in the overworld.");
                return;
            }
            Block block = event.getClickedBlock();
            Location blockLocation = block.getLocation();
            /*if (!FactionsCoreListener.attemptBuild(player, blockLocation, ChatColor.YELLOW + "You cannot do this in the territory of %1$s" + ChatColor.YELLOW + '.')) {
                return;
            }*/
            Crowbar crowbar = (Crowbar) crowbarOptional.get();
            BlockState blockState = block.getState();
            if ((blockState instanceof CreatureSpawner)) {
                int remainingUses = crowbar.getSpawnerUses();
                if (remainingUses <= 0) {
                    player.sendMessage(ChatColor.RED + "This crowbar has no more Spawner uses.");
                    return;
                }
                crowbar.setSpawnerUses(remainingUses - 1);
                player.setItemInHand(crowbar.getItemIfPresent());
                CreatureSpawner spawner = (CreatureSpawner) blockState;
                block.setType(Material.AIR);
                blockState.update();
                //world.dropItemNaturally(blockLocation, new ItemBuilder(Material.MOB_SPAWNER).displayName(ChatColor.GREEN + "Spawner").data((short) spawner.getData().getData()).loreLine(ChatColor.WHITE + WordUtils.capitalizeFully(spawner.getSpawnedType().name())).build());
            } else if (block.getType() == Material.ENDER_PORTAL_FRAME) {
                if (block.getType() != Material.ENDER_PORTAL_FRAME) {
                    return;
                }
                int remainingUses = crowbar.getEndFrameUses();
                if (remainingUses <= 0) {
                    player.sendMessage(ChatColor.RED + "This crowbar has no more End Portal Frame uses.");
                    return;
                }
                boolean destroyed = false;
                int blockX = blockLocation.getBlockX();
                int blockY = blockLocation.getBlockY();
                int blockZ = blockLocation.getBlockZ();
                int searchRadius = 4;
                for (int x = blockX - searchRadius; x <= blockX + searchRadius; x++)
                    for (int z = blockZ - searchRadius; z <= blockZ + searchRadius; z++) {
                        Block next = world.getBlockAt(x, blockY, z);
                        if (next.getType() == Material.ENDER_PORTAL) {
                            next.setType(Material.AIR);
                            next.getState().update();
                            destroyed = true;
                        }
                    }
                if (destroyed) {
                    //PlayerFaction playerFaction = plugin.getFactionManager().getPlayerFaction(player);
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Ender Portal is no longer active");
                    /*if (playerFaction != null) {
                        boolean informFaction = false;
                        for (Claim claim : playerFaction.getClaims())
                            if (claim.contains(blockLocation)) {
                                informFaction = true;
                                break;
                            }
                        if (informFaction) {
                            FactionMember factionMember = playerFaction.getMember(player);
                            String astrix = factionMember.getRole().getAstrix();
                            playerFaction.broadcast(astrix + ConfigurationService.TEAMMATE_COLOUR + " has used a Crowbar de-activating one of the factions' end portals.", new java.util.UUID[]{player.getUniqueId()});
                        }
                    }*/
                }
                crowbar.setEndFrameUses(remainingUses - 1);
                player.setItemInHand(crowbar.getItemIfPresent());
                block.setType(Material.AIR);
                blockState.update();
                world.dropItemNaturally(blockLocation, new ItemStack(Material.ENDER_PORTAL_FRAME, 1));
            } else {
                if (block.getType() != Material.DRAGON_EGG) {
                    return;
                }
                int remainingUses = crowbar.getEndDragonUses();
                if (remainingUses != 1) {
                    player.sendMessage(ChatColor.RED + "This crowbar has no more Dragon egg uses.");
                    return;
                }
                crowbar.setEndDragonUses(0);
                player.setItemInHand(crowbar.getItemIfPresent());
                block.setType(Material.AIR);
                blockState.update();
                String[] arrstring = new String[1];
                //arrstring[0] = (ChatColor.YELLOW + player.getName() + ChatColor.WHITE + " took from " + (plugin.getFactionManager().getFactionAt(blockLocation) != null ? plugin.getFactionManager().getFactionAt(blockLocation).getName() : "wilderness"));
                //world.dropItemNaturally(blockLocation, new ItemBuilder(Material.DRAGON_EGG).displayName(ChatColor.WHITE + "Dragon Egg").lore(arrstring).build());
            }
            if (event.getItem().getType() == Material.AIR) {
                player.playSound(blockLocation, Sound.ITEM_BREAK, 1.0F, 1.0F);
            } else {
                player.playSound(blockLocation, Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        ItemStack stack = event.getItemInHand();
        Player player = event.getPlayer();
        ItemMeta meta;
        if (((block.getState() instanceof CreatureSpawner)) && (stack.hasItemMeta()) && ((meta = stack.getItemMeta()).hasLore()) && (meta.hasDisplayName())) {

            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            List lore = meta.getLore();
            String spawnerName;
            Optional entityTypeOptional;
            /*if ((!lore.isEmpty()) && ((entityTypeOptional = Enums.getIfPresent(EntityType.class, spawnerName = ChatColor.stripColor(((String) lore.get(0)).toUpperCase()))).isPresent())) {
                spawner.setSpawnedType((EntityType) entityTypeOptional.get());
                spawner.update(true, true);
                player.sendMessage(ChatColor.AQUA + "Placed a " + ChatColor.BLUE + spawnerName + ChatColor.AQUA + " spawner.");
            }*/
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onPrepareCrowbarCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        if ((event.isRepair()) && (event.getRecipe().getResult().getType() == Crowbar.CROWBAR_TYPE)) {
            int endFrameUses = 0;
            int spawnerUses = 0;
            int dragonUses = 0;
            boolean changed = false;
            ItemStack[] matrix2;
            ItemStack[] matrix = matrix2 = inventory.getMatrix();
            for (ItemStack ingredient : matrix2) {
                Optional<Crowbar> crowbarOptional = Crowbar.fromStack(ingredient);
                if (crowbarOptional.isPresent()) {
                    Crowbar crowbar = (Crowbar) crowbarOptional.get();
                    spawnerUses += crowbar.getSpawnerUses();
                    dragonUses += crowbar.getEndDragonUses();
                    endFrameUses += crowbar.getEndFrameUses();
                    changed = true;
                }
            }
            if (changed) {
                inventory.setResult(new Crowbar(spawnerUses, endFrameUses, dragonUses).getItemIfPresent());
            }
        }
    }
}

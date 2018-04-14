package me.idarkyy.nanocore.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.zencode.mango.Mango;
import org.zencode.mango.factions.Faction;
import org.zencode.mango.factions.claims.Claim;
import org.zencode.mango.factions.types.PlayerFaction;

import java.util.*;

public class MangoUtil {

    private static final BlockFace[] BlockFaceArray = {
            BlockFace.NORTH,
            BlockFace.NORTH_EAST,
            BlockFace.EAST,
            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH,
            BlockFace.SOUTH_WEST,
            BlockFace.WEST,
            BlockFace.NORTH_WEST
    };
    public static MangoUtil util;
    private static Map<UUID, String> cachedPlayers = new HashMap<UUID, String>();

    public static void log(String... s) {
        for (String string : s) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
    }

    public static MangoUtil getUtil() {
        return util;
    }

    public static String yawTofacing(float yaw) {
        BlockFace facing = BlockFaceArray[Math.round(yaw / 45.0F) & 0x7].getOppositeFace();
        if (facing == BlockFace.NORTH) return "N";
        if (facing == BlockFace.NORTH_EAST) return "NE";
        if (facing == BlockFace.EAST) return "E";
        if (facing == BlockFace.SOUTH_EAST) return "SE";
        if (facing == BlockFace.SOUTH) return "S";
        if (facing == BlockFace.SOUTH_WEST) return "SW";
        if (facing == BlockFace.WEST) return "W";
        if (facing == BlockFace.NORTH_WEST) return "NW";
        return "?";
    }

    public static String getFactionClaim(Player player) {
        Claim claim = Mango.getInstance().getClaimManager().getClaimAt(player.getLocation());
        PlayerFaction faction = Mango.getInstance().getFactionManager().getFaction(player);

        if (claim != null) {
            if (claim.getOwner().equals(faction)) {
                return "&a" + faction.getName();
            }
            if (((faction instanceof PlayerFaction)) && (faction.getAllies().contains(claim.getOwner()))) {
                return "&d" + claim.getOwner().getName();
            }
            return "&c" + claim.getOwner().getName();
        }
        return "&2Wilderness";
    }

    public static boolean hasFaction(Player player) {
        Iterator<Faction> it = Mango.getInstance().getFactionManager().getFactions().iterator();

        while (it.hasNext()) {
            Faction f = it.next();

            if ((f instanceof PlayerFaction)) {
                PlayerFaction faction = (PlayerFaction) f;

                if (faction.getOnlinePlayers().contains(player)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getFactionName(Player player) {
        if (hasFaction(player)) {
            return Mango.getInstance().getFactionManager().getFaction(player).getName();
        }

        return "&cNone";
    }

    public static String getFactionDTR(Player player) {
        if (hasFaction(player)) {
            return Double.toString(Mango.getInstance().getFactionManager().getFaction(player).getDtr());
        }
        return "0.0";
    }

    public static String getFactionMaxDTR(Player player) {
        if (hasFaction(player)) {
            return Double.toString(Mango.getInstance().getFactionManager().getFaction(player).getMaxDtr());
        }
        return "0.0";
    }

    public static String getFactionHome(Player player) {
        if (hasFaction(player)) {
            if (Mango.getInstance().getFactionManager().getFaction(player).getHome() != null) {
                return (int) Mango.getInstance().getFactionManager().getFaction(player).getHome().getX() + ", " + (int) Mango.getInstance().getFactionManager().getFaction(player).getHome().getZ();
            }
            return "Unset";
        }
        return "0, 0";
    }

    public static String getFactionMember(Player player, int i) {
        if (hasFaction(player)) {
            List<UUID> members = new ArrayList<UUID>(Mango.getInstance().getFactionManager().getFaction(player).getMembers());
            members.addAll(Mango.getInstance().getFactionManager().getFaction(player).getOfficers());

            if (i > members.size() - 1) {
                return "";
            }
            UUID uuid = members.get(i);
            if (!cachedPlayers.containsKey(uuid)) {
                cachedPlayers.put(uuid, Bukkit.getOfflinePlayer(uuid).getName());
            }
            String name = cachedPlayers.get(uuid);
            return name;
        }
        if (i == 0) {
            return "None";
        }
        return "";
    }

    public static String getFactionLeader(Player player) {
        if (hasFaction(player)) {
            try {
                UUID uuid = Mango.getInstance().getFactionManager().getFaction(player).getLeader();
                if (!cachedPlayers.containsKey(uuid)) {
                    cachedPlayers.put(uuid, Bukkit.getOfflinePlayer(uuid).getName());
                }
                return cachedPlayers.get(uuid);
            } catch (IndexOutOfBoundsException e) {
                return "";
            }
        }
        return "None";
    }

    public static String getFactionAlly(Player player) {
        if (hasFaction(player)) {
            Iterator<PlayerFaction> localIterator = Mango.getInstance().getFactionManager().getFaction(player).getAllies().iterator();
            if (localIterator.hasNext()) {
                PlayerFaction ally = (PlayerFaction) localIterator.next();
                return ally.getName();
            }
        }
        return "&cNone";
    }
}

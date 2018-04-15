package me.idarkyy.nanocore.managers;

import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.UUID;

public class CooldownManager {
    private static CooldownManager manager;
    public static CooldownManager getManager() {
        return manager;
    }
    public static void setManager(CooldownManager manager) {
        CooldownManager.manager = manager;
    }

    private HashMap<UUID, Long> pearlCooldown = new HashMap<>();

    public boolean hasCooldown(Player player, CooldownType type) {
        if (type == CooldownType.ENDERPEARL) {
            boolean contain = pearlCooldown.containsKey(player.getUniqueId());
            if (contain) {

                long start = pearlCooldown.get(player.getUniqueId());
                long now = System.currentTimeMillis();

                long dif = now - start;

                return dif < 16000D;
                
            }
            return false;
        }

        return false;
    }

    public String getCooldown(Player player, CooldownType type) {
        if(type == CooldownType.ENDERPEARL) {
            DecimalFormat df = new DecimalFormat("#.#");
            long start = pearlCooldown.get(player.getUniqueId());
            long now = System.currentTimeMillis();
            long dif = now - start;
            long time = (16000 - dif) / 1000;
            return df.format(time);
        }

        return null;
    }

    public void putCooldown(Player player, CooldownType type, long currentTimeMilis) {
        if(type.equals(CooldownType.ENDERPEARL)) {
            pearlCooldown.put(player.getUniqueId(), currentTimeMilis);
        }
    }

    public enum CooldownType {
        ENDERPEARL
    }
}

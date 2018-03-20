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

    private HashMap<UUID, Double> pearlCooldown = new HashMap<>();

    public Boolean hasCooldown(Player player, CooldownType type) {
        if (type.equals(CooldownType.ENDERPEARL)) {
            boolean contain = pearlCooldown.containsKey(player.getUniqueId());
            if (contain) {

                double date = pearlCooldown.get(player.getUniqueId());
                double now = System.currentTimeMillis();

                double dif = now - date;

                return dif < 16000D;
            }
            return false;
        }

        return null;
    }

    public String getCooldown(Player player, CooldownType type) {
        if(type.equals(CooldownType.ENDERPEARL)) {
            DecimalFormat df = new DecimalFormat("#.#");
            double date = pearlCooldown.get(player.getUniqueId());
            double now = System.currentTimeMillis();
            double dif = now - date;
            double time = (16000D - dif) / 1000;
            return df.format(time);
        }

        return null;
    }

    public void putCooldown(Player player, CooldownType type, double currentTimeMilis) {
        if(type.equals(CooldownType.ENDERPEARL)) {
            pearlCooldown.put(player.getUniqueId(), currentTimeMilis);
        }
    }

    public enum CooldownType {
        ENDERPEARL
    }
}

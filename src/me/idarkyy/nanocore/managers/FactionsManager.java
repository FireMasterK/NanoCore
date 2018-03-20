package me.idarkyy.nanocore.managers;

import me.idarkyy.nanocore.NanoCore;
import org.bukkit.entity.Player;
import org.zencode.mango.Mango;
import org.zencode.mango.factions.FactionManager;
import org.zencode.mango.factions.types.PlayerFaction;

public class FactionsManager {
    private static FactionsManager instance;
    private NanoCore.FactionPlugin factionPlugin = NanoCore.getInstance().getFactionPlugin();
    private FactionManager fm = Mango.getInstance().getFactionManager();

    public static FactionsManager getInstance() {
        return instance;
    }

    public static void setInstance(FactionsManager instance) {
        FactionsManager.instance = instance;
    }

    public void refresh() {
        factionPlugin = NanoCore.getInstance().getFactionPlugin();
    }

    public PlayerFaction getFaction(Player player) {
        if (factionPlugin.equals(NanoCore.FactionPlugin.MANGO)) {
            return fm.getFaction(player);
        }

        return null;
    }
}

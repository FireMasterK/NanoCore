package me.idarkyy.nanocore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.idarkyy.nanocore.NanoCore;
import me.idarkyy.nanocore.managers.ClassManager;

public class ClassListener implements Listener {
	
	public ClassListener () {
		new BukkitRunnable() {
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					
					if (p.getGameMode() != GameMode.SURVIVAL)
						continue;
					
					switch (ClassManager.getManager().getClass(p)) {
					case DIAMOND:
						break;
					case BARD:
						p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 8, 0));
						break;
					case ROGUE:
						break;
					case ARCHER:
						break;
					case MINER:
						break;
					case NONE:
						break;
					}
				}
			}
		}.runTaskTimer(NanoCore.getInstance(), 0L, 140L);
		//TODO: Async?
	}
}

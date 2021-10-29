package me.sora819.utils;

import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.skills.mining.MiningManager;
import com.gmail.nossr50.util.player.UserManager;
import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;

public class ExplosivePickaxeMcMMO {
	public SpecialItems specialitems;
	public SoraCore core;

	public ExplosivePickaxeMcMMO() {
		specialitems = (SpecialItems)Bukkit.getPluginManager().getPlugin("SpecialItems");
		core = specialitems.core;
		
	}
	public void mcmmoBlockHandling(BlockBreakEvent e) {

		McMMOPlayer mcmmo = UserManager.getPlayer(e.getPlayer());
		
		MiningManager miningmanager = mcmmo.getMiningManager();
		
		miningmanager.miningBlockCheck(e.getBlock().getState());
	}

	public mcMMO getmcMMO() {
		return (mcMMO) specialitems.getServer().getPluginManager().getPlugin("mcMMO");
	}
	
}

package me.sora819.items;


import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

public class MobCatcher implements Listener{
	public SoraCore core;
	public SpecialItems main;
	
	// Init
	public MobCatcher(){
		main = SpecialItems.getInstance();
		core = main.core;

		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	public boolean checkItem(ItemStack item) {

		return false;
	}

	@EventHandler
	public void itemClick(PlayerInteractEvent e){
	//checkItem(((CraftPlayer) e.getPlayer()).getHandle().getItemInMainHand());
	}

}
//todo - kód befejezése
package me.sora819.items;

import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class HealingTalisman {
	
	public SoraCore core;
	public BukkitScheduler scheduler;
	public SpecialItems main;
	
	public HealingTalisman() {
		main = SpecialItems.getInstance();
		core = main.core;
	}
	
	@SuppressWarnings("unchecked")
	public ItemStack getItem() {
		ItemStack item;
		try {
			item = new ItemStack(Material.getMaterial("WATCH"), 1);
		}catch(Exception e){
			item = new ItemStack(Material.getMaterial("CLOCK"), 1);
		}finally {
			
		}
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("healing_item_name")));
		List<String> list = new ArrayList<String>();
		for (String str : (List<String>)core.configManager.getList("healing_item_lore")) {
			list.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		meta.setLore(list);
		item.setItemMeta(meta);

		main.nbth.setNBTString(item,"special","healing");

		return item;
	}

	public void addRecipes() {
		core.recipeManager.addRecipe(main, core.recipeManager.getSpecialItemRecipe(main, "healing", getItem()));

		ArrayList<Object> asd = new ArrayList<Object>();
	}
	
	public boolean itemCheck_old(ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
				for(int i = 0; i<core.configManager.getList("healing_item_lore").size(); i++) {
					if(!item.getItemMeta().getLore().get(i).equals(getItem().getItemMeta().getLore().get(i))) {
						return false;
					}
				}
			}else {
				return false;
			}
			return true;
		}else {
			return false;
		}
	}

	public boolean itemCheck(ItemStack item){
		if(item == null || item.getType() == Material.AIR){
			return false;
		}
		if(main.nbth.getNBTString(item, "special").equals("healing")){
			return true;
		}
		return false;
	}

	public void startCounter() {
		BukkitScheduler scheduler = main.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(main, new Runnable() {
			@Override
            public void run() {
				List<Player> players = core.playerManager.getPlayers();
            	for (Player ply : players) {
					int stack = 0;
					for(ItemStack item: ply.getInventory()) {
						if (item == null) {
							continue;
						}
						if(itemCheck(item)) {
							stack = stack + item.getAmount();
						}
					}
					if(!Boolean.parseBoolean(core.configManager.getString("healing_stackable"))) {
						stack = 1;
					}
					for(int i=0; i<stack; i++) {
						if(ply.getHealth()<=0){
							continue;
						}
						if(ply.getHealth() < 20){
							if(ply.getHealth()+core.configManager.getInt("healing_amount") > 20){
								ply.setHealth(20);
							}else {
								ply.setHealth(ply.getHealth()+core.configManager.getInt("healing_amount"));
							}
						}
					}
            	}
        	}
        }, 0L, Integer.toUnsignedLong(core.configManager.getInt("healing_time")));
	}
}

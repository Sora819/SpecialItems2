package me.sora819.items;

import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class FeedingTalisman{
	
	public SoraCore core;
	public BukkitScheduler scheduler;
	public SpecialItems main;
	
	public FeedingTalisman() {
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
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("feeding_item_name")));
		List<String> list = new ArrayList<String>();
		
		for (String str : (List<String>)core.configManager.getList("feeding_item_lore")) {
			list.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		meta.setLore(list);
		item.setItemMeta(meta);

		main.nbth.setNBTString(item,"special","feeding");
		
		return item;
	}
	
	public void addRecipes() {
		core.recipeManager.addRecipe(main, core.recipeManager.getSpecialItemRecipe(main, "feeding", getItem()));
	}

	public boolean itemCheck_old(ItemStack item) {
		if(item != null) {
			if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
				for(int i = 0; i<core.configManager.getList("feeding_item_lore").size(); i++) {
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
		if(main.nbth.getNBTString(item, "special").equals("feeding")){
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
        			if(!Boolean.parseBoolean(core.configManager.getString("feeding_stackable"))) {
        				stack = 1;
        			}
    				for(int i=0; i<stack; i++) {
    					if(ply.getFoodLevel() >= 20){
    						continue;
						}
    					ply.setFoodLevel(ply.getFoodLevel()+core.configManager.getInt("feeding_amount"));
    				}
            	}
        	}
        }, 0L, Integer.toUnsignedLong(core.configManager.getInt("feeding_time")));
	}
}

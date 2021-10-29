package me.sora819.items;

import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class FlyingBoots implements Listener{
	
	public SoraCore core;
	public BukkitScheduler scheduler;
	public SpecialItems main;
	
	public FlyingBoots() {
		main = SpecialItems.getInstance();
		core = main.core;
		main.getServer().getPluginManager().registerEvents(this, main);
	}
	
	@SuppressWarnings("unchecked")
	public ItemStack getItem() {
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("flying_item_name")));
		List<String> list = new ArrayList<String>();
		
		for (String str : (List<String>)core.configManager.getList("flying_item_lore")) {
			list.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		meta.setLore(list);
		item.setItemMeta(meta);

		main.nbth.setNBTString(item,"special","flying");

		return item;
	}
	
	public void addRecipes() {
		//Fix that you can re-craft the item, with the item itself
		
		core.recipeManager.addRecipe(main, core.recipeManager.getSpecialItemRecipe(main, "flying", getItem()));
	}
	
	public boolean itemCheck_old(ItemStack item) {
		if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
			if(item.getItemMeta().getLore().equals(getItem().getItemMeta().getLore())) {
				return true;
				
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	public boolean itemCheck(ItemStack item){
		if(item == null || item.getType() == Material.AIR){
			return false;
		}
		if(main.nbth.getNBTString(item, "special").equals("flying")){
			return true;
		}
		return false;
	}

	@EventHandler
	public void onArmorEquip(InventoryClickEvent e) {
    	Player ply = (Player)e.getWhoClicked();
		boolean boot = false;
		if(ply.getInventory().getBoots() != null) {
			if(itemCheck(ply.getInventory().getBoots())) {
				boot = true;
			}
		}
		boolean boots = boot;
		
        new BukkitRunnable() {
            
            @Override
            public void run() {
	        	if(ply.getInventory().getBoots() != null) {
	    			if(ply.getGameMode() == GameMode.SURVIVAL || ply.getGameMode() == GameMode.ADVENTURE) {
	    				if(itemCheck(ply.getInventory().getBoots())) {
	    					ply.setAllowFlight(true);
	    				}
		    		}
	    		}else if(boots){
	    			if(ply.getGameMode() == GameMode.SURVIVAL || ply.getGameMode() == GameMode.ADVENTURE) {
	    				ply.setAllowFlight(false);
	    			}
	    		}
	        }
		}.runTaskLater(this.main, 1);
	}
	
	@EventHandler
	public void onGamemodeChange(PlayerGameModeChangeEvent e) {
		Player ply = e.getPlayer();
    	if(ply.getInventory().getBoots() != null) {
			if(e.getNewGameMode() == GameMode.SURVIVAL || e.getNewGameMode() == GameMode.ADVENTURE) {
				if(itemCheck(ply.getInventory().getBoots())) {
					boolean flying = ply.isFlying();
					new BukkitRunnable() {
			            
			            @Override
			            public void run() {
			            	if(flying) {
			            		ply.setAllowFlight(true);
			            		ply.setFlying(true);
			            	}else {
			            		ply.setAllowFlight(true);
			            	}
						}
			            
					}.runTaskLater(this.main, 1);
					
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerJoinCheck(PlayerJoinEvent e) {
		Player ply = e.getPlayer();
    	if(ply.getInventory().getBoots() != null) {
			if(ply.getGameMode() == GameMode.SURVIVAL || ply.getGameMode() == GameMode.ADVENTURE) {
				if(itemCheck(ply.getInventory().getBoots())) {
					ply.setAllowFlight(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerEquipArmor(PlayerInteractEvent e) {
		Player ply = e.getPlayer();
    	if(ply.getInventory().getBoots() == null) {
			if(ply.getGameMode() == GameMode.SURVIVAL || ply.getGameMode() == GameMode.ADVENTURE) {
				if(itemCheck(ply.getInventory().getItemInMainHand())) {
					ply.setAllowFlight(true);
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		Player ply = e.getPlayer();
    	if(ply.getInventory().getBoots() != null) {
			if(ply.getGameMode() == GameMode.SURVIVAL || ply.getGameMode() == GameMode.ADVENTURE) {
				if(itemCheck(ply.getInventory().getBoots())) {
					ply.setAllowFlight(true);
				}else {
					ply.setAllowFlight(false);
				}
			}
		}else {
			if(ply.getGameMode() == GameMode.SURVIVAL || ply.getGameMode() == GameMode.ADVENTURE) {
				ply.setAllowFlight(false);
			}
		}
	}
	
	@EventHandler
	public void onCraft(PrepareItemCraftEvent e) {
		if(e.getRecipe() != null) {
			if(itemCheck(e.getRecipe().getResult())) {
				for(ItemStack item : e.getInventory().getMatrix()) {
					if(item != null) {
						if(itemCheck(item)) {
							e.getInventory().setResult(new ItemStack(Material.AIR));
						}
					}
				}
			}
		}
	}
}

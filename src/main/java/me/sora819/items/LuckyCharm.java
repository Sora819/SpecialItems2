package me.sora819.items;

import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import org.apache.commons.lang.WordUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.Map.Entry;


public class LuckyCharm implements Listener{

	public SoraCore core;
	public SpecialItems main;
	public HashMap<Player,Boolean> cooldown;
	
	// Init
	public LuckyCharm(){
		main = SpecialItems.getInstance();
		core = main.core;

		main.getServer().getPluginManager().registerEvents(this, main);
		
		cooldown = new HashMap<Player, Boolean>();
	}
	
	// Get the default item
	@SuppressWarnings("unchecked")
	public ItemStack getItem() {
		ItemStack item;
		
		item = new ItemStack(Material.EMERALD, 1);
			
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("luckycharm_item_name")));
		List<String> list = new ArrayList<String>();
		
		for (String str : (List<String>)core.configManager.getList("luckycharm_item_lore")) {
			list.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		meta.setLore(list);
		item.setItemMeta(meta);

		main.nbth.setNBTString(item,"special","luckycharm");
		main.nbth.setNBTString(item,"ores","");

		return item;
	}
	
	// Checking the item
	public boolean itemCheck_old(ItemStack item) {
		if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
			for(int i = 0; i<core.configManager.getList("luckycharm_item_lore").size(); i++) {
				if(!item.getItemMeta().getLore().get(i).equals(getItem().getItemMeta().getLore().get(i))) {
					return false;
				}
			}
		}else {
			return false;
		}
		return true;
	}

	public boolean itemCheck(ItemStack item){
		if(item == null || item.getType() == Material.AIR){
			return false;
		}if(main.nbth.getNBTString(item, "special").equals("luckycharm")){
			return true;
		}
		return false;
	}
	
	// Adding crafting recipes
	public void addRecipes() {
		core.recipeManager.addRecipe((Plugin)main, core.recipeManager.getSpecialItemRecipe((Plugin)main, "luckycharm", getItem()));
			
		HashMap<Material, Double> blockmap = getChances();
		
		for (Entry<Material, Double> entry : blockmap.entrySet()) {
			Bukkit.addRecipe(new ShapelessRecipe(NamespacedKey.minecraft("specialitems."+entry.getKey().name().toLowerCase()),getItem()).addIngredient(getItem().getType()).addIngredient(entry.getKey()) );
		}
		
	}
	
	public HashMap<Material, Double> getChances(){
		Map<String, Object> ores;
		HashMap<Material, Double> blockmap = new HashMap<Material,Double>();
		
		ores = core.configManager.getListKeyValue("luckycharm_ores");

		for(String line : ores.keySet()) {
			blockmap.put(Material.getMaterial(line.toUpperCase()), Double.parseDouble(ores.get(line).toString()));
		}
		
		return blockmap;
	}
	
	// Loop through the blocks
	public int loopBlocks(Location loc, int radius, Player ply) {
		Random r = new Random();
		int state = 1;
		Material new_block = Material.STONE;
		Double prev = 100.0;
		
		for (int x=-core.configManager.getInt("luckycharm_radius"); x < core.configManager.getInt("luckycharm_radius"); x++) {
			for (int y=-core.configManager.getInt("luckycharm_radius"); y < core.configManager.getInt("luckycharm_radius"); y++) {
				for (int z=-core.configManager.getInt("luckycharm_radius"); z < core.configManager.getInt("luckycharm_radius"); z++) {
					BlockBreakEvent event = new BlockBreakEvent(loc.getWorld().getBlockAt(loc.getBlockX()+x, loc.getBlockY()+y, loc.getBlockZ()+z),ply);
					Bukkit.getServer().getPluginManager().callEvent(event);
					if(event.isCancelled()) {
						return 2;
					}
				}
			}
		}
		
		for (int x=-core.configManager.getInt("luckycharm_radius"); x < core.configManager.getInt("luckycharm_radius"); x++) {
			for (int y=-core.configManager.getInt("luckycharm_radius"); y < core.configManager.getInt("luckycharm_radius"); y++) {
				for (int z=-core.configManager.getInt("luckycharm_radius"); z < core.configManager.getInt("luckycharm_radius"); z++) {
					double randomValue = 0 + (100 - 0) * r.nextDouble();
					Block block = loc.getWorld().getBlockAt(loc.getBlockX()+x,loc.getBlockY()+y,loc.getBlockZ()+z);
					new_block = Material.STONE;
					prev = 100.0;

					if(block.getType() == Material.STONE) {
						for(Entry<Material, Double> entry : getChances().entrySet()) {
							if(randomValue < entry.getValue() && prev > entry.getValue()) {
								state = 0;
								if(getOres(ply.getInventory().getItemInMainHand()).isEmpty()) {
									return 3;
								}

								if(getOres(ply.getInventory().getItemInMainHand()).contains(entry.getKey())) {
									new_block = entry.getKey();
									prev = entry.getValue();
								}
							}
						}
						if(new_block == Material.STONE) {
							state = 0;
						}
						block.setType(new_block);
					}
				}
			}
		}
		return (state);
	}
	
	// Rightclick with charm event
	@EventHandler
	public void onRightClick(PlayerInteractEvent e) {
		Player ply = e.getPlayer();
		PlayerInventory inv = ply.getInventory();
		ItemStack hand = inv.getItemInMainHand();

		if(hand.getType() == Material.AIR){
			return;
		}

		if(itemCheck(hand)) {
			if(!cooldown.containsKey(e.getPlayer())) {
				cooldown.put(ply, false);
			}
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK && !cooldown.get(e.getPlayer())) {
				Location loc = e.getClickedBlock().getLocation();
				cooldown.put(e.getPlayer(), true);
				startCounter(ply);

				getOres(hand);

				int loop_state = loopBlocks(loc, core.configManager.getInt("luckycharm_radius"), ply);
				
				if(loop_state == 0) {
					if(ply.getGameMode() != GameMode.CREATIVE) {
						if(hand.getAmount() > 1) {
							hand.setAmount(hand.getAmount()-1); 
						}else {
							inv.setItemInMainHand(new ItemStack(Material.AIR));
						}
					}
				}else if(loop_state == 1){
					ply.sendMessage(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("luckycharm_no_stone")));
				}else if(loop_state == 2){
					ply.sendMessage(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("protection_message")));
				}else if(loop_state == 3){
					ply.sendMessage(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("luckycharm_empty")));
				}
			}
		}
	}

	// Cooldown timer
	public void startCounter(Player ply) {
       new BukkitRunnable() {
           
            @Override
            public void run() {
            	cooldown.put(ply, false);
            }
            
        }.runTaskLater(this.main, core.configManager.getInt("luckycharm_delay"));
    }
	
	
	// Converts the material id into name
	public String materialName(Material material) {
		return WordUtils.capitalizeFully(material.toString().toLowerCase().replace("_", " "));
	}
	
	// Converts the name back into material id
	public String materialNameBack(String name) {
		String matname =  ChatColor.stripColor(name).toUpperCase().replace(" ", "_");
		return matname;
	}
	
	public List<Material> getOres_old(ItemStack item){
		List<Material> ores = new ArrayList<Material>();
		ItemMeta meta = item.getItemMeta();
		
		for(String line : meta.getLore().subList(main.getConfig().getList("luckycharm_item_lore").size(), meta.getLore().size())) {
			if(main.getConfig().getConfigurationSection("luckycharm_ores").getValues(false).keySet().contains(materialNameBack(line).toLowerCase())) {
				ores.add(Material.getMaterial(materialNameBack(line)));
			}
		}
		
		return ores;
	}

	public List<Material> getOres(ItemStack item){
		List<String> orenames = main.nbth.getNBTStringList(item, "ores");
		List<Material> ores = new ArrayList<Material>();
		for(String str : orenames){
			ores.add(Material.getMaterial(materialNameBack(str)));
		}
		return ores;
	}
	
	//@EventHandler
	public void onCraftWithOre_old(PrepareItemCraftEvent e) {
		if(e.getInventory().getResult() == null) {
			return;
		}
		if(itemCheck(e.getInventory().getResult())) {
			int num = 0;
			int ore = 0;
			int other = 0;
			ItemStack charm = getItem();
			ItemStack ore_item = new ItemStack(Material.COAL_ORE);
			
			CraftingInventory craft = e.getInventory();
			ItemStack[] matrix = craft.getMatrix();
		
			for(ItemStack item : matrix) {
				if(item != null) {
					if(itemCheck(item)) {
						num = num+1;
						charm = item;
					}
					else if(getChances().containsKey(item.getType())) {
						ore = ore+1;
						ore_item = item;
					}else {
						other = other+1;
					}
				}
			}
			if(num == 1 && ore == 1 && other == 0) {
				ItemMeta meta = charm.getItemMeta();
				List<String> lore = meta.getLore();

				for (String line : charm.getItemMeta().getLore()) {
					if(line.contains(materialName(ore_item.getType()))) {
						e.getInventory().setResult(new ItemStack(Material.AIR));
						return;
					}
				}
				
				lore.add(ChatColor.translateAlternateColorCodes('&',"&"+core.configManager.getString("luckycharm_lore_list_color")+materialName(ore_item.getType())));
				meta.setLore(lore);
				
				ItemStack result = getItem();
				result.setItemMeta(meta);
				e.getInventory().setResult(result);
			}else {
				return;
			}
		}
	}

	@EventHandler
	public void onCraftWithOre(PrepareItemCraftEvent e) {
		if(e.getInventory().getResult() == null) {
			return;
		}
		if(itemCheck(e.getInventory().getResult())) {
			int num = 0;
			int ore = 0;
			int other = 0;
			ItemStack charm = getItem();
			ItemStack ore_item = new ItemStack(Material.COAL_ORE);

			CraftingInventory craft = e.getInventory();
			ItemStack[] matrix = craft.getMatrix();

			for(ItemStack item : matrix) {
				if(item != null) {
					if(itemCheck(item)) {
						num = num+1;
						charm = item;
					}
					else if(getChances().containsKey(item.getType())) {
						ore = ore+1;
						ore_item = item;
					}else {
						other = other+1;
					}
				}
			}
			if(num == 1 && ore == 1 && other == 0) {
				ItemMeta meta = charm.getItemMeta();
				List<String> lore = meta.getLore();

				for (String line : main.nbth.getNBTStringList(charm,"ores")) {
					//main.message(line);
					if(line.contains(materialName(ore_item.getType()))) {
						e.getInventory().setResult(new ItemStack(Material.AIR));
						return;
					}
				}

				lore.add(ChatColor.translateAlternateColorCodes('&',"&"+core.configManager.getString("luckycharm_lore_list_color")+materialName(ore_item.getType())));
				meta.setLore(lore);

				ItemStack result = charm;
				result.setItemMeta(meta);

				List<String> ores = main.nbth.getNBTStringList(result,"ores");
				ores.add(materialName(ore_item.getType()));
				main.nbth.setNBTStringList(result,"ores", ores);

				e.getInventory().setResult(result);
			}else {
				return;
			}
		}
	}
}
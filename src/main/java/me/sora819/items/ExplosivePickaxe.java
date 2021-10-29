package me.sora819.items;

import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import me.sora819.utils.ExplosivePickaxeMcMMO;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ExplosivePickaxe implements Listener{
	
	public SoraCore core;
	public BukkitScheduler scheduler;
	public SpecialItems main;
	public ExplosivePickaxeMcMMO mcmmo;
	
	public List<Location> explosions;
	
	public ExplosivePickaxe() {
		main = SpecialItems.getInstance();
		core = main.core;
		main.getServer().getPluginManager().registerEvents(this, main);
		
		explosions = new ArrayList<Location>();
		
		if(installedmcMMO()) {
			mcmmo = new ExplosivePickaxeMcMMO();
		}
	}
	
	public boolean installedmcMMO() {
		for (Plugin plug : main.getServer().getPluginManager().getPlugins()) {
			if(plug.getName().equals("mcMMO")) {
				return true;
			}
		}
		return false;
	}

	@Deprecated
	public boolean itemCheck_old(ItemStack item) {
		if(item.hasItemMeta() && item.getItemMeta().hasLore()) {
			for(int i = 0; i<core.configManager.getList("explosive_item_lore").size(); i++) {
				if(!item.getItemMeta().getLore().get(i).equals(getItem().getItemMeta().getLore().get(i))) {
					return false;
				}
			}
		}else {
			return false;
		}
		return true;
	}

	public boolean itemCheck(ItemStack item) {
		if(item == null || item.getType() == Material.AIR){
			return false;
		}
		if(main.nbth.getNBTString(item, "special").equals("explosive")){
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public ItemStack getItem() {
		ItemStack item = new ItemStack(Material.DIAMOND_PICKAXE, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("explosive_item_name")));
		List<String> list = new ArrayList<String>();
		
		for (String str : (List<String>)core.configManager.getList("explosive_item_lore")) {
			list.add(ChatColor.translateAlternateColorCodes('&', str));
		}
		String radiuslore = core.configManager.getString("explosive_radius_lore");
		if(radiuslore.contains("%size%")) {
			radiuslore = radiuslore.replace("%size%", "3x3");
		}
		if(radiuslore.contains("%radius%")) {
			radiuslore = radiuslore.replace("%radius%", "1");
		}
		
		list.add(ChatColor.translateAlternateColorCodes('&', radiuslore));
		meta.setLore(list);
		item.setItemMeta(meta);

		main.nbth.setNBTString(item,"special","explosive");
		main.nbth.setNBTInt(item,"radius",1);

		return item;
	}
	
	public void addRecipes() {
		core.recipeManager.addRecipe(main, core.recipeManager.getSpecialItemRecipe(main, "explosive", getItem()));
	}

	@Deprecated
	public String getRadiusCode_old() {
		return "�r�1�r";
	}

	@Deprecated
	public int getItemRadius_old(ItemStack item) {
		String radiuslore = item.getItemMeta().getLore().toArray()[core.configManager.getList("explosive_item_lore").size()].toString();
		String temp = "";
		List<String> radiuslore2 = new ArrayList<String>();
		int radius = 0;
		for(int i=0; i<radiuslore.length()-1; i++) {
			radiuslore2.add( Character.toString(radiuslore.charAt(i)) );
		}
		for(String ch : radiuslore2) {
			temp = temp+ch;
			if(temp.contains(getRadiusCode_old())) {
				
				temp = "";
				radius = radius +1;
			}
				
		}
		return radius;
	}

	public int getItemRadius(ItemStack item){
		return main.nbth.getNBTInt(item,"radius");
	}

	public boolean setItemRadius(ItemStack item, int radius, Player ply){
		if(item==null) {
			ply.sendMessage(ChatColor.translateAlternateColorCodes('&',core.configManager.getString("wrong_item")));
			return false;
		}else if(!itemCheck(item)){
			ply.sendMessage(ChatColor.translateAlternateColorCodes('&',core.configManager.getString("wrong_item")));
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();

		String radiuslore = core.configManager.getString("explosive_radius_lore");
		if(radiuslore.contains("%size%")) {
			radiuslore = radiuslore.replace("%size%", Integer.toString(radius*2+1)+"x"+Integer.toString(radius*2+1));
		}
		if(radiuslore.contains("%radius%")) {
			radiuslore = radiuslore.replace("%radius%", Integer.toString(radius));
		}
		lore.set(core.configManager.getList("explosive_item_lore").size(), ChatColor.translateAlternateColorCodes('&', radiuslore));
		meta.setLore(lore);

		item.setItemMeta(meta);

		main.nbth.setNBTInt(item,"radius",radius);

		return true;
	}

	@Deprecated
	public boolean setItemRadius_old(ItemStack item, int radius, Player ply) {
		if(item==null) {
			ply.sendMessage(ChatColor.translateAlternateColorCodes('&',core.configManager.getString("wrong_item")));
			return false;
		}else if(!itemCheck(item)){
			ply.sendMessage(ChatColor.translateAlternateColorCodes('&',core.configManager.getString("wrong_item")));
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		
		String radiusCode = "";
		for (int r=0; r<radius; r++) {
			radiusCode = radiusCode+getRadiusCode_old();
		}
		String radiuslore = core.configManager.getString("explosive_radius_lore");
		if(radiuslore.contains("%size%")) {
			radiuslore = radiuslore.replace("%size%", Integer.toString(radius*2+1)+"x"+Integer.toString(radius*2+1));
		}
		if(radiuslore.contains("%radius%")) {
			radiuslore = radiuslore.replace("%radius%", Integer.toString(radius));
		}
		lore.set(core.configManager.getList("explosive_item_lore").size(), ChatColor.translateAlternateColorCodes('&', radiusCode+radiuslore));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return true;
	}

	
	@SuppressWarnings("deprecation")
	@EventHandler(priority=EventPriority.HIGH)
	public void onMine(BlockBreakEvent e) {
		if(itemCheck(e.getPlayer().getInventory().getItemInMainHand()) && e.getBlock().getType() != Material.BEDROCK) {
			boolean fromExplosion = false;
			for(Location loc : explosions) {
				if(Math.abs(loc.getX()-e.getBlock().getLocation().getX()) <= this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand())) {
					if(Math.abs(loc.getY()-e.getBlock().getLocation().getY()) <= this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand())) {
						if(Math.abs(loc.getZ()-e.getBlock().getLocation().getZ()) <= this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand())) {
							if(!itemCheck(e.getPlayer().getInventory().getItemInMainHand())) {
								return;
							}
							
							if(e.getPlayer().getGameMode() == GameMode.CREATIVE) {
								e.getBlock().setType(Material.AIR);
								return;
							}

							fromExplosion = true;
							if(!e.isCancelled()) {
								if(e.getBlock().getType() != Material.AIR) {
								}
								
								if(e.getBlock().getType() == Material.COAL_ORE) {
									e.getPlayer().giveExp((int)(Math.random()*3));
								}
								if(e.getBlock().getType() == Material.DIAMOND_ORE) {
									e.getPlayer().giveExp((int)(Math.random()*4+3));
								}
								if(e.getBlock().getType() == Material.EMERALD_ORE) {
									e.getPlayer().giveExp((int)(Math.random()*4+3));
								}
								if(e.getBlock().getType() == Material.LAPIS_ORE) {
									e.getPlayer().giveExp((int)(Math.random()*3+2));
								}
								try {
									if(e.getBlock().getType() == Material.getMaterial("NETHER_QUARTZ_ORE")) {
										e.getPlayer().giveExp((int)(Math.random()*3+2));
									}
								}catch(Exception ex) {
									if(e.getBlock().getType() == Material.getMaterial("QUARTZ_ORE")) {
										e.getPlayer().giveExp((int)(Math.random()*3+2));
									}
								}
								if(e.getBlock().getType() == Material.REDSTONE_ORE) {
									e.getPlayer().giveExp((int)(Math.random()*4+1));
								}
								try {
									if(e.getBlock().getType() == Material.getMaterial("SPAWNER")) {
										e.getPlayer().giveExp((int)(Math.random()*28+15));
									}
								}catch(Exception ex) {
									if(e.getBlock().getType() == Material.getMaterial("MOB_SPAWNER")) {
										e.getPlayer().giveExp((int)(Math.random()*28+15));
									}
								}
								
								if(installedmcMMO()) {
									mcmmo.mcmmoBlockHandling(e);
								}
								
								e.getBlock().breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
								
								Random r = new Random();
								double rand = 0 + (100 - 0) * r.nextDouble();
								
								int level = 0;
								
								if(e.getPlayer().getItemInHand().containsEnchantment(Enchantment.DURABILITY)) {
									level = e.getPlayer().getItemInHand().getEnchantmentLevel(Enchantment.DURABILITY);
								}
								if(rand <= (100 / (level+1) )) {
									e.getPlayer().getInventory().getItemInMainHand().setDurability((short) (e.getPlayer().getInventory().getItemInMainHand().getDurability()+1));
									if(e.getPlayer().getInventory().getItemInMainHand().getDurability() > e.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability()+1) {
										e.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
										e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 3, 1);
										return;
									}
								}
							}
						}
					}
				}
			}
			
			if(fromExplosion == false) {
				explosions.add(e.getBlock().getLocation());
				e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(),0.0f);
				Location loc = e.getBlock().getLocation();
				for(int x= -this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand()); x<=this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand()); x++) {
					for(int z= -this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand()); z<=this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand()); z++) {
						for(int y= -this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand()); y<=this.getItemRadius(e.getPlayer().getInventory().getItemInMainHand()); y++) {
							if(e.isCancelled()) {
								e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', core.configManager.getString("protection_message")));
							}
							
							Bukkit.getServer().getPluginManager().callEvent(new BlockBreakEvent(e.getPlayer().getWorld().getBlockAt((int)loc.getX()+x, (int)loc.getY()+y, (int)loc.getZ()+z),e.getPlayer()));
							if(e.getPlayer().getInventory().getItemInMainHand() == null) {
								return;
							}
						}
					}
				}
			}
			//e.getBlock().getWorld().createExplosion(e.getBlock().getLocation(), (float)me.sora819.core.configManager.getDouble(specialitems, "explosion_radius"));
		}
	}
	
	@EventHandler
	public void onExplosion(BlockExplodeEvent e) {
		if(explosions.contains(e.getBlock().getLocation())) {
			e.setYield(1);
			new BukkitRunnable() {
	            @Override
	            public void run() {
	    			explosions.remove(e.getBlock().getLocation());
		        }
			}.runTaskLater(this.main, 1);
			
		}
	}

	@EventHandler
	public void onEntityExplosionDamage(EntityDamageEvent e) {
		if(e.getEntityType() == EntityType.PLAYER || e.getEntityType() == EntityType.DROPPED_ITEM) {
			Entity ply = e.getEntity();
			for(Location loc : explosions) {
				if(Math.abs(loc.getX()-ply.getLocation().getX()) < core.configManager.getDouble("explosion_radius")+1) {
					if(Math.abs(loc.getY()-ply.getLocation().getY()) < core.configManager.getDouble("explosion_radius")+1) {
						if(Math.abs(loc.getZ()-ply.getLocation().getZ()) < core.configManager.getDouble("explosion_radius")+1) {
							e.setCancelled(true);
						}
					}
				}
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
// todo - Tesztek
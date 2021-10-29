package me.sora819.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;
import de.tr7zw.nbtapi.NBTItem;
import me.sora819.core.SoraCore;
import me.sora819.SpecialItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class NBTHandler {
	public SpecialItems main;
	public SoraCore core;

	public NBTHandler() {
		main = (SpecialItems) Bukkit.getPluginManager().getPlugin("SpecialItems");
		core = main.core;
	}

	public NBTItem getNBT(ItemStack item) {
		return new NBTItem(item);
	}

	public void setNBTString(ItemStack item, String key, String value) {
		NBTItem nbti = getNBT(item);
		nbti.setString(key,value);
		nbti.applyNBT(item);
	}
	public void setNBTInt(ItemStack item, String key, Integer value) {
		NBTItem nbti = getNBT(item);
		nbti.setInteger(key,value);
		nbti.applyNBT(item);
	}
	public void setNBTBool(ItemStack item, String key, Boolean value) {
		NBTItem nbti = getNBT(item);
		nbti.setBoolean(key,value);
		nbti.applyNBT(item);
	}
	public void setNBTStringList(ItemStack item, String key, List<String> value) {
		NBTItem nbti = getNBT(item);

		String data = "";
		for(String str : value){
			data=data+str+";";
		}

		nbti.setString(key, data);
		nbti.applyNBT(item);
	}

	public String getNBTString(ItemStack item, String key) {
		NBTItem nbti = getNBT(item);
		return nbti.getString(key);
	}
	public Integer getNBTInt(ItemStack item, String key) {
		NBTItem nbti = getNBT(item);
		return nbti.getInteger(key);
	}
	public Boolean getNBTBool(ItemStack item, String key) {
		NBTItem nbti = getNBT(item);
		return nbti.getBoolean(key);
	}

	public Boolean hasNBT(ItemStack item){
		NBTItem nbti = getNBT(item);
		return nbti.hasKey("special");
	}

	public List<String> getNBTStringList(ItemStack item, String key) {
		NBTItem nbti = getNBT(item);
		List<String> data = new ArrayList<String>();

		for(String str : nbti.getString(key).split(";")){
			if(str != ""){
				data.add(str);
			}
		}

		return data;
	}

}

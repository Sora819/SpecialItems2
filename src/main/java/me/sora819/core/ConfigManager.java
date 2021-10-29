package me.sora819.core;

import me.sora819.SpecialItems;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public class ConfigManager {
   SpecialItems main;

   public ConfigManager(){
      main = SpecialItems.getInstance();
      saveDefaultConfig();
      reloadConfig();
   }

   public void saveDefaultConfig() {
      main.saveDefaultConfig();
   }

   public void reloadConfig() {
      main.reloadConfig();
   }

   public void saveConfig() {
      main.saveConfig();
   }

   public String getString(String key) {
      return main.getConfig().getString(key);
   }

   public boolean getBoolean(String key) {
      return main.getConfig().getBoolean(key);
   }

   public int getInt(String key) {
      return main.getConfig().getInt(key);
   }

   public double getDouble(String key) {
      return main.getConfig().getDouble(key);
   }

   public List<?> getList(String key) {
      return main.getConfig().getList(key);
   }

   public Map<String, Object> getListKeyValue(String key, boolean values) {
      Map<String, Object> map = main.getConfig().getConfigurationSection(key).getValues(values);
      return map;
   }

   public Map<String, Object> getListKeyValue(String key) {
      Map<String, Object> map = main.getConfig().getConfigurationSection(key).getValues(true);
      return map;
   }
}

package me.sora819.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CustomFileManager {
   public FileConfiguration loadConfig(Plugin plugin, String file) {
      return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), file));
   }

   public void saveDefaultConfig(Plugin plugin, String file) {
      File cfile = new File(plugin.getDataFolder(), file);
      if (!cfile.exists()) {
         plugin.saveResource(file, false);
      }

   }

   public void saveConfig(Plugin plugin, FileConfiguration config, String file) {
      try {
         config.save(new File(plugin.getDataFolder(), file));
      } catch (IOException var5) {
         var5.printStackTrace();
      }

   }

   public String getString(Plugin plugin, String file, String key) {
      FileConfiguration config = this.loadConfig(plugin, file);
      return config.getString(key);
   }

   public int getInt(Plugin plugin, String file, String key) {
      FileConfiguration config = this.loadConfig(plugin, file);
      return config.getInt(key);
   }

   public boolean getBoolean(Plugin plugin, String file, String key) {
      FileConfiguration config = this.loadConfig(plugin, file);
      return config.getBoolean(key);
   }

   public double getDouble(Plugin plugin, String file, String key) {
      FileConfiguration config = this.loadConfig(plugin, file);
      return config.getDouble(key);
   }

   public List<?> getList(Plugin plugin, String file, String key) {
      FileConfiguration config = this.loadConfig(plugin, file);
      return config.getList(key);
   }

   public Map<String, Object> getListKeyValue(Plugin plugin, String file, String key) {
      FileConfiguration config = this.loadConfig(plugin, file);
      Map<String, Object> map = config.getConfigurationSection(key).getValues(true);
      return map;
   }

   public Map<String, Object> getListKeyValue(Plugin plugin, String file, String key, boolean value) {
      FileConfiguration config = this.loadConfig(plugin, file);
      Map<String, Object> map = config.getConfigurationSection(key).getValues(value);
      return map;
   }

   public void setString(Plugin plugin, String file, String key, String value) {
      FileConfiguration config = this.loadConfig(plugin, file);
      config.set(key, value);
      this.saveConfig(plugin, config, file);
   }

   public void setInt(Plugin plugin, String file, String key, int value) {
      FileConfiguration config = this.loadConfig(plugin, file);
      config.set(key, value);
      this.saveConfig(plugin, config, file);
   }

   public void setDouble(Plugin plugin, String file, String key, double value) {
      FileConfiguration config = this.loadConfig(plugin, file);
      config.set(key, value);
      this.saveConfig(plugin, config, file);
   }

   public void setList(Plugin plugin, String file, String key, List<?> value) {
      FileConfiguration config = this.loadConfig(plugin, file);
      config.set(key, value);
      this.saveConfig(plugin, config, file);
   }
}

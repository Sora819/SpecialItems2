package me.sora819.core;

import me.sora819.SpecialItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class SoraCore{
   public ConfigManager configManager;
   public CustomFileManager customFileManager;
   public Inventory invManager;
   public Players playerManager;
   public Recipes recipeManager;
   SpecialItems main;

   public SoraCore(){

   }

   public void setupCore(){
      main = SpecialItems.getInstance();
      configManager = new ConfigManager();
      customFileManager = new CustomFileManager();
      invManager = new Inventory();
      playerManager = new Players();
      recipeManager = new Recipes();

   }

   public String getVersion() {
      if (Bukkit.getVersion().contains("1.12")) {
         return "1.12";
      } else if (Bukkit.getVersion().contains("1.13")) {
         return "1.13";
      } else if (Bukkit.getVersion().contains("1.14")) {
         return "1.14";
      } else if (Bukkit.getVersion().contains("1.15")) {
         return "1.15";
      } else if (Bukkit.getVersion().contains("1.16")) {
         return "1.16";
      } else {
         return "Not compatible";
      }
   }

}

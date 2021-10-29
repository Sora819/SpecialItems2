package me.sora819.core;

import me.sora819.SpecialItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class Players {
   public SpecialItems main;

   public Players(){
      main = SpecialItems.getInstance();
   }

   public List<Player> getPlayers() {

      return (List)this.main.getServer().getOnlinePlayers();
   }
}

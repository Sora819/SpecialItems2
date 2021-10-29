package me.sora819.core;

import me.sora819.SpecialItems;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class Inventory {
   public SoraCore core;
   public SpecialItems main;

   public Inventory(){
      main = SpecialItems.getInstance();
      core = main.getCore();
   }

   @Deprecated
   public boolean checkInventory(Player player, ItemStack item) {
      PlayerInventory inv = player.getInventory();
      ItemStack[] items;
      int items_num = (items = inv.getStorageContents()).length;

      for(int i = 0; items_num < items_num; ++items_num) {
         ItemStack item2 = items[items_num];
         if (item2 != null && item2.hasItemMeta() && item2.getItemMeta().hasLore() && item2.getItemMeta().getLore().equals(item.getItemMeta().getLore())) {
            return true;
         }
      }

      return false;
   }

   @Deprecated
   public void repairHand_old(Player player) {
      ItemStack item = player.getInventory().getItemInMainHand();
      if (item.getType().getMaxDurability() > 30 && item.getDurability() > 0) {
         item.setDurability((short)(item.getDurability() - 1));
      }
   }

   public void repairHand(Player player) {
      ItemStack item = player.getInventory().getItemInMainHand();
      repair(item);
   }

   public void repair(ItemStack item){
      if(item == null){
         return;
      }
      if(!item.hasItemMeta()){
         return;
      }
      if ( item.getItemMeta() instanceof Damageable) {
         Damageable meta = (Damageable)item.getItemMeta();
         if(meta.hasDamage()) {
            meta.setDamage(meta.getDamage() - 1);
         }
         item.setItemMeta((ItemMeta) meta);
      }
   }

   public void repairAll(Player player, int amount) {
      PlayerInventory inv = player.getInventory();
      ItemStack[] items;
      int items_num = (items = inv.getStorageContents()).length;

      ItemStack item;
      int i;
      for(i = 0; i < items_num; i++) {
         item = items[i];
         repair(item);
      }

      items_num = (items = inv.getArmorContents()).length;

      for(i = 0; i < items_num; i++) {
         item = items[i];
         repair(item);
      }

      item = inv.getItemInOffHand();
      repair(item);

   }
}

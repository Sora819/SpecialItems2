package me.sora819.core;

import me.sora819.SpecialItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Recipes {
   public SpecialItems main;
   public SoraCore core;

   public Recipes() {
      main = SpecialItems.getInstance();
      core = main.getCore();
      main.getServer().resetRecipes();
   }

   public void addRecipe(Plugin plugin, ShapedRecipe recipe) {
      plugin.getServer().addRecipe(recipe);
   }

   public void addRecipe(Plugin plugin, ShapelessRecipe recipe) {
      plugin.getServer().addRecipe(recipe);
   }

   public ShapedRecipe getSpecialItemRecipe(Plugin specialitems, String id, ItemStack item) {
      ShapedRecipe recipe = new ShapedRecipe(NamespacedKey.minecraft(specialitems.getName().toLowerCase() + "." + id), item);
      recipe.shape(
              core.configManager.getList(id + "_recipe").get(0).toString(),
              core.configManager.getList(id + "_recipe").get(1).toString(),
              core.configManager.getList(id + "_recipe").get(2).toString());

      Map<String, Object> ingredients = this.core.configManager.getListKeyValue(id + "_ingredients");
      Iterator var7 = ingredients.entrySet().iterator();

      while(var7.hasNext()) {
         Entry<String, Object> entry = (Entry)var7.next();
         String itemid;
         if (entry.getValue().toString().contains(":")) {
            itemid = entry.getValue().toString().split(":")[0].toUpperCase();
            int itemdata = Integer.parseInt(entry.getValue().toString().split(":")[1]);


            ItemStack recipeitem = new ItemStack(Material.getMaterial(itemid), 1, (short)itemdata);
            recipe.setIngredient(((String)entry.getKey()).charAt(0), new MaterialData(recipeitem.getType(), recipeitem.getData().getData()));
         } else {
            itemid = entry.getValue().toString().toUpperCase();

            //main.getServer().getConsoleSender().sendMessage(recipe.getIngredientMap().toString());
            recipe.setIngredient((entry.getKey().toString()).charAt(0), Material.getMaterial(itemid));
         }
      }

      if (recipe.getIngredientMap().containsKey(' ')) {
         recipe.setIngredient(' ', Material.AIR);
      }

      return recipe;
   }
}

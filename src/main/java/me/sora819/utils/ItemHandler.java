package me.sora819.utils;

import me.sora819.SpecialItems;
import me.sora819.core.SoraCore;
import me.sora819.items.*;

public class ItemHandler {
    public FeedingTalisman feeding;
    public RepairTalisman repair;
    public FlyingBoots flying;
    public HealingTalisman healing;
    public ExplosivePickaxe explosive;
    public LuckyCharm luckycharm;
    public MobCatcher mobcatcher;

    SpecialItems main;
    SoraCore core;

    public ItemHandler(){
        main = SpecialItems.getInstance();
        core = main.getCore();
    }

    public void Initialize(){

        if(itemEnabled("feeding")) {
            feeding = new FeedingTalisman();
            if(itemCraftEnabled("feeding")) {
                feeding.addRecipes();
            }
            feeding.startCounter();
        }

        if(itemEnabled("repair")) {
            repair = new RepairTalisman();
            if(itemCraftEnabled("repair")) {
                repair.addRecipes();
            }
            repair.startCounter();
        }

        if(itemEnabled("flying")) {
            flying = new FlyingBoots();
            if(itemCraftEnabled("flying")) {
                flying.addRecipes();
            }
        }

        if(itemEnabled("healing")) {
            healing = new HealingTalisman();
            if(itemCraftEnabled("healing")) {
                healing.addRecipes();
            }
            healing.startCounter();
        }

        if(itemEnabled("explosive")) {
            explosive = new ExplosivePickaxe();
            if(itemCraftEnabled("explosive")) {
                explosive.addRecipes();
            }
        }
        if(itemEnabled("luckycharm")) {
            luckycharm = new LuckyCharm();
            if(itemCraftEnabled("luckycharm")) {
                luckycharm.addRecipes();
            }
        }
        if(itemEnabled("mobcatcher")) {
            mobcatcher = new MobCatcher();
            if(itemCraftEnabled("mobcatcher")) {
                //mobcatcher.addRecipes();
            }
        }
    }


    public boolean itemEnabled(String id) {
        if(Boolean.parseBoolean(core.configManager.getString(id+"_enabled"))) {
            return true;
        }else{

            return false;
        }
    }

    public boolean itemCraftEnabled(String id) {
        if(Boolean.parseBoolean(core.configManager.getString(id+"_craftenabled"))) {
            return true;
        }else{
            return false;
        }
    }
}

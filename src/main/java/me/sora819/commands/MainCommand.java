package me.sora819.commands;

import me.sora819.SpecialItems;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    SpecialItems main;
    public MainCommand(){
        main = SpecialItems.getInstance();
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("repairtalisman") && main.itemh.itemEnabled("repair")) {
            if(!sender.hasPermission("main.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("repair_give_msg")));
                main.getServer().getPlayer(sender.getName()).getInventory().addItem(main.itemh.repair.getItem());
                return true;
            }else if(args.length == 1){
                try {
                    Player ply = main.getServer().getPlayer(args[0]);

                    String msg = main.core.configManager.getString("repair_give_other_msg");
                    if(msg.contains("%player%")) {
                        msg = msg.replace("%player%", ply.getName());
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    ply.getInventory().addItem(main.itemh.repair.getItem());
                    return true;
                }catch(Exception ex) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("player_not_found_msg")));
                    return true;
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("feedingtalisman") && main.itemh.itemEnabled("feeding")) {
            if(!sender.hasPermission("main.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("feeding_give_msg")));
                main.getServer().getPlayer(sender.getName()).getInventory().addItem(main.itemh.feeding.getItem());
                return true;
            }else if(args.length == 1){
                try {
                    Player ply = main.getServer().getPlayer(args[0]);

                    String msg = main.core.configManager.getString("feeding_give_other_msg");
                    if(msg.contains("%player%")) {
                        msg = msg.replace("%player%", ply.getName());
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    main.getServer().getPlayer(ply.getName()).getInventory().addItem(main.itemh.feeding.getItem());
                    return true;
                }catch(Exception ex) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("player_not_found_msg")));
                    return true;
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("flyingboots") && main.itemh.itemEnabled("flying")) {
            if(!sender.hasPermission("main.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("flying_give_msg")));
                main.getServer().getPlayer(sender.getName()).getInventory().addItem(main.itemh.flying.getItem());
                return true;
            }else if(args.length == 1){
                try {
                    Player ply = main.getServer().getPlayer(args[0]);

                    String msg = main.core.configManager.getString("flying_give_other_msg");
                    if(msg.contains("%player%")) {
                        msg = msg.replace("%player%", ply.getName());
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    ply.getInventory().addItem(main.itemh.flying.getItem());
                    return true;
                }catch(Exception ex) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("player_not_found_msg")));
                    return true;
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("healingtalisman") && main.itemh.itemEnabled("healing")) {
            if(!sender.hasPermission("main.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("healing_give_msg")));
                main.getServer().getPlayer(sender.getName()).getInventory().addItem(main.itemh.healing.getItem());
                return true;
            }else if(args.length == 1){
                try {
                    Player ply = main.getServer().getPlayer(args[0]);

                    String msg = main.core.configManager.getString("healing_give_other_msg");
                    if(msg.contains("%player%")) {
                        msg = msg.replace("%player%", ply.getName());
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    ply.getInventory().addItem(main.itemh.healing.getItem());
                    return true;
                }catch(Exception ex) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("player_not_found_msg")));
                    return true;
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("explosivepickaxe") && main.itemh.itemEnabled("explosive")) {
            if(!sender.hasPermission("main.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("explosive_give_msg")));
                main.getServer().getPlayer(sender.getName()).getInventory().addItem(main.itemh.explosive.getItem());
                return true;
            }else if(args.length == 1){
                try {
                    Player ply = main.getServer().getPlayer(args[0]);
                    String msg = main.core.configManager.getString("explosive_give_other_msg");
                    if(msg.contains("%player%")) {
                        msg = msg.replace("%player%", ply.getName());
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    ply.getInventory().addItem(main.itemh.explosive.getItem());
                    return true;
                }catch(Exception ex) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("player_not_found_msg")));
                    return true;
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("luckycharm") && main.itemh.itemEnabled("luckycharm")) {
            if(!sender.hasPermission("main.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("luckycharm_give_msg")));
                main.getServer().getPlayer(sender.getName()).getInventory().addItem(main.itemh.luckycharm.getItem());
                return true;
            }else if(args.length == 1){
                try {
                    Player ply = main.getServer().getPlayer(args[0]);
                    String msg = main.core.configManager.getString("luckycharm_give_other_msg");
                    if(msg.contains("%player%")) {
                        msg = msg.replace("%player%", ply.getName());
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                    ply.getInventory().addItem(main.itemh.luckycharm.getItem());
                    return true;
                }catch(Exception ex) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("player_not_found_msg")));
                    return true;
                }
            }
        }


        if(cmd.getName().equalsIgnoreCase("setpickaxeradius") && main.itemh.itemEnabled("explosive")) {
            if(!sender.hasPermission("main.setpickaxeradius")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length != 1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("wrong_cmd")));
            }else {
                if(Integer.parseInt(args[0]) < 1) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("setexplosiveradius_small_msg")));
                    return true;
                }else if(Integer.parseInt(args[0]) > main.core.configManager.getInt("max_explosion_radius")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("setexplosiveradius_large_msg").replace("%max_radius%", main.core.configManager.getString("max_explosion_radius"))));
                    return true;
                }
                if(main.itemh.explosive.setItemRadius(main.getServer().getPlayer(sender.getName()).getInventory().getItemInMainHand(), Integer.parseInt(args[0]), (Player) sender)) {

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("setexplosiveradius_msg").replace("%radius%", args[0])));

                }
                return true;
            }
        }

        if(cmd.getName().equalsIgnoreCase("specialitems")) {
            if(!sender.hasPermission("specialitems.admin")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                return true;
            }
            if(args.length!=1) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("wrong_cmd")));
            }else if(args[0].equalsIgnoreCase("reload")){
                if(!sender.hasPermission("specialitems.admin")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                    return true;
                }
                main.core.configManager.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("reload_cmd")));
            }else if(args[0].equalsIgnoreCase("help")){
                if(!sender.hasPermission("specialitems.admin")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',main.core.configManager.getString("permission_message")));
                    return true;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_header")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_help")));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_reload")));
                if(main.itemh.itemEnabled("repair")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_repairtalisman")));
                }
                if(main.itemh.itemEnabled("feeding")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_feedingtalisman")));
                }
                if(main.itemh.itemEnabled("flying")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_flyingboots")));
                }
                if(main.itemh.itemEnabled("healing")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_healingtalisman")));
                }
                if(main.itemh.itemEnabled("explosive")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_explosivepickaxe")));
                }
                if(main.itemh.itemEnabled("explosive")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_setpickaxeradius")));
                }
                if(main.itemh.itemEnabled("luckycharm")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("help_luckycharm")));
                }
            }else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', main.core.configManager.getString("wrong_cmd")));
            }
        }

        return false;
    }
}

package me.sora819;

import me.sora819.commands.MainCommand;
import me.sora819.core.SoraCore;
import me.sora819.utils.ItemHandler;
import me.sora819.utils.NBTHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class SpecialItems extends JavaPlugin{
	public SoraCore core;
	public ItemHandler itemh;
	public NBTHandler nbth;

	private static SpecialItems INSTANCE;

	public void message(String text){
		this.getServer().getConsoleSender().sendMessage(text);
	}

	public SoraCore getCore(){
		return core;
	}

	public void onEnable() {
		INSTANCE = this;
		core = new SoraCore();
		core.setupCore();
		nbth = new NBTHandler();
		itemh = new ItemHandler();
		itemh.Initialize();

		this.getCommand("specialitems").setExecutor(new MainCommand());
		this.getCommand("repairtalisman").setExecutor(new MainCommand());
		this.getCommand("feedingtalisman").setExecutor(new MainCommand());
		this.getCommand("flyingboots").setExecutor(new MainCommand());
		this.getCommand("healingtalisman").setExecutor(new MainCommand());
		this.getCommand("explosivepickaxe").setExecutor(new MainCommand());
		this.getCommand("luckycharm").setExecutor(new MainCommand());
		this.getCommand("setpickaxeradius").setExecutor(new MainCommand());
	}

	public static SpecialItems getInstance(){
		return INSTANCE;
	}
}

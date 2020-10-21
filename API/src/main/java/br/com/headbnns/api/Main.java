package br.com.headbnns.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.headbnns.api.account.commands.GroupCommand;
import br.com.headbnns.api.account.listeners.ListenerUpdateAccount;

public class Main extends JavaPlugin{

    public Plugin plugin;	
    public API api;
	
	public void onEnable() {
		this.plugin = this;
		
		this.api = new API();
		Bukkit.getPluginManager().registerEvents(new ListenerUpdateAccount(this.api), this);
		getCommand("group").setExecutor(new GroupCommand(this.api));
				
	}
	public void onDisable() {
		
		this.plugin=null;
		
		this.api.account_global.clear();
		
		
	}
	
}

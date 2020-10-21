package br.com.headbnns.api.account.listeners;

import java.sql.SQLException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import br.com.headbnns.api.API;

public class ListenerUpdateAccount implements Listener {

	private API api;

	public ListenerUpdateAccount(API api) {
		this.api=api;
	}

	@EventHandler
	void playerJoinEvent(PlayerJoinEvent event) {
		event.setJoinMessage(null);
		makeAccount(event);
	}
	@EventHandler
	void playerQuitEvent(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		saveAccount(event);
	}
	@EventHandler
	void playerChatEvent(AsyncPlayerChatEvent event) {
		getAPI().getAccount().formatPlayerChat(event);
	}

	public void makeAccount(PlayerJoinEvent event) {
		try {
			if(this.getAPI().getAccount().getAccount(event.getPlayer().getName())==null) {
				this.getAPI().getAccount().makeAccount(event.getPlayer().getName());
			}else {
				this.getAPI().getAccount().expireGroupFromAccount(event.getPlayer().getName());
				this.getAPI().getAccount().modifyAccount(event.getPlayer().getName());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void saveAccount(PlayerQuitEvent event) {
		try {
			this.getAPI().getAccount().expireGroupFromAccount(event.getPlayer().getName());
			this.getAPI().getAccount().modifyAccount(event.getPlayer().getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public API getAPI() {
		return api;
	}

}

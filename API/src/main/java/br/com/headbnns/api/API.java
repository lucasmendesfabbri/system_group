package br.com.headbnns.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import br.com.headbnns.api.account.Account;
import br.com.headbnns.api.account.AccountManager;
import br.com.headbnns.api.account.enums.Group;
import br.com.headbnns.api.account.enums.League;
import br.com.headbnns.api.account.listeners.ListenerUpdateAccount;
import br.com.headbnns.api.database.Database;

public class API {

	public HashMap<String, Account> account_global = new HashMap<String, Account>();
	public List<Account> accounts_global_count = new ArrayList<Account>();;
	
	public AccountManager account;
	public Connection connection;
	public Database database;
	
	public API() {
		this.loadInformations();
	}
	
	public void loadInformations() {
		
		for(Entity entity : Bukkit.getWorld("world").getEntities())entity.remove();
		Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
		Bukkit.getWorld("world").setAutoSave(false);
		
		this.database=new Database();
		this.connection=this.database.connection;
		this.createTables();
		
		this.account=new AccountManager(this);
		
	}
	
	public void createTables() {
		
		try {
			PreparedStatement ps = this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `accounts_global` (`player_name` VARCHAR(20), `player_league` VARCHAR(20), `primary_group` VARCHAR(20),`secondary_group` VARCHAR(20),`player_xp` INT(20), `primary_Login` BIGINT(30), `primary_group_expire` BIGINT(30), `secondary_group_expire` BIGINT(30))");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public AccountManager getAccount() {
		return account;
	}
	public Connection getConnection() {
		return connection;
	}
	
}

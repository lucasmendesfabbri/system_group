package br.com.headbnns.api.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.bukkit.Bukkit;

public class Database {

	public Connection connection;

	public Database(){
		
		try {
			
			if(connection!=null)this.connection.close();
			
			String url = "jdbc:mysql://sql10.freemysqlhosting.net:3306/sql10371765";
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, "sql10371765", "R6k7z8jwse");
			Bukkit.getLogger().info("{CONNECTED-MYSQL}");
			
		}catch (Exception e) {
			Bukkit.getLogger().info("{ERROR-MYSQL}");
		}
	}

	public void closeConnection() {
		try {
			
			this.connection.close();
			System.out.println("{CLOSED-MYSQL}");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

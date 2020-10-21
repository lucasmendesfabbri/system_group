package br.com.headbnns.api.account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import br.com.headbnns.api.API;
import br.com.headbnns.api.account.enums.Group;
import br.com.headbnns.api.account.enums.League;

public class AccountManager {

	private API api;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public AccountManager(API api) {

		this.api=api;

		try {

			ps = getAPI().getConnection().prepareStatement("SELECT * FROM `accounts_global`");
			rs = ps.executeQuery(); 
			while(rs.next()) {getAPI().account_global.put(rs.getString("player_name"), new Account(rs.getString("player_name"), League.valueOf(rs.getString("player_league")), Group.valueOf(rs.getString("primary_group")), Group.valueOf(rs.getString("secondary_group")), rs.getInt("player_xp"), rs.getLong("primary_login"), rs.getLong("primary_group_expire"), rs.getLong("secondary_group_expire")));}
			ps.close();
			rs.close();

		}catch(SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Account getAccount(String name) {
		if(getAPI().account_global.containsKey(name))return getAPI().account_global.get(name);
		return null;
	}

	public void modifyAccount(String name) throws SQLException {

		Account account = this.getAccount(name)!=null?this.getAccount(name):new Account(name, League.UNRANKED, Group.MEMBRO, Group.MEMBRO, 0, System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis());

		if(getAPI().account_global.containsKey(account.getName())) {
			ps = getAPI().getConnection().prepareStatement("UPDATE `accounts_global` SET `player_league`='"+account.getLeague()+"',`primary_group`='"+account.getPrimaryGroup()+"',`secondary_group`='"+account.getSecondaryGroup()+"',`player_xp`='"+account.getXP()+"',`primary_Login`='"+account.getPrimaryLogin()+"',`primary_group_expire`='"+account.getPrimaryExpire()+"',`secondary_group_expire`='"+account.getSecondaryExpire()+"' WHERE `player_name`='"+name+"'");
			ps.executeUpdate();
			ps.close();
			getAPI().account_global.remove(name);getAPI().account_global.put(name, account);
			Bukkit.getLogger().info("{PLAYER MODIFY}");
		}
	}

	public void makeAccount(String name) throws SQLException	{

		Account account;
		if(!getAPI().account_global.containsKey(name)) {
			account = new Account(name, League.UNRANKED, Group.MEMBRO, Group.MEMBRO, 0, System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis());
			ps = getAPI().getConnection().prepareStatement("INSERT INTO `accounts_global`(`player_name`, `player_league`, `primary_group`, `secondary_group`, `player_xp`, `primary_Login`, `primary_group_expire`, `secondary_group_expire`) VALUES ('"+name+"','"+account.getLeague()+"','"+account.getPrimaryGroup()+"','"+account.getSecondaryGroup()+"','"+account.getXP()+"','"+account.getPrimaryLogin()+"','"+account.getPrimaryExpire()+"','"+account.getSecondaryExpire()+"');");
			ps.executeUpdate();
			ps.close();
			getAPI().account_global.put(name, account);
			Bukkit.getLogger().info("{PLAYER CREATED}");
		}

	}

	public void deleteAccount(String name) throws SQLException {

		Account account = this.getAccount(name)!=null?this.getAccount(name):null;
		if(account==null)return;

		ps = getAPI().getConnection().prepareStatement("");
		ps.executeUpdate(); ps.close();
		this.getAPI().account_global.remove(name);
		Bukkit.getLogger().info("{PLAYER DELETE}");

	}

	public void formatPlayerChat(AsyncPlayerChatEvent event) {
		String format = this.getAccount(event.getPlayer().getName()).getPrimaryGroup()!=Group.MEMBRO?"" + this.getAccount(event.getPlayer().getName()).getPrimaryGroup().getTag()+" " + event.getPlayer().getName() + " §7("+ this.getAccount(event.getPlayer().getName()).getLeague().getTag()+"§7): §f" +event.getMessage()+"":"§7"+event.getPlayer().getName()+" §7("+ this.getAccount(event.getPlayer().getName()).getLeague().getTag()+"§7): " + event.getMessage();
		event.setFormat(format);
	}

	public void expireGroupFromAccount(String name) {

		long TIME_LEFT_PRIMARY = this.getAccount(name).getPrimaryExpire();
		long TIME_LEFT_SECONDARY = this.getAccount(name).getSecondaryExpire();

		Account account = this.getAccount(name);
		Player player = Bukkit.getPlayer(name);

		if(TIME_LEFT_PRIMARY != -1 && TIME_LEFT_PRIMARY == System.currentTimeMillis() && TIME_LEFT_PRIMARY > System.currentTimeMillis() && account.getPrimaryGroup() != Group.MEMBRO) {

			account.setPrimaryGroup(account.getSecondaryGroup());
			account.setPrimaryGroupExpire(account.getSecondaryExpire());
			account.setSecondaryGroup(Group.MEMBRO);
			account.setSecondaryGroupExpire(-1);
			try {
				this.modifyAccount(name);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			player.sendMessage("§cGrupo expirado.");

		}
		if(TIME_LEFT_SECONDARY != -1 && TIME_LEFT_SECONDARY == System.currentTimeMillis() && TIME_LEFT_SECONDARY > System.currentTimeMillis() && account.getSecondaryGroup() != Group.MEMBRO) {

			account.setSecondaryGroup(Group.MEMBRO);
			account.setSecondaryGroupExpire(-1);
			try {
				this.modifyAccount(name);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			player.sendMessage("§cGrupo expirado.");

		}
	}

	public String formatDateGroup(String name, long timer) {

		Account account = this.getAccount(name)!=null?this.getAccount(name):null;
		if(account == null)return "Conta não registrada";
		SimpleDateFormat format = new SimpleDateFormat("§eEEEE, dd/MMMMMMMM/yyyy | HH:mm:ss ");
		String date = format.format(new Date(timer));
		if(timer!=-1)return date;
		return "§ePermamente";
	}

	public String timerLeftToExpireGroup(String name) {

		Account account = this.getAccount(name)!=null?this.getAccount(name):null;
		if(account==null)return "Conta não encontrada.";
		String primaryString = "§fGrupo primário: §a"+ account.getPrimaryGroup().getPrefix()+" §7- §e" + formatDateGroup(name, account.getPrimaryExpire()) + "!";
		String groupSecondary = account.getSecondaryGroup()!=Group.MEMBRO?""+account.getSecondaryGroup().getPrefix()+" §7- §e" + formatDateGroup(name, account.getSecondaryExpire()) + "!":"§aNenhum";
		return primaryString + "\n§fGrupo secundário: §a"+groupSecondary;

	}

	public API getAPI() {
		return api;
	}


}

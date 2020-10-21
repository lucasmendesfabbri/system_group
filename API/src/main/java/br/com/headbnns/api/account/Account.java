package br.com.headbnns.api.account;

import br.com.headbnns.api.account.enums.Group;
import br.com.headbnns.api.account.enums.League;

public class Account {

	private String playerName;
	private League playerLeague;
	private int playerXP;
	private Group primaryGroup, secondaryGroup;
	private long playerPrimaryLogin, primaryGroupExpire, secondaryGroupExpire;
	
	public Account(String playerName, League league, Group primary, Group secondary, int xp, long primaryLogin, long primaryExpire, long secondaryExpire){
		
		this.playerName=playerName;
		this.playerLeague=league;
		this.primaryGroup=primary;
		this.secondaryGroup=secondary;
		this.playerXP=xp;
		this.playerPrimaryLogin=primaryLogin;
		this.primaryGroupExpire=primaryExpire;
		this.secondaryGroupExpire=secondaryExpire;
	}
	public void makeAccount(String name) {
		new Account(name, League.UNRANKED, Group.MEMBRO, Group.MEMBRO, 0, System.currentTimeMillis(), System.currentTimeMillis(), System.currentTimeMillis());
	}
	
	public League getLeague() {
		return playerLeague;
	}
	public String getName() {
		return playerName;
	}
	public long getPrimaryLogin() {
		return playerPrimaryLogin;
	}
	public int getXP() {
		return playerXP;
	}
	public Group getPrimaryGroup() {
		return primaryGroup;
	}
	public long getPrimaryExpire() {
		return primaryGroupExpire;
	}
	public Group getSecondaryGroup() {
		return secondaryGroup;
	}
	public long getSecondaryExpire() {
		return secondaryGroupExpire;
	}
	public void setPlayerLeague(League playerLeague) {
		this.playerLeague = playerLeague;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public void setPlayerPrimaryLogin(long playerPrimaryLogin) {
		this.playerPrimaryLogin = playerPrimaryLogin;
	}
	public void setPlayerXP(int playerXP) {
		this.playerXP = playerXP;
	}
	public void setPrimaryGroup(Group primaryGroup) {
		this.primaryGroup = primaryGroup;
	}
	public void setPrimaryGroupExpire(long primaryGroupExpire) {
		this.primaryGroupExpire = primaryGroupExpire;
	}
	public void setSecondaryGroup(Group secondaryGroup) {
		this.secondaryGroup = secondaryGroup;
	}
	public void setSecondaryGroupExpire(long secondaryGroupExpire) {
		this.secondaryGroupExpire = secondaryGroupExpire;
	}
	
}

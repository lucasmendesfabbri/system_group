package br.com.headbnns.api.account.enums;

public enum League {

	UNRANKED("Unranked", "§fUnranked", "§f-"),
	MASTER("Master", "§cMaster", "§c-");
	
	private String name, prefix, tag;
	
	private League(String name, String prefix, String tag) {
		this.name=name;
		this.prefix=prefix;
		this.tag=tag;
	}
	
	public String getName() {
		return name;
	}
	public String getTag() {
		return tag;
	}
	public String getPrefix() {
		return prefix;
	}
	public boolean verifyLeague(String tagLeague) {
		if(this.getName().equalsIgnoreCase(tagLeague))return true;
		return false;
	}
	
}

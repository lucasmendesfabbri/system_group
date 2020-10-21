package br.com.headbnns.api.account.enums;

public enum Group {

	DONO("Dono", "§4Dono", "§4§lDONO §4", 1),
	BETA("Beta", "§1Beta", "§1§lBETA §1", 10),
	MEMBRO("Membro", "§7Membro", "§7", 20);
	
	private String name, prefix, tag;
	private int id;
	
	private Group(String name, String prefix, String tag, int id) {
		this.name=name;
		this.prefix=prefix;
		this.tag=tag;
		this.id=id;
	}
	
	public String getName() {
		return name;
	}
	public String getPrefix() {
		return prefix;
	}
	public String getTag() {
		return tag;
	}
	public int getId() {
		return id;
	}
	public boolean verifyTag(String tagName) {
		if(this.getName().equalsIgnoreCase(tagName))return true;
		return false;
	}
}

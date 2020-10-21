package br.com.headbnns.api.account.commands;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.headbnns.api.API;
import br.com.headbnns.api.account.Account;
import br.com.headbnns.api.account.enums.Group;

public class GroupCommand implements CommandExecutor{

	private API api;

	public GroupCommand(API api) {
		this.api=api;
	}

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(command.getName().equalsIgnoreCase("group")) {

			if(args.length == 0) {
				if(sender instanceof Player) {
					Player player = (Player)sender;
					Account playerAccount = getAPI().getAccount().getAccount(player.getName());
					if(playerAccount.getPrimaryGroup().getId()>1) {
						sender.sendMessage("§cSem permissão.");
						return true;
					}
					return true;
				}
			}
			if(args.length == 0||args.length > 3||args.length == 1) {
				sender.sendMessage("§cUtilize /group <player> <group> <timer>");
				return true;
			}

			String target = args[0];
			String groupName = args.length == 1 ? "§cUtilize /group <player> <group> <timer>".toUpperCase() : args[1].toUpperCase();

			if(!getAPI().account_global.containsKey(target)) {
				sender.sendMessage("§cO jogador '"+target+"' não foi registrado.");
				return true;
			}

			if(!verifyStringToGroup(groupName)) {
				sender.sendMessage("§cO Grupo '"+groupName.toUpperCase()+"' não foi encontrado.");
				return true;
			}

			Account targetAccount = getAPI().getAccount().getAccount(target);

			if(args.length == 2) {
				targetAccount.setSecondaryGroup(targetAccount.getPrimaryGroup());
				targetAccount.setSecondaryGroupExpire(targetAccount.getPrimaryExpire());
				targetAccount.setPrimaryGroup(Group.valueOf(groupName));
				targetAccount.setPrimaryGroupExpire(-1);
				try {
					getAPI().getAccount().modifyAccount(target);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sender.sendMessage("§aO Grupo "+targetAccount.getPrimaryGroup().getPrefix()+"§a foi adicionado à conta" + target+"§a!");

				return true;
			}

			String stringTimer = args.length == 2?"-1":args[2].substring(args[2].length() - 1, args[2].length());
			int stringDuration = Integer.valueOf(args[2].substring(0, args[2].length() - 1));
			long duration;

			switch (stringTimer) {
			case "s":
				duration = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(stringDuration*1, TimeUnit.SECONDS);
				targetAccount.setSecondaryGroup(targetAccount.getPrimaryGroup());
				targetAccount.setSecondaryGroupExpire(targetAccount.getPrimaryExpire());
				targetAccount.setPrimaryGroup(Group.valueOf(groupName));
				targetAccount.setPrimaryGroupExpire(duration);
				try {
					getAPI().getAccount().modifyAccount(target);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sender.sendMessage("§aO Grupo "+targetAccount.getPrimaryGroup().getPrefix()+"§a foi adicionado à conta" + target+"§a!");
				sender.sendMessage("§aO cargo irá expirar em: " + this.getAPI().getAccount().formatDateGroup(target, stringDuration-System.currentTimeMillis()));
				return true;

			case "d":
				duration = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(stringDuration*1, TimeUnit.DAYS);
				targetAccount.setSecondaryGroup(targetAccount.getPrimaryGroup());
				targetAccount.setSecondaryGroupExpire(targetAccount.getPrimaryExpire());
				targetAccount.setPrimaryGroup(Group.valueOf(groupName));
				targetAccount.setPrimaryGroupExpire(duration);
				try {
					getAPI().getAccount().modifyAccount(target);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sender.sendMessage("§aO Grupo "+targetAccount.getPrimaryGroup().getPrefix()+"§a foi adicionado à conta" + target+"§a!");
				sender.sendMessage("§aO cargo irá expirar em: " + this.getAPI().getAccount().formatDateGroup(target, stringDuration-System.currentTimeMillis()));
				return true;

			case "h":
				duration = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(stringDuration*1, TimeUnit.HOURS);
				targetAccount.setSecondaryGroup(targetAccount.getPrimaryGroup());
				targetAccount.setSecondaryGroupExpire(targetAccount.getPrimaryExpire());
				targetAccount.setPrimaryGroup(Group.valueOf(groupName));
				targetAccount.setPrimaryGroupExpire(duration);
				try {
					getAPI().getAccount().modifyAccount(target);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				sender.sendMessage("§aO Grupo "+targetAccount.getPrimaryGroup().getPrefix()+"§a foi adicionado à conta" + target+"§a!");
				sender.sendMessage("§aO cargo irá expirar em: " + this.getAPI().getAccount().formatDateGroup(target, stringDuration-System.currentTimeMillis()));
				return true;

			default:
				break;
			}

			return true;

		}

		return true;
	}

	public boolean verifyStringToGroup(String group) {
		for(Group ranks:Group.values()) if(ranks.getName().equalsIgnoreCase(group))return true;
		return false;
	}

	public API getAPI() {
		return api;
	}

}

package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.afforess.minecartmaniacore.utils.StringUtils;

public class MinecartManiaBaseCommand extends MinecartManiaCommand{

	@Override
	public boolean isPlayerOnly() {
		return false;
	}

	@Override
	public CommandType getCommand() {
		return CommandType.MM;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		@SuppressWarnings("unused")
		int page;
		if (args.length == 0) {
			page = 1;
		}
		else {
			try {
				page = Integer.valueOf(StringUtils.getNumber(args[0])); 
			}
			catch (NumberFormatException e) {
				page = 1;
			}
		}
		
		
		return false;
	}

}

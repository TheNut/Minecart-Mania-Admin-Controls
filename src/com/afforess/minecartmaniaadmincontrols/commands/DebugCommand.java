package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.debug.DebugMode;

public class DebugCommand extends MinecartManiaCommand{

	@Override
	public boolean isPlayerOnly() {
		return false;
	}

	@Override
	public CommandType getCommand() {
		return CommandType.Debug;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,	String label, String[] args) {
		if (args.length == 1) {
			DebugMode mode = null;
			for (DebugMode m : DebugMode.values()) {
				if (args[1].equalsIgnoreCase(m.name())) {
					mode = m;
					break;
				}
			}
			if (mode != null) {
				MinecartManiaCore.log.switchDebugMode(mode);
				sender.sendMessage(LocaleParser.getTextKey("AdminControlsDebugMode", mode.name()));
				
			}
		}
		else {
			String modes = "";
			for (DebugMode m : DebugMode.values()) {
				modes += m.name().toLowerCase() + ", ";
			}
			modes.substring(0, modes.length() - 3);
			sender.sendMessage(LocaleParser.getTextKey("AdminControlsValidDebugModes", modes));
		}
		return true;
	}


}

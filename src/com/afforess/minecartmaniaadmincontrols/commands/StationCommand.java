package com.afforess.minecartmaniaadmincontrols.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.entity.MinecartManiaPlayer;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class StationCommand extends MinecartManiaCommand {

	@Override
	public boolean isPlayerOnly() {
		return true;
	}

	@Override
	public CommandType getCommand() {
		return CommandType.St;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player)sender;
		if (args.length < 1) {
			return false;
		}
		MinecartManiaPlayer mmp = MinecartManiaWorld.getMinecartManiaPlayer(player);
		String station = args[0];
		mmp.setLastStation(station);
		if (args.length > 2) {
			if (args[2].contains("s")) {
				mmp.setDataValue("Reset Station Data", Boolean.TRUE);
			}
		}
		else {
			mmp.setDataValue("Reset Station Data", null);
		}
		mmp.sendMessage(LocaleParser.getTextKey("AdminControlsStation", station));
		return true;
	}

}

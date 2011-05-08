package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.utils.StringUtils;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class ClearAllCartsCommand extends MinecartManiaCommand implements ClearMinecartCommand {

	@Override
	public boolean isPlayerOnly() {
		return false;
	}

	@Override
	public CommandType getCommand() {
		return CommandType.ClearAllCarts;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		int distance = -1;
		boolean delete = false;
		if (args.length > 0) {
			for (String arg : args) {
				try {
					distance = Integer.parseInt(StringUtils.getNumber(args[0]));
				}
				catch (Exception e) {
					delete = delete || arg.contains("-d");
				}
			}
		}
		
		Vector location = null;
		if (sender instanceof Player) {
			location = ((Player)sender).getLocation().toVector();
		}
		else {
			distance = -1;
		}
		MinecartManiaWorld.pruneMinecarts();
		
		int count = 0;
		ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
		for (MinecartManiaMinecart minecart : minecartList) {
			if (!minecart.isDead() && !minecart.minecart.isDead()) {
				if (distance < 0 || (minecart.minecart.getLocation().toVector().distance(location) < distance)) {
					if (shouldRemoveMinecart(minecart)) {
						count++;
						minecart.kill(!delete);
					}
				}
			}
		}
		sender.sendMessage(LocaleParser.getTextKey("AdminControlsMinecartsRemoved", count));
		return true;
	}

	@Override
	public boolean shouldRemoveMinecart(MinecartManiaMinecart minecart) {
		return true;
	}

}

package com.afforess.minecartmaniaadmincontrols.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;

public class MinecartInfoCommand extends MinecartManiaCommand{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		MinecartManiaWorld.pruneMinecarts();
		ArrayList<MinecartManiaMinecart> minecarts = MinecartManiaWorld.getMinecartManiaMinecartList();
		int total = minecarts.size();
		int empty, passenger, powered, storage, moving, unmoving;
		empty = passenger = powered = storage = moving = unmoving = 0;
		HashMap<String, Integer> owners = new HashMap<String, Integer>();
		for (MinecartManiaMinecart minecart : minecarts) {
			if (minecart.isStandardMinecart()) {
				if (minecart.hasPlayerPassenger())  {
					passenger++;
				}
				else {
					empty++;
				}
			}
			else if (minecart.isPoweredMinecart()) {
				powered++;
			}
			else if (minecart.isStorageMinecart()) {
				storage++;
			}
			if (minecart.isMoving()) {
				moving++;
			}
			else {
				unmoving++;
			}
			if (minecart.getOwner() instanceof Player) {
				String name = ((Player)minecart.getOwner()).getName();
				if (owners.containsKey(name)) {
					owners.put(name, owners.get(name) + 1);
				}
				else {
					owners.put(name, 1);
				}
			}
		}
		
		String most = null;
		int mostCarts = 0;
		Iterator<Entry<String, Integer>> i = owners.entrySet().iterator();
		while(i.hasNext()) {
			Entry<String, Integer> e = i.next();
			if (most == null || e.getValue() > mostCarts) {
				most = e.getKey();
				mostCarts = e.getValue();
			}
		}
		
		sender.sendMessage("----------------Minecart Mania Information---------------");
		sender.sendMessage("Total Minecarts: " + total);
		sender.sendMessage("Empty Minecarts: " + empty);
		sender.sendMessage("Occupied Minecarts: " + passenger);
		sender.sendMessage("Powered Minecarts: " + powered);
		sender.sendMessage("Storage Minecarts: " + storage);
		sender.sendMessage("Moving Minecarts: " + moving);
		sender.sendMessage("Stalled Minecarts: " + unmoving);
		if (most != null) {
			sender.sendMessage("Largest Minecart Owner: " + most + " (" + owners.get(most) + ")");
		}
		
		return true;
	}

	@Override
	public boolean isPlayerOnly() {
		return false;
	}

	@Override
	public CommandType getCommand() {
		return CommandType.Info;
	}

}

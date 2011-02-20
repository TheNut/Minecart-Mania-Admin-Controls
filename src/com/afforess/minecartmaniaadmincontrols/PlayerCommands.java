package com.afforess.minecartmaniaadmincontrols;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.MinecartManiaPlayer;
import com.afforess.minecartmaniacore.MinecartManiaWorld;
import com.afforess.minecartmaniacore.utils.ChatUtils;
import com.afforess.minecartmaniacore.utils.DirectionUtils;
import com.afforess.minecartmaniacore.utils.StringUtils;

public class PlayerCommands {

	public static boolean doMomentumCommand(Player player, String command) {
		if (command.toLowerCase().contains("/momentum")) {
			if (player.getVehicle() instanceof Minecart) {
				MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)player.getVehicle());
				ChatUtils.sendMultilineMessage(player, String.format("Momentum X: %.2f [NEWLINE] Momentum Y: %.2f [NEWLINE] Momentum Z: %.2f", (float)minecart.getMotionX(), (float)minecart.getMotionY(), (float)minecart.getMotionZ()));
			}
			else {
				ChatUtils.sendMultilineWarning(player, "You can only have momentum inside a minecart.");
			}
			return true;
		}
		return false;
	}
	
	public static boolean doStationCommand(Player player, String command) {
		String split[] = command.toLowerCase().split(" ");
		if (split[0].contains("/st") && split.length > 1) {
			//if (player.getVehicle() instanceof Minecart) {
				MinecartManiaPlayer mmp = MinecartManiaWorld.getMinecartManiaPlayer(player);
				String station = split[1];
				mmp.setLastStation(station);
				if (split.length > 2) {
					if (split[2].contains("s")) {
						mmp.setDataValue("Reset Station Data", Boolean.TRUE);
					}
				}
				else {
					mmp.setDataValue("Reset Station Data", null);
				}
				ChatUtils.sendMultilineMessage(player, "Station Type set to:  " + station, ChatColor.GREEN.toString());
				return true;
			//}
		}
		return false;
	}
	
	public static boolean getConfigurationKey(Player player, String command) {
		if (command.toLowerCase().contains("/getconfigkey")) {
			String key;
			//key is wrapped by []
			int start = command.indexOf('[');
			int end = command.indexOf(']');
			if (start > -1 && end > -1) {
				key = command.substring(start+1, end);
				Object value = MinecartManiaWorld.getConfigurationValue(key);
				ChatUtils.sendMultilineMessage(player, "The value of '" + key + "' is '" + value + "'", ChatColor.GREEN.toString());
			}
			else {
				ChatUtils.sendMultilineWarning(player, "Proper Usage: '/getconfigkey [key]'. [NEWLINE] Hint: The key should be surrounded by brackets.");
			}
			return true;
		}
		return false;
	}
	
	public static boolean listConfigurationKeys(Player player, String command) {
		if (command.contains("/listconfigkeys")) {
			ConcurrentHashMap<String, Object> configKeys= MinecartManiaWorld.getConfiguration();
			 
			 int page;
			 if (StringUtils.getNumber(command).isEmpty()) {
				 page = 1;
			 }
			 else {
				 try {
					 page = Integer.valueOf(StringUtils.getNumber(command)); 
				 }
				 catch (NumberFormatException e) {
					 ChatUtils.sendMultilineWarning(player, "Invalid page number");
					 return true;
				 }
			 }
			 
			 int curLine = 0;
			 int maxPages = (int) Math.ceil(((double)configKeys.size() / 6));
			 
			 if (page <= maxPages) {
				 
				 player.sendMessage(ChatColor.YELLOW.toString() + "Minecart Mania Configuration Values (Page " + page + " of " + maxPages + ")");
				 
				 Iterator<Entry<String, Object>> i = configKeys.entrySet().iterator();
				 while (i.hasNext()) {
					 Entry<String, Object> e = i.next();
					 if (curLine / 6 == (page - 1)) {
						 player.sendMessage("Key: " + ChatColor.GREEN + e.getKey() + ChatColor.WHITE + " Value: " + ChatColor.GREEN + e.getValue());
					 } 
					 curLine++;
				 }
			 }
			 else {
				 ChatUtils.sendMultilineWarning(player, "There is no page " + page);
			 }
			return true;
		}
		return false;
	}

	public static boolean doCompass(Player player, String command) {
		if (command.contains("/trucompass")) {
			DirectionUtils.CompassDirection facingDir = DirectionUtils.getDirectionFromRotation((player.getLocation().getYaw()- 90.0F));
			player.sendMessage(ChatColor.YELLOW+facingDir.toString());
			return true;
		}
		return false;
	}
}

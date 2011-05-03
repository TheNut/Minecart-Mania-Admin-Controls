package com.afforess.minecartmaniaadmincontrols;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.minecart.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.entity.MinecartManiaPlayer;
import com.afforess.minecartmaniacore.world.MinecartManiaWorld;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.utils.DirectionUtils;
import com.afforess.minecartmaniacore.utils.StringUtils;

public class PlayerCommands {

	public static boolean doMomentumCommand(Player player, String command) {
		if (command.toLowerCase().contains("/momentum")) {
			if (player.getVehicle() instanceof Minecart) {
				MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)player.getVehicle());
				player.sendMessage(LocaleParser.getTextKey("AdminControlsMomentum", (float)minecart.getMotionX(), (float)minecart.getMotionY(), (float)minecart.getMotionZ()));
			}
			else {
				player.sendMessage(LocaleParser.getTextKey("AdminControlsMomentumInvalid"));
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
				mmp.sendMessage(LocaleParser.getTextKey("AdminControlsStation", station));
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
				player.sendMessage(LocaleParser.getTextKey("AdminControlsConfigKey", key, value));
			}
			else {
				player.sendMessage(LocaleParser.getTextKey("AdminControlsConfigKeyUsage"));
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
					player.sendMessage(LocaleParser.getTextKey("AdminControlsListConfigKeyError"));
					return true;
				}
			}
			
			int curLine = 0;
			int maxPages = (int) Math.ceil(((double)configKeys.size() / 6));
			
			if (page <= maxPages) {
				player.sendMessage(LocaleParser.getTextKey("AdminControlsListConfigKeyPageHeader", page, maxPages));
		
				Iterator<Entry<String, Object>> i = configKeys.entrySet().iterator();
				while (i.hasNext()) {
					Entry<String, Object> e = i.next();
					if (curLine / 6 == (page - 1)) {
						player.sendMessage(LocaleParser.getTextKey("AdminControlsListConfigKey", e.getKey(), e.getValue()));
					} 
					curLine++;
				}
			}
			else {
				player.sendMessage(LocaleParser.getTextKey("AdminControlsListConfigKeyPage", page));
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
	
	public static boolean doThrottle(Player player, String command) {
		if (command.contains("/throttle")) {
			if (player.getVehicle() != null && player.getVehicle() instanceof Minecart) {
				try {
					String num = StringUtils.getNumber(command);
					double throttle = Double.valueOf(num);
					if (throttle <= 500D && throttle >= 0.0D) {
						MinecartManiaMinecart minecart = MinecartManiaWorld.getMinecartManiaMinecart((Minecart)player.getVehicle());
						minecart.setDataValue("throttle", new Double(throttle));
						if (throttle <= 100D) 
							player.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleSet"));
						else
							player.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleSetOverdrive"));
					}
					else {
						player.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleUsage"));
					}
				}
				catch (Exception e) {
					player.sendMessage(LocaleParser.getTextKey("AdminControlsThrottleUsage"));
				}
				return true;
			}
		}
		return false;
	}
}

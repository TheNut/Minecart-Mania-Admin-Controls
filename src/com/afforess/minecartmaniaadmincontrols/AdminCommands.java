package com.afforess.minecartmaniaadmincontrols;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.MinecartManiaMinecart;
import com.afforess.minecartmaniacore.MinecartManiaWorld;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.debug.DebugMode;
import com.afforess.minecartmaniacore.utils.StringUtils;

public class AdminCommands {
	public static boolean doDebugMode(Player player, String command) {
		String split[] = command.split(" ");
		if (command.toLowerCase().contains("/debug")) {
			if (split.length == 3) {
				DebugMode mode = null;
				for (DebugMode m : DebugMode.values()) {
					if (split[2].equalsIgnoreCase(m.name())) {
						mode = m;
						break;
					}
				}
				if (mode != null) {
					MinecartManiaCore.log.switchDebugMode(mode);
					player.sendMessage(LocaleParser.getTextKey("AdminControlsDebugMode", mode.name()));
					
				}
			}
			else {
				String modes = "";
				for (DebugMode m : DebugMode.values()) {
					modes += m.name().toLowerCase() + ", ";
				}
				modes.substring(0, modes.length() - 3);
				player.sendMessage(LocaleParser.getTextKey("AdminControlsDebugMode", modes));
			}
			return true;
		}
		return false;
	}
	public static boolean doEjectPlayer(Player player, String command) {
		String split[] = command.split(" ");
		if (command.toLowerCase().contains("/eject")) {
			if (split.length == 2) {
				List<Player> matchingPlayers = MinecartManiaAdminControls.server.matchPlayer(split[1]);
				if (!matchingPlayers.isEmpty()) {
					for (Player p : matchingPlayers) {
						if (p.isInsideVehicle())
							p.leaveVehicle();
					}
				}
				else {
					player.sendMessage(LocaleParser.getTextKey("AdminControlsNoPlayerFound"));
				}
			}
			else {
				player.sendMessage(LocaleParser.getTextKey("AdminControlsEjectUsage"));
			}
			return true;
		}
		return false;
	}
	
	public static boolean doPermEjectPlayer(Player player, String command) {
		String split[] = command.split(" ");
		if (command.toLowerCase().contains("/permeject")) {
			if (split.length == 2) {
				List<Player> matchingPlayers = MinecartManiaAdminControls.server.matchPlayer(split[1]);
				if (!matchingPlayers.isEmpty()) {
					for (Player p : matchingPlayers) {
						if (p.isInsideVehicle())
							p.leaveVehicle();
						
						VehicleControl.toggleBlockFromEntering(p);
					}
				}
				else {
					player.sendMessage(LocaleParser.getTextKey("AdminControlsNoPlayerFound"));
				}
			}
			else {
				player.sendMessage(LocaleParser.getTextKey("AdminControlsPermEjectUsage"));
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Clears all carts of the given type
	 * @param player who issued the command
	 * @param command
	 * @return returns true if the command was processed
	 */
	public static boolean doKillCarts(Player player, String command) {
		int type = -1; //the type of minecarts to clear. -1 - invalid. 0 - empty. 1 - powered. 2 - storage. 3 - occupied. 4 - all.
		int distance = -1;
		if (command.toLowerCase().contains("/clearemptycarts")) {
			type = 0;
		}
		else if (command.toLowerCase().contains("/clearpoweredcarts")) {
			type = 1;
		}
		else if (command.toLowerCase().contains("/clearstoragecarts")) {
			type = 2;
		}
		else if (command.toLowerCase().contains("/clearoccupiedcarts")) {
			type = 3;
		}
		else if (command.toLowerCase().contains("/clearallcarts")) {
			type = 4;
		}
		
		String[] split = command.split(" ");
		if (split.length > 1) {
			try {
				distance = Integer.parseInt(StringUtils.getNumber(split[1]));
			}
			catch (Exception e) {}
		}
		
		boolean delete = command.contains("-d");
		
		if (type != -1) {
			int count = 0;
			
			ArrayList<MinecartManiaMinecart> minecartList = MinecartManiaWorld.getMinecartManiaMinecartList();
			for (MinecartManiaMinecart minecart : minecartList) {
				if (!minecart.isDead() && !minecart.minecart.isDead()) {
					if (distance < 0 || (minecart.minecart.getLocation().toVector().distance(player.getLocation().toVector()) < distance)) {
						switch(type) {
							case 0:	
								if (minecart.isStandardMinecart() && minecart.minecart.getPassenger() == null) {
									minecart.kill(!delete);
									count++;
								}
								break;
							case 1:
								if (minecart.isPoweredMinecart()) {
									minecart.kill(!delete);
									count++;
								}
								break;
							case 2:
								if (minecart.isStorageMinecart()) {
									minecart.kill(!delete);
									count++;
								}
								break;
							case 3:
								if (minecart.isStandardMinecart() && minecart.minecart.getPassenger() != null) {
									minecart.kill(!delete);
									count++;
								}
								break;
							case 4:
								minecart.kill(!delete);
								count++;
								break;
						}
					}
				}
			}
			player.sendMessage(LocaleParser.getTextKey("AdminControlsMinecartsRemoved", count));
			return true;
		}
		return false;
	}
	
	public static boolean setConfigurationKey(Player player, String command) {
		if (command.toLowerCase().contains("/setconfigkey")) {
			String key;
			String value;
			//key is wrapped by []
			int start = command.indexOf('[');
			int end = command.indexOf(']');
			if (start > -1 && end > -1) {
				key = command.substring(start+1, end);
				//value is wrapped by []
				start = command.indexOf('[', end+1);
				end = command.indexOf(']', end+1);
				if (start > -1 && end > -1) {
					value = command.substring(start+1, end);
					if (value.equalsIgnoreCase("null")) {
						MinecartManiaWorld.setConfigurationValue(key, null);
						player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, "null"));
					}
					else {
						if (value.equalsIgnoreCase("true")) {
							player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, value));
							MinecartManiaWorld.setConfigurationValue(key, Boolean.TRUE);
						}
						else if (value.equalsIgnoreCase("false")) {
							player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, value));
							MinecartManiaWorld.setConfigurationValue(key, Boolean.FALSE);
						}
						else if (StringUtils.containsLetters(value)) {
							//save it as a string
							player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, value));
							MinecartManiaWorld.setConfigurationValue(key, value);
						}
						else {
							try {
								value = StringUtils.getNumber(value);
								Double d = Double.valueOf(value);
								if (d.intValue() == d) {
									//try to save it as an int
									player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, String.valueOf(d.intValue())));
									MinecartManiaWorld.setConfigurationValue(key, new Integer(d.intValue()));
								}
								else {
									//save it as a double
									player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKey", key, String.valueOf(d.doubleValue())));
									MinecartManiaWorld.setConfigurationValue(key, d);
								}
							}
							catch (Exception e) {
								player.sendMessage(LocaleParser.getTextKey("AdminControlsInvalidValue"));
							}
						}
					}
				}
				else {
					player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKeyUsage"));
				}
			}
			else {
				player.sendMessage(LocaleParser.getTextKey("AdminControlsSetConfigKeyUsage"));
			}
			return true;
		}
		return false;
	}
}

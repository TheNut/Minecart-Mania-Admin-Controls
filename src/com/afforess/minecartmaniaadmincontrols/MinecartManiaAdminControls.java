package com.afforess.minecartmaniaadmincontrols;

import java.util.Arrays;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmaniaadmincontrols.commands.Command;
import com.afforess.minecartmaniaadmincontrols.commands.CommandType;
import com.afforess.minecartmaniaadmincontrols.permissions.PermissionBlockListener;
import com.afforess.minecartmaniaadmincontrols.permissions.PermissionManager;
import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;

public class MinecartManiaAdminControls extends JavaPlugin{

	public static MinecartManiaLogger log = MinecartManiaLogger.getInstance();
	public static Server server;
	public static PluginDescriptionFile description;
	private static final AdminControlsPlayerListener playerListener = new AdminControlsPlayerListener();
	private static final VehicleControl vehicleListener = new VehicleControl();
	private static final MinecartTimer timer = new MinecartTimer();
	public static PermissionManager permissions;
	private static final PermissionBlockListener permissionListener = new PermissionBlockListener();
	

	public void onEnable(){
		server = this.getServer();
		description = this.getDescription();
		permissions = new PermissionManager(getServer());
		MinecartManiaConfigurationParser.read(description.getName() + "Configuration.xml", MinecartManiaCore.dataDirectory, new AdminControlsSettingParser());
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_ENTER, vehicleListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, timer, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.SIGN_CHANGE, permissionListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, permissionListener, Priority.Normal, this);
		if (permissions.isHasPermissions()) {
			//getServer().getPluginManager().registerEvent(Event.Type.SIGN_CHANGE, permissionListener, Priority.Normal, this);
		}
		
		log.info( description.getName() + " version " + description.getVersion() + " is enabled!" );
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args) {
		String commandPrefix;
		if (commandLabel.equals("mm")) {
			if (args == null || args.length == 0) {
				return false;
			}
			commandPrefix = args[0];
			if (args.length > 1) {
				args = Arrays.copyOfRange(args, 1, args.length);
			}
			else {
				String[] temp = {};
				args = temp;
			}
		}
		else {
			commandPrefix = commandLabel;
		}
		commandPrefix = commandPrefix.toLowerCase();
		
		System.out.println("Command: " + commandPrefix);
		Command command = getMinecartManiaCommand(commandPrefix);
		if (command == null) {
			return false;
		}
		if (command.canExecuteCommand(sender)) {
			command.onCommand(sender, cmd, commandPrefix, args);
		}
		else {
			sender.sendMessage(LocaleParser.getTextKey("LackPermissionForCommand"));
		}

	
		return true;
	}
	
	public static Command getMinecartManiaCommand(String command) {
		for (CommandType c : CommandType.values()){
			if (c.toString().equalsIgnoreCase(command)) {
				return c.getCommand();
			}
		}
		return null;
	}
}

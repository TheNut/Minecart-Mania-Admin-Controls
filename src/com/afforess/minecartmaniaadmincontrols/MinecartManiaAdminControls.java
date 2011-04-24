package com.afforess.minecartmaniaadmincontrols;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmanaiaadmincontrols.permissions.PermissionBlockListener;
import com.afforess.minecartmanaiaadmincontrols.permissions.PermissionManager;
import com.afforess.minecartmaniaadmincontrols.commands.Commands;
import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.LocaleParser;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;
import com.afforess.minecartmaniacore.debug.MinecartManiaLogger;
import com.afforess.minecartmaniacore.utils.StringUtils;

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
	
	//TODO split this into seperate classes, e.g command executers?
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		//if (!commandLabel.equals("mm")) {
		//	return false;
		//}
		
		Player player = (Player)sender;
		String command;
		String commandPrefix;
		if (commandLabel.equals("mm")) {
			commandPrefix = args[0];
			command = "/" + StringUtils.join(args, 0);
		}
		else {
			commandPrefix = commandLabel;
			command = "/" + commandLabel + " " + StringUtils.join(args, 0);
		}
		commandPrefix = commandPrefix.toLowerCase();
		
		if (Commands.isAdminCommand(commandPrefix)) {
			if (!permissions.canUseAdminCommand(player, commandPrefix)) {
				player.sendMessage(LocaleParser.getTextKey("LackPermissionForCommand"));
				return true;
			}
		}
		else {
			if (!permissions.canUseCommand(player, commandPrefix)) {
				player.sendMessage(LocaleParser.getTextKey("LackPermissionForCommand"));
				return true;
			}
		}

		boolean action = false;
		
		if (player.isOp()) {
			if (!action) {
				action = AdminCommands.doEjectPlayer(player, command);
			}
			
			if (!action) {
				action = AdminCommands.doPermEjectPlayer(player, command);
			}
			
			if (!action) {
				action = AdminCommands.doKillCarts(player, command);
			}

			if (!action) {
				action = AdminCommands.setConfigurationKey(player, command);
			}
			
			if (!action) {
				action = AdminCommands.doDebugMode(player, command);
			}
		}
		
		
		if (!action) {
			action = PlayerCommands.getConfigurationKey(player, command);
		}
		
		if (!action) {
			action = PlayerCommands.doStationCommand(player, command);
		}
		
		if (!action) {
			action = PlayerCommands.listConfigurationKeys(player, command);
		}
		
		if (!action) {
			action = PlayerCommands.doMomentumCommand(player, command);
		}
		
		if (!action) {
			action = PlayerCommands.doCompass(player, command);
		}
		
		if (!action) {
			action = PlayerCommands.doThrottle(player, command);
		}
		return action;
	}
}

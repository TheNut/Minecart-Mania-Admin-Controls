package com.afforess.minecartmaniaadmincontrols;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.afforess.minecartmaniacore.MinecartManiaCore;
import com.afforess.minecartmaniacore.config.MinecartManiaConfigurationParser;
import com.afforess.minecartmaniacore.utils.StringUtils;

public class MinecartManiaAdminControls extends JavaPlugin{

	public static Logger log;
	public static Server server;
	public static PluginDescriptionFile description;
	private static final AdminControlsPlayerListener playerListener = new AdminControlsPlayerListener();
	private static final VehicleControl vehicleListener = new VehicleControl();
	private static final MinecartTimer timer = new MinecartTimer();
	

	public void onEnable(){
		server = this.getServer();
		description = this.getDescription();
		log = Logger.getLogger("Minecraft");
		PluginDescriptionFile pdfFile = this.getDescription();
		
		Plugin MinecartMania = server.getPluginManager().getPlugin("Minecart Mania Core");
		
		if (MinecartMania == null) {
			log.severe(pdfFile.getName() + " requires Minecart Mania Core to function!");
			log.severe(pdfFile.getName() + " is disabled!");
			this.setEnabled(false);
		}
		else {
			MinecartManiaConfigurationParser.read(description.getName().replaceAll(" ","") + "Configuration.xml", MinecartManiaCore.dataDirectory, SettingList.config);
	        getServer().getPluginManager().registerEvent(Event.Type.PLAYER_ITEM, playerListener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.VEHICLE_ENTER, vehicleListener, Priority.Normal, this);
	        getServer().getPluginManager().registerEvent(Event.Type.CUSTOM_EVENT, timer, Priority.Normal, this);
	        
	        
	        log.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
		}
	}
	
	public void onDisable(){
		
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player)sender;
		String command = "/" + commandLabel + " " + StringUtils.join(args, 0);
		
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
				action = AdminCommands.reloadConfig(player, command);
			}
			
			if (!action) {
				action = AdminCommands.setConfigurationKey(player, command);
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
